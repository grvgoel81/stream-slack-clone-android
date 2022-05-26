package io.getstream.slackclone.uionboarding.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.getstream.slackclone.commonui.theme.SlackCloneColorProvider
import io.getstream.slackclone.commonui.theme.SlackCloneTypography

@Composable
fun WorkspaceInputView(
  modifier: Modifier,
  onBoardingVM: OnBoardingVM
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentWidth()
  ) {
    Text(
      text = "Workspace URL",
      style = SlackCloneTypography.caption.copy(
        color = SlackCloneColorProvider.colors.textPrimary.copy(alpha = 0.7f),
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start
      ),
      modifier = Modifier.padding(bottom = 4.dp)
    )
    Row(
      modifier = modifier
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Start
    ) {
      TextHttps()
      WorkspaceTF(onBoardingVM)
      TextSlackCom()
    }
  }
}

@Composable
private fun TextHttps() {
  Text(
    text = "https://",
    style = textStyleField().copy(
      color = SlackCloneColorProvider.colors.textPrimary.copy(
        alpha = 0.4f
      )
    )
  )
}

@Composable
private fun TextSlackCom() {
  Text(
    ".slack.com",
    style = textStyleField().copy(
      color = SlackCloneColorProvider.colors.textPrimary.copy(
        alpha = 0.4f
      )
    ),
    overflow = TextOverflow.Clip,
    maxLines = 1
  )
}

@Composable
private fun WorkspaceTF(onBoardingVM: OnBoardingVM) {
  val workspace by onBoardingVM.input.collectAsState()

  BasicTextField(
    value = workspace,
    onValueChange = { newEmail -> onBoardingVM.input.value = newEmail },
    textStyle = textStyleField(),
    singleLine = true,
    modifier = Modifier
      .width(IntrinsicSize.Min)
      .padding(top = 12.dp, bottom = 12.dp),
    maxLines = 1,
    cursorBrush = SolidColor(SlackCloneColorProvider.colors.textPrimary),
    decorationBox = { inputTf ->
      Box {
        if (workspace.isEmpty()) {
          Text(
            text = "your-workspace",
            style = textStyleField(),
            textAlign = TextAlign.Start,
            modifier = Modifier.width(IntrinsicSize.Max),
          )
        } else {
          inputTf()
        }
      }
    }
  )
}

@Composable
private fun textStyleField() = SlackCloneTypography.h6.copy(
  color = SlackCloneColorProvider.colors.textPrimary.copy(alpha = 0.7f),
  fontWeight = FontWeight.Normal,
  textAlign = TextAlign.Start
)
