package com.dkproject.presentation.ui.screen.home.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dkproject.domain.common.Resource
import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.model.chat.ChatRoom
import com.dkproject.domain.usecase.chat.GetChatRoomsUseCase
import com.dkproject.domain.usecase.user.GetUserInfoUseCase
import com.dkproject.presentation.ui.screen.home.home.GuestUiModel
import com.dkproject.presentation.ui.screen.home.home.toUiModel
import com.dkproject.presentation.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val getChatRoomsUseCase: GetChatRoomsUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
):ViewModel() {
    private val _state = MutableStateFlow(ChatRoomUiState(emptyList()))
    val state : StateFlow<ChatRoomUiState> = _state.asStateFlow()

    companion object{
        const val TAG="ChatRoomViewModel"
    }

    init {
        getChatRoom()
    }

   fun getChatRoom(){
       viewModelScope.launch {
           getChatRoomsUseCase(Constants.myToken).collect{chatRoom->
               Log.d("ChatRoomViewModel", chatRoom.toString())
               getUserInfo(chatRoom.otherUserUid,chatRoom)
           }
       }
   }

    fun getUserInfo(userUid:String,chatRoom: ChatRoom){
        viewModelScope.launch {
            getUserInfoUseCase(userUid).collect{userInfo->
                when(userInfo){
                    is Resource.Loading->{
                        Log.d(TAG, "Loading")
                    }
                    is Resource.Error->{
                        Log.d(TAG, "Error")
                    }
                    is Resource.Success->{
                        Log.d(TAG, "Success")
                        updateChatRoom(chatRoom,userInfo.data!!)
                    }
                }
            }
        }
    }


    fun updateChatRoom(chatRoom:ChatRoom,userInfo: UserInfo){
        val existingItemIndex = state.value.chatRoomList.indexOfFirst {
            it.chatRoomInfo.chatRoomId == chatRoom.chatRoomId
        }
        val list :MutableList<chatRoomUi> = state.value.chatRoomList.toMutableList()
        if(existingItemIndex!=-1){
            val data = chatRoomUi(chatRoom,userInfo)
            list[existingItemIndex] = data
        }else{
            val data = chatRoomUi(chatRoomInfo = chatRoom, userInfo = userInfo)
            list.add(data)
        }
        updateData(list)
    }
    
    fun updateData(data:List<chatRoomUi>){
        _state.update { it.copy(chatRoomList = data) }
    }

}

data class chatRoomUi(
    var chatRoomInfo : ChatRoom,
    val userInfo:UserInfo
)

data class ChatRoomUiState(
    val chatRoomList: List<chatRoomUi>
)

