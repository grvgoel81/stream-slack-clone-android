package io.getstream.slackclone.domain.usecases.users

import io.getstream.slackclone.domain.model.login.LoginState
import io.getstream.slackclone.domain.repository.UsersRepository
import io.getstream.slackclone.domain.usecases.BaseUseCase

class UseCaseLoginUser(private val usersRepository: UsersRepository) :
  BaseUseCase<LoginState, String> {

  override suspend fun perform(params: String): LoginState {
    return usersRepository.login(params)
  }
}
