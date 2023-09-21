/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.data

import org.scalatest.flatspec.AnyFlatSpec

class ConfigTest extends AnyFlatSpec:
  "Config" should "load config" in {
    Config.load((os.pwd / "src" / "test" / "resources" / "config.json").toString) match
      case Right(result) =>
        assertResult(true)(result.isDefined)
        println(result.get)
      case Left(error) =>
        fail(error)

    Config.load((os.pwd / "src" / "test" / "resources" / "configERROR.json").toString) match
      case Right(result) =>
        assertResult(true)(result.isEmpty)
      case Left(error) =>
        fail(error)
  }
