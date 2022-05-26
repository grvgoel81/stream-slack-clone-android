package io.getstream.slackclone.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.models.User
import io.getstream.slackclone.common.injection.dispatcher.CoroutineDispatcherProvider
import io.getstream.slackclone.data.local.dao.SlackChannelDao
import io.getstream.slackclone.data.local.model.DBSlackChannel
import io.getstream.slackclone.data.mapper.EntityMapper
import io.getstream.slackclone.domain.model.channel.DomainLayerChannels
import io.getstream.slackclone.domain.model.message.ChannelMessage
import io.getstream.slackclone.domain.model.users.DomainLayerUsers
import io.getstream.slackclone.domain.repository.ChannelsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SlackChannelsRepositoryImpl @Inject constructor(
  private val slackChannelDao: SlackChannelDao,
  private val slackUserChannelMapper: EntityMapper<DomainLayerUsers.SlackUser, DBSlackChannel>,
  private val slackChannelMapper: EntityMapper<DomainLayerChannels.SlackChannel, DBSlackChannel>,
  private val coroutineMainDispatcherProvider: CoroutineDispatcherProvider,
  private val chatClient: ChatClient
) :
  ChannelsRepository {

  override fun fetchChannelsPaged(params: String?): Flow<PagingData<DomainLayerChannels.SlackChannel>> {
    val chatPager = Pager(PagingConfig(pageSize = 20)) {
      params?.takeIf { it.isNotEmpty() }?.let {
        slackChannelDao.channelsByName(params)
      } ?: run {
        slackChannelDao.channelsByName()
      }
    }
    return chatPager.flow.map {
      it.map { message ->
        slackChannelMapper.mapToDomain(message)
      }
    }
  }

  override suspend fun channelCount(): Int {
    return withContext(coroutineMainDispatcherProvider.io) {
      slackChannelDao.count()
    }
  }

  override fun fetchChannels(): Flow<List<DomainLayerChannels.SlackChannel>> {
    return slackChannelDao.getAllAsFlow()
      .map { list -> dbToDomList(list) }
  }

  override suspend fun createChannel(domainLayerChannels: DomainLayerChannels.SlackChannel) {
    val user = chatClient.getCurrentUser() ?: User()
    chatClient.createChannel(
      channelType = "messaging",
      channelId = domainLayerChannels.uuid ?: "",
      memberIds = listOf(user.id),
      extraData = mapOf(
        "name" to (domainLayerChannels.name ?: ""),
        "image" to (domainLayerChannels.avatarUrl ?: "")
      )
    ).await()
  }

  override suspend fun sendMessageToChannel(channelMessage: ChannelMessage) {
    val channelClient = chatClient.channel(channelMessage.cid)
    chatClient.sendMessage(
      channelType = channelClient.channelType,
      channelId = channelClient.channelId,
      message = Message(text = channelMessage.message)
    ).await()
  }

  private fun dbToDomList(list: List<DBSlackChannel>) =
    list.map { channel -> slackChannelMapper.mapToDomain(channel) }

  override suspend fun getChannel(uuid: String): DomainLayerChannels.SlackChannel? {
    val dbSlack = slackChannelDao.getById(uuid)
    return dbSlack?.let { slackChannelMapper.mapToDomain(it) }
  }

  override suspend fun saveOneToOneChannels(params: List<DomainLayerUsers.SlackUser>) {
    return withContext(coroutineMainDispatcherProvider.io) {
      slackChannelDao.insertAll(
        params.map {
          slackUserChannelMapper.mapToData(it)
        }
      )
    }
  }

  override suspend fun saveChannel(params: DomainLayerChannels.SlackChannel): DomainLayerChannels.SlackChannel? {
    return withContext(coroutineMainDispatcherProvider.io) {
      slackChannelDao.insert(slackChannelMapper.mapToData(params))
      slackChannelDao.getById(params.uuid!!)?.let { slackChannelMapper.mapToDomain(it) }
    }
  }
}
