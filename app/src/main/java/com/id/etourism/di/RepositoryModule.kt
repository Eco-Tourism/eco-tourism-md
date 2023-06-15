package com.id.etourism.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.id.etourism.data.local.SessionManager
import com.id.etourism.data.network.ApiServices
import com.id.etourism.data.network.repository.auth.AuthRepository
import com.id.etourism.data.network.repository.auth.AuthRepositoryImpl
import com.id.etourism.data.network.repository.wisata.WisataRepository
import com.id.etourism.data.network.repository.wisata.WisataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(
        database: FirebaseFirestore
    ): WisataRepository {
        return WisataRepositoryImpl(database)
    }
    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        fireStore: FirebaseFirestore,
        apiServices: ApiServices,
        sessioinManager : SessionManager
    ): AuthRepository {
        return AuthRepositoryImpl(
            firebaseAuth,
            fireStore,
            apiServices,
            sessioinManager
        )
    }
}