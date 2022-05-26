package io.getstream.slackclone.uionboarding.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.slackclone.domain.model.login.LoginState
import io.getstream.slackclone.domain.usecases.users.UseCaseLoginUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingVM @Inject constructor(
  private val useCaseLoginUser: UseCaseLoginUser
) : ViewModel() {

  val input: MutableStateFlow<String> = MutableStateFlow("")
  val loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Nothing)

  fun connectUser() {
    viewModelScope.launch {
      loginState.value = LoginState.Loading
      loginState.value = useCaseLoginUser.perform(input.value)
    }
  }
}
