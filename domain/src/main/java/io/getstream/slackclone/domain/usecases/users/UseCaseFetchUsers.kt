package io.getstream.slackclone.domain.usecases.users

import io.getstream.slackclone.domain.model.users.DomainLayerUsers
import io.getstream.slackclone.domain.repository.UsersRepository
import io.getstream.slackclone.domain.usecases.BaseUseCase

class UseCaseFetchUsers(private val usersRepository: UsersRepository) :
  BaseUseCase<List<DomainLayerUsers.SlackUser>, Int> {
  override suspend fun perform(params: Int): List<DomainLayerUsers.SlackUser> {
    return usersRepository.getUsers(params)
  }
}
