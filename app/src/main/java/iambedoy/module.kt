package iambedoy

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import iambedoy.coronatracker.ServiceApi
import iambedoy.coronatracker.fragment.countries.CountryListFragment
import iambedoy.coronatracker.fragment.CountryFragment
import iambedoy.coronatracker.fragment.JHUListFragment
import iambedoy.coronatracker.repository.CoronaRepository
import iambedoy.coronatracker.viewmodel.CoronaViewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Corona Tracker
 *
 * Created by bedoy on 13/05/20.
 */

private val moshi = Moshi.Builder()
    //.add(XNullableAdapterFactory())
    .add(KotlinJsonAdapterFactory())
    //.add(TypesAdapterFactory())
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://disease.sh")
    .addCallAdapterFactory(NetworkResponseAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

val repository = module {
    single {
        CoronaRepository(get())
    }
}

val viewModel = module {
    factory {
        CoronaViewModel(get())
    }
}

val service = module {
    single {
        retrofit.create(ServiceApi::class.java)
    }
}

val fragments = module {
    factory {
        CountryListFragment()
    }
    factory {
        JHUListFragment()
    }
    factory {
        CountryFragment()
    }
}

val injections = listOf(repository, viewModel, service, fragments)