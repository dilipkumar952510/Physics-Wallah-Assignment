package com.physicswallah.assignment.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.physicswallah.assignment.ui.viewModelRepo.CharacterRepo
import com.physicswallah.assignment.ui.viewModelRepo.CharacterViewModel

@Suppress("UNCHECKED_CAST")

class ViewModelFactory(private val repository : BaseRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(CharacterViewModel::class.java) -> CharacterViewModel(repository as CharacterRepo) as T
            else -> throw IllegalArgumentException("view model class not found")
        }
    }
}