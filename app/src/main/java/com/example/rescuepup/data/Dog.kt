package com.example.rescuepup.data

// Example Dog data class
data class Dog(val name: String, val description: String)

val dogList = listOf(
    Dog("Max", "A friendly golden retriever"),
    Dog("Bella", "A playful beagle"),
    Dog("Charlie", "A loyal German Shepherd")
)