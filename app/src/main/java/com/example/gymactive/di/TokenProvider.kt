package com.example.gymactive.di

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenProvider @Inject constructor() {
    var token: String? = null
}