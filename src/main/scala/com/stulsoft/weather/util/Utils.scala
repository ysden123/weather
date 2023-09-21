/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.util

object Utils:
  def apiKey(): Option[String] = sys.env.get("PIRATE_WEATHER_API_KEY")
