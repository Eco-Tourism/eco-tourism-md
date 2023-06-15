package com.id.etourism.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.id.etourism.data.network.model.Wisata
import com.id.etourism.data.network.repository.wisata.WisataRepository
import com.id.etourism.utils.ExceptionState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WisataRepository
): ViewModel() {

    private val _data = MutableLiveData<ExceptionState<List<Wisata>>>()
    val data: LiveData<ExceptionState<List<Wisata>>>
            get() = _data

    fun getWisata() {
        _data.value = ExceptionState.Loading
        repository.getWisata { _data.value = it }
    }
    fun searchWisata(search:String){
        _data.value = ExceptionState.Loading
        repository.getWisata { _data.value = it }
    }



}