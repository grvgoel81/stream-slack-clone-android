package io.getstream.slackclone.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.slackclone.navigator.ComposeNavigator
import io.getstream.slackclone.navigator.SlackRoute
import io.getstream.slackclone.uidashboard.nav.dashboardNavigation
import io.getstream.slackclone.uionboarding.nav.onboardingNavigation
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var composeNavigator: ComposeNavigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)

    installSplashScreen()
    setContent {
      val navController = rememberNavController()

      LaunchedEffect(Unit) {
        composeNavigator.handleNavigationCommands(navController)
      }
      NavHost(
        navController = navController,
        startDestination = SlackRoute.OnBoarding.name,
      ) {
        onboardingNavigation(
          composeNavigator = composeNavigator,
        )
        dashboardNavigation(
          composeNavigator = composeNavigator
        )
      }
    }
  }
}
