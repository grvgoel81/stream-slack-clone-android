package io.getstream.slackclone.domain.usecases.users

import io.getstream.slackclone.domain.repository.UsersRepository
import io.getstream.slackclone.domain.usecases.BaseUseCase

class UseCaseLogoutUser(private val usersRepository: UsersRepository) :
  BaseUseCase<Unit, Unit> {
  override suspend fun perform() {
    usersRepository.logout()
  }
}
