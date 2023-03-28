package dev.future.taxipark.utils

import com.google.gson.Gson
import android.content.SharedPreferences
class PrefsHelper(
    private val gson: Gson,
    private val preferences: SharedPreferences
) {
    var language: String?
        get() = preferences.getString(Keys.LANGUAGE, "")

        set(value) = preferences.put(Keys.LANGUAGE, value)


    var isRegistered: Boolean
        get() = preferences.getBoolean(Keys.IS_REGISTERED, false)
        set(value) = preferences.put(Keys.IS_REGISTERED, value)

    var accessToken: String?
        get() = preferences.getString(Keys.ACCESS_TOKEN, null)
        set(value) = preferences.put(Keys.ACCESS_TOKEN, value)

    var phoneNumber: String?
        get() = preferences.getString(Keys.PHONE_NUMBER, null)
        set(value) = preferences.put(Keys.PHONE_NUMBER, value)

    var refreshToken: String?
        get() = preferences.getString(Keys.REFRESH_TOKEN, null)
        set(value) = preferences.put(Keys.REFRESH_TOKEN, value)

    var password: String?
        get() = preferences.getString(Keys.PASSWORD, null)
        set(value) = preferences.put(Keys.PASSWORD, value)

    var tokenExpiry: Long
        get() = preferences.getLong(Keys.TOKEN_EXPIRY, 0)
        set(value) = preferences.put(Keys.TOKEN_EXPIRY, value)

    var lastUpdatePromptTime: Long
        get() = preferences.getLong(Keys.LAST_UPDATE_PROMPT, 0)
        set(value) = preferences.put(Keys.LAST_UPDATE_PROMPT, value)
    //#endregion

    fun resetUserData() {
        isRegistered = false
        accessToken = null
        refreshToken = null
        phoneNumber = null
        password=null
        tokenExpiry = 0
        language = null
        lastUpdatePromptTime = 0
    }
}