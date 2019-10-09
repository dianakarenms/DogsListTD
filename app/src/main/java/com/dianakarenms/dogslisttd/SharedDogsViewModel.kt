package com.dianakarenms.dogslisttd

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dianakarenms.models.Dog

class SharedDogsViewModel : ViewModel() {
    val dogsList = MutableLiveData<ArrayList<Dog>>()

    init {
        dogsList.value = ArrayList()
    }

    fun addNew(dog: Dog) {
        val list = dogsList.value
        list?.add(dog)
        dogsList.value = list
    }
}