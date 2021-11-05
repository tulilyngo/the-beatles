package com.example.thebeatles

class Album {
  private var album: String = ""
  private var producer: String = ""
  private var date: String = ""
  private var file: String = ""

  constructor(album: String, producer: String, date: String, file: String) {
    this.album = album
    this.producer = producer
    this.date = date
    this.file = file
  }

  public fun setAlbum(album: String) {
    this.album = album
  }

  public fun getAlbum(): String {
    return album
  }

  public fun setProducer(producer: String) {
    this.producer = producer
  }

  public fun getProducer(): String {
    return producer
  }

  public fun setDate(date: String) {
    this.date = date
  }

  public fun getDate(): String {
    return date
  }

  public fun setFile(file: String) {
    this.file = file
  }

  public fun getFile(): String {
    return file
  }
}
