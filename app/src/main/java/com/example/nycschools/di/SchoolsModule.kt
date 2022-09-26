package com.example.nycschools.di

import com.example.nycschools.SchoolRepository
import com.example.nycschools.SchoolRepositoryImpl
import com.example.nycschools.network.SchoolsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SchoolsModule {

    @Singleton
    @Provides
    fun provideSchoolsApi(retrofit: Retrofit): SchoolsApi =
        retrofit.create(SchoolsApi::class.java)

    @Singleton
    @Provides
    fun providesSchoolsRepository(schoolsApi: SchoolsApi): SchoolRepository =
        SchoolRepositoryImpl(schoolsApi)
}
