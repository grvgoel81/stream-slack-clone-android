package io.getstream.slackclone.uichat.chatthread.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.models.Reaction
import io.getstream.chat.android.common.state.React
import io.getstream.chat.android.compose.state.messageoptions.MessageOptionItemState
import io.getstream.chat.android.compose.state.messages.MessagesState
import io.getstream.chat.android.compose.state.messages.SelectedMessageOptionsState
import io.getstream.chat.android.compose.state.messages.SelectedMessageReactionsState
import io.getstream.chat.android.compose.ui.components.messageoptions.defaultMessageOptionsState
import io.getstream.chat.android.compose.ui.components.selectedmessage.SelectedMessageMenu
import io.getstream.chat.android.compose.ui.components.selectedmessage.SelectedReactionsMenu
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.slackclone.uichat.chatthread.composables.reactions.SlackCloneReactions

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BoxScope.ChatMessageReactionSelectMenu(
  listViewModel: MessageListViewModel,
  currentState: MessagesState
) {
  val selectedMessageState = currentState.selectedMessageState
  val selectedMessage = selectedMessageState?.message ?: Message()
  val user by listViewModel.user.collectAsState()

  val ownCapabilities = selectedMessageState?.ownCapabilities ?: setOf()
  val newMessageOptions = defaultMessageOptionsState(
    selectedMessage = selectedMessage,
    currentUser = user,
    isInThread = listViewModel.isInThread,
    ownCapabilities = ownCapabilities
  )

  var messageOptions by remember { mutableStateOf<List<MessageOptionItemState>>(emptyList()) }
  if (newMessageOptions.isNotEmpty()) {
    messageOptions = newMessageOptions
  }

  AnimatedVisibility(
    visible = selectedMessageState is SelectedMessageOptionsState && selectedMessage.id.isNotEmpty(),
    enter = fadeIn(),
    exit = fadeOut(animationSpec = tween(durationMillis = AnimationConstants.DefaultDurationMillis / 2))
  ) {
    SelectedMessageMenu(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .animateEnterExit(
          enter = slideInVertically(
            initialOffsetY = { height -> height },
            animationSpec = tween()
          ),
          exit = slideOutVertically(
            targetOffsetY = { height -> height },
            animationSpec = tween(durationMillis = AnimationConstants.DefaultDurationMillis / 2)
          )
        ),
      messageOptions = messageOptions,
      message = selectedMessage,
      ownCapabilities = ownCapabilities,
      onMessageAction = { action ->
        listViewModel.performMessageAction(action)
      },
      onShowMoreReactionsSelected = {
        listViewModel.selectExtendedReactions(selectedMessage)
      },
      onDismiss = { listViewModel.removeOverlay() }
    )
  }

  AnimatedVisibility(
    visible = selectedMessageState is SelectedMessageReactionsState && selectedMessage.id.isNotEmpty(),
    enter = fadeIn(),
    exit = fadeOut(animationSpec = tween(durationMillis = AnimationConstants.DefaultDurationMillis / 2))
  ) {
    SelectedReactionsMenu(
      modifier = Modifier
        .align(Alignment.Center)
        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        .wrapContentSize()
        .animateEnterExit(
          enter = fadeIn(),
          exit = fadeOut(animationSpec = tween(durationMillis = AnimationConstants.DefaultDurationMillis / 2))
        ),
      currentUser = user,
      message = selectedMessage,
      onMessageAction = { action ->
        listViewModel.performMessageAction(action)
      },
      onShowMoreReactionsSelected = {
        listViewModel.selectExtendedReactions(selectedMessage)
      },
      shape = RoundedCornerShape(8.dp),
      onDismiss = { listViewModel.removeOverlay() },
      ownCapabilities = selectedMessageState?.ownCapabilities ?: setOf(),
      centerContent = {},
      headerContent = {
        SlackCloneReactions(
          message = selectedMessage
        ) { action ->
          when (action) {
            is React -> {
              sendReactions(
                selectedMessage,
                action.reaction,
                listViewModel
              )
            }
            else -> {
              listViewModel.performMessageAction(action)
            }
          }
        }
      }
    )
  }
}

private fun sendReactions(
  message: Message,
  reaction: Reaction,
  listViewModel: MessageListViewModel
) {
  if (message.ownReactions.any { it.messageId == reaction.messageId && it.type == reaction.type }) {
    ChatClient.instance().deleteReaction(message.id, reaction.type).enqueue { result ->
      if (result.isSuccess) {
        listViewModel.removeOverlay()
      }
    }
  } else {
    ChatClient.instance().sendReaction(reaction, enforceUnique = true).enqueue { result ->
      if (result.isSuccess) {
        listViewModel.removeOverlay()
      }
    }
  }
}