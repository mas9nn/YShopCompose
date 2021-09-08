package kz.yshop.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.yshop.data.db.YShopDatabase
import kz.yshop.data.remote.YShopApi
import kz.yshop.data.remote.interceptors.AuthInterceptor
import kz.yshop.data.repository.ProductRepositoryImpl
import kz.yshop.data.repository.ShopRepositoryImpl
import kz.yshop.data.repository.UserRepositoryImpl
import kz.yshop.domain.repository.ProductRepository
import kz.yshop.domain.repository.ShopRepository
import kz.yshop.domain.repository.UserRepository
import kz.yshop.util.Constants
import kz.yshop.util.Constants.SHARED_PREF_NAME
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserRepository(api: YShopApi): UserRepository {
        return UserRepositoryImpl(api = api)
    }

    @Singleton
    @Provides
    fun provideProductRepository(api: YShopApi): ProductRepository {
        return ProductRepositoryImpl(api = api)
    }

    @Singleton
    @Provides
    fun provideShopRepository(api: YShopApi): ShopRepository {
        return ShopRepositoryImpl(api = api)
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor() = AuthInterceptor()

    @Singleton
    @Provides
    fun provideSharedPreference(app: Application): SharedPreferences = app.getSharedPreferences(
        SHARED_PREF_NAME,
        Context.MODE_PRIVATE
    )


    @Singleton
    @Provides
    fun provideDatabase(app: Application) = YShopDatabase(app)

    @Singleton
    @Provides
    fun provideYShopApi(authInterceptor: AuthInterceptor): YShopApi {
        val version = "/api/v1"
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .client(okkHttpClient)
            .baseUrl("${Constants.BASE_URL}$version/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YShopApi::class.java)
    }

}