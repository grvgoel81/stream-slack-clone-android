package io.getstream.slackclone.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.getstream.slackclone.data.local.model.DBSlackUser

@Dao
interface SlackUserDao {
  @Query("SELECT * FROM slackuser")
  fun getAll(): List<DBSlackUser>

  @Query("SELECT * FROM slackuser WHERE uuid IN (:slackuserIds)")
  fun loadAllByIds(slackuserIds: IntArray): List<DBSlackUser>

  @Query(
    "SELECT * FROM slackuser WHERE first_name LIKE :first AND " +
      "last_name LIKE :last LIMIT 1"
  )
  fun findByName(first: String, last: String): DBSlackUser

  @Insert
  fun insertAll(slackUsers: List<DBSlackUser>)

  @Delete
  fun delete(slackUser: DBSlackUser)
}
