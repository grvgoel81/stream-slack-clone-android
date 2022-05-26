package io.getstream.slackclone.domain.repository

import io.getstream.slackclone.domain.model.login.LoginState
import io.getstream.slackclone.domain.model.users.DomainLayerUsers

interface UsersRepository {
  suspend fun getUsers(count: Int): List<DomainLayerUsers.SlackUser>
  suspend fun login(userName: String): LoginState
  suspend fun logout()
}
