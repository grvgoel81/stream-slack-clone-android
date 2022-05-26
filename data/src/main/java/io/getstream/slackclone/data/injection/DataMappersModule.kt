package io.getstream.slackclone.data.injection

import com.github.vatbub.randomusers.result.RandomUser
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.getstream.slackclone.data.local.model.DBSlackChannel
import io.getstream.slackclone.data.mapper.EntityMapper
import io.getstream.slackclone.data.mapper.SlackChannelMapper
import io.getstream.slackclone.data.mapper.SlackUserChannelMapper
import io.getstream.slackclone.data.mapper.SlackUserMapper
import io.getstream.slackclone.domain.model.channel.DomainLayerChannels
import io.getstream.slackclone.domain.model.users.DomainLayerUsers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataMappersModule {

  @Binds
  @Singleton
  abstract fun bindSlackUserChannelMapper(slackUserChannelMapper: SlackUserChannelMapper): EntityMapper<DomainLayerUsers.SlackUser, DBSlackChannel>

  @Binds
  @Singleton
  abstract fun bindSlackUserDataDomainMapper(slackUserMapper: SlackUserMapper): EntityMapper<DomainLayerUsers.SlackUser, RandomUser>

  @Binds
  @Singleton
  abstract fun bindSlackChannelDataDomainMapper(slackChannelMapper: SlackChannelMapper): EntityMapper<DomainLayerChannels.SlackChannel, DBSlackChannel>
}
