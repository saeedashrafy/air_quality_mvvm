package com.ashr.cleanMvvmAir.remote.di

import com.ashr.cleanMvvmAir.data.datasource.remote.CityRemoteDataSource
import com.ashr.cleanMvvmAir.data.datasource.remote.CountryRemoteDataSource
import com.ashr.cleanMvvmAir.data.datasource.remote.StateRemoteDataSource
import com.ashr.cleanMvvmAir.remote.mapper.CityMapper
import com.ashr.cleanMvvmAir.remote.mapper.CountryMapper
import com.ashr.cleanMvvmAir.remote.mapper.StateMapper
import com.ashr.cleanMvvmAir.remote.service.AirQualityService
import com.ashr.cleanMvvmAir.remote.service.Interceptor
import com.ashr.cleanMvvmAir.remote.source.CityRemoteDataSourceImpl
import com.ashr.cleanMvvmAir.remote.source.CountryRemoteDataSourceImpl
import com.ashr.cleanMvvmAir.remote.source.StateRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
            .addInterceptor(Interceptor())
            .build()


    @Singleton
    @Provides
    fun provideAirQualityApiService(retrofit: Retrofit): AirQualityService =
        retrofit.create(AirQualityService::class.java)

    @Provides
    @Singleton
    fun provideCountryRemoteDataSource(
        airQualityService: AirQualityService,
        countryMapper: CountryMapper
    ): CountryRemoteDataSource = CountryRemoteDataSourceImpl(airQualityService, countryMapper)

    @Provides
    @Singleton
    fun provideStateRemoteDataSource(
        airQualityService: AirQualityService,
        stateMapper: StateMapper
    ): StateRemoteDataSource = StateRemoteDataSourceImpl(airQualityService, stateMapper)


    @Provides
    @Singleton
    fun provideCityRemoteDataSource(
        airQualityService: AirQualityService,
        cityMapper: CityMapper
    ): CityRemoteDataSource = CityRemoteDataSourceImpl(airQualityService, cityMapper)

    companion object {

        const val BASE_URL = "https://api.airvisual.com/v2/"
    }
}