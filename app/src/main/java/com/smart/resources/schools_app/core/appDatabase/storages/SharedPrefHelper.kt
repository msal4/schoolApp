package com.smart.resources.schools_app.core.appDatabase.storages

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.extentions.TAG
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefHelper @Inject constructor(@ApplicationContext context: Context) {
    private val mSharedPreferences: SharedPreferences

    init {
        mSharedPreferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

//        val masterKey = MasterKey.Builder(context).build()
//        mSharedPreferences = EncryptedSharedPreferences.create(
//            context,
//            PREF_NAME,
//            masterKey,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )

    }

    var currentUserId: String?
        get() = try {
            mSharedPreferences.getString(USER_ID, "")
        } catch (e: ClassCastException) {
            // in case of value with same key is stored but with different type like INT-> this happens in older versions of app
            ""
        }
        set(uid) {
            mSharedPreferences.edit {
                putString(USER_ID, uid)
            }
        }

    var firstRun: Boolean
        get() = try {
            mSharedPreferences.getBoolean(FIRST_RUN, true)
        } catch (e: ClassCastException) {
            true
        }
        set(firstRun) {
            mSharedPreferences.edit {
                putBoolean(FIRST_RUN, firstRun)
            }
        }


    var encryptionKey: String
        get() = try {
            mSharedPreferences.getString(ENCRYPTION_KEY, "")?:""
        } catch (e: ClassCastException) {
            // in case of value with same key is stored but with different type like INT-> this happens in older versions of app
            ""
        }
        set(encryptionKey) {
            mSharedPreferences.edit {
                putString(ENCRYPTION_KEY, encryptionKey)
            }
        }

    var initializationVector: String
        get() = try {
            mSharedPreferences.getString(INITIALIZATION_VECTOR, "")?:""
        } catch (e: ClassCastException) {
            ""
        }
        set(initializationVector) {
            mSharedPreferences.edit {
                putString(INITIALIZATION_VECTOR, initializationVector)
            }
        }


    companion object {
        lateinit var instance: SharedPrefHelper
        private const val PREF_NAME = "appSettingsPref"
        private const val USER_ID = "userId"
        private const val FIRST_RUN = "firstRun"
        private const val ENCRYPTION_KEY = "encryptionKey"
        private const val INITIALIZATION_VECTOR = "initializationVector"

    }


}