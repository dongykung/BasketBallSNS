package com.dkproject.presentation.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }


    @Provides
    @Singleton
    fun provideGoogleSignOptions(@ApplicationContext context: Context): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().requestId()
            .requestIdToken("862407085606-c9vtcqnuh9ibhdfdpoe0a1idpvdk9edf.apps.googleusercontent.com")
            .build()
    }

    @Provides
    @Singleton
    fun provideGoogleClient(@ApplicationContext context: Context, gso: GoogleSignInOptions): GoogleSignInClient {
        return GoogleSignIn.getClient(context,gso)
    }
}