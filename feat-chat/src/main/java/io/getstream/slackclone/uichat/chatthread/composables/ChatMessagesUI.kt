package io.getstream.slackclone.uichat.chatthread.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import io.getstream.chat.android.compose.ui.components.LoadingIndicator
import io.getstream.chat.android.compose.ui.messages.list.Messages
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.util.rememberMessageListState
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.models.MessagesState
import io.getstream.chat.android.ui.common.state.messages.list.DateSeparatorItemState
import io.getstream.chat.android.ui.common.state.messages.list.MessageItemState
import io.getstream.chat.android.ui.common.state.messages.list.MessageListState
import io.getstream.chat.android.ui.common.state.messages.list.SelectedMessageState
import io.getstream.chat.android.ui.common.state.messages.list.SystemMessageItemState
import io.getstream.slackclone.uichat.chatthread.composables.reactions.SlackCloneReactionFactory

@Composable
fun ChatMessagesUI(
  modifier: Modifier,
  listViewModel: MessageListViewModel,
  currentState: MessageListState,
  selectedState: SelectedMessageState?,
) {
  val keyboardController = LocalSoftwareKeyboardController.current
  val isLoading = currentState.isLoading

  ChatTheme(
    reactionIconFactory = SlackCloneReactionFactory()
  ) {
    Box(modifier = modifier.fillMaxSize()) {
      when {
        isLoading -> LoadingIndicator(Modifier.fillMaxSize())
        !isLoading && currentState.messageItems.isNotEmpty() ->
          Messages(
            modifier = Modifier
                .fillMaxSize()
                .background(ChatTheme.colors.appBackground),
            messagesState = listViewModel.currentMessagesState,
            messagesLazyListState = rememberMessageListState(parentMessageId = listViewModel.currentMessagesState.parentMessageId),
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

                is DateSeparatorItemState -> ChatMessageDateSeparator(dateSeparator = messageListItemState)
                is SystemMessageItemState -> ChatSystemMessage(systemMessageState = messageListItemState)
                else -> Unit
              }
            },
            onMessagesStartReached = {},
            onLastVisibleMessageChanged = {},
            onScrolledToBottom = {},
            onMessagesEndReached = {},
            onScrollToBottom = {}
          )

        else -> MessageListEmptyContent(Modifier.fillMaxSize())
      }

      ChatMessageReactionSelectMenu(
        selectedMessageState = selectedState,
        listViewModel = listViewModel
      )
    }
  }
}
