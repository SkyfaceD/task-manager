package com.skyface.taskmanager.kotlin.utils

import java.awt.Color

const val APP_NAME = "Task Manager"

val DEFAULT_BUTTON_BACKGROUND = Color(0x242F3D)
val DEFAULT_BUTTON_FOREGROUND = Color(0x576673)

fun String.addMargin(): String {
    return "<html><p style='margin-bottom:12; margin-left:4; margin-top:12'>$this"
}