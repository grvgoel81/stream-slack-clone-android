package io.getstream.slackclone.uidashboard.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.getstream.slackclone.navigator.ComposeNavigator
import io.getstream.slackclone.navigator.SlackRoute
import io.getstream.slackclone.navigator.SlackScreen
import io.getstream.slackclone.uichannels.createsearch.CreateNewChannelUI
import io.getstream.slackclone.uichannels.createsearch.SearchCreateChannelUI
import io.getstream.slackclone.uichat.newchat.NewChatThreadScreen
import io.getstream.slackclone.uidashboard.compose.DashboardUI

fun NavGraphBuilder.dashboardNavigation(
  composeNavigator: ComposeNavigator,
) {
  navigation(
    startDestination = SlackScreen.Dashboard.name,
    route = SlackRoute.Dashboard.name
  ) {
    composable(SlackScreen.Dashboard.name) {
      DashboardUI(composeNavigator)
    }
    composable(SlackScreen.CreateChannelsScreen.name) {
      SearchCreateChannelUI(composeNavigator = composeNavigator)
    }
    composable(SlackScreen.CreateNewChannel.name) {
      CreateNewChannelUI(composeNavigator)
    }
    composable(SlackScreen.CreateNewDM.name) {
      NewChatThreadScreen(composeNavigator)
    }
  }
}
