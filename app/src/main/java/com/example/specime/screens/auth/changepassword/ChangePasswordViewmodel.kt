package com.example.specime.screens.auth.changepassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.specime.screens.auth.components.validateConfirmPassword
import com.example.specime.screens.auth.components.validateSignupPassword
import com.example.specime.userrepository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewmodel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    var state by mutableStateOf(ChangePasswordState())
        private set

    fun handleAction(action: ChangePasswordAction) {
        when (action) {
            is ChangePasswordAction.EnterOldPassword -> {
                state = state.copy(currentPassword = action.oldPassword)
                state = state.copy(currentPasswordError = null)
            }

            is ChangePasswordAction.EnterConfirmPassword -> {
                state = state.copy(confirmPassword = action.confirmPassword)
                state = state.copy(confirmPasswordError = null)
            }

            is ChangePasswordAction.EnterNewPassword -> {
                state = state.copy(newPassword = action.newPassword)
                state = state.copy(newPasswordError = null)
            }

            ChangePasswordAction.SubmitChange -> {
                val currentPasswordError = validateSignupPassword(state.currentPassword)
                val newPasswordError = validateSignupPassword(state.newPassword)
                val confirmPasswordError =
                    validateConfirmPassword(state.newPassword, state.confirmPassword)

                if (currentPasswordError != null || newPasswordError != null || confirmPasswordError != null) {
                    state = state.copy(
                        currentPasswordError = currentPasswordError,
                        newPasswordError = newPasswordError,
                        confirmPasswordError = confirmPasswordError
                    )
                    return
                }

                userRepository.changePasswordWithVerification(
                    currentPassword = state.currentPassword,
                    newPassword = state.newPassword
                ) { success, message ->
                    if (success) {
                        state = state.copy(isChanged = true)
                    } else {
                        state = state.copy(currentPasswordError = message)
                    }
                }
            }
        }
    }
}