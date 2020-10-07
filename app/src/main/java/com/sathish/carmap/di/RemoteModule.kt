package com.sathish.carmap.di

import com.google.android.gms.location.LocationRequest
import com.sathish.carmap.BuildConfig
import com.sathish.carmap.data.repo.CarRepo
import com.sathish.carmap.data.repo.CarRepoImpl
import com.sathish.carmap.data.repo.CarRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {  provideLocationRequest()}
    single { provideRetrofit(get()) }
    single { provideAvailableCarService(get()) }
    factory { okHttpClient() }
    single { CarRepoImpl(get()) }.bind(CarRepository::class)

}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}


fun okHttpClient(): OkHttpClient {
    val interceptor: HttpLoggingInterceptor? = HttpLoggingInterceptor()
    interceptor!!.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient().newBuilder().addInterceptor(interceptor)
        .build()
}


fun provideAvailableCarService(retrofit: Retrofit): CarRepo =
    retrofit.create(CarRepo::class.java)

fun provideLocationRequest(): LocationRequest? {
    return LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        .setInterval(1000)
}