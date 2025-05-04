package com.eshan.filmapp.model

//data class Movie(
//    val title: String,
//    val year: String,
//    val rated: String,
//    val released: String,
//    val runtime: String,
//    val genre: String,
//    val director: String,
//    val writer: String,
//    val actors: String,
//    val plot: String
//)

data class Movie(
    val title: String,
    val year: String,
    val rated: String?,
    val released: String?,
    val runtime: String?,
    val genre: String?,
    val director: String?,
    val writer: String?,
    val actors: String?,
    val plot: String?
)
