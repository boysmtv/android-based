package com.kotlin.learn.api.splash.di

import android.content.Context
import com.kotlin.learn.api.splash.data.api.SplashApi
import com.kotlin.learn.api.splash.data.local.SplashAppConfigResource
import com.kotlin.learn.api.splash.data.local.SplashLanguagePackResource
import com.kotlin.learn.core.entity.auth.LanguagePack
import com.kotlin.learn.core.entity.splash.AppConfigResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SplashApiModule {

    @Singleton
    @Provides
    fun provideSplashApi(retrofit: Retrofit): SplashApi = retrofit.create(SplashApi::class.java)

    @Singleton
    @Provides
    fun provideAppConfigJsonAdapter(
        moshi: Moshi
    ): JsonAdapter<AppConfigResponse> = moshi.adapter(AppConfigResponse::class.java)

    @Singleton
    @Provides
    fun provideAppConfigResource(
        @ApplicationContext app: Context,
        adapter: JsonAdapter<AppConfigResponse>
    ) = SplashAppConfigResource(app, adapter)


    @Singleton
    @Provides
    fun provideLanguagePackJsonAdapter(moshi: Moshi): JsonAdapter<LanguagePack> =
        moshi.adapter(LanguagePack::class.java)

    @Singleton
    @Provides
    fun provideLanguagePackResource(
        @ApplicationContext app: Context,
        adapter: JsonAdapter<LanguagePack>
    ) = SplashLanguagePackResource(app, adapter)

}
