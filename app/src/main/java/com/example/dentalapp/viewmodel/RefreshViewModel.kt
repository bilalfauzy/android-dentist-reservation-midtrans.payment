package com.example.dentalapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RefreshViewModel : ViewModel() {
    private val _needRefresh = mutableStateOf(false)
    val needRefresh: State<Boolean> = _needRefresh

    fun refreshPage() {
        _needRefresh.value = true
    }

    fun resetRefreshStatus() {
        _needRefresh.value = false
    }
}