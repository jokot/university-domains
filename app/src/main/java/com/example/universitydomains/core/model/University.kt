package com.example.universitydomains.core.model

data class University(
    val alphaTwoCode: String,
    val country: String,
    val domains: List<String>,
    val name: String,
    val webPages: List<String>
)