package com.shapes.watch.presentation.search.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shapes.watch.domain.model.SearchVideoContent
import com.shapes.watch.presentation.home.component.LoadingScreen
import com.shapes.watch.presentation.home.component.Video
import com.shapes.watch.presentation.navigation.Screen
import com.shapes.watch.presentation.search.SearchState
import com.shapes.watch.presentation.search.SearchViewModel
import com.shapes.watch.ui.theme.onSurfaceCarbon
import kotlinx.coroutines.launch

@Composable
fun SearchTopBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Surface {
        Row(
            modifier = Modifier.padding(end = 16.dp, start = 12.dp, top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onCloseClick
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }

                Spacer(modifier = Modifier.width(12.dp))

                SearchTextField(
                    text = text,
                    onTextChange = onTextChange,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            SearchButton(text, onSearchClick)
        }
    }
}

@Composable
private fun SearchButton(text: String, onSearchClick: () -> Unit) {
    IconButton(
        enabled = text.isNotBlank(),
        onClick = onSearchClick
    ) {
        Icon(imageVector = Icons.Default.Search, contentDescription = null)
    }
}

@Composable
private fun SearchTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit
) {
    Box {
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            singleLine = true,
            modifier = modifier,
            textStyle = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onSurface),
            cursorBrush = SolidColor(MaterialTheme.colors.primary)
        )

        if (text.isBlank()) {
            Text(
                text = "Search",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.subtitle1.copy(
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.4f)
                )
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel(), navController: NavHostController) {
    var text by remember { mutableStateOf(String()) }
    val onTextChange: (String) -> Unit = { text = it }
    val scope = rememberCoroutineScope()
    val state = viewModel.state.value
    Scaffold(
        topBar = {
            SearchTopBarContainer(
                text = text,
                onTextChange = onTextChange,
                onCloseClick = {
                    navController.popBackStack()
                },
                onSearchClick = {
                    scope.launch {
                        viewModel.search(text)
                    }
                }
            )
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            when (state) {
                is SearchState.Content -> {
                    SearchScreenContent(state.searchVideoContent, navController)
                }
                SearchState.Empty -> {
                    EmptySearchScreen()
                }
                is SearchState.Error -> TODO()
                SearchState.Loading -> {
                    LoadingScreen()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SearchScreenContent(searchVideoContent: SearchVideoContent, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(searchVideoContent.videos) { video ->
            Video(
                video = video,
                onClick = {
                    navController.navigate(
                        route = Screen.VideoScreen.route + it.toRoute()
                    )
                },
                onCreatorClick = {
                    navController.navigate(
                        route = Screen.CreatorScreen.route + it.toRoute()
                    )
                }
            )
        }
    }
}

@Composable
fun EmptySearchScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Search a video", style = MaterialTheme.typography.h6)
    }
}

@Composable
private fun SearchTopBarContainer(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Column {
        SearchTopBar(
            text = text,
            onTextChange = onTextChange,
            onCloseClick = onCloseClick,
            onSearchClick = onSearchClick
        )

        Divider(color = MaterialTheme.colors.onSurfaceCarbon)
    }
}

//
//@Preview(showBackground = true, heightDp = 640, widthDp = 360)
//@Composable
//fun SearchScreenPreview() {
//    WatchTheme {
//        SearchScreen()
//    }
//}
