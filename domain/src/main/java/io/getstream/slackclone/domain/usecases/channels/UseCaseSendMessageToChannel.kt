package io.getstream.slackclone.domain.usecases.channels

import io.getstream.slackclone.domain.model.message.ChannelMessage
import io.getstream.slackclone.domain.repository.ChannelsRepository
import io.getstream.slackclone.domain.usecases.BaseUseCase

class UseCaseSendMessageToChannel(private val channelsRepository: ChannelsRepository) :
  BaseUseCase<Unit, ChannelMessage> {
  override suspend fun perform(params: ChannelMessage) {
    channelsRepository.sendMessageToChannel(params)
  }
}
