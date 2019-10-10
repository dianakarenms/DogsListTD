package com.dianakarenms.dogslisttd

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dianakarenms.data.models.Dog
import com.dianakarenms.utils.PrefsStorage

class DogListViewModel(private val activity: AppCompatActivity) : ViewModel() {
    val dogsList = MutableLiveData<ArrayList<Dog>>()

    init {
        dogsList.value = PrefsStorage.getItemFromPrefs(PrefsStorage.DOGS_LIST, activity) ?: arrayListOf()
    }

    fun addNew(dog: Dog) {
        val list = dogsList.value
        list?.add(dog)
        dogsList.value = list
        if (list != null) {
            PrefsStorage.saveItemToPrefs(PrefsStorage.DOGS_LIST, list, activity)
        }
    }
}