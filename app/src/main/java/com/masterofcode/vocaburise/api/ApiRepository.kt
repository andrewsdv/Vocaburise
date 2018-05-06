package com.masterofcode.vocaburise.api

import com.masterofcode.vocaburise.api.impl.NetworkApiRepositoryImpl

/**
 * Created by andrews on 30.04.18.
 */

private val impl = NetworkApiRepositoryImpl()

object ApiRepository : IApiRepository by impl