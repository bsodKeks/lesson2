package com.als.l2.presentation.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.als.l2.model.AppState
import com.als.l2.presentation.App.Companion.getHistoryDao
import com.als.l2.repository.ILocalRepository
import com.als.l2.repository.LocalRepository

class HistoryViewModel (
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepository: ILocalRepository = LocalRepository(getHistoryDao())
) : ViewModel() {

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        historyLiveData.value = AppState.Success(historyRepository.getAllHistory())
    }
}
