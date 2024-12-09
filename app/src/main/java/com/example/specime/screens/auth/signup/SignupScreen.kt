package com.example.specime.screens.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.specime.screens.auth.components.FlexibleButton
import com.example.specime.components.common.FlexibleTextField

@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: SignupViewmodel = hiltViewModel()
) {
    val state = viewModel.state

    LaunchedEffect(state.isSignedUp) {
        if (state.isSignedUp) {
            val title = "Đăng ký thành công"
            val message = "Vui lòng kiểm tra hộp thư đến của bạn"
            val buttonText = "ĐĂNG NHẬP"
            val route = "login"
            navController.navigate("confirmation/$title/$message/$buttonText/$route")
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Đăng Ký",
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(30.dp))
        FlexibleTextField(
            label = "Email",
            value = state.email,
            width = 320,
            height = 50,
            rounded = 7,
            onValueChange = { email ->
                viewModel.handleAction(SignupAction.EnterEmail(email))
            },
            leadingIcon = Icons.Filled.Email,
            errorMessage = state.emailError
        )
        FlexibleTextField(
            label = "Tên người dùng",
            value = state.username,
            width = 320,
            height = 50,
            rounded = 7,
            onValueChange = { username ->
                viewModel.handleAction(SignupAction.EnterUsername(username))
            },
            leadingIcon = Icons.Filled.Person,
            errorMessage = state.usernameError
        )
        FlexibleTextField(
            label = "Mật khẩu",
            value = state.password,
            width = 320,
            height = 50,
            rounded = 7,
            onValueChange = { password ->
                viewModel.handleAction(SignupAction.EnterPassword(password))
            },
            leadingIcon = Icons.Filled.Lock,
            errorMessage = state.passwordError,
            isPassword = true
        )
        FlexibleTextField(
            label = "Nhập lại mật khẩu",
            value = state.confirmPassword,
            width = 320,
            height = 50,
            rounded = 7,
            onValueChange = { confirmPassword ->
                viewModel.handleAction(SignupAction.EnterConfirmPassword(confirmPassword))
            },
            leadingIcon = Icons.Filled.Lock,
            errorMessage = state.confirmPasswordEroor,
            isPassword = true
        )
        Spacer(modifier = Modifier.height(30.dp))
        FlexibleButton(
            text = "ĐĂNG KÝ",
            width = 320,
            height = 45,
            onClick = {
                viewModel.handleAction(SignupAction.SubmitSignup)
            },
            rounded = 40
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 30.dp)
        ) {
            Text(
                "Bạn đã có tài khoản? ",
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "Đăng nhập",
                color = MaterialTheme.colorScheme.inversePrimary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.clickable {
                    navController.navigate("login")
                }
            )
        }
    }

    if (state.isSigningUp) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}