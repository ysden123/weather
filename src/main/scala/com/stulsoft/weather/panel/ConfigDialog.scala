/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.panel

import com.stulsoft.weather.data.City

import javax.swing.{JFrame, JOptionPane}
import scala.swing.BorderPanel.Position
import scala.swing.event.ButtonClicked
import scala.swing._

object ConfigDialog:
  def showDialog(mainFrame: MainFrame, city: City): Option[City] =
    var result: Option[City] = None
    val dialog: Dialog = new Dialog(mainFrame) {
      val dialogFrame: Dialog = this
      modal = true
      title = "Place configuration"
      size = new Dimension(700, 300)
      resizable = false
      centerOnScreen()

      val nameField = new TextField(city.name)
      val latitudeField = new TextField(city.latitude.toString)
      val longitudeField = new TextField(city.longitude.toString)

      val okButton: Button = new Button("OK") {
        reactions += {
          case ButtonClicked(_) =>
            try
              result = Some(City(nameField.text, latitudeField.text.toDouble, longitudeField.text.toDouble))
              dialogFrame.close()
            catch
              case exception: Exception =>
                JOptionPane.showMessageDialog(new JFrame(), exception.getMessage, title, JOptionPane.ERROR_MESSAGE)
                result = None
        }
      }

      val cancelButton: Button = new Button("Cancel") {
        reactions += {
          case ButtonClicked(_) =>
            result = None
            dialogFrame.close()
        }
      }

      val dataPanel: GridPanel = new GridPanel(1, 6) {
        contents ++= Seq(new Label("Place:"), nameField,
          new Label("Latitude:"), latitudeField,
          new Label("Longitude:"), longitudeField
        )
      }

      val buttonPanel: FlowPanel = new FlowPanel(FlowPanel.Alignment.Center)(Array(okButton, cancelButton)*)

      defaultButton = okButton

      contents = new BorderPanel {
        layout(dataPanel) = Position.Center
        layout(buttonPanel) = Position.South
      }
    }
    dialog.open()
    result
