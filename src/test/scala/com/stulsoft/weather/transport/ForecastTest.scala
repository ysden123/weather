/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.transport

import org.scalatest.flatspec.AnyFlatSpec
class ForecastTest extends AnyFlatSpec:
  "Forecast" should "read forecast" in {
    val result=Forecast.readForecast(32.085300,34.781769)
    result match
      case Right(response) =>
        succeed
      case Left(value) =>
        fail(value)
  }
