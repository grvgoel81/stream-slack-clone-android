package io.getstream.slackclone.domain.usecases.channels

import io.getstream.slackclone.domain.model.channel.DomainLayerChannels
import io.getstream.slackclone.domain.repository.ChannelsRepository
import io.getstream.slackclone.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow

class UseCaseFetchChannels(
  private val channelsRepository: ChannelsRepository,
) : BaseUseCase<List<DomainLayerChannels.SlackChannel>, Unit> {

  override fun performStreaming(params: Unit?): Flow<List<DomainLayerChannels.SlackChannel>> {
    return channelsRepository.fetchChannels()
  }
}
