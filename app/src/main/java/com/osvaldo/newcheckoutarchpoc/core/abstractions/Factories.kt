package com.osvaldo.newcheckoutarchpoc.core.abstractions

object Factories {

    val breadFactory: MutableList<String> = mutableListOf("Flat bread", "Italian", "Wheat", "White")
    val condimentsFactory: MutableList<String> = mutableListOf(
        "Lettuce",
        "Tomato",
        "Onion",
        "Pickles",
        "Olives",
        "Pepper",
        "Salt",
        "Vinegar",
        "Mayo",
        "Mustard",
        "Ketchup"
    )

    val meatFactory: MutableList<String> =
        mutableListOf("Turkey", "Ham", "Roast Beef", "Salami", "Bologna")

    val fishFactory: MutableList<String> = mutableListOf("Tuna", "Salmon", "Sardines", "Anchovies")

    val booleanFactory : MutableList<Boolean> = mutableListOf(true, false)
}