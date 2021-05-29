package com.example.cardviewkotlin

class Album {
    var name: String? = null
    var numOfSongs = 0
    var thumbnail = 0

    constructor() {}
    constructor(name: String?, numOfSongs: Int, thumbnail: Int) {
        this.name = name
        this.numOfSongs = numOfSongs
        this.thumbnail = thumbnail
    }
}
