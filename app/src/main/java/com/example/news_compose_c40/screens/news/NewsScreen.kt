package com.example.news_compose_c40.screens.news
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.news_compose_c40.R
import com.example.news_compose_c40.screens.news_details.NewsDetailsViewModel
import com.example.news_compose_c40.widgets.ErrorDialog
import com.example.news_compose_c40.widgets.NewsList
import com.example.news_compose_c40.widgets.NewsTopAppBar
import com.example.news_compose_c40.widgets.SourcesTabRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.Serializable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news_compose_c40.screens.search.SearchScreen
import com.example.news_compose_c40.widgets.NewsList
import com.example.news_compose_c40.widgets.SourcesTabRow

@Serializable
data class NewsRoute(val categoryID: String,
                     val categoryName: Int)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    vm: NewsViewModel = hiltViewModel(),
    categoryID: String,
    categoryName: Int,
    scope: CoroutineScope,
    drawerState: DrawerState,
    onNewsClick: (String, String) -> Unit,
    onSearchClick: () -> Unit
) {
    var isSearching by remember { mutableStateOf(false) } // State to toggle search bar visibility
    val searchQuery by vm.searchQuery  // To observe the search query

    val foundError = vm.uiMessage.errorMessage != null || vm.uiMessage.errorMessageId != null
    if (foundError && vm.isErrorDialogVisible) {
        val errorMessage = getErrorMessage(vm.uiMessage.errorMessage, vm.uiMessage.errorMessageId)
        ErrorDialog(
            errorMessage,
            onRetry = vm.uiMessage.retryAction,
            onDismiss = { vm.hideErrorDialog() }
        )
    }

    Scaffold(topBar = {
        NewsTopAppBar(
            shouldDisplaySearchIcon = true,
            shouldDisplayMenuIcon = true,
            titleResourceId = categoryName,
            scope = scope,
            drawerState = drawerState,
            onSearchClick = {
                isSearching = true // Show the search bar when the search icon is clicked
            }
        )
    }) { paddingValues ->

        LaunchedEffect(key1 = null) {
            vm.getSources(categoryID)
        }

        // Fetch the news for the first source when the sources are available
        LaunchedEffect(key1 = vm.sourcesList) {
            if (!vm.sourcesList.isNullOrEmpty()) {
                vm.getNewsBySource(vm.sourcesList!![0].id)
            }
        }

        Column(
            modifier = Modifier
                .paint(painterResource(id = R.drawable.bg_pattern), contentScale = ContentScale.Crop)
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Show Search Bar if isSearching is true
            if (isSearching) {
                SearchScreen(
                    searchQuery = searchQuery,
                    onQueryChanged = { query -> vm.onSearchQueryChanged(query) },
                    onClose = { isSearching = false } // Hide the search bar
                )
            }

            vm.sourcesList?.let { sources ->
                SourcesTabRow(
                    sourcesList = sources,
                    onTabClicked = { sourceId ->
                        vm.getNewsBySource(sourceId)
                    }
                )
            }

            NewsList(
                newsList = vm.articlesList,
                shouldDisplayNoArticlesFound = vm.uiMessage.shouldDisplayNoArticlesFound,
                loadingState = vm.uiMessage.isLoading,
                onNewsClick = onNewsClick
            )
        }
    }
}

@Composable
fun getErrorMessage(errorMessage: String?, errorMessageId: Int?): String {
    return when {
        !errorMessage.isNullOrEmpty() -> errorMessage
        errorMessageId != null -> stringResource(id = errorMessageId)
        else -> stringResource(id = R.string.something_went_wrong)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewNewsFragmentScreen() {
    NewsScreen(
        categoryName = R.string.business,
        categoryID = "business",
        scope = rememberCoroutineScope(),
        drawerState = rememberDrawerState(
            initialValue = DrawerValue.Closed
        ),
        onSearchClick = {},
        onNewsClick = {_,_->

        }
    )

}
