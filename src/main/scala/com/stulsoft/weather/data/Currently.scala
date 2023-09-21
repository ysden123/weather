/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.data

import upickle.default.*

case class Currently(time: Long,
                     summary: String,
                     icon: String,
                     nearestStormDistance: Int,
                     nearestStormBearing: Int,
                     precipIntensity: Double,
                     precipProbability: Double,
                     precipIntensityError: Double,
                     precipType: String,
                     temperature: Double,
                     apparentTemperature: Double,
                     dewPoint: Double,
                     humidity: Double,
                     pressure: Double,
                     windSpeed: Double,
                     windGust: Double,
                     windBearing: Int,
                     cloudCover: Int,
                     uvIndex: Double,
                     visibility: Double,
                     ozone: Double
                    )derives ReadWriter
