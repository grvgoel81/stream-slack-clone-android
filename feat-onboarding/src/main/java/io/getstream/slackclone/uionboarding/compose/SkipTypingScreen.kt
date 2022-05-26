package io.getstream.slackclone.uionboarding.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.getstream.slackclone.commonui.material.SlackSurfaceAppBar
import io.getstream.slackclone.commonui.theme.SlackCloneColor
import io.getstream.slackclone.commonui.theme.SlackCloneColorProvider
import io.getstream.slackclone.commonui.theme.SlackCloneSurface
import io.getstream.slackclone.commonui.theme.SlackCloneTheme
import io.getstream.slackclone.commonui.theme.SlackCloneTypography
import io.getstream.slackclone.commonui.theme.slackFontFamily
import io.getstream.slackclone.navigator.ComposeNavigator
import io.getstream.slackclone.navigator.SlackScreen
import io.getstream.slackclone.uionboarding.R

@Composable
fun SkipTypingUI(composeNavigator: ComposeNavigator) {
  SlackCloneTheme {
    val scaffoldState = rememberScaffoldState()
    val sysUiController = rememberSystemUiController()
    SideEffect {
      sysUiController.setNavigationBarColor(color = SlackCloneColor)
      sysUiController.setSystemBarsColor(color = SlackCloneColor)
    }
    Scaffold(
      backgroundColor = SlackCloneColor,
      contentColor = SlackCloneColorProvider.colors.textSecondary,
      modifier = Modifier.statusBarsPadding(), scaffoldState = scaffoldState,
      topBar = {
        SlackSurfaceAppBar(
          title = {
          },
          navigationIcon = {
            IconButton(onClick = {
              composeNavigator.navigateUp()
            }) {
              Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "Clear",
                modifier = Modifier.padding(start = 8.dp), tint = Color.White
              )
            }
          },
          backgroundColor = SlackCloneColor,
          elevation = 0.dp
        )
      },
      snackbarHost = {
        scaffoldState.snackbarHostState
      }
    ) { innerPadding ->
      Box(modifier = Modifier.padding(innerPadding)) {
        SlackCloneSurface(
          color = SlackCloneColor,
          modifier = Modifier
            .padding(28.dp)
        ) {
          Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
              .fillMaxHeight()
              .fillMaxWidth()
          ) {
            Image(
              painter = painterResource(id = R.drawable.gettingstarted),
              contentDescription = "Logo",
              Modifier
            )
            TitleSubtitleText()
            Spacer(Modifier.padding(8.dp))
            Column {
              EmailMeMagicLink(composeNavigator)
              Box(modifier = Modifier.height(12.dp))
              IWillSignInManually(composeNavigator)
            }
          }
        }
      }
    }
  }
}

@Composable
fun EmailMeMagicLink(composeNavigator: ComposeNavigator) {
  OutlinedButton(
    onClick = {
      composeNavigator.navigate(SlackScreen.EmailAddressInputUI.name)
    },
    border = BorderStroke(1.dp, color = Color.White),
    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
    modifier = Modifier
      .fillMaxWidth()
      .height(40.dp),
  ) {
    Text(
      text = "Email me a magic link",
      style = SlackCloneTypography.subtitle2.copy(color = Color.White)
    )
  }
}

@Composable
private fun IWillSignInManually(composeNavigator: ComposeNavigator) {
  Button(
    onClick = {
      composeNavigator.navigate(SlackScreen.WorkspaceInputUI.name)
    },
    Modifier
      .fillMaxWidth()
      .height(40.dp),
    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
  ) {
    Text(
      text = "I'll sign in manually",
      style = SlackCloneTypography.subtitle2.copy(color = SlackCloneColor)
    )
  }
}

@Composable
private fun TitleSubtitleText(modifier: Modifier = Modifier) {
  Text(
    text = buildAnnotatedString {
      withStyle(
        style = SpanStyle(
          fontFamily = slackFontFamily,
          fontWeight = FontWeight.Bold,
          fontSize = SlackCloneTypography.h6.fontSize, color = Color.White
        )
      ) {
        append("Want to skip the typing ?\n\n")
      }
      withStyle(
        style = SpanStyle(
          fontFamily = slackFontFamily,
          fontWeight = FontWeight.Normal,
          fontSize = SlackCloneTypography.subtitle2.fontSize, color = Color.White
        )
      ) {
        append("We can email you a magic sign-in link that adds all your workspaces at once")
      }
    },
    textAlign = TextAlign.Center,
    modifier = modifier.fillMaxWidth(),
    style = SlackCloneTypography.h4
  )
}
