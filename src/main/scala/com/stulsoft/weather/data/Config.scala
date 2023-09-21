/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.data

import com.typesafe.scalalogging.StrictLogging
import upickle.default.*

import java.io.File

case class Config(cities: List[City])derives ReadWriter

object Config extends StrictLogging:
  def load(filename: String): Either[String, Option[Config]] =
    try
      val path = os.Path(filename)
      if path.toIO.exists() then
        val config = read[Config](os.read(path))
        Right(Option(config))
      else
        Right(None)
    catch
      case exception: Exception =>
        logger.error(exception.getMessage, exception)
        Left(exception.getMessage)
