/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.data

import upickle.default.*

case class DataItem(
                     time: Long,
                     icon: String,
                     summary: String,
                     sunriseTime: Long,
                     sunsetTime: Long,
                     moonPhase: Double,
                     precipIntensity: Double,
                     precipIntensityMax: Double,
                     precipIntensityMaxTime: Double,
                     precipProbability: Double,
                     precipAccumulation: Double,
                     precipType: String,
                     temperatureHigh: Double,
                     temperatureHighTime: Long,
                     temperatureLow: Double,
                     temperatureLowTime: Long,
                     apparentTemperatureHigh: Double,
                     apparentTemperatureHighTime: Long,
                     apparentTemperatureLow: Double,
                     apparentTemperatureLowTime: Long,
                     dewPoint: Double,
                     humidity: Double,
                     pressure: Double,
                     windSpeed: Double,
                     windGust: Double,
                     windGustTime: Long,
                     windBearing: Int,
                     cloudCover: Double,
                     uvIndex: Double,
                     uvIndexTime: Long,
                     visibility: Double,
                     temperatureMin: Double,
                     temperatureMinTime: Long,
                     temperatureMax: Double,
                     temperatureMaxTime: Long,
                     apparentTemperatureMin: Double,
                     apparentTemperatureMinTime: Long,
                     apparentTemperatureMax: Double,
                     apparentTemperatureMaxTime: Long
                   )derives ReadWriter
