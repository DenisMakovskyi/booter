package com.makovskyi.booter.data.preferences

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import android.content.Context
import androidx.core.content.edit
import androidx.annotation.WorkerThread

class ConfigPreferences(context: Context) {

    companion object {

        private const val KEY_DISMISSED_COUNT = "_dismissed_count_"
        private const val KEY_DISMISSALS_NUMBER = "_dismissals_number_"
        private const val KEY_DISMISSALS_INTERVAL = "_dismissals_interval_"
    }

    val dismissalsNumberFlow: StateFlow<Int>
        get() = _dismissalsNumberFlow.asStateFlow()
    val dismissalsIntervalFlow: StateFlow<Long>
        get() = _dismissalsIntervalFlow.asStateFlow()

    var dismissedCount: Int
        get() = preferences.getInt(KEY_DISMISSED_COUNT, 0)
        set(value) {
            preferences.edit {
                putInt(KEY_DISMISSED_COUNT, value)
            }
        }
    @set:WorkerThread
    var dismissalsNumber: Int
        get() = preferences.getInt(KEY_DISMISSALS_NUMBER, 0)
        set(value) {
            preferences.edit(true) {
                putInt(KEY_DISMISSALS_NUMBER, value)
            }
            populatePreferences()
        }
    @set:WorkerThread
    var dismissalsInterval: Long
        get() = preferences.getLong(KEY_DISMISSALS_INTERVAL, 0L)
        set(value) {
            preferences.edit(true) {
                putLong(KEY_DISMISSALS_INTERVAL, value)
            }
            populatePreferences()
        }

    private val preferences = context.getSharedPreferences(
        "${context.packageName}.ConfigPreferences",
        Context.MODE_PRIVATE
    )

    private val _dismissalsNumberFlow = MutableStateFlow(0)
    private val _dismissalsIntervalFlow = MutableStateFlow(0L)

    init {
        populatePreferences()
    }

    private fun populatePreferences() {
        _dismissalsNumberFlow.value = dismissalsNumber
        _dismissalsIntervalFlow.value = dismissalsInterval
    }
}
