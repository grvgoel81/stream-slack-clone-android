package io.getstream.slackclone.domain.repository

import androidx.paging.PagingData
import io.getstream.slackclone.domain.model.channel.DomainLayerChannels
import io.getstream.slackclone.domain.model.message.ChannelMessage
import io.getstream.slackclone.domain.model.users.DomainLayerUsers
import kotlinx.coroutines.flow.Flow

interface ChannelsRepository {
  fun fetchChannels(): Flow<List<DomainLayerChannels.SlackChannel>>
  fun fetchChannelsPaged(params: String?): Flow<PagingData<DomainLayerChannels.SlackChannel>>
  suspend fun createChannel(domainLayerChannels: DomainLayerChannels.SlackChannel)
  suspend fun sendMessageToChannel(channelMessage: ChannelMessage)
  suspend fun saveChannel(params: DomainLayerChannels.SlackChannel): DomainLayerChannels.SlackChannel?
  suspend fun getChannel(uuid: String): DomainLayerChannels.SlackChannel?
  suspend fun channelCount(): Int
  suspend fun saveOneToOneChannels(params: List<DomainLayerUsers.SlackUser>)
}
