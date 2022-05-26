package io.getstream.slackclone.common.startup

import android.content.Context
import androidx.startup.Initializer
import io.getstream.slackclone.common.BuildConfig
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

  override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
