package io.getstream.slackclone.chatcore.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.getstream.slackclone.chatcore.ChannelUIModelMapper
import io.getstream.slackclone.chatcore.data.UiLayerChannels
import io.getstream.slackclone.domain.mappers.UiModelMapper
import io.getstream.slackclone.domain.model.channel.DomainLayerChannels
import io.getstream.slackclone.domain.model.users.DomainLayerUsers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UiModelMapperModule {

  @Binds
  @Singleton
  abstract fun bindSlackUserChannelMapper(userChannelUiMapper: UserChannelUiMapper): UiModelMapper<DomainLayerUsers.SlackUser, UiLayerChannels.SlackChannel>

  @Binds
  @Singleton
  abstract fun bindChannelUIModelMapper(channelUIModelMapper: ChannelUIModelMapper): UiModelMapper<DomainLayerChannels.SlackChannel, UiLayerChannels.SlackChannel>
}
