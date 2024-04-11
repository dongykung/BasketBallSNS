package com.dkproject.presentation.ui.component.shop

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(
    modifier: Modifier=Modifier,
    imageList:List<String>
) {
    val pagerState = rememberPagerState(pageCount = { imageList.size })
    Box(modifier = modifier){
        HorizontalPager(state = pagerState) {index->
            val image = imageList[index]
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(model = image), contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
        Row(modifier=Modifier.align(Alignment.BottomCenter)
            .padding(bottom = 8.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center){
            repeat(pagerState.pageCount){iteration->
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
            }
        }
    }
}