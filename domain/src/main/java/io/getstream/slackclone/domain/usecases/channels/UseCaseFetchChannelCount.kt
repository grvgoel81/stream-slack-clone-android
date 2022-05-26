package io.getstream.slackclone.domain.usecases.channels

import io.getstream.slackclone.domain.repository.ChannelsRepository
import io.getstream.slackclone.domain.usecases.BaseUseCase

class UseCaseFetchChannelCount(private val channelsRepository: ChannelsRepository) :
  BaseUseCase<Int, Unit> {

  override suspend fun perform(): Int {
    return channelsRepository.channelCount()
  }
}
