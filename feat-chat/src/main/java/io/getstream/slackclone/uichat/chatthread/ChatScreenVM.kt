package io.getstream.slackclone.uichat.chatthread

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.slackclone.chatcore.data.UiLayerChannels
import io.getstream.slackclone.domain.mappers.UiModelMapper
import io.getstream.slackclone.domain.model.channel.DomainLayerChannels
import io.getstream.slackclone.domain.model.message.ChannelMessage
import io.getstream.slackclone.domain.usecases.channels.UseCaseCreateRemoteChannel
import io.getstream.slackclone.domain.usecases.channels.UseCaseSendMessageToChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenVM @Inject constructor(
  private val useCaseRemoteChannel: UseCaseCreateRemoteChannel,
  private val useCaseSendMessageToChannel: UseCaseSendMessageToChannel,
  private val channelMapper: UiModelMapper<DomainLayerChannels.SlackChannel, UiLayerChannels.SlackChannel>
) : ViewModel() {
  val message = MutableStateFlow("")
  val chatBoxState = MutableStateFlow(BoxState.Collapsed)

  fun createChannel(uiLayerChannels: UiLayerChannels.SlackChannel) {
    viewModelScope.launch {
      useCaseRemoteChannel.perform(
        channelMapper.mapToDomain(uiLayerChannels)
      )
    }
  }

  fun sendMessage(cid: String, message: String) {
    viewModelScope.launch {
      useCaseSendMessageToChannel.perform(ChannelMessage(cid, message))

      // clear chat box & input states
      chatBoxState.value = BoxState.Collapsed
      this@ChatScreenVM.message.value = ""
    }
  }

  fun switchChatBoxState() {
    chatBoxState.value =
      if (chatBoxState.value == BoxState.Collapsed) BoxState.Expanded else BoxState.Collapsed
  }
}
