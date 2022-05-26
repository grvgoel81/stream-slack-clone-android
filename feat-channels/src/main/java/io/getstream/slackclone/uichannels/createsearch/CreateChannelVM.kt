package io.getstream.slackclone.uichannels.createsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.slackclone.domain.model.channel.DomainLayerChannels
import io.getstream.slackclone.domain.usecases.channels.UseCaseCreateLocalChannel
import io.getstream.slackclone.navigator.ComposeNavigator
import io.getstream.slackclone.navigator.NavigationKeys
import io.getstream.slackclone.navigator.SlackScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateChannelVM @Inject constructor(
  private val composeNavigator: ComposeNavigator,
  private val useCaseCreateChannel: UseCaseCreateLocalChannel
) :
  ViewModel() {

  val channel =
    MutableStateFlow(DomainLayerChannels.SlackChannel(isOneToOne = false, avatarUrl = null))

  fun createChannel() {
    viewModelScope.launch {
      if (channel.value.name?.isNotEmpty() == true) {
        val channel = useCaseCreateChannel.perform(channel.value)
        composeNavigator.navigateBackWithResult(
          NavigationKeys.navigateChannel,
          channel?.uuid!!,
          SlackScreen.Dashboard.name
        )
      }
    }
  }
}
