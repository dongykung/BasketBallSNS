package com.dkproject.presentation.ui.component.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dkproject.domain.model.shop.Articles
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.screen.home.shop.formatNumber
import com.dkproject.presentation.ui.theme.BasketballSNSTheme


@Composable
fun ArticleInfoSection(
    modifier: Modifier=Modifier,
    article: Articles
) {
    Column(modifier=modifier) {
        Row(modifier=Modifier.padding(6.dp),verticalAlignment = Alignment.CenterVertically) {
//            Text(text = stringResource(id = R.string.category))
            Text(modifier=Modifier.weight(3f).padding(4.dp),text = "카테고리",
                style = TextStyle(color = Color.Gray, fontSize = 18.sp)
            )

            Text(modifier=Modifier.weight(7f),text = article.type,
                style = TextStyle(color = Color.Black, fontSize = 20.sp))
        }
        HorizontalDivider(modifier=Modifier.padding(horizontal = 6.dp))
        Row(modifier=Modifier.padding(6.dp),verticalAlignment = Alignment.CenterVertically) {
//            Text(text = stringResource(id = R.string.articlename))
            Text(modifier=Modifier.weight(3f).padding(4.dp),text = "제품명",
                style = TextStyle(color = Color.Gray, fontSize = 18.sp))

            Text(modifier=Modifier.weight(7f),text = article.name,
                style = TextStyle(color = Color.Black, fontSize = 20.sp))
        }
        HorizontalDivider(modifier=Modifier.padding(horizontal = 6.dp))
        Row(modifier=Modifier.padding(6.dp),verticalAlignment = Alignment.CenterVertically) {
//            Text(text = stringResource(id = R.string.price))
            Text(modifier=Modifier.weight(3f).padding(4.dp),text = "가격",
                style = TextStyle(color = Color.Gray, fontSize = 18.sp))

            Text(modifier=Modifier.weight(7f),text = formatNumber(article.price.toLong()) +"원",
                style = TextStyle(color = Color.Black, fontSize = 20.sp))
        }
        HorizontalDivider(modifier=Modifier.padding(horizontal = 6.dp))
        Row(modifier=Modifier.padding(6.dp),verticalAlignment = Alignment.CenterVertically) {
//            Text(text = stringResource(id = R.string.articleexplain))
            Text(modifier=Modifier.weight(3f).padding(4.dp),text = "설명",
                style = TextStyle(color = Color.Gray, fontSize = 18.sp))

            Text(modifier=Modifier.weight(7f),text = article.content,
                style = TextStyle(color = Color.Black, fontSize = 20.sp))
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun ArticleInfoSectionPreview(){
    BasketballSNSTheme {
        ArticleInfoSection(
            modifier=Modifier.fillMaxWidth() ,
            article = Articles("","","나이키 농구공", emptyList(),1000,"농구공"
        ,"아주좋은 농구공 입니다 직거래 원하구여 자세한 설명은 채팅 부탁드릴게요 ㅎㅎ","",0.0,0.0))
    }
}