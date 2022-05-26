package io.getstream.slackclone.uionboarding.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import io.getstream.slackclone.commonui.theme.SlackCloneColorProvider
import io.getstream.slackclone.commonui.theme.SlackCloneSurface
import io.getstream.slackclone.commonui.theme.SlackCloneTheme
import io.getstream.slackclone.commonui.theme.SlackCloneTypography
import io.getstream.slackclone.domain.model.login.LoginState
import io.getstream.slackclone.navigator.ComposeNavigator
import io.getstream.slackclone.navigator.SlackRoute
import io.getstream.slackclone.navigator.SlackScreen

@Composable
fun CommonInputUI(
  composeNavigator: ComposeNavigator,
  TopView: @Composable (modifier: Modifier) -> Unit,
  subtitleText: String,
  onBoardingVM: OnBoardingVM
) {
  val scaffoldState = rememberScaffoldState()
  val loginState by onBoardingVM.loginState.collectAsState()

  SlackCloneTheme {
    Scaffold(
      backgroundColor = SlackCloneColorProvider.colors.uiBackground,
      contentColor = SlackCloneColorProvider.colors.textSecondary,
      modifier = Modifier
        .statusBarsPadding()
        .navigationBarsPadding(),
      scaffoldState = scaffoldState,
      snackbarHost = {
        scaffoldState.snackbarHostState
      }
    ) { innerPadding ->
      Box(modifier = Modifier.padding(innerPadding)) {
        SlackCloneSurface(
          color = SlackCloneColorProvider.colors.uiBackground,
          modifier = Modifier
        ) {
          ConstraintLayout(
            modifier = Modifier
              .padding(12.dp)
              .navigationBarsWithImePadding()
              .fillMaxHeight()
              .fillMaxWidth()
          ) {
            val (inputView, subtitle, button, loading) = createRefs()

            TopView(
              modifier = Modifier.constrainAs(inputView) {
                top.linkTo(parent.top)
                bottom.linkTo(button.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
              }
            )

            SubTitle(
              modifier = Modifier.constrainAs(subtitle) {
                top.linkTo(inputView.bottom)
              },
              subtitleText
            )

            NextButton(
              modifier = Modifier.constrainAs(button) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
              },
              onBoardingVM, composeNavigator
            )

            if (loginState == LoginState.Loading) {
              CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.constrainAs(loading) {
                  top.linkTo(parent.top)
                  bottom.linkTo(parent.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
                }
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun NextButton(
  modifier: Modifier = Modifier,
  onBoardingVM: OnBoardingVM,
  composeNavigator: ComposeNavigator
) {
  val input by onBoardingVM.input.collectAsState()
  val loginState by onBoardingVM.loginState.collectAsState()
  val context = LocalContext.current

  LaunchedEffect(loginState) {
    if (loginState == LoginState.Success) {
      composeNavigator.navigate(SlackScreen.Dashboard.name) {
        this.popUpTo(SlackRoute.OnBoarding.name) {
          this.inclusive = true
        }
      }
    } else if (loginState is LoginState.Failure) {
      Toast.makeText(context, (loginState as LoginState.Failure).message, Toast.LENGTH_SHORT).show()
    }
  }

  Button(
    onClick = { onBoardingVM.connectUser() },
    modifier
      .fillMaxWidth()
      .height(50.dp)
      .padding(top = 8.dp),
    enabled = input.isNotEmpty(),
    colors = ButtonDefaults.buttonColors(
      backgroundColor = SlackCloneColorProvider.colors.buttonColor,
      disabledBackgroundColor = SlackCloneColorProvider.colors.error,
    )
  ) {
    Text(
      text = "Next",
      color = SlackCloneColorProvider.colors.buttonTextColor,
      style = SlackCloneTypography.subtitle2.copy(color = SlackCloneColorProvider.colors.buttonTextColor)
    )
  }
}

@Composable
private fun SubTitle(modifier: Modifier = Modifier, subtitleText: String) {
  Text(
    subtitleText,
    modifier = modifier
      .fillMaxWidth()
      .wrapContentWidth(align = Alignment.Start),
    style = SlackCloneTypography.subtitle2.copy(
      color = SlackCloneColorProvider.colors.textPrimary.copy(alpha = 0.8f),
      fontWeight = FontWeight.Normal,
      textAlign = TextAlign.Start
    )
  )
}
