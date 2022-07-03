package com.example.flow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val countDownTimerFlow = flow<Int> {
        val countDownFrom = 20
        var counter = countDownFrom
        while (counter > 0) {
            delay(1000)
            counter--
            emit(counter)
        }
    }

    init {
        collectInViewModel()
        collectInViewModelOnEach()
        collectInViewModelCollectLatest()
    }

    private fun collectInViewModel() {
        viewModelScope.launch {
            countDownTimerFlow.filter {
                it % 3 == 0
            }.map {
                it + it
            }.collect {
                println("counter is $it")
            }
        }
    }

    private fun collectInViewModelOnEach() {
        // Daha temiz bir kullanim
        countDownTimerFlow.onEach {
            println("$it")
        }.launchIn(viewModelScope)
    }

    private fun collectInViewModelCollectLatest() {
        viewModelScope.launch {
            countDownTimerFlow.collectLatest {
                delay(2000)
                println("$it")
            }
        }
    }


    // Livedata comparisons
    private val _liveData = MutableLiveData<String>("Hello World")
    val liveData: LiveData<String> = _liveData

    fun changeLiveData() {
        _liveData.value = "Hello Omer"
    }

    private val _stateFlow = MutableStateFlow("Hello Ahmet")
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun changeStateFlowValue() {
        _stateFlow.value = "Hello Google"
    }

    fun changeSharedFlowValue() {
        viewModelScope.launch {
            _sharedFlow.emit("Hello Sinan")
        }
    }
}