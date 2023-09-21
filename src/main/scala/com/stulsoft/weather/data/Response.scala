/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.weather.data

import upickle.default.*

case class Response(
                     currently: Currently,
                     daily: Daily
                   )derives ReadWriter
