package io.getstream.slackclone.data.repository

import com.github.vatbub.randomusers.Generator
import com.github.vatbub.randomusers.result.RandomUser
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.User
import io.getstream.slackclone.common.injection.dispatcher.CoroutineDispatcherProvider
import io.getstream.slackclone.data.mapper.EntityMapper
import io.getstream.slackclone.domain.model.login.LoginState
import io.getstream.slackclone.domain.model.users.DomainLayerUsers
import io.getstream.slackclone.domain.repository.UsersRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SlackUserRepository @Inject constructor(
  private val mapper: EntityMapper<DomainLayerUsers.SlackUser, RandomUser>,
  private val coroutineMainDispatcherProvider: CoroutineDispatcherProvider,
  private val chatClient: ChatClient
) : UsersRepository {

  override suspend fun getUsers(count: Int): List<DomainLayerUsers.SlackUser> {
    return withContext(coroutineMainDispatcherProvider.io) {
      Generator.generateRandomUsers(
        RandomUser.RandomUserSpec(),
        count
      ).map {
        mapper.mapToDomain(it)
      }
    }
  }

  override suspend fun login(userName: String): LoginState {
    val name: String = if (userName.contains("@")) {
      userName.split("@")[0]
    } else {
      userName
    }
    val user = User(
      id = name,
      name = name,
      image = "http://placekitten.com/200/300"
    )
    val token = chatClient.devToken(user.id)
    val result = chatClient.connectUser(user = user, token = token).await()
    return if (result.isSuccess) {
      LoginState.Success
    } else {
      LoginState.Failure(result.errorOrNull()?.message.orEmpty())
    }
  }

  override suspend fun logout() {
    chatClient.disconnect(flushPersistence = true)
  }
}
