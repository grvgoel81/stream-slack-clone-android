package io.getstream.slackclone.chatcore.views

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.models.Channel
import io.getstream.slackclone.commonui.reusable.SlackListItem
import io.getstream.slackclone.commonui.reusable.SlackOnlineBox
import io.getstream.slackclone.commonui.theme.SlackCloneColorProvider
import io.getstream.slackclone.commonui.theme.SlackCloneTypography
import io.getstream.slackclone.domain.model.message.DomainLayerMessages

@Composable
fun SlackChannelItem(
  slackChannel: Channel,
  textColor: Color = SlackCloneColorProvider.colors.textPrimary,
  onItemClick: (Channel) -> Unit
) {
  when (slackChannel.memberCount <= 2) {
    true -> {
      DirectMessageChannel(onItemClick, slackChannel, textColor)
    }
    else -> {
      GroupChannelItem(slackChannel, onItemClick)
    }
  }
}

@Composable
private fun GroupChannelItem(
  slackChannel: Channel,
  onItemClick: (Channel) -> Unit
) {
  SlackListItem(
    icon = if (slackChannel.hidden == true) Icons.Default.Lock else Icons.Default.MailOutline,
    title = slackChannel.name,
    onItemClick = {
      onItemClick(slackChannel)
    }
  )
}

@Composable
private fun DirectMessageChannel(
  onItemClick: (Channel) -> Unit,
  slackChannel: Channel,
  textColor: Color
) {
  Row(
    modifier = Modifier
      .padding(8.dp)
      .fillMaxWidth()
      .clickable {
        onItemClick(slackChannel)
      },
    verticalAlignment = Alignment.CenterVertically
  ) {
    SlackOnlineBox(imageModel = slackChannel.image)
    ChannelText(slackChannel, textColor)
  }
}

@Composable
fun DMLastMessageItem(
  onItemClick: (Channel) -> Unit,
  slackChannel: Channel,
  slackMessage: DomainLayerMessages.SlackMessage,
) {
  Row(
    modifier = Modifier
      .padding(start = 8.dp, bottom = 4.dp)
      .fillMaxWidth()
      .clickable {
        onItemClick(slackChannel)
      },
    verticalAlignment = Alignment.CenterVertically
  ) {

    SlackListItem(icon = {
      SlackOnlineBox(
        imageModel = slackChannel.image,
        parentModifier = Modifier.size(48.dp),
        imageModifier = Modifier.size(40.dp)
      )
    }, center = {
      Column(it) {
        ChannelText(slackChannel, SlackCloneColorProvider.colors.textPrimary)
        ChannelMessage(slackMessage, SlackCloneColorProvider.colors.textSecondary)
      }
    }, trailingItem = {
      RelativeTime(slackMessage.createdDate)
    }, onItemClick = {
      onItemClick(slackChannel)
    })
  }
}

@Composable
private fun ChannelMessage(slackMessage: DomainLayerMessages.SlackMessage, textSecondary: Color) {
  Text(
    text = slackMessage.message,
    style = SlackCloneTypography.subtitle1.copy(
      color = textSecondary.copy(
        alpha = 0.8f
      ),
    ),
    modifier = Modifier
      .padding(start = 8.dp, top = 4.dp),
    maxLines = 2,
    overflow = TextOverflow.Ellipsis
  )
}

@Composable
fun RelativeTime(createdDate: Long) {
  Text(
    DateUtils.getRelativeTimeSpanString(
      createdDate,
      System.currentTimeMillis(),
      0L,
      DateUtils.FORMAT_ABBREV_RELATIVE
    ).toString(),
    style = SlackCloneTypography.caption.copy(
      color = SlackCloneColorProvider.colors.textSecondary
    ),
    modifier = Modifier.padding(4.dp)
  )
}

@Composable
private fun ChannelText(
  slackChannel: Channel,
  textColor: Color
) {
  Text(
    text = slackChannel.name,
    style = SlackCloneTypography.subtitle1.copy(
      color = textColor.copy(
        alpha = 0.8f
      )
    ),
    modifier = Modifier
      .padding(8.dp),
    maxLines = 1,
    overflow = TextOverflow.Ellipsis
  )
}
