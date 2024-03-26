package com.dkproject.data.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {
    @Provides
    @Singleton
    fun provideFirebaseFirestore():FirebaseFirestore{
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideFirebaseFireStorage():FirebaseStorage{
        return Firebase.storage
    }
}