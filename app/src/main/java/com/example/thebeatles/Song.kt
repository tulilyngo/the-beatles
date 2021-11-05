package com.example.thebeatles

class Song {
  private var song: String = ""
  private var producer: String = ""

  constructor(song: String, producer: String) {
    this.song = song
    this.producer = producer
  }

  public fun setSong(song: String) {
    this.song = song
  }

  public fun getSong(): String {
    return song
  }

  public fun setProducer(producer: String) {
    this.producer = producer
  }

  public fun getProducer(): String {
    return producer
  }
}
