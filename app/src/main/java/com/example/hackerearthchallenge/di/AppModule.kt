package com.example.hackerearthchallenge.di

import android.content.Context
import com.example.hackerearthchallenge.data.network.DataApi
import com.example.hackerearthchallenge.data.RemoteDataSource
import com.example.hackerearthchallenge.utils.CShowProgress
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesAuthApi(remoteDataSource: RemoteDataSource): DataApi {
        return remoteDataSource.buildApi(DataApi::class.java)
    }

    @Provides
    fun provideProgressDialog(@ApplicationContext context: Context) : CShowProgress {
        return CShowProgress(context)
    }
}