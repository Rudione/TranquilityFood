package com.example.tranquilityfood.domain.model

interface LoginUseCase {
    fun login(email: String, password: String)
}