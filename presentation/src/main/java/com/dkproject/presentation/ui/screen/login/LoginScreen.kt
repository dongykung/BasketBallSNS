package com.dkproject.presentation.ui.screen.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.activity.HomeActivity
import com.dkproject.presentation.ui.activity.UserFirstActivity
import com.dkproject.presentation.ui.component.GoogleButton
import com.dkproject.presentation.ui.component.KakaoButton
import com.dkproject.presentation.ui.theme.BasketballSNSTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val autoLogin = viewModel.autoLogin.value
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(result.idToken, null)
                viewModel.googleSignIn(credential, moveToHome = {
                    context.startActivity(Intent(context, HomeActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                }, moveToFirst = {
                    context.startActivity(Intent(context, UserFirstActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        putExtra("Uid",it)
                    })
                })
            } catch (e: ApiException) {
                Toast.makeText(context, "Failed Google Login", Toast.LENGTH_SHORT).show()
            }
        }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.homebackground),
            contentDescription = "Homebackground",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = "BasketBall",
                style = MaterialTheme.typography.headlineMedium,

            )
            Spacer(modifier = Modifier.weight(3f))
            Column(modifier = Modifier.weight(1f)) {
                if (autoLogin) {
                    CircularProgressIndicator()
                    context.startActivity(Intent(context,HomeActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                } else {
                    GoogleButton(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                        onClick = {
                            Log.d("Test", "onclick")
                            launcher.launch(viewModel.googleClientIntent)
                        })
                    Spacer(modifier = Modifier.height(22.dp))
                    KakaoButton(modifier= Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(50.dp),
                        onClick = {
                            viewModel.kakaoSignIn(
                                context=context,
                                moveToFirst = {
                                    context.startActivity(Intent(context, UserFirstActivity::class.java).apply {
                                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        putExtra("Uid",it)
                                    })
                                },
                                moveToHome = {
                                    context.startActivity(Intent(context, HomeActivity::class.java).apply {
                                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    })
                                }
                            )
                        })
                }
            }

        }

    }
}


@Preview
@Composable
fun LoginScrennPreview() {
    BasketballSNSTheme {
        LoginScreen()
    }
}
