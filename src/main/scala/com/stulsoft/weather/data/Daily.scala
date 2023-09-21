/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.data

import upickle.default.*

case class Daily(
                  summary: String,
                  icon: String,
                  data: List[DataItem]
                )derives ReadWriter

object Daily:
  def maxTemperature(daily: Daily):Double=
    daily.data.foldLeft(Double.MinValue)(_ max _.temperatureMax)

  def minTemperature(daily: Daily):Double=
    daily.data.foldLeft(Double.MaxValue)(_ min _.temperatureMin)
