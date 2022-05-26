package io.getstream.slackclone.uionboarding.nav

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.getstream.slackclone.navigator.ComposeNavigator
import io.getstream.slackclone.navigator.SlackRoute
import io.getstream.slackclone.navigator.SlackScreen
import io.getstream.slackclone.uionboarding.compose.EmailAddressInputUI
import io.getstream.slackclone.uionboarding.compose.GettingStartedUI
import io.getstream.slackclone.uionboarding.compose.SkipTypingUI
import io.getstream.slackclone.uionboarding.compose.WorkspaceInputUI

fun NavGraphBuilder.onboardingNavigation(
  composeNavigator: ComposeNavigator
) {
  navigation(
    startDestination = SlackScreen.GettingStarted.name,
    route = SlackRoute.OnBoarding.name
  ) {
    composable(SlackScreen.GettingStarted.name) {
      GettingStartedUI(composeNavigator)
    }
    composable(SlackScreen.SkipTypingScreen.name) {
      SkipTypingUI(composeNavigator)
    }
    composable(SlackScreen.WorkspaceInputUI.name) {
      WorkspaceInputUI(composeNavigator, hiltViewModel())
    }
    composable(SlackScreen.EmailAddressInputUI.name) {
      EmailAddressInputUI(composeNavigator, hiltViewModel())
    }
  }
}
