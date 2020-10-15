package com.smart.resources.schools_app.core.typeConverters.room

import androidx.room.TypeConverter
import com.smart.resources.schools_app.core.appDatabase.storages.AppDatabase
import com.smart.resources.schools_app.core.myTypes.DecryptedString
import com.smart.resources.schools_app.core.utils.EncryptionHelper
import javax.inject.Inject

class DecryptedStringToStringConverter {
    private val encryptionHelper = AppDatabase.encryptionHelper

    @TypeConverter
    fun fromString(value: String?): DecryptedString? {
        if (value != null) {
            val decryptedMsg = encryptionHelper.decryptMessage(value)
            return DecryptedString(decryptedMsg)
        }

        return null
    }

    @TypeConverter
    fun toString(decryptedString: DecryptedString?): String? {
        if (decryptedString == null) return null
        return encryptionHelper.encryptMessage(decryptedString.value)
    }
}
