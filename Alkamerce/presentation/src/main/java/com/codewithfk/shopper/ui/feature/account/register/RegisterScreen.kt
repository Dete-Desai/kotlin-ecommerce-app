package com.codewithfk.shopper.ui.feature.account.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codewithfk.shopper.navigation.HomeScreen
import com.codewithfk.shopper.navigation.RegisterScreen
import com.codewithfk.shopper.ui.feature.account.login.LoginState
import com.codewithfk.shopper.ui.feature.account.login.LoginViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.codewithfk.shopper.R

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = koinViewModel()) {

    val loginState = viewModel.registerState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),  // Added white background here
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = loginState.value) {
            is RegisterState.Success -> {
                LaunchedEffect(loginState.value) {
                    navController.navigate(HomeScreen) {
                        popUpTo(HomeScreen) {
                            inclusive = true
                        }
                    }
                }
            }

            is RegisterState.Error -> {
                Text(text = state.message)
                // Show error message
            }

            is RegisterState.Loading -> {
                CircularProgressIndicator()
                Text(text = stringResource(id = R.string.loading))
            }

            else -> {
                RegisterContent(onRegisterClicked = { email, password, name ->
                    viewModel.register(email = email, password = password, name = name)
                },
                    onSignInClick = {
                        navController.popBackStack()
                    })
            }
        }
    }
}


@Composable
fun RegisterContent(
    onRegisterClicked: (String, String, String) -> Unit,
    onSignInClick: () -> Unit
) {
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val name = remember {
        mutableStateOf("")
    }

    val tealColor = Color(0xFF03DAC5)
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)  // Added white background here too
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Add logo at the top
        Image(
            painter = painterResource(id = R.drawable.light_logo),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .padding(horizontal = 5.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        val nameFocusRequester = remember { FocusRequester() }
        val emailFocusRequester = remember { FocusRequester() }
        val passwordFocusRequester = remember { FocusRequester() }

        OutlinedTextField(
            value = name.value,
            onValueChange = {
                name.value = it
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .focusRequester(nameFocusRequester),
            label = { Text(text = stringResource(id = R.string.name)) },
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tealColor,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = tealColor
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { emailFocusRequester.requestFocus() }
            )
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            modifier = Modifier
                .padding(vertical = 3.dp)
                .fillMaxWidth()
                .focusRequester(emailFocusRequester),
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
            keyboardActions = KeyboardActions(
                onNext = { passwordFocusRequester.requestFocus() }
            )
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester),
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
                    if (email.value.isNotEmpty() && password.value.isNotEmpty() && name.value.isNotEmpty()) {
                        onRegisterClicked(email.value, password.value, name.value)
                    }
                }
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onRegisterClicked(email.value, password.value, name.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = email.value.isNotEmpty() && password.value.isNotEmpty() && name.value.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = tealColor,
                disabledContainerColor = tealColor.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.alread_have_an_account) + " ")
            Text(
                text = stringResource(id = R.string.login),
                color = tealColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onSignInClick() }
            )
        }
    }
}
