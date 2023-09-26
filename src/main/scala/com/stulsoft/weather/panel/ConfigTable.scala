/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.panel

import com.stulsoft.weather.data.{City, Config}

import javax.swing.table.DefaultTableModel
import javax.swing.{JFrame, JOptionPane, ListSelectionModel}
import scala.swing.*
import scala.swing.BorderPanel.Position
import scala.swing.event.{ButtonClicked, TableRowsSelected}

class ConfigTable(config: Config, mainFrame: MainFrame) extends BorderPanel:
  private val model = new DefaultTableModel() {
    override def isCellEditable(row: Int, column: Int): Boolean = false
  }

  model.addColumn("Name")
  model.addColumn("Longitude")
  model.addColumn("Latitude")

  for (rowId <- config.cities.indices)
    model.addRow(Array[Any](config.cities(rowId).name, config.cities(rowId).longitude, config.cities(rowId).latitude))

  private val table = new Table(model)
  table.peer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)

  private var selectedRow = -1

  listenTo(table.selection)
  reactions += {
    case TableRowsSelected(_, _, false) =>
      selectedRow = table.selection.rows.leadIndex
      editButton.enabled = true
      deleteButton.enabled = true
  }

  private val addButton = new Button("Add") {
    reactions += {
      case ButtonClicked(_) =>
        ConfigDialog.showDialog(new MainFrame(), City("", 0.0, 0.0))
          .foreach(city => {
            val duplicated = config.cities.count(aCity => aCity.name == city.name) > 0
            if !duplicated then
              config.cities = city :: config.cities
              model.insertRow(0, Array[Any](city.name, city.longitude, city.latitude))
              Config.save(config)
              selectedRow = -1
            else
              JOptionPane.showMessageDialog(new JFrame(), "Duplicated name", "Add error", JOptionPane.ERROR_MESSAGE)
          })
    }
  }

  private val editButton = new Button("Edit") {
    reactions += {
      case ButtonClicked(_) =>
        if selectedRow > -1 && selectedRow < config.cities.length then
          ConfigDialog.showDialog(new MainFrame(), config.cities(selectedRow))
            .foreach(city => {
              var duplicated = false
              for (rowId <- config.cities.indices)
                if config.cities(rowId).name == city.name && rowId != selectedRow then duplicated = true
              if !duplicated then
                config.cities(selectedRow).name = city.name
                config.cities(selectedRow).latitude = city.latitude
                config.cities(selectedRow).longitude = city.longitude
                model.setValueAt(city.name, selectedRow, 0)
                model.setValueAt(city.latitude, selectedRow, 1)
                model.setValueAt(city.longitude, selectedRow, 2)
                Config.save(config)
                selectedRow = -1
              else
                JOptionPane.showMessageDialog(new JFrame(), "Duplicated name", "Edit error", JOptionPane.ERROR_MESSAGE)
            })
    }
  }

  private val deleteButton = new Button("Delete") {
    reactions += {
      case ButtonClicked(_) =>
        if selectedRow > -1 && selectedRow < config.cities.length then
          model.removeRow(selectedRow)
          config.cities = config.cities.filter(city => city.name != config.cities(selectedRow).name)
          Config.save(config)
          selectedRow = -1
    }
  }

  editButton.enabled = false
  deleteButton.enabled = false

  private val buttons = new GridPanel(1, 3) {
    hGap = 25
    vGap = 5
    contents ++= Seq(addButton, editButton, deleteButton)
  }

  private val scrollPanel = new ScrollPane(table)

  layout(scrollPanel) = Position.Center
  layout(buttons) = Position.South