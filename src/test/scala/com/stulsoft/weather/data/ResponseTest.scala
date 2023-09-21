/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.data

import org.scalatest.flatspec.AnyFlatSpec
import upickle.default.*

class ResponseTest extends AnyFlatSpec:
  "Response" should "support deserialize" in {
    try
      var response = read[Response](os.read(os.pwd / "src" / "test" / "resources" / "data" / "response1.json"))
      var currently = response.currently
      assertResult(1695283020)(currently.time)

      response = read[Response](os.read(os.pwd / "src" / "test" / "resources" / "data" / "response2.json"))
      currently = response.currently
      assertResult(1695290340)(currently.time)
      val daily = response.daily
      assertResult(8)(daily.data.length)
      daily.data.foreach(item => println(s"temperatureMax=${item.temperatureMax}, temperatureMin=${item.temperatureMin}"))
      println(s"maxTemperature=${Daily.maxTemperature(daily)}")
      println(s"minTemperature=${Daily.minTemperature(daily)}")
    catch
      case exception: Exception =>
        exception.printStackTrace()
        fail(exception.getMessage)
  }
