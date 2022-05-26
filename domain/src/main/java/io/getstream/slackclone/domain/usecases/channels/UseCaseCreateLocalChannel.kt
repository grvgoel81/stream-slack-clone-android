package io.getstream.slackclone.domain.usecases.channels

import io.getstream.slackclone.domain.model.channel.DomainLayerChannels
import io.getstream.slackclone.domain.repository.ChannelsRepository
import io.getstream.slackclone.domain.usecases.BaseUseCase

class UseCaseCreateLocalChannel(private val channelsRepository: ChannelsRepository) :
  BaseUseCase<DomainLayerChannels.SlackChannel, DomainLayerChannels.SlackChannel> {
  override suspend fun perform(params: DomainLayerChannels.SlackChannel): DomainLayerChannels.SlackChannel? {
    return channelsRepository.saveChannel(params)
  }
}
