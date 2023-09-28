/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.panel

//import com.stulsoft.weather.data.{Config, Currently, Daily, DataItem, Response}

import com.stulsoft.weather.data.*
import com.stulsoft.weather.data.Daily.{maxTemperature, minTemperature}
import com.stulsoft.weather.transport.Forecast

import javax.swing.ListSelectionModel
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
  model.addColumn("Max t")
  model.addColumn("Min t")

  for (rowId <- config.cities.indices)
    model.addRow(Array[Any](
      config.cities(rowId).name,
      "Waiting..."
    ))

  private val table = new Table(model)
  table.peer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)

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
            model.setValueAt(maxTemperature(response.daily), rowId, 2)
            model.setValueAt(minTemperature(response.daily), rowId, 3)
        }
      case Failure(exception) =>
        exception.printStackTrace()
        model.setValueAt(exception.getMessage, rowId, 1)
    }

  config.cities.indices.foreach(rowId => fillForecast(model, config, rowId))