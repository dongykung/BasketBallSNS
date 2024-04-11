package com.dkproject.data.repository

import android.util.Log
import com.dkproject.data.model.ChatRoomDTO
import com.dkproject.domain.model.chat.ChatRoom
import com.dkproject.domain.repository.ChatRoomRepository
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRoomRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : ChatRoomRepository {
    override suspend fun UploadChatRoom(myUid:String, chatRoom: ChatRoom) : String {
        try {
            var chatRoomUid = ""
            val DatabaseRef =  firebaseDatabase.reference.child("ChatRoom").child(myUid).child(chatRoom.otherUserUid)
            DatabaseRef.get().addOnSuccessListener {
                if(it.value==null){
                    DatabaseRef.setValue(chatRoom)
                    chatRoomUid = chatRoom.chatRoomId
                }else{
                    val update = mapOf(
                        "lastMessage" to chatRoom.lastMessage,
                        "lastMessageTime" to chatRoom.lastMessageTime,
                    )
                    val data = it.getValue(ChatRoomDTO::class.java)
                    chatRoomUid = data?.chatRoomId ?: ""
                    DatabaseRef.updateChildren(update)
                }
            }.await()

            val OtherDatabaseRef=firebaseDatabase.reference.child("ChatRoom").child(chatRoom.otherUserUid).child(myUid)
            val otherChatRoomdata = chatRoom.apply {
                otherUserUid = myUid
            }
            OtherDatabaseRef.get().addOnSuccessListener {
                if(it.value==null){
                    OtherDatabaseRef.setValue(otherChatRoomdata)
                }else{
                    val update = mapOf(
                        "lastMessage" to chatRoom.lastMessage,
                        "lastMessageTime" to chatRoom.lastMessageTime,
                    )
                    OtherDatabaseRef.updateChildren(update)
                }
            }.await()

            return chatRoomUid
        }catch (e:Exception){
            return e.message.toString()
        }
    }

    override suspend fun getChatUidCheck(myUid: String, otherUid: String): String? {
        try {
            val data =firebaseDatabase.reference.child("ChatRoom").child(myUid).child(otherUid).get().await()
            if(data.value==null)
                return null
            else
            {
                val uid = data.getValue(ChatRoomDTO::class.java)
                return uid?.chatRoomId ?:""
            }
        }catch (e:Exception){
            return null
        }
    }

    override suspend fun getChatRooms(userUid: String):Flow<ChatRoom> = callbackFlow {
        var ref : DatabaseReference?= null
        try {
            ref = firebaseDatabase.reference.child("ChatRoom").child(userUid)
        }catch (e:Throwable){
            close(e)
        }

        val subscription = ref?.
        addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatRoom = snapshot.getValue(ChatRoomDTO::class.java)?.toDomainModel()
                chatRoom?:return
                try {
                    Log.d("ChatRepositoryImpl", chatRoom.toString())
                    trySend(chatRoom)
                }catch (e:Throwable){

                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val chatRoom = snapshot.getValue(ChatRoomDTO::class.java)?.toDomainModel()
                chatRoom?:return
                try {
                    Log.d("ChatRepositoryImpl", chatRoom.toString())
                    trySend(chatRoom)
                }catch (e:Throwable){

                }
            }
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


}

