package io.getstream.slackclone.uichat.chatthread.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import io.getstream.chat.android.compose.state.messages.MessagesState
import io.getstream.chat.android.compose.state.messages.list.DateSeparatorState
import io.getstream.chat.android.compose.state.messages.list.MessageItemState
import io.getstream.chat.android.compose.state.messages.list.SystemMessageState
import io.getstream.chat.android.compose.ui.components.LoadingIndicator
import io.getstream.chat.android.compose.ui.messages.list.Messages
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.util.rememberMessageListState
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.slackclone.uichat.chatthread.composables.reactions.SlackCloneReactionFactory

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatMessagesUI(
  modifier: Modifier,
  listViewModel: MessageListViewModel,
  currentState: MessagesState
) {
  val (isLoading, _, _, messages) = currentState
  val keyboardController = LocalSoftwareKeyboardController.current

  ChatTheme(
    reactionIconFactory = SlackCloneReactionFactory()
  ) {
    Box(modifier = modifier.fillMaxSize()) {
      when {
        isLoading -> LoadingIndicator(Modifier.fillMaxSize())
        !isLoading && messages.isNotEmpty() ->
          Messages(
            modifier = Modifier
              .fillMaxSize()
              .background(ChatTheme.colors.appBackground),
            messagesState = listViewModel.currentMessagesState,
            lazyListState = rememberMessageListState(parentMessageId = listViewModel.currentMessagesState.parentMessageId),
            itemContent = { messageListItemState ->
              when (messageListItemState) {
                is MessageItemState -> ChatMessage(
                  messageItemState = messageListItemState,
                  onLongItemClick = {
                    listViewModel.selectMessage(it)
                    keyboardController?.hide()
                  },
                  onReactionsClick = {
                    listViewModel.selectReactions(it)
                  }
                )
                is DateSeparatorState -> ChatMessageDateSeparator(dateSeparator = messageListItemState)
                is SystemMessageState -> ChatSystemMessage(systemMessageState = messageListItemState)
                else -> Unit
              }
            },
            onMessagesStartReached = {},
            onLastVisibleMessageChanged = {},
            onScrolledToBottom = {},
          )
        else -> MessageListEmptyContent(Modifier.fillMaxSize())
      }

      ChatMessageReactionSelectMenu(
        currentState = currentState,
        listViewModel = listViewModel
      )
    }
  }
}
