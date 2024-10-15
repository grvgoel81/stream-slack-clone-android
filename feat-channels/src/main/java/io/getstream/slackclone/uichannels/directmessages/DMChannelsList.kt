package io.getstream.slackclone.uichannels.directmessages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.models.Channel
import io.getstream.slackclone.chatcore.data.UiLayerChannels
import io.getstream.slackclone.chatcore.extensions.toSlackDomainChannelMessage
import io.getstream.slackclone.chatcore.extensions.toSlackUIChannel
import io.getstream.slackclone.chatcore.views.DMLastMessageItem
import io.getstream.slackclone.commonui.theme.SlackCloneColorProvider

@Composable
fun DMChannelsList(
  onItemClick: (UiLayerChannels.SlackChannel) -> Unit,
  channelVM: DMessageViewModel = hiltViewModel()
) {

  val channels by channelVM.channels.collectAsState()
  val listState = rememberLazyListState()

  LaunchedEffect(key1 = Unit) {
    channelVM.refresh()
  }

  if (channels.isNotEmpty()) {
    LazyColumn(state = listState) {
      for (index in channels.indices) {
        val channel: Channel = channels[index]

        item {
          DMLastMessageItem({
            onItemClick(it.toSlackUIChannel())
          }, channel, channel.toSlackDomainChannelMessage())
        }
      }
    }
  } else {
    ChatTheme {
      EmptyContent(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = io.getstream.chat.android.compose.R.drawable.stream_compose_empty_channels),
        text = stringResource(io.getstream.chat.android.compose.R.string.stream_compose_channel_list_empty_channels),
      )
    }
  }
}

@Composable
fun EmptyContent(
  text: String,
  painter: Painter,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.background(color = SlackCloneColorProvider.colors.uiBackground),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Icon(
      painter = painter,
      contentDescription = null,
      tint = ChatTheme.colors.disabled,
      modifier = Modifier.size(96.dp),
    )

    Spacer(Modifier.size(16.dp))

    Text(
      text = text,
      style = ChatTheme.typography.title3,
      color = ChatTheme.colors.textLowEmphasis,
      textAlign = TextAlign.Center
    )
  }
}
