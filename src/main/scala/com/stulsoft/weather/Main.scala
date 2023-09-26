/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather

import com.stulsoft.common.ManifestInfo
import com.stulsoft.weather.data.{City, Config}
import com.stulsoft.weather.panel.{ConfigDialog, ConfigTable}

import javax.swing.{JFrame, JOptionPane}
import scala.swing.{Action, Dimension, Frame, MainFrame, Menu, MenuBar, MenuItem, SimpleSwingApplication}

object Main extends SimpleSwingApplication:
  override def top: Frame = new MainFrame:
    private val theMainFrame: MainFrame = this

    title = ManifestInfo("com.stulsoft", "weather").buildTitle("Weather")

    menuBar = new MenuBar {
      contents += new Menu("Configuration") {
        contents += new MenuItem(Action("Change configuration") {
          var config = Config.load() match
            case Right(result) =>
              result match
                case Some(conf) => conf
                case None => Config(Nil)
            case Left(error) =>
              JOptionPane.showMessageDialog(new JFrame(), error, "Configuration", JOptionPane.ERROR_MESSAGE)
              Config(Nil)

          var configTable= ConfigTable(config, theMainFrame)
          new Frame{
            title = "Configuration"
            contents = configTable
            size=new Dimension(600, 400)
            centerOnScreen()
          }.open()
/*
          val city = City("test", 12.34, 56.78)
          ConfigDialog.showDialog(theMainFrame, city)
*/
        })
      }
    }

    size = new Dimension(600, 600)
    centerOnScreen()