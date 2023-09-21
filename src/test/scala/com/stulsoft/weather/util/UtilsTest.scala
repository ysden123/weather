/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.util

import org.scalatest.flatspec.AnyFlatSpec

class UtilsTest extends AnyFlatSpec:
  "Utils" should "read API key" in {
    val apiKey=Utils.apiKey()
    println(s"apiKey=$apiKey")
  }
