package com.dkproject.presentation.ui.screen.login

data class GoogleSignInResult(
    val data:GoogleUserData?,
    val errorMessage:String?,
)

data class GoogleUserData(
    val userId:String,
    val username:String?,
    val photoUrl:String?,
)