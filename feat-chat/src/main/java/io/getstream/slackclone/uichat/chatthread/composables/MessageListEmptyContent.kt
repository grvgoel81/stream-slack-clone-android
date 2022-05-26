package io.getstream.slackclone.uichat.chatthread.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import io.getstream.chat.android.compose.R
import io.getstream.chat.android.compose.ui.theme.ChatTheme

@Composable
fun MessageListEmptyContent(modifier: Modifier) {
  Box(
    modifier = modifier.background(color = ChatTheme.colors.appBackground),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = stringResource(R.string.stream_compose_message_list_empty_messages),
      style = ChatTheme.typography.body,
      color = ChatTheme.colors.textLowEmphasis,
      textAlign = TextAlign.Center
    )
  }
}