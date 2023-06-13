package com.kotlin.learn.catalog.core.network.di;

import java.lang.System;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007\u00a8\u0006\n"}, d2 = {"Lcom/kotlin/learn/catalog/core/network/di/KtorModule;", "", "()V", "provideChuckerInterceptor", "Lcom/chuckerteam/chucker/api/ChuckerInterceptor;", "context", "Landroid/content/Context;", "provideKtorClient", "Lcom/kotlin/learn/catalog/core/network/KtorClient;", "chuckerInterceptor", "network_debug"})
@dagger.Module
public final class KtorModule {
    
    public KtorModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @javax.inject.Singleton
    @dagger.Provides
    public final com.chuckerteam.chucker.api.ChuckerInterceptor provideChuckerInterceptor(@org.jetbrains.annotations.NotNull
    @dagger.hilt.android.qualifiers.ApplicationContext
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @javax.inject.Singleton
    @dagger.Provides
    public final com.kotlin.learn.catalog.core.network.KtorClient provideKtorClient(@org.jetbrains.annotations.NotNull
    com.chuckerteam.chucker.api.ChuckerInterceptor chuckerInterceptor) {
        return null;
    }
}