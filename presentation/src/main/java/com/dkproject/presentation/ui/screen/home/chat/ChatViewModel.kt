package com.dkproject.presentation.ui.screen.home.chat

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.model.chat.ChatMessage
import com.dkproject.domain.model.chat.ChatRoom
import com.dkproject.domain.usecase.chat.GetChatItemUseCase
import com.dkproject.domain.usecase.chat.GetChatUidCheckUseCase
import com.dkproject.domain.usecase.chat.GetUpdateChatRoomUseCase
import com.dkproject.domain.usecase.chat.UploadChatUseCase
import com.dkproject.domain.usecase.token.GetTokenUseCase
import com.dkproject.domain.usecase.user.GetUserInfoUseCase
import com.dkproject.presentation.ui.component.showToastMessage
import com.dkproject.presentation.ui.screen.home.home.guest.GuestViewModel
import com.dkproject.presentation.util.Constants
import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val getUpdateChatRoomUseCase: GetUpdateChatRoomUseCase,
    private val uploadChatUseCase: UploadChatUseCase,
    private val getChatUidCheckUseCase: GetChatUidCheckUseCase,
    private val getChatItemUseCase: GetChatItemUseCase,
) : ViewModel() {
    private val _state =
        MutableStateFlow(ChatUiState(UserInfo("", "", "", emptyList(), emptyList(), ""), "", false,
            emptyList()
        ))
    val state: StateFlow<ChatUiState> = _state.asStateFlow()



    fun load(otherUid: String) {
        Log.d("Test1", otherUid)
        viewModelScope.launch {
            val uid = getChatUidCheckUseCase(Constants.myToken, otherUid)
            if (!uid.isNullOrEmpty()) {
                updateexist(true)
                getChatItemUseCase(uid).collect{
                    updateMessage(it)
                }
            } else {
                updateexist(false)
            }
        }
    }

    fun updateMessage(item:ChatMessage){
        val list = (state.value.messages + item).sortedBy { it.time }
        _state.update { it.copy(messages = list) }
    }

    fun updateexist(ex: Boolean) {
        _state.update { it.copy(isEXist = ex) }
    }


    fun getChatUserInfo(uid: String) {
        viewModelScope.launch {
            getUserInfoUseCase(uid).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update { it.copy(userInfo = result.data!!) }
                        Log.d(GuestViewModel.TAG, result.data.toString())
                    }

                    is Resource.Loading -> {
                        Log.d(GuestViewModel.TAG, result.toString())
                    }

                    is Resource.Error -> {
                        Log.d(GuestViewModel.TAG, result.toString())
                    }
                }
            }
        }
    }

    fun updateMyMessage(msg: String) {
        _state.update { it.copy(myMessage = msg) }
    }

    fun sendMessage(otherUid: String, context: Context) {
        viewModelScope.launch {
            val myUid = getTokenUseCase()
            val chatRoomUid = UUID.randomUUID().toString()
            if (myUid != null) {
                val data = ChatRoom(
                    chatRoomId = chatRoomUid,
                    lastMessage = state.value.myMessage,
                    lastMessageTime = System.currentTimeMillis(),
                    otherUserUid = otherUid
                )
                getUpdateChatRoomUseCase(myUid = myUid, chatRoom = data).onSuccess { roomuid ->
                    val message = ChatMessage(
                        chatId = "",
                        message = state.value.myMessage,
                        time = 0,
                        userUid = myUid,
                    )
                    uploadChatUseCase(roomUid = roomuid, message = message).onFailure {
                        showToastMessage(context, "메시지 전송에 실패했습니다")
                    }.onSuccess {
                        updateMyMessage("")
                        if(!state.value.isEXist){
                            getChatItemUseCase(chatRoomUid).collect{
                                updateMessage(it)
                            }
                        }
                    }
                }
            }
        }
    }
}


data class ChatUiState(
    val userInfo: UserInfo,
    val myMessage: String,
    val isEXist: Boolean,
    val messages :List<ChatMessage>
)

data class messageTest(
    var chatId:String="",
    val message:String="",
    val time:Long = System.currentTimeMillis(),
    val userUid:String="",
)
