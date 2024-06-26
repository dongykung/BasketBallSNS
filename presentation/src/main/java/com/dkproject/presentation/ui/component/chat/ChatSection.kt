package com.dkproject.presentation.ui.component.chat

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.updateBounds
import coil.compose.rememberAsyncImagePainter
import com.dkproject.domain.model.chat.ChatMessage
import com.dkproject.presentation.R
import com.dkproject.presentation.util.Constants
import com.dkproject.presentation.util.Keyboard
import com.dkproject.presentation.util.convertiChatTimeMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ChatSection(
    modifier: Modifier = Modifier,
    chatList: List<ChatMessage>,
    otherProfileUrl: String,
    otherNickname: String,
    listState: LazyListState,
    profileClick: (String) -> Unit,


) {


    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(6.dp),
        state = listState,
        reverseLayout = true
    ) {
        items(count = chatList.size,
            key = { index ->
                chatList.get(index).chatId
            }) { index ->

            chatList[index].run {
                val mychat = Constants.myToken == this.userUid
                //내 채팅
                if (mychat) {
                    Log.d("othertime", this.toString())
                    ChatItem(
                        index = index,
                        item = this,
                        otherProfileUrl = otherProfileUrl,
                        ImageVisible = true,
                        mychat = true,
                        otherNickname = otherNickname
                    )

                } else {//상대방 채팅일 때
                    //만약 같은 분안에 보냇을 시 프로필을 그리지 않음
                    if (index > 0 && chatList[index - 1].userUid == chatList[index].userUid && chatList[index - 1].time == chatList[index].time) {
                        ChatItem(
                            index = index,
                            item = this,
                            otherProfileUrl = otherProfileUrl,
                            ImageVisible = false,
                            otherchat = true,
                            otherNickname = otherNickname,
                            profileClick = {
                                profileClick(this.userUid)
                            },
                        )
                    } else { //만약 같은 분이 아닐 시 프로필 사진을 그림
                        Log.d("othertime2", this.toString())
                        ChatItem(
                            index = index,
                            item = this,
                            otherProfileUrl = otherProfileUrl,
                            ImageVisible = true,
                            otherchat = true,
                            otherNickname = otherNickname,
                            profileClick = {
                                profileClick(this.userUid)
                            }
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun ChatItem(
    index: Int,
    otherNickname: String,
    item: ChatMessage,
    otherProfileUrl: String,
    ImageVisible: Boolean,
    mychat: Boolean = false,
    otherchat: Boolean = false,
    profileClick:()->Unit={}
) {
    val bgimg = ContextCompat.getDrawable(
        LocalContext.current, if (mychat)
            R.drawable.theme_chatroom_bubble_me_01_image else R.drawable.theme_chatroom_bubble_you_01_image
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .align(if (mychat) Alignment.End else Alignment.Start)
                .widthIn(min = 10.dp, max = 350.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //상대방 메시지&&이미지를 그림
            if (ImageVisible && otherchat) {
                Image(
                    painter = rememberAsyncImagePainter(model = otherProfileUrl),
                    contentDescription = "ProfileImage",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { profileClick() }
                )
            } else Spacer(modifier = Modifier.width(50.dp))
            Column(modifier = Modifier.padding(start = 4.dp)) {
                if (otherchat && ImageVisible)
                    Text(text = otherNickname)
                Row(verticalAlignment = Alignment.Bottom) {
                    if (mychat) {
                        Text(text = convertiChatTimeMillis(item.time))
                        Text(text = item.message,
                            modifier = Modifier
                                .drawBehind {
                                    bgimg?.updateBounds(
                                        0,
                                        0,
                                        size.width.toInt(),
                                        size.height.toInt()
                                    )
                                    bgimg?.draw(drawContext.canvas.nativeCanvas)
                                }
                                .padding(8.dp))
                    } else {
                        Text(text = item.message,
                            modifier = Modifier
                                .drawBehind {
                                    bgimg?.updateBounds(
                                        0,
                                        0,
                                        size.width.toInt(),
                                        size.height.toInt()
                                    )
                                    bgimg?.draw(drawContext.canvas.nativeCanvas)
                                }
                                .padding(8.dp)
                                .widthIn(min = 10.dp, max = 200.dp))
                        Text(
                            text = convertiChatTimeMillis(item.time),
                            maxLines = 1,
                        )
                    }
                }
            }
        }

    }
}