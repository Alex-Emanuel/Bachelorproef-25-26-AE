package com.example.dpdetectorapplication.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dpdetectorapplication.data.model.DarkPattern
import com.example.dpdetectorapplication.data.model.Detectie
import com.example.dpdetectorapplication.data.model.darkPatterns
import com.example.dpdetectorapplication.data.repository.DetectieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetectieRepository
) : ViewModel() {

    private val _detectie = MutableStateFlow<Detectie?>(null)
    val detectie: StateFlow<Detectie?> = _detectie

    val pattern: DarkPattern?
        get() = _detectie.value?.let { detectie ->
            darkPatterns.find { pattern ->
                pattern.id == detectie.patroonId
            }
        }

    fun loadDetectie(id: Int) {
        viewModelScope.launch {
            _detectie.value = repository.getDetectieById(id)
            repository.markAsRead(id)
        }
    }
}