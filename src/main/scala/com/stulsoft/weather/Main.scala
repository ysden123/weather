/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather

import com.stulsoft.common.ManifestInfo
import com.stulsoft.weather.data.Config
import com.stulsoft.weather.panel.{ConfigTable, ForecastTable}

import javax.swing.{JFrame, JOptionPane}
import scala.swing.*

object Main extends SimpleSwingApplication:
  override def top: Frame = new MainFrame:
    private val theMainFrame: MainFrame = this

    title = ManifestInfo("com.stulsoft", "weather").buildTitle("Weather")

    menuBar = new MenuBar {
      contents += new Menu("Weather") {
        contents += new MenuItem(Action("Forecast") {
          val config = Config.load() match
            case Right(result) =>
              result match
                case Some(conf) => conf
                case None => Config(Nil)
            case Left(error) =>
              JOptionPane.showMessageDialog(new JFrame(), error, "Forecast", JOptionPane.ERROR_MESSAGE)
              Config(Nil)
          
          val forecastTable = ForecastTable(config, theMainFrame)
          new Frame {
            title = "Forecast"
            contents = forecastTable
            size = new Dimension(600, 400)
            centerOnScreen()
          }.open()
        })

        contents += new MenuItem(Action("Configuration") {
          val config = Config.load() match
            case Right(result) =>
              result match
                case Some(conf) => conf
                case None => Config(Nil)
            case Left(error) =>
              JOptionPane.showMessageDialog(new JFrame(), error, "Configuration", JOptionPane.ERROR_MESSAGE)
              Config(Nil)

          val configTable = ConfigTable(config, theMainFrame)
          new Frame {
            title = "Configuration"
            contents = configTable
            size = new Dimension(600, 400)
            centerOnScreen()
          }.open()
        })
      }
    }

    size = new Dimension(600, 600)
    centerOnScreen()