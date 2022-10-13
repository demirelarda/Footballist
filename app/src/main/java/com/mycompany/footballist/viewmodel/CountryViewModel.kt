package com.mycompany.footballist.viewmodel


import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mycompany.footballist.model.country.Country
import com.mycompany.footballist.model.country.CountryResponse
import com.mycompany.footballist.service.FootballApiService
import com.mycompany.footballist.service.FutDatabase
import com.mycompany.footballist.utils.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) : BaseViewModel(application){

    private val footballApiService = FootballApiService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 30*60*1000*1000*1000L


    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()


    fun refreshData(){
        val updateTime = customPreferences.getTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()
        }
        else{
            getDataFromApi()
        }

    }

    fun refreshFromAPI(){
        getDataFromApi()
    }

    private fun getDataFromSQLite(){
        countryLoading.value = true
        launch {
            val countries = FutDatabase(getApplication()).footballDao().getAllCountries()
            showCountries(countries)
        }


    }

    private fun getDataFromApi(){
        countryLoading.value = true

        disposable.add(
            footballApiService.getCountryList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CountryResponse>(){
                    override fun onSuccess(t: CountryResponse) {
                        storeCountriesInSQLite(t.response)
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                    }

                })
        )

    }


    private fun showCountries(countryList : List<Country>){
        countryLoading.value = false
        countries.value = countryList
        countryError.value = false
    }

    private fun storeCountriesInSQLite(countryList : List<Country>){
        launch {
            val dao = FutDatabase(getApplication()).footballDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAllCountries(*countryList.toTypedArray())
            var i = 0
            while(i<countryList.size){
                countryList[i].uuids = listLong[i].toInt()
                i += 1
            }

            showCountries(countryList)

        }

        customPreferences.saveTime(System.nanoTime())


    }






}