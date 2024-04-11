package com.dkproject.presentation.ui.component.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.dkproject.domain.model.UserInfo
import com.dkproject.domain.model.chat.ChatRoom
import com.dkproject.presentation.ui.theme.BasketballSNSTheme
import com.dkproject.presentation.util.displayDateInfo


@Composable
fun ChatListCard(
    modifier: Modifier=Modifier,
    chatRoom: ChatRoom,
    userInfo: UserInfo,
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Image(painter = rememberAsyncImagePainter(model = userInfo.profileImageUrl), contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier= Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp)))
        Spacer(modifier = Modifier.width(15.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = userInfo.nickname)
            Text(text = chatRoom.lastMessage)
        }
        Text(text = displayDateInfo(chatRoom.lastMessageTime))
    }
}



