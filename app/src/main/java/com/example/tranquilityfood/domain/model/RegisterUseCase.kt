package com.example.tranquilityfood.domain.model


interface RegisterUseCase {
    fun register(email: String, password: String)
}