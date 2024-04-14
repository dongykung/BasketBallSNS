package com.dkproject.presentation.ui.component.section

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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


@Composable
fun ApplyGuestsSection(
    modifier: Modifier=Modifier,
    itemList:List<UserInfo>,
    clickGuest:(String)->Unit,
) {
    Text(text = "참가자 목록",
        style = MaterialTheme.typography.titleLarge)

    Surface(modifier= modifier
        .fillMaxWidth().padding(top = 8.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier= Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
           itemList.forEach{
               Row(verticalAlignment = Alignment.CenterVertically,modifier=Modifier.fillMaxWidth().clickable {
                   clickGuest(it.useruid)
               }) {
                   Image(painter = rememberAsyncImagePainter(model = it.profileImageUrl), contentDescription = "",
                       contentScale = ContentScale.Crop,
                       modifier= Modifier
                           .size(55.dp)
                           .clip(CircleShape))
                   Spacer(modifier = Modifier.width(18.dp))
                   Text(text = it.nickname)
               }
           }
        }
    }
}