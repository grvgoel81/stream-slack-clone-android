package io.getstream.slackclone.common.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.getstream.slackclone.common.injection.dispatcher.CoroutineDispatcherProvider
import io.getstream.slackclone.common.injection.dispatcher.RealCoroutineDispatcherProvider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DispatcherModule {
  @Provides
  @Singleton
  fun providesCoroutineDispatcher(): CoroutineDispatcherProvider {
    return RealCoroutineDispatcherProvider()
  }
}
