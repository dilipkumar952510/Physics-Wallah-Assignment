package com.physicswallah.assignment.ui.model

import java.io.Serializable

data class CharacterResponse(
    val info: Info,
    val results: ArrayList<Character>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class Character(
    val id: Int?=null,
    val name: String?=null,
    val status: String?=null,
    val species: String?=null,
    val type: String?=null,
    val gender: String?=null,
    val origin: Origin?=null,
    val location: Location?=null,
    val image: String?=null,
    val episode: List<String>?=null,
    val url: String?=null,
    val created: String?=null
):Serializable

data class Origin(
    val name: String,
    val url: String
):Serializable

data class Location(
    val name: String,
    val url: String
):Serializable
