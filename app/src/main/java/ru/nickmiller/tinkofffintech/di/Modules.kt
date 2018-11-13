package ru.nickmiller.tinkofffintech.di

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
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
import ru.nickmiller.tinkofffintech.data.repository.CoursesRepository
import ru.nickmiller.tinkofffintech.data.repository.EventsRepository
import ru.nickmiller.tinkofffintech.data.repository.LoginRepository
import ru.nickmiller.tinkofffintech.data.repository.ProfileRepository
import ru.nickmiller.tinkofffintech.ui.courses.CoursesViewModel
import ru.nickmiller.tinkofffintech.ui.events.EventsViewModel
import ru.nickmiller.tinkofffintech.ui.login.LoginViewModel
import ru.nickmiller.tinkofffintech.ui.profile.ProfileViewModel
import ru.nickmiller.tinkofffintech.utils.NetworkUtil
import ru.nickmiller.tinkofffintech.utils.cookies.AddCookiesInterceptor
import ru.nickmiller.tinkofffintech.utils.cookies.CookiesStore
import ru.nickmiller.tinkofffintech.utils.cookies.GetCookiesInterceptor


val mainModule: Module = module {
    viewModel { ProfileViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { EventsViewModel(get()) }
    viewModel { CoursesViewModel(get()) }
    single { LoginRepository(get(), get()) }
    single { ProfileRepository(get(), get()) }
    single { EventsRepository(get(), get()) }
    single { CoursesRepository(get(), get()) }
}

val networkModule: Module = module {
    single { NetworkUtil(androidContext()) }
    single { GsonBuilder().setLenient().create() }
    single { CookiesStore(androidApplication()) }
    single { GetCookiesInterceptor(get()) }
    single { AddCookiesInterceptor(get()) }
    single { RxJava2CallAdapterFactory.create() }
    single {
        OkHttpClient.Builder()
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
 
 
