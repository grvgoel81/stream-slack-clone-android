package io.getstream.slackclone.data.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.getstream.slackclone.data.repository.SlackChannelsRepositoryImpl
import io.getstream.slackclone.data.repository.SlackUserRepository
import io.getstream.slackclone.domain.repository.ChannelsRepository
import io.getstream.slackclone.domain.repository.UsersRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
  @Binds
  @Singleton
  abstract fun bindLocalChannelsRepository(slackLocalChannelsRepositoryImpl: SlackChannelsRepositoryImpl): ChannelsRepository

  @Binds
  @Singleton
  abstract fun bindSlackUserRepository(slackUserRepository: SlackUserRepository): UsersRepository
}
