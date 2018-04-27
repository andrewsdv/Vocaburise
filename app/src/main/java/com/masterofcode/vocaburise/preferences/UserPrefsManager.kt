package com.masterofcode.vocaburise.preferences

import com.masterofcode.vocaburise.preferences.impl.UserPrefsManagerImpl

/**
 * Created by andrews on 24.04.18.
 */

private val impl = UserPrefsManagerImpl()

object UserPrefsManager : IUserPrefsManager by impl