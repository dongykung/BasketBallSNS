package com.dkproject.presentation.ui.screen.home.shop

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dkproject.presentation.ui.theme.BasketballSNSTheme


@Composable
fun ShopCard(
    modifier: Modifier = Modifier,
    profileImage: String,
    name: String,
    type: String,
    address: String,
    price: Int,
) {
    Surface(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()

            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = profileImage,
                        contentScale = ContentScale.Crop
                    ), contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(75.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = name,
                        style = TextStyle(fontSize = 18.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = address,
                        maxLines = 1,
                        color = Color.Gray,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(width = 1.dp, color = Color.LightGray)
                    ) {
                        Text(
                            modifier = Modifier.padding(6.dp),
                            text = type
                        )
                    }
                    Text(
                        text = price.toString(),
                        style = TextStyle(
                            color = Color.Blue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun ShopCardPreview() {
    BasketballSNSTheme {
        ShopCard(
            profileImage = "",
            name = "dongkyung",
            address = "경기도의왕시 내손1동",
            type = "농구공",
            price = 10000
        )
    }
}