package com.example.hackerearthchallenge.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackerearthchallenge.data.Resource
import com.example.hackerearthchallenge.data.models.DataResponse
import com.example.hackerearthchallenge.data.network.DataApi
import com.example.hackerearthchallenge.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val userApi: DataApi
    ) : ViewModel() {

    private val _dataRes: MutableLiveData<Resource<DataResponse>> = MutableLiveData()

    val datRes: LiveData<Resource<DataResponse>>
        get() = _dataRes

    fun fetchData() = viewModelScope.launch {
        _dataRes.value = Resource.Loading
        _dataRes.value = repository.getQuesData()
    }

}