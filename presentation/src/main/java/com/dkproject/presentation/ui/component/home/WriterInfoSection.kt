package com.dkproject.presentation.ui.component.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
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
import com.dkproject.presentation.ui.theme.BasketballSNSTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WriterInfoSection(
    modifier: Modifier = Modifier,
    user: UserInfo,
    profileClick:(String)->Unit
) {
    Column(modifier = modifier
        .padding(top = 8.dp)
        ) {
        Row(modifier= Modifier.fillMaxWidth().clickable { profileClick(user.useruid) },
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = rememberAsyncImagePainter(model = user.profileImageUrl), contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier= Modifier
                    .size(50.dp)
                    .clip(CircleShape))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = user.nickname,
                style = MaterialTheme.typography.titleLarge)
        }
        FlowRow(modifier= Modifier.padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            user.playPosition.forEach{
                AssistChip(onClick = { /*TODO*/ }, label = { Text(text = it)})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWriterInfo(){
    BasketballSNSTheme {
        WriterInfoSection(user = UserInfo("","동경","", listOf("포인트 가드","파워 포워드"), emptyList(),"")){

        }
    }
}