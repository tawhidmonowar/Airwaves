package org.tawhid.airwaves.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.tawhid.airwaves.utils.DATA_STORE_FILE_NAME

actual fun dataStorePreferences(): DataStore<Preferences> {
    return AppSettings.getDataStore {
        DATA_STORE_FILE_NAME
    }
}