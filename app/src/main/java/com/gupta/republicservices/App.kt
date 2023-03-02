package com.gupta.republicservices

import android.app.Application
import androidx.room.Room
import com.gupta.republicservices.db.*
import com.gupta.republicservices.network.RemoteDataSource
import com.gupta.republicservices.network.provideApi
import com.gupta.republicservices.network.provideRetrofit
import com.gupta.republicservices.repositories.ApiRepository
import com.gupta.republicservices.viewmodels.MainViewModel
import com.gupta.republicservices.viewmodels.RouteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(module {
                single { ApiRepository(get(), get(), get()) }
                viewModel { MainViewModel(get()) }
                viewModel { (driverId: String) -> RouteViewModel(driverId, get()) }
                single { provideRetrofit() }
                factory { provideApi(get()) }
                single {
                    Room.databaseBuilder(
                        androidContext(),
                        AppDatabase::class.java, "app-database"
                    ).fallbackToDestructiveMigration().build()
                }
                single { provideDriverDataDao(get())}
                single { provideRouteDataDao(get())}
                factory { RemoteDataSource(get()) }
            })
        }
    }

}
