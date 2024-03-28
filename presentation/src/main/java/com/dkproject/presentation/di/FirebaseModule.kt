package com.dkproject.presentation.di

import android.content.Context
import com.dkproject.presentation.BuildConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
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
     val API_KEY :String = BuildConfig.GOOGLE_API_KEY
     val LOGIN_KEY:String=BuildConfig.GOOGLE_LOGIN_KEY
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
            .requestIdToken(LOGIN_KEY)
            .build()
    }

    @Provides
    @Singleton
    fun provideGoogleClient(@ApplicationContext context: Context, gso: GoogleSignInOptions): GoogleSignInClient {
        return GoogleSignIn.getClient(context,gso)
    }

    @Provides
    @Singleton
    fun providePlacesClient(@ApplicationContext context:Context) : PlacesClient{
        Places.initialize(context, API_KEY)
        return Places.createClient(context)
    }
}