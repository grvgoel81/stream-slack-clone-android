package io.getstream.slackclone.domain.usecases.channels

import io.getstream.slackclone.domain.model.channel.DomainLayerChannels
import io.getstream.slackclone.domain.repository.ChannelsRepository
import io.getstream.slackclone.domain.usecases.BaseUseCase

class UseCaseGetChannel(private val channelsRepository: ChannelsRepository) :
  BaseUseCase<DomainLayerChannels.SlackChannel, String> {
  override suspend fun perform(params: String): DomainLayerChannels.SlackChannel? {
    return channelsRepository.getChannel(uuid = params)
  }
}
