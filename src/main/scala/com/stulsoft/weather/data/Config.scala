/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.data

import com.typesafe.scalalogging.StrictLogging
import upickle.default.*

import scala.util.{Failure, Success, Try}

case class Config(var cities: List[City])derives ReadWriter

object Config extends StrictLogging:
  private val configPath = s"""${System.getenv("APPDATA")}\\ys-weather\\config.json"""

  private val configDirPath = os.Path(os.Path(configPath).toIO.getParent)

  try
    os.makeDir.all(configDirPath)
  catch
    case exception: Exception =>
      logger.error(exception.getMessage, exception)

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

  def load(): Either[String, Option[Config]] =
    load(configPath)

  def save(filename: String, config: Config): Try[Unit] =
    try
      os.write.over(os.Path(filename), write(config))
      Success(())
    catch
      case exception: Exception =>
        logger.error(exception.getMessage, exception)
        Failure(exception)

  def save(config: Config): Try[Unit] =
    save(configPath, config)
