package com.als.l2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.als.l2.model.IRepository
import com.als.l2.model.Repository
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve : MutableLiveData<AppState> = MutableLiveData()) : ViewModel() {

    private val repository: IRepository = Repository()

    fun getLiveData() : LiveData<AppState> = liveDataToObserve

    fun getWeather(){
        liveDataToObserve.postValue( AppState.Loading)
        Thread{
            sleep(2000)
            liveDataToObserve.postValue(AppState.Success(repository.getWeatherFromServer()))

        }.start()
    }
}