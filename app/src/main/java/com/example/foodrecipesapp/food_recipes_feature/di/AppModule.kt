package com.example.foodrecipesapp.food_recipes_feature.di

import android.content.Context
import androidx.room.Room
import com.example.foodrecipesapp.utils.Constants.Companion.BASE_URL
import com.example.foodrecipesapp.food_recipes_feature.data.api.ApiService
import com.example.foodrecipesapp.food_recipes_feature.data.db.MealDao
import com.example.foodrecipesapp.food_recipes_feature.data.db.MealDatabase
import com.example.foodrecipesapp.food_recipes_feature.data.repository.CategoryMealsRepositoryImpl
import com.example.foodrecipesapp.food_recipes_feature.data.repository.HomeRepositoryImpl
import com.example.foodrecipesapp.food_recipes_feature.data.repository.MealsRepositoryImpl
import com.example.foodrecipesapp.food_recipes_feature.domain.repository.CategoryMealsRepository
import com.example.foodrecipesapp.food_recipes_feature.domain.repository.HomeRepository
import com.example.foodrecipesapp.food_recipes_feature.domain.repository.MealsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHomeRepository(api: ApiService, db: MealDatabase): HomeRepository {
        return HomeRepositoryImpl(db.mealDao(), api)
    }
    @Provides
    @Singleton
    fun provideMealsRepository(api: ApiService, db: MealDatabase): MealsRepository {
        return MealsRepositoryImpl(db.mealDao(), api)
    }

    @Provides
    @Singleton
    fun provideCategoryMealsRepository(api: ApiService): CategoryMealsRepository {
        return CategoryMealsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): MealDatabase =
        Room.databaseBuilder(context, MealDatabase::class.java, "postDatabase")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesPostDao(mealDatabase: MealDatabase): MealDao =
        mealDatabase.mealDao()

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


}