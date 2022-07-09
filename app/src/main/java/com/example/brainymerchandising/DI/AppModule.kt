package com.example.brainymerchandising.DI

import android.content.Context
import android.content.SharedPreferences
import com.example.brainymerchandising.BuildConfig
import com.example.brainymerchandising.Display.Repository.Display_Service
import com.example.brainymerchandising.Login.Repository.AuthService
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Product.Repository.Product_Service
import com.example.brainymerchandising.Utils.Token.AuthInterceptor
import com.example.brainymerchandising.Utils.Token.EncodeInterceptors
import com.example.brainymerchandising.Utils.Token.SessionManager
import com.example.brainymerchandising.Utils.resources.Server_date.Repository.Date_service
import com.example.brainymerchandising.Utils.resources.Urls
import com.example.brainymerchandising.Visite.Store.Repository.StoreService
import com.example.brainymerchandising.Visite.visite.Repository.VisiteService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module

@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context) = if (BuildConfig.DEBUG) {
        val authInterceptor = AuthInterceptor(context)
        val logIntercept = HttpLoggingInterceptor()
        val encodeInterceptors = EncodeInterceptors()
        logIntercept.setLevel(HttpLoggingInterceptor.Level.BODY)


        OkHttpClient.Builder()
         //   .addInterceptor(encodeInterceptors)
            .addInterceptor(authInterceptor)
            .addInterceptor(logIntercept)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    @Named("Normal")
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient):
            Retrofit = Retrofit.Builder()
        .baseUrl(Urls.baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()


    @Singleton
    @Provides
    @Named("Time")
    fun provideRetrofitTime(gson: Gson, okHttpClient: OkHttpClient):
            Retrofit = Retrofit.Builder()
        .baseUrl(Urls.timeTunisUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()


    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)}

    @Provides
    @Singleton
    fun provideAuthInterceptor(@ApplicationContext context: Context): AuthInterceptor {
        return AuthInterceptor(context) }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE) }

    @Provides
    fun provideAuthService(@Named("Normal") retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    fun provideVisiteService(@Named("Normal") retrofit: Retrofit): VisiteService =
        retrofit.create(VisiteService::class.java)

    @Provides
    fun provideDateService(@Named("Normal") retrofit: Retrofit): Date_service =
        retrofit.create(Date_service::class.java)

    @Provides
    fun provideStoreService(@Named("Normal") retrofit: Retrofit): StoreService =
        retrofit.create(StoreService::class.java)

    @Provides
    fun provideDisplayService(@Named("Normal") retrofit: Retrofit): Display_Service =
        retrofit.create(Display_Service::class.java)

    @Provides
    fun provideProductService(@Named("Normal") retrofit: Retrofit): Product_Service =
        retrofit.create(Product_Service::class.java)





}