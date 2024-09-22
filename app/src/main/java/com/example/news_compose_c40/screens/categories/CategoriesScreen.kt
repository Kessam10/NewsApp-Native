package com.example.news_compose_c40.screens.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.news_compose_c40.R
import com.example.news_compose_c40.model.Category
import com.example.news_compose_c40.model.categoriesList
import com.example.news_compose_c40.ui.theme.Exo
import com.example.news_compose_c40.ui.theme.Poppins
import com.example.news_compose_c40.ui.theme.textColor
import com.example.news_compose_c40.widgets.NewsTopAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object CategoriesRoute

@Composable
fun CategoriesScreen(viewModel:CategoriesViewModel= viewModel(), scope:CoroutineScope, drawerState:DrawerState, onCategoryClick:(String, Int)->Unit) {

    Scaffold(topBar = {
        NewsTopAppBar(
            shouldDisplaySearchIcon = false,
            shouldDisplayMenuIcon = true,
            titleResourceId = R.string.app_name, scope = scope, drawerState = drawerState
        ) {
            scope.launch {
                drawerState.open()
            }
        }

    }) { paddingValues: PaddingValues ->
        Column(modifier = Modifier
            .paint(painterResource(id = R.drawable.bg_pattern), contentScale = ContentScale.Crop)
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())) {
            Text(
                text = stringResource(R.string.pick_your_category_of_interest),
                fontSize = 22.sp, fontFamily = Poppins, color = textColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 24.dp, bottom = 16.dp, end = 24.dp)
            )
            CategoriesList(viewModel) { id, name ->
                onCategoryClick(id, name)
            }

        }
    }

}

@Composable
private fun CategoriesList(viewModel: CategoriesViewModel,onCategoryClick:(String,Int)->Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),modifier=Modifier.padding(horizontal = 10.dp)) {
        items(viewModel.categories.size) { index ->
            CategoryItem(viewModel.categories[index],onCategoryClick={id,name->onCategoryClick(id,name)})
        }
    }
}

@Composable
fun CategoryItem(category: Category, onCategoryClick:(String, Int)->Unit) {
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier
        .padding(8.dp)
        .clickable { onCategoryClick(category.apiID, category.name) }) {
        Image(painter = painterResource(id = category.image), contentDescription = category.apiID)
        Text(text = stringResource(id = category.name), color = Color.White, fontSize = 22.sp , modifier=Modifier.padding(bottom = 20.dp),fontFamily=Exo)
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewCategoryItem() {
    CategoryItem(categoriesList[0]){ _, _ ->

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCategoriesList() {
    CategoriesList(viewModel()){ _, _ ->

    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewCategoriesFragment() {
    CategoriesScreen(viewModel(), rememberCoroutineScope(), rememberDrawerState(initialValue = DrawerValue.Closed)) { id, name ->

    }
}
