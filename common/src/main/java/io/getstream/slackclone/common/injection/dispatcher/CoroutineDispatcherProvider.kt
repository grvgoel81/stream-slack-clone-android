package io.getstream.slackclone.common.injection.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
  val main: CoroutineDispatcher
  val io: CoroutineDispatcher
  val default: CoroutineDispatcher
  val unconfirmed: CoroutineDispatcher
}
