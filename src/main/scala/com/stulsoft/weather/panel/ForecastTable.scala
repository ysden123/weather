/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.panel

//import com.stulsoft.weather.data.{Config, Currently, Daily, DataItem, Response}

import com.stulsoft.weather.Main.resourceFromClassloader
import com.stulsoft.weather.data.*
import com.stulsoft.weather.data.Daily.{maxTemperature, minTemperature}
import com.stulsoft.weather.transport.Forecast

import javax.swing.{ImageIcon, ListSelectionModel}
import scala.swing.*
import javax.swing.table.DefaultTableModel
import scala.concurrent.{ExecutionContext, Future}
import scala.swing.BorderPanel.Position
import scala.swing.{BorderPanel, MainFrame}
import scala.util.{Failure, Success}

class ForecastTable(config: Config, mainFrame: MainFrame) extends BorderPanel:
  given ec: ExecutionContext = scala.concurrent.ExecutionContext.global


  private val model = new DefaultTableModel() {
    override def isCellEditable(row: Int, column: Int): Boolean = false
  }

  model.addColumn("City")
  model.addColumn("Status")
  model.addColumn(" ")
  model.addColumn("Summary")
  model.addColumn("Max t")
  model.addColumn("Min t")

  for (rowId <- config.cities.indices)
    model.addRow(Array[Any](
      config.cities(rowId).name,
      "Waiting..."
    ))

  private val table = new Table(model)
  table.peer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)

  private val icons = Map(
    "clear" -> loadImageIcon("images/clear.png", table),
    "clear-day" -> loadImageIcon("images/clear-day.png", table),
    "clear-night" -> loadImageIcon("images/clear-night.png", table),
    "cloudy" -> loadImageIcon("images/cloudy.png", table),
    "fog" -> loadImageIcon("images/fog.png", table),
    "partly-cloudy-day" -> loadImageIcon("images/partly-cloudy-day.png", table),
    "partly-cloudy-night" -> loadImageIcon("images/partly-cloudy-night.png", table),
    "rain" -> loadImageIcon("images/rain.png", table),
    "sleet" -> loadImageIcon("images/sleet.png", table),
    "snow" -> loadImageIcon("images/snow.png", table),
    "wind" -> loadImageIcon("images/wind.png", table)
  )


  private val scrollPanel = new ScrollPane(table)

  layout(scrollPanel) = Position.Center

  private def fillForecast(model: DefaultTableModel, config: Config, rowId: Int): Unit =
    val future = Future[Either[String, Response]] {
      Forecast.readForecast(config.cities(rowId).latitude, config.cities(rowId).longitude)
    }

    future.onComplete {
      case Success(result) =>
        result match {
          case Left(error) =>
            model.setValueAt(error, rowId, 1)
          case Right(response) =>
            model.setValueAt("Done", rowId, 1)
            val anIconValue = if icons.contains(response.daily.icon) then icons(response.daily.icon) else " "
            model.setValueAt(anIconValue, rowId, 2)
            model.setValueAt(response.daily.summary, rowId, 3)
            model.setValueAt(maxTemperature(response.daily), rowId, 4)
            model.setValueAt(minTemperature(response.daily), rowId, 5)
        }
      case Failure(exception) =>
        exception.printStackTrace()
        model.setValueAt(exception.getMessage, rowId, 1)
    }
  end fillForecast

  private def loadImageIcon(path: String, table: Table): ImageIcon =
    try
      val imageIcon = new ImageIcon(resourceFromClassloader(path))
      val maxHeight = table.rowHeight - 3
      new ImageIcon(imageIcon.getImage.getScaledInstance(maxHeight, maxHeight, java.awt.Image.SCALE_SMOOTH))
    catch
      case exception: Exception =>
        exception.printStackTrace()
        null
  end loadImageIcon

  config.cities.indices.foreach(rowId => fillForecast(model, config, rowId))
end ForecastTable
