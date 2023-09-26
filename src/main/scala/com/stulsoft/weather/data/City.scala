/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.data

import upickle.default.*

case class City(var name: String, var latitude: Double, var longitude: Double)derives ReadWriter
