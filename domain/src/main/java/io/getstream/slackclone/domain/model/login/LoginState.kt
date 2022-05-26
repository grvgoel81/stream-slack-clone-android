package io.getstream.slackclone.domain.model.login

sealed class LoginState {
  object Nothing : LoginState()
  object Loading : LoginState()
  object Success : LoginState()
  class Failure(val message: String) : LoginState()
}
