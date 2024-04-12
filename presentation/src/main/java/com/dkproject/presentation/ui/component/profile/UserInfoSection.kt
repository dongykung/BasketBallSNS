package com.dkproject.presentation.ui.component.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dkproject.domain.model.UserInfo
import com.dkproject.presentation.ui.screen.home.profile.UserInfoUiModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserInfoSection(
    modifier: Modifier = Modifier,
    userInfo: UserInfo,
    editProfile:()->Unit,
) {
    Column(modifier = modifier) {
        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(model = userInfo.profileImageUrl),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = userInfo.nickname,
                style = TextStyle(color = Color.Black, fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                editProfile()
            }) {
                Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit")
            }
        }


        Spacer(modifier = Modifier.height(40.dp))
        userInfoDetail("포지션",userInfo.playPosition)
        Spacer(modifier = Modifier.height(20.dp))
        userInfoDetail("스타일",userInfo.playStyle)
        Spacer(modifier = Modifier.height(20.dp))
        userInfoDetail("숙련도",skill=userInfo.userSkill)


    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun userInfoDetail(
    value:String,
    valueList:List<String> = emptyList(),
    skill:String=""
){
    Text(modifier= Modifier
        .padding(horizontal = 12.dp)
        .padding(top = 18.dp),
        text = value,
        style= TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp)
    )
    Surface(modifier= Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier=Modifier.padding(16.dp)) {
            if(valueList.isNotEmpty()) {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    valueList.forEach {
                        PositionComponent(position = it)
                    }
                }
            }
            if(skill.isNotEmpty()){
                PositionComponent(position = skill)
            }
        }
    }
}

