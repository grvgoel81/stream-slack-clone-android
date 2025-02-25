/*
 * Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.slackclone.chatcore.startup

import android.content.Context
import androidx.startup.Initializer
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory

/**
 * StreamChatInitializer initializes all Stream Client components.
 */
class StreamChatInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    // Set up the OfflinePlugin for offline storage
    val offlinePluginFactory = StreamOfflinePluginFactory(appContext = context)
    val statePluginFactory =
      StreamStatePluginFactory(config = StatePluginConfig(), appContext = context)

    // Set up the client for API calls with the plugin for offline storage
    ChatClient.Builder("6wj48bfwxg4h", context)
      .withPlugins(offlinePluginFactory, statePluginFactory)
      .logLevel(ChatLogLevel.DEBUG)
      .build()
  }

  override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
