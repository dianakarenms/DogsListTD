package com.dianakarenms.utils

import android.app.Activity
import android.preference.PreferenceManager
import com.dianakarenms.data.models.Dog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefsStorage {
    companion object {
        /**
         * Save and get HashMap in SharedPreference
         */

        val DOGS_LIST = "dogs_list"

        fun saveItemToPrefs(key: String, obj: Any, activity: Activity) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
            val editor = prefs.edit()
            val gson = Gson()
            val json = gson.toJson(obj)
            editor.putString(key, json)
            editor.apply()     // This line is IMPORTANT !!!
        }

        inline fun <reified T> getItemFromPrefs(key: String, activity: Activity): T? {
            val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
            val json = prefs.getString(key, "")
            val type = object : TypeToken<T>() {}.type
            return if(json.isNotEmpty())
                Gson().fromJson<T>(json, type)
            else
                null
        }
    }
}