package ru.nickmiller.tinkofffintech.di

import android.arch.persistence.room.Room
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.nickmiller.tinkofffintech.BuildConfig
import ru.nickmiller.tinkofffintech.data.Api
import ru.nickmiller.tinkofffintech.data.cache.AppDatabase
import ru.nickmiller.tinkofffintech.data.repository.CoursesRepository
import ru.nickmiller.tinkofffintech.data.repository.EventsRepository
import ru.nickmiller.tinkofffintech.data.repository.LoginRepository
import ru.nickmiller.tinkofffintech.data.repository.ProfileRepository
import ru.nickmiller.tinkofffintech.ui.courses.CoursesViewModel
import ru.nickmiller.tinkofffintech.ui.events.EventsViewModel
import ru.nickmiller.tinkofffintech.ui.login.LoginViewModel
import ru.nickmiller.tinkofffintech.ui.profile.ProfileViewModel
import ru.nickmiller.tinkofffintech.utils.AuthInterceptor
import ru.nickmiller.tinkofffintech.utils.NetworkUtil
import ru.nickmiller.tinkofffintech.utils.cookies.AddCookiesInterceptor
import ru.nickmiller.tinkofffintech.utils.cookies.CookiesStore
import ru.nickmiller.tinkofffintech.utils.cookies.GetCookiesInterceptor


val mainModule: Module = module {
    viewModel { ProfileViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { EventsViewModel(get()) }
    viewModel { CoursesViewModel(get()) }
    single { LoginRepository(get(), get(), get()) }
    single { ProfileRepository(get(), get(), get(), get()) }
    single { EventsRepository(get(), get(), get()) }
    single { CoursesRepository(get(), get(), get()) }
}

val networkModule: Module = module {
    single { NetworkUtil(androidContext()) }
    single { GsonBuilder().setLenient().create() }
    single { CookiesStore(androidApplication()) }
    single { GetCookiesInterceptor(get()) }
    single { AddCookiesInterceptor(get()) }
    single { AuthInterceptor(androidApplication()) }
    single { RxJava2CallAdapterFactory.create() }
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(get() as AuthInterceptor)
            .addInterceptor(get() as GetCookiesInterceptor)
            .addInterceptor(get() as AddCookiesInterceptor)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(get() as RxJava2CallAdapterFactory)
            .client(get())
            .build()
            .create(Api::class.java)
    }
}

val cacheModule: Module = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "fintech-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
 
 
