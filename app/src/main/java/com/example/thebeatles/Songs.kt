package com.example.thebeatles

class Songs {
  private var name: String = ""
  private var date: String = ""
  private var pic: String = ""

  constructor(name: String, date: String, pic: String) {
    this.name = name
    this.date = date
    this.pic = pic
  }

  public fun setName(name: String) {
    this.name = name
  }

  public fun getName(): String {
    return name
  }

  public fun getDate(): String {
    return date
  }

  public fun setDate(date: String) {
    this.date = date
  }

  public fun setPic(pic: String) {
    this.pic = pic
  }

  public fun getPic(): String {
    return pic
  }

}

