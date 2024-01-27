/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.transport

import com.stulsoft.weather.data.Response
import com.stulsoft.weather.util.Utils
import com.typesafe.scalalogging.StrictLogging
import sttp.client4.quick.*
import upickle.default.*


object Forecast extends StrictLogging:
  def readForecast(latitude: Double, longitude: Double): Either[String, Response] =
    val apiKey = Utils.apiKey()
    if apiKey.isEmpty then
      Left("API key is missing")
    else
      val url = s"https://api.pirateweather.net/forecast/${apiKey.get}/$latitude,$longitude?units=ca"

      try
        val body = quickRequest.get(uri"$url").send().body
        try
          val response: Response = read[Response](body)
          Right(response)
        catch
          case exception: Exception=>
            logger.error("Can't parse the response: {}", body)
            logger.error(exception.getMessage, exception)
            Left(exception.getMessage)
      catch
        case exception: Exception =>
          logger.error("Can't get response from {}", url)
          logger.error(exception.getMessage, exception)
          Left(exception.getMessage)
