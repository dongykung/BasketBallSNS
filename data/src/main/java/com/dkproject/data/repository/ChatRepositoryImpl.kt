package com.dkproject.data.repository

import android.util.Log
import com.dkproject.data.model.ChatMessageDTO
import com.dkproject.domain.model.chat.ChatMessage
import com.dkproject.domain.repository.ChatRepository
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) :ChatRepository{
    override suspend fun getChatMessages(roomUid:String) : Flow<ChatMessage> = callbackFlow {
        var ref : DatabaseReference ?= null
        try {
            ref = firebaseDatabase.reference.child("Chat").child(roomUid)
        }catch (e:Throwable){
            close(e)
        }


        val subscription = ref?.
        addChildEventListener(object:ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue(ChatMessageDTO::class.java)?.toDimainModel()
                chatItem?:return
                try {
                    Log.d("ChatRepositoryImpl", chatItem.toString())
                    trySend(chatItem)
                }catch (e:Throwable){

                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
        awaitClose{
            if (subscription != null) {
                ref?.removeEventListener(subscription)
            }
        }
    }

    override suspend fun uploadChat(roomUid: String,message:ChatMessage) :Boolean {
        val messageDTO = ChatMessageDTO(
            chatId = message.chatId,
            message = message.message,
            time = convertMillsSecond(),
            userUid = message.userUid
        )
        try {
            firebaseDatabase.reference.child("Chat").child(roomUid).push().apply {
                messageDTO.chatId = key!!
                setValue(messageDTO).await()
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }
}


fun convertMillsSecond():Long{
    val currentTimeMillis = System.currentTimeMillis()

    // 현재 시간의 밀리초를 "yyyy년MM월dd일HH시mm분" 형태로 포맷팅.
    val dateFormat = SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault())
    val formattedTime = dateFormat.format(currentTimeMillis)

    // 포맷팅된 문자열을 다시 밀리초로 파싱.
    val parsedDate = dateFormat.parse(formattedTime)

    // 다시 밀리초로 변환합니다.
    val parsedTimeMillis = parsedDate?.time ?: 0L
    return parsedTimeMillis

}