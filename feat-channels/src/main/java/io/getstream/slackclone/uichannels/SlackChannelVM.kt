package io.getstream.slackclone.uichannels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.slackclone.chatcore.data.UiLayerChannels
import io.getstream.slackclone.domain.mappers.UiModelMapper
import io.getstream.slackclone.domain.model.channel.DomainLayerChannels
import io.getstream.slackclone.domain.usecases.channels.UseCaseFetchChannels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SlackChannelVM @Inject constructor(
  private val ucFetchChannels: UseCaseFetchChannels,
  private val chatPresentationMapper: UiModelMapper<DomainLayerChannels.SlackChannel, UiLayerChannels.SlackChannel>
) : ViewModel() {

  val channels = MutableStateFlow<Flow<List<UiLayerChannels.SlackChannel>>>(emptyFlow())

  fun allChannels() {
    channels.value = ucFetchChannels.performStreaming(null).map { channels ->
      domSlackToPresentation(channels)
    }
  }

  fun loadDirectMessageChannels() {
    channels.value = ucFetchChannels.performStreaming(null).map { channels ->
      domSlackToPresentation(channels)
    }
  }

  fun loadStarredChannels() {
    channels.value = ucFetchChannels.performStreaming(null).map { channels ->
      domSlackToPresentation(channels)
    }
  }

  private fun domSlackToPresentation(channels: List<DomainLayerChannels.SlackChannel>) =
    channels.map { channel ->
      chatPresentationMapper.mapToPresentation(channel)
    }
}
