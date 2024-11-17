package org.tawhid.airwaves.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual fun getDeviceType(): DeviceType {
    return DeviceType.Desktop
}

