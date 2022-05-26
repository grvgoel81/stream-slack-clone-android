package io.getstream.slackclone.data.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

  @Provides
  @Singleton
  fun provideStreamChatClient() = ChatClient.instance()
}
