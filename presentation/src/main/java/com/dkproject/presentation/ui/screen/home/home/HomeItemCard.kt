package com.dkproject.presentation.ui.screen.home.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dkproject.presentation.R
import com.dkproject.presentation.util.getDayOfWeekFromMillis
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomeItemCard(
    modifier: Modifier = Modifier,
    item: GuestUiModel
) {
    val ddayFormatter = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    val monthTimeFormatter = SimpleDateFormat("MM.dd", Locale.getDefault())
    val timeTimeFormatter = SimpleDateFormat("HH시mm분", Locale.getDefault())
    val dday = ddayFormatter.format(item.date).toInt()-ddayFormatter.format(System.currentTimeMillis()).toInt()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),

    ) {
        Column(
            modifier = Modifier.weight(7f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.detailAddress,
                maxLines = 1,
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium
            )
            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    modifier = Modifier.padding(top = 3.dp),
                    text = monthTimeFormatter.format(item.date)
                )
                Text(text = "(${getDayOfWeekFromMillis(item.date)})")

                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = timeTimeFormatter.format(item.date)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(modifier=Modifier.height(100.dp),
            horizontalAlignment = Alignment.End) {

            Text(modifier=Modifier.padding(top = 6.dp),
                text = "D -$dday",
                style = TextStyle(fontSize = 16.sp, color = Color.Blue)
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Person, contentDescription = "",
                    tint = Color.Gray
                )
                Text(text = stringResource(id = R.string.guestsize, item.guestSize, item.count))
            }
        }

    }
}


@Composable
@Preview(showBackground = true)
fun HomeItemCardPriview() {
    HomeItemCard(
        item = GuestUiModel(
            writeUid = "",
            uid = "",
            title = "포인트가드 2명 구합니다",
            positionList = emptyList(),
            count = 5,
            detailAddress = "경기도 의왕시 내손1동 체육관",
            date = 1713868200000,
            guestSize = 0
        )
    )

}