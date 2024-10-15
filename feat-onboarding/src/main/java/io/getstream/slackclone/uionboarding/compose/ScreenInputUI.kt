package io.getstream.slackclone.uionboarding.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.getstream.slackclone.commonui.theme.SlackCloneTheme
import io.getstream.slackclone.navigator.ComposeNavigator
import io.getstream.slackclone.uionboarding.R

@Composable
fun EmailAddressInputUI(
  composeNavigator: ComposeNavigator,
  onBoardingVM: OnBoardingVM
) {
  SlackCloneTheme {
    CommonInputUI(
      composeNavigator = composeNavigator,
      topView = { modifier -> EmailInputView(modifier, onBoardingVM) },
      subtitleText = stringResource(id = R.string.subtitle_this_email_slack),
      onBoardingVM = onBoardingVM
    )
  }
}

@Composable
fun WorkspaceInputUI(
  composeNavigator: ComposeNavigator,
  onBoardingVM: OnBoardingVM
) {
  SlackCloneTheme {
    CommonInputUI(
      composeNavigator = composeNavigator,
      topView = { modifier -> WorkspaceInputView(modifier, onBoardingVM) },
      subtitleText = stringResource(id = R.string.subtitle_this_address_slack),
      onBoardingVM = onBoardingVM
    )
  }
}
