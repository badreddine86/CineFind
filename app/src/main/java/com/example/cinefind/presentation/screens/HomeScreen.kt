package com.example.cinefind.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getDrawable
import com.example.cinefind.BuildConfig
import com.example.cinefind.R
import com.example.cinefind.core.utils.AppConstants
import com.example.cinefind.presentation.models.DrawerItemModel
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val drawerItems = listOf(
        DrawerItemModel(
            stringResource(id = R.string.movies_list),
            AppConstants.MOVIES_SCREEN
        ),
        DrawerItemModel(
            stringResource(id = R.string.parameters),
            AppConstants.PARAMETERS_SCREEN
        ),
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val versionName = BuildConfig.VERSION_NAME
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf(drawerItems[0]) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(colorResource(id = R.color.white))
                    ) {

                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center),
                            painter = rememberDrawablePainter(
                                drawable = getDrawable(
                                    LocalContext.current,
                                    R.drawable.cinema_logo
                                )
                            ),
                            contentDescription = "cinema Logo",
                            contentScale = ContentScale.Crop,
                        )
                        /*Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp)
                        )*/
                    }

                    Spacer(Modifier.height(12.dp))
                    drawerItems.forEach { item ->
                        NavigationDrawerItem(
                            label = { Text(item.title) },
                            selected = currentScreen == item,
                            onClick = {
                                currentScreen = item
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "V$versionName",
                            style =  MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
                        )
                    }
                }
            },
            content = {
                Scaffold(
                    topBar = {
                        Surface(
                            shadowElevation = 4.dp,
                        ) {
                            CenterAlignedTopAppBar(
                                modifier = Modifier
                                    .height(56.dp)
                                    .padding(8.dp)
                                    .wrapContentSize(Alignment.Center),
                                title = {
                                    Text(
                                        currentScreen.title,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(
                                            Icons.Filled.Menu,
                                            contentDescription = null,
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                }
                            )
                        }

                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentScreen.route) {
                            AppConstants.MOVIES_SCREEN -> MovieListScreen()
                            AppConstants.PARAMETERS_SCREEN -> ParametersScreen()
                        }
                    }
                }
            }
        )
    }
}