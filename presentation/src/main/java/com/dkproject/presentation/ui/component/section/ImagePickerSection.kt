package com.dkproject.presentation.ui.component.section

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.dkproject.presentation.ui.component.writeshop.GetImageButton
import com.dkproject.presentation.ui.component.writeshop.ImageBox


@Composable
 fun ImagePickerSection(
    selectedImageList: List<String>,
    onAddImage: () -> Unit,
    onRemoveImage: (Uri) -> Unit
) {
    // 이미지 선택 및 삭제 관련 UI 구성
    Row(
        modifier = Modifier
            .padding(top = 12.dp)
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //add imagebutton
        GetImageButton(selectedImageList.size, getImageClick = onAddImage)
        //get image
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(selectedImageList) { uri ->
                ImageBox(imageUrl = uri.toUri(), onCnacel = {
                    onRemoveImage(it)
                })
            }
        }
    }
}