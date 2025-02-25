package io.getstream.slackclone.uichat.chatthread.composables

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.ui.common.state.messages.list.DateSeparatorItemState

@Composable
fun ChatMessageDateSeparator(dateSeparator: DateSeparatorItemState) {
  Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
    Surface(
      modifier = Modifier
        .padding(vertical = 8.dp),
      color = ChatTheme.colors.overlayDark,
      shape = RoundedCornerShape(16.dp)
    ) {
      Text(
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 16.dp),
        text = DateUtils.getRelativeTimeSpanString(
          dateSeparator.date.time,
          System.currentTimeMillis(),
          DateUtils.DAY_IN_MILLIS,
          DateUtils.FORMAT_ABBREV_RELATIVE
        ).toString(),
        color = ChatTheme.colors.barsBackground,
        style = ChatTheme.typography.body
      )
    }
  }
}