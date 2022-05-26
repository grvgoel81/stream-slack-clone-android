package io.getstream.slackclone.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.getstream.slackclone.data.local.dao.SlackChannelDao
import io.getstream.slackclone.data.local.dao.SlackUserDao
import io.getstream.slackclone.data.local.model.DBSlackChannel
import io.getstream.slackclone.data.local.model.DBSlackUser

@Database(
  entities = [DBSlackUser::class, DBSlackChannel::class],
  version = 1
)
abstract class SlackDatabase : RoomDatabase() {
  abstract fun slackUserDao(): SlackUserDao
  abstract fun slackChannelDao(): SlackChannelDao
}
