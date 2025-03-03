package com.example.alkamerce.ui.feature.account.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alkamerce.R
import com.example.alkamerce.navigation.HomeScreen
import com.example.alkamerce.navigation.RegisterScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = koinViewModel()) {

    val loginState = viewModel.loginState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = loginState.value) {
            is LoginState.Success -> {
                LaunchedEffect(loginState.value) {
                    navController.navigate(HomeScreen) {
                        popUpTo(HomeScreen) {
                            inclusive = true
                        }
                    }
                }
            }

            is LoginState.Error -> {
                Text(text = state.message, modifier = Modifier.testTag("errorMsg"))
                // Show error message
            }

            is LoginState.Loading -> {
                CircularProgressIndicator()
                Text(text = stringResource(id = R.string.loading))
            }

            else -> {
                LoginContent(onSignInClicked = { email, password ->
                    viewModel.login(email, password)
                                               },
                    onRegisterClick = {
                        navController.navigate(RegisterScreen)
                    })
            }
        }
    }
}


@Composable
fun LoginContent(onSignInClicked: (String, String) -> Unit, onRegisterClick: () -> Unit) {
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    val tealColor = Color(0xFF03DAC5)
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // alkamerce logo image moved up
        Image(
            painter = painterResource(id = R.drawable.light_logo),
            contentDescription = null,
            modifier = Modifier
                .size(250.dp)
                .padding(horizontal = 5.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

//        val emailFocusRequester = remember { FocusRequester() }
//        val passwordFocusRequester = remember { FocusRequester() }

        val emailFocused = remember { mutableStateOf(false) }
        val passwordFocused = remember { mutableStateOf(false) }

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .testTag("emailField")
//                .focusRequester(emailFocusRequester)
                .onFocusChanged {
                    emailFocused.value = it.isFocused
                },
            label = { Text(text = stringResource(id = R.string.email)) },
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tealColor,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = tealColor
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
//            keyboardActions = KeyboardActions(
//                onNext = { passwordFocusRequester.requestFocus() }
//            )
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .testTag("passwordField")
//                .focusRequester(passwordFocusRequester)
                .onFocusChanged {
                    passwordFocused.value = it.isFocused
                },
            label = { Text(text = stringResource(id = R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tealColor,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = tealColor
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                        onSignInClicked(email.value, password.value)
                    }
                }
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onSignInClicked(email.value, password.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("loginButton"),
            enabled = email.value.isNotEmpty() && password.value.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = tealColor,
                disabledContainerColor = tealColor.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.dont_have_account) + " ")
            Text(
                text = stringResource(id = R.string.register),
                color = tealColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onRegisterClick() }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewLoginScreen() {
    LoginContent(onSignInClicked = { email, password ->
    }, onRegisterClick = {})
}