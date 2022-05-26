package io.getstream.slackclone.domain.usecases.channels

import androidx.paging.PagingData
import io.getstream.slackclone.domain.model.channel.DomainLayerChannels
import io.getstream.slackclone.domain.repository.ChannelsRepository
import io.getstream.slackclone.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow

class UseCaseSearchChannel(private val channelsRepository: ChannelsRepository) :
  BaseUseCase<PagingData<DomainLayerChannels.SlackChannel>, String> {
  override fun performStreaming(params: String?): Flow<PagingData<DomainLayerChannels.SlackChannel>> {
    return channelsRepository.fetchChannelsPaged(params)
  }
}
