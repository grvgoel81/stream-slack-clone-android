package io.getstream.slackclone.domain.usecases.channels

import io.getstream.slackclone.domain.model.users.DomainLayerUsers
import io.getstream.slackclone.domain.repository.ChannelsRepository
import io.getstream.slackclone.domain.usecases.BaseUseCase

class UseCaseCreateLocalChannels(private val channelsRepository: ChannelsRepository) :
  BaseUseCase<Unit, List<DomainLayerUsers.SlackUser>> {
  override suspend fun perform(params: List<DomainLayerUsers.SlackUser>) {
    return channelsRepository.saveOneToOneChannels(params)
  }
}
