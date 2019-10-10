package com.dianakarenms.data.viewmodels

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dianakarenms.dogslisttd.DogListViewModel

class ViewModelFactory(private val application: AppCompatActivity) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DogListViewModel(application) as T
    }

}