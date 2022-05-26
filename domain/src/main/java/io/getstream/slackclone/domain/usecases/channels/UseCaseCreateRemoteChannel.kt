package io.getstream.slackclone.domain.usecases.channels

import io.getstream.slackclone.domain.model.channel.DomainLayerChannels
import io.getstream.slackclone.domain.repository.ChannelsRepository
import io.getstream.slackclone.domain.usecases.BaseUseCase

class UseCaseCreateRemoteChannel(private val channelsRepository: ChannelsRepository) :
  BaseUseCase<Unit, DomainLayerChannels.SlackChannel> {
  override suspend fun perform(params: DomainLayerChannels.SlackChannel) {
    channelsRepository.createChannel(params)
  }
}
