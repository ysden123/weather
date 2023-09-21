/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.data

import upickle.default.*

case class City(name: String, latitude: Double, longitude: Double)derives ReadWriter
