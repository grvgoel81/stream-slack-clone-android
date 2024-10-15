package io.getstream.slackclone.uichat.chatthread.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.ui.common.state.messages.list.SystemMessageItemState

@Composable
fun ChatSystemMessage(systemMessageState: SystemMessageItemState) {
  Text(
    modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp, horizontal = 16.dp),
    text = systemMessageState.message.text,
    color = ChatTheme.colors.textLowEmphasis,
    style = ChatTheme.typography.footnoteBold,
    textAlign = TextAlign.Center
  )
}