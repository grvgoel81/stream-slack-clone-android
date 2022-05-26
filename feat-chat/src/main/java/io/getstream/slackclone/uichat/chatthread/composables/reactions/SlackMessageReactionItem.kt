package io.getstream.slackclone.uichat.chatthread.composables.reactions

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.getstream.chat.android.compose.state.reactionoptions.ReactionOptionItemState
import io.getstream.slackclone.commonui.theme.SlackCloneColorProvider

@Composable
fun SlackMessageReactionItem(
  option: ReactionOptionItemState,
  score: Int,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier
      .wrapContentSize()
      .padding(horizontal = 4.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Image(
      modifier = Modifier
        .size(20.dp)
        .padding(2.dp)
        .align(Alignment.CenterVertically),
      painter = option.painter,
      contentDescription = null
    )

    Spacer(modifier = Modifier.width(4.dp))

    Text(
      text = score.toString(),
      color = SlackCloneColorProvider.colors.textPrimary,
      fontSize = 11.sp
    )
  }
}