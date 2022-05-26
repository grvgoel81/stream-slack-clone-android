package io.getstream.slackclone.uidashboard.compose

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import io.getstream.slackclone.chatcore.data.UiLayerChannels
import io.getstream.slackclone.chatcore.extensions.toStreamChannel
import io.getstream.slackclone.commonui.reusable.SlackDragComposableView
import io.getstream.slackclone.commonui.theme.SlackCloneColor
import io.getstream.slackclone.commonui.theme.SlackCloneColorProvider
import io.getstream.slackclone.commonui.theme.SlackCloneSurface
import io.getstream.slackclone.commonui.theme.SlackCloneTheme
import io.getstream.slackclone.commonui.theme.SlackCloneTypography
import io.getstream.slackclone.navigator.ComposeNavigator
import io.getstream.slackclone.navigator.SlackScreen
import io.getstream.slackclone.uichat.chatthread.ChatScreenUI
import io.getstream.slackclone.uichat.chatthread.ChatScreenVM
import io.getstream.slackclone.uidashboard.home.DirectMessagesUI
import io.getstream.slackclone.uidashboard.home.HomeScreenUI
import io.getstream.slackclone.uidashboard.home.MentionsReactionsUI
import io.getstream.slackclone.uidashboard.home.SearchMessagesUI
import io.getstream.slackclone.uidashboard.home.UserProfileUI

@Composable
fun DashboardUI(
  composeNavigator: ComposeNavigator,
  dashboardVM: DashboardVM = hiltViewModel()
) {
  val scaffoldState = rememberScaffoldState()
  val dashboardNavController = rememberNavController()

  SlackCloneTheme {
    DashboardScreenRegular(scaffoldState, dashboardNavController, composeNavigator, dashboardVM)
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun DashboardScreenRegular(
  scaffoldState: ScaffoldState,
  dashboardNavController: NavHostController,
  composeNavigator: ComposeNavigator,
  dashboardVM: DashboardVM,
  viewModel: ChatScreenVM = hiltViewModel()
) {
  val keyboardController = LocalSoftwareKeyboardController.current
  val lastChannel by dashboardVM.selectedChatChannel.collectAsState()

  var isLeftNavOpen by remember { mutableStateOf(false) }
  val isChatViewClosed by dashboardVM.isChatViewClosed.collectAsState()
  val screenWidth = LocalConfiguration.current.screenWidthDp.dp
  val sideNavWidth = screenWidth * 0.8f
  val sideNavPxValue = with(LocalDensity.current) { sideNavWidth.toPx() }
  val screenWidthPxValue = with(LocalDensity.current) { screenWidth.toPx() }

  BackHandler(enabled = !isChatViewClosed) {
    if (!isChatViewClosed) {
      dashboardVM.isChatViewClosed.value = true
    }
  }

  SideEffect {
    lastChannel?.let {
      viewModel.createChannel(it)
    }
    if (isChatViewClosed) {
      keyboardController?.hide()
    }
  }

  SlackDragComposableView(
    isLeftNavOpen = isLeftNavOpen,
    isChatViewClosed = checkChatViewClosed(lastChannel, isChatViewClosed),
    mainScreenOffset = sideNavPxValue,
    chatScreenOffset = screenWidthPxValue,
    onOpenCloseLeftView = {
      isLeftNavOpen = it
    },
    onOpenCloseRightView = {
      dashboardVM.isChatViewClosed.value = it
    },
    leftViewComposable = { sideNavModifier ->
      SideNavigation(
        modifier = sideNavModifier.width(sideNavWidth),
        dashboardVM = dashboardVM,
        composeNavigator = composeNavigator
      )
    },
    rightViewComposable = { chatViewModifier ->
      lastChannel?.let { slackChannel ->
        ChatScreenUI(
          modifier = chatViewModifier,
          slackChannel = slackChannel.toStreamChannel(),
          onBackClick = { dashboardVM.isChatViewClosed.value = true },
        )
      }
    }
  ) { mainViewModifier ->
    DashboardScaffold(
      isChatViewOpen = isChatViewClosed.not(),
      isLeftNavOpen = isLeftNavOpen,
      scaffoldState = scaffoldState,
      dashboardNavController = dashboardNavController,
      modifier = mainViewModifier,
      appBarIconClick = { isLeftNavOpen = isLeftNavOpen.not() },
      onItemClick = {
        dashboardVM.selectedChatChannel.value = it
        dashboardVM.isChatViewClosed.value = false
      },
      composeNavigator = composeNavigator
    )
  }
}

private fun checkChatViewClosed(
  lastChannel: UiLayerChannels.SlackChannel?,
  isChatViewClosed: Boolean
) = lastChannel == null || isChatViewClosed

@Composable
private fun DashboardScaffold(
  isChatViewOpen: Boolean,
  isLeftNavOpen: Boolean,
  scaffoldState: ScaffoldState,
  dashboardNavController: NavHostController,
  modifier: Modifier,
  appBarIconClick: () -> Unit,
  onItemClick: (UiLayerChannels.SlackChannel) -> Unit,
  composeNavigator: ComposeNavigator,
) {
  Box(modifier) {
    Scaffold(
      backgroundColor = SlackCloneColorProvider.colors.uiBackground,
      contentColor = SlackCloneColorProvider.colors.textSecondary,
      modifier = Modifier
        .statusBarsPadding()
        .navigationBarsPadding(),
      scaffoldState = scaffoldState,
      bottomBar = {
        DashboardBottomNavBar(dashboardNavController)
      },
      snackbarHost = {
        scaffoldState.snackbarHostState
      },
      floatingActionButton = {
        floatingDM(composeNavigator)
      }
    ) { innerPadding ->
      Box(modifier = Modifier.padding(innerPadding)) {
        SlackCloneSurface(
          color = SlackCloneColorProvider.colors.uiBackground,
          modifier = Modifier.fillMaxSize()
        ) {
          NavHost(
            dashboardNavController,
            startDestination = Screen.Home.route,
          ) {
            composable(Screen.Home.route) {
              HomeScreenUI(
                appBarIconClick,
                onItemClick = onItemClick,
                onCreateChannelRequest = {
                  composeNavigator.navigate(SlackScreen.CreateChannelsScreen.name)
                }
              )
            }
            composable(Screen.DMs.route) {
              DirectMessagesUI(onItemClick = onItemClick)
            }
            composable(Screen.Mentions.route) {
              MentionsReactionsUI()
            }
            composable(Screen.Search.route) {
              SearchMessagesUI()
            }
            composable(Screen.You.route) {
              UserProfileUI()
            }
          }
        }
      }
      if (isLeftNavOpen || isChatViewOpen) {
        OverlayDark(appBarIconClick)
      }
    }
  }
}

@Composable
private fun floatingDM(composeNavigator: ComposeNavigator) {
  FloatingActionButton(onClick = {
    composeNavigator.navigate(SlackScreen.CreateNewDM.name)
  }, backgroundColor = Color.White) {
    Icon(
      imageVector = Icons.Default.Edit,
      contentDescription = null,
      tint = SlackCloneColor
    )
  }
}

@Composable
private fun OverlayDark(appBarIconClick: () -> Unit) {
  Box(
    Modifier
      .fillMaxSize()
      .clickable {
        appBarIconClick()
      }
      .background(Color.Black.copy(alpha = 0.4f))
  ) {
  }
}

sealed class Screen(val route: String, val image: ImageVector, @StringRes val resourceId: Int) {
  object Home : Screen("Home", Icons.Filled.Home, io.getstream.slackclone.uidashboard.R.string.home)
  object DMs : Screen("DMs", Icons.Filled.Menu, io.getstream.slackclone.uidashboard.R.string.dms)
  object Mentions :
    Screen("Mentions", Icons.Filled.Email, io.getstream.slackclone.uidashboard.R.string.mentions)

  object Search :
    Screen("Search", Icons.Filled.Search, io.getstream.slackclone.uidashboard.R.string.search)

  object You : Screen("You", Icons.Default.Face, io.getstream.slackclone.uidashboard.R.string.you)
}

@Composable
fun DashboardBottomNavBar(navController: NavHostController) {
  Column(Modifier.background(color = SlackCloneColorProvider.colors.uiBackground)) {
    Divider(
      color = SlackCloneColorProvider.colors.textPrimary.copy(alpha = 0.2f),
      thickness = 0.5.dp
    )
    BottomNavigation(backgroundColor = SlackCloneColorProvider.colors.uiBackground) {
      val navBackStackEntry by navController.currentBackStackEntryAsState()
      val currentDestination = navBackStackEntry?.destination
      val dashTabs = getDashTabs()
      dashTabs.forEach { screen ->
        BottomNavItem(screen, currentDestination, navController)
      }
    }
  }
}

@Composable
private fun RowScope.BottomNavItem(
  screen: Screen,
  currentDestination: NavDestination?,
  navController: NavHostController,
) {

  BottomNavigationItem(
    selectedContentColor = SlackCloneColorProvider.colors.bottomNavSelectedColor,
    unselectedContentColor = SlackCloneColorProvider.colors.bottomNavUnSelectedColor,
    icon = { Icon(screen.image, contentDescription = null) },
    label = {
      Text(
        stringResource(screen.resourceId),
        maxLines = 1,
        style = SlackCloneTypography.overline,
      )
    },
    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
    onClick = {
      navigateTab(navController, screen)
    }
  )
}

private fun navigateTab(
  navController: NavHostController,
  screen: Screen
) {
  navController.navigate(screen.route) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    popUpTo(navController.graph.findStartDestination().id) {
      saveState = true
    }
    // Avoid multiple copies of the same destination when
    // reselecting the same item
    launchSingleTop = true
    // Restore state when reselecting a previously selected item
    restoreState = true
  }
}

private fun getDashTabs(): MutableList<Screen> {
  return mutableListOf<Screen>().apply {
    add(Screen.Home)
    add(Screen.DMs)
    add(Screen.Mentions)
    add(Screen.Search)
    add(Screen.You)
  }
}
