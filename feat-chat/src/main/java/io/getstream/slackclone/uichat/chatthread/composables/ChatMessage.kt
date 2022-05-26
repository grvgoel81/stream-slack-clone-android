package io.getstream.slackclone.uichat.chatthread.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.compose.state.messages.list.MessageItemState
import io.getstream.chat.android.compose.state.reactionoptions.ReactionOptionItemState
import io.getstream.chat.android.compose.ui.components.messages.MessageReactions
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.slackclone.common.extensions.calendar
import io.getstream.slackclone.common.extensions.formattedTime
import io.getstream.slackclone.commonui.reusable.SlackImageBox
import io.getstream.slackclone.commonui.theme.SlackCloneColorProvider
import io.getstream.slackclone.commonui.theme.SlackCloneTypography
import io.getstream.slackclone.uichat.chatthread.composables.reactions.SlackMessageReactionItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatMessage(
  messageItemState: MessageItemState,
  onLongItemClick: (Message) -> Unit,
  onReactionsClick: (Message) -> Unit = {}
) {
  ListItem(
    modifier = Modifier.padding(2.dp),
    icon = {
      SlackImageBox(
        modifier = Modifier.size(48.dp),
        imageModel = messageItemState.currentUser?.image ?: "http://placekitten.com/200/300"
      )
    },
    secondaryText = {
      ChatMessageItem(
        messageItemState = messageItemState,
        onLongItemClick = onLongItemClick,
        onReactionsClick = onReactionsClick
      )
    }, text = {
      ChatUserDateTime(messageItemState)
    }
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ChatMessageItem(
  messageItemState: MessageItemState,
  onLongItemClick: (Message) -> Unit,
  onReactionsClick: (Message) -> Unit = {},
) {
  val message = messageItemState.message

  Column(Modifier.combinedClickable(
    interactionSource = remember { MutableInteractionSource() },
    indication = null,
    onClick = {},
    onLongClick = { onLongItemClick(message) }
  )) {
    Text(
      message.text,
      style = SlackCloneTypography.subtitle2.copy(
        color = SlackCloneColorProvider.colors.textSecondary
      ),
      modifier = Modifier.padding(4.dp)
    )
    ChatMessageReactions(
      message = message,
      onReactionsClick = onReactionsClick
    )
  }
}

@Composable
private fun ChatMessageReactions(
  message: Message,
  onReactionsClick: (Message) -> Unit = {},
) {
  if (message.deletedAt == null) {
    val ownReactions = message.ownReactions
    val reactionCounts = message.reactionCounts.ifEmpty { return }
    val iconFactory = ChatTheme.reactionIconFactory
    reactionCounts
      .filter { iconFactory.isReactionSupported(it.key) }
      .takeIf { it.isNotEmpty() }
      ?.map { it.key }
      ?.map { type ->
        val isSelected = ownReactions.any { it.type == type }
        val reactionIcon = iconFactory.createReactionIcon(type)
        ReactionOptionItemState(
          painter = reactionIcon.getPainter(isSelected),
          type = type
        )
      }
      ?.let { options ->
        MessageReactions(
          modifier = Modifier
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = rememberRipple(bounded = false)
            ) {
              onReactionsClick(message)
            }
            .padding(horizontal = 4.dp, vertical = 2.dp),
          itemContent = { option ->
            SlackMessageReactionItem(
              option = option,
              score = message.ownReactions.filter { it.type == option.type }.size
            )
          },
          options = options
        )
      }
  }
}

@Composable
private fun ChatUserDateTime(messageItemState: MessageItemState) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(
      messageItemState.message.user.name + " \uD83C\uDF34",
      style = SlackCloneTypography.subtitle1.copy(
        fontWeight = FontWeight.Bold,
        color = SlackCloneColorProvider.colors.textPrimary
      ),
      modifier = Modifier.padding(4.dp)
    )
    Text(
      messageItemState.message.createdAt?.time?.calendar()?.formattedTime() ?: "",
      style = SlackCloneTypography.overline.copy(
        color = SlackCloneColorProvider.colors.textSecondary.copy(alpha = 0.8f)
      ),
      modifier = Modifier.padding(4.dp)
    )
  }
}
