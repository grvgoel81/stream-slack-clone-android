package io.getstream.slackclone.data.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.getstream.slackclone.domain.repository.ChannelsRepository
import io.getstream.slackclone.domain.repository.UsersRepository
import io.getstream.slackclone.domain.usecases.channels.UseCaseCreateLocalChannel
import io.getstream.slackclone.domain.usecases.channels.UseCaseCreateLocalChannels
import io.getstream.slackclone.domain.usecases.channels.UseCaseCreateRemoteChannel
import io.getstream.slackclone.domain.usecases.channels.UseCaseFetchChannelCount
import io.getstream.slackclone.domain.usecases.channels.UseCaseFetchChannels
import io.getstream.slackclone.domain.usecases.channels.UseCaseGetChannel
import io.getstream.slackclone.domain.usecases.channels.UseCaseSearchChannel
import io.getstream.slackclone.domain.usecases.channels.UseCaseSendMessageToChannel
import io.getstream.slackclone.domain.usecases.users.UseCaseFetchUsers
import io.getstream.slackclone.domain.usecases.users.UseCaseLoginUser
import io.getstream.slackclone.domain.usecases.users.UseCaseLogoutUser

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

  @Provides
  @ViewModelScoped
  fun provideUseCaseFetchChannels(channelsRepository: ChannelsRepository) =
    UseCaseFetchChannels(channelsRepository)

  @Provides
  @ViewModelScoped
  fun provideUseCaseCreateLocalChannel(channelsRepository: ChannelsRepository) =
    UseCaseCreateLocalChannel(channelsRepository)

  @Provides
  @ViewModelScoped
  fun provideUseCaseCreateLocalChannels(channelsRepository: ChannelsRepository) =
    UseCaseCreateLocalChannels(channelsRepository)

  @Provides
  @ViewModelScoped
  fun provideUseCaseCreateRemoteChannel(channelsRepository: ChannelsRepository) =
    UseCaseCreateRemoteChannel(channelsRepository)

  @Provides
  @ViewModelScoped
  fun provideUseCaseSendMessageToChannel(channelsRepository: ChannelsRepository) =
    UseCaseSendMessageToChannel(channelsRepository)

  @Provides
  @ViewModelScoped
  fun provideUseCaseGetChannel(channelsRepository: ChannelsRepository) =
    UseCaseGetChannel(channelsRepository)

  @Provides
  @ViewModelScoped
  fun provideUseCaseFetchChannelCount(channelsRepository: ChannelsRepository) =
    UseCaseFetchChannelCount(channelsRepository)

  @Provides
  @ViewModelScoped
  fun provideUseCaseSearchChannel(channelsRepository: ChannelsRepository) =
    UseCaseSearchChannel(channelsRepository)

  @Provides
  @ViewModelScoped
  fun provideUseCaseFetchUsers(slackUsersRepository: UsersRepository) =
    UseCaseFetchUsers(slackUsersRepository)

  @Provides
  @ViewModelScoped
  fun provideUseCaseLogoutUser(slackUsersRepository: UsersRepository) =
    UseCaseLogoutUser(slackUsersRepository)

  @Provides
  @ViewModelScoped
  fun provideUseCaseLoginUser(slackUsersRepository: UsersRepository) =
    UseCaseLoginUser(slackUsersRepository)
}
