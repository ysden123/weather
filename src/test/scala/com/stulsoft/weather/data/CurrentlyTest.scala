/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.data

import upickle.default.*
import org.scalatest.flatspec.AnyFlatSpec

class CurrentlyTest extends AnyFlatSpec:
  "Currently" should "support deserialize" in {
    try
      val currently: Currently = read[Currently](os.read(os.pwd / "src" / "test" / "resources" / "data" / "currently.json"))
      assertResult(1695283020)(currently.time)
    catch
      case exception: Exception =>
        exception.printStackTrace()
        fail(exception.getMessage)
  }
