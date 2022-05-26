package io.getstream.slackclone.uichannels.directmessages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryChannelsRequest
import io.getstream.chat.android.client.call.await
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Filters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DMessageViewModel @Inject constructor(
  private val chatClient: ChatClient,
) : ViewModel() {

  val channels = MutableStateFlow<List<Channel>>(emptyList())

  fun refresh() {
    viewModelScope.launch {
      val currentUser = requireNotNull(chatClient.getCurrentUser())
      val channelsRequest = QueryChannelsRequest(
        filter = Filters.and(
          Filters.eq("type", "messaging"),
          Filters.`in`("members", listOf(currentUser.id)),
        ),
        offset = 0,
        limit = 20,
      )
      val result = chatClient.queryChannels(channelsRequest).await()
      if (result.isSuccess) {
        channels.value = result.data()
      }
    }
  }
}
