package com.example.simpleboardapp.util

class Constants {
    companion object {
        private const val LOCAL_IP = "10.53.68.211"
        const val BASE_URL = "http://$LOCAL_IP:3000/"

        const val TAG = "Patrick0422"


        const val PREFERENCES_NAME = "SimpleBoardAppDataStore"

        const val PREFERENCES_USER_ID = "userId"
        const val PREFERENCES_USER_NICKNAME = "userNickname"
        const val PREFERENCES_USER_EMAIL = "userEmail"
        const val PREFERENCES_USER_PASSWORD = "userPassword"
        const val PREFERENCES_USER_TOKEN = "userToken"
        const val PREFERENCES_USER_CREATED_AT = "userCreatedAt"
    }
}