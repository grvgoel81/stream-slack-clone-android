package io.getstream.slackclone.uichannels.createsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.slackclone.chatcore.data.UiLayerChannels
import io.getstream.slackclone.domain.mappers.UiModelMapper
import io.getstream.slackclone.domain.model.channel.DomainLayerChannels
import io.getstream.slackclone.domain.usecases.channels.UseCaseFetchChannelCount
import io.getstream.slackclone.domain.usecases.channels.UseCaseSearchChannel
import io.getstream.slackclone.navigator.ComposeNavigator
import io.getstream.slackclone.navigator.NavigationKeys
import io.getstream.slackclone.navigator.SlackScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchChannelsVM @Inject constructor(
  private val composeNavigator: ComposeNavigator,
  private val ucFetchChannels: UseCaseSearchChannel,
  private val useCaseFetchChannelCount: UseCaseFetchChannelCount,
  private val chatPresentationMapper: UiModelMapper<DomainLayerChannels.SlackChannel, UiLayerChannels.SlackChannel>
) : ViewModel() {

  val search = MutableStateFlow("")
  val channelCount = MutableStateFlow(0)
  val channels = MutableStateFlow(flow(""))

  init {
    viewModelScope.launch {
      val count = useCaseFetchChannelCount.perform()
      channelCount.value = count
    }
  }

  private fun flow(search: String) = ucFetchChannels.performStreaming(search).map { channels ->
    channels.map { channel ->
      chatPresentationMapper.mapToPresentation(channel)
    }
  }

  fun search(newValue: String) {
    search.value = newValue
    channels.value = flow(newValue)
  }

  fun navigate(channel: UiLayerChannels.SlackChannel) {
    composeNavigator.navigateBackWithResult(
      NavigationKeys.navigateChannel,
      channel.uuid!!,
      SlackScreen.Dashboard.name
    )
  }
}
