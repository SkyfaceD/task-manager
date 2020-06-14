package com.skyface.taskmanager.kotlin

import com.skyface.taskmanager.kotlin.ui.MainFrame
import javax.swing.UIManager

fun main() {
    UIManager.put("OptionPane.yesButtonText", "Да")
    UIManager.put("OptionPane.noButtonText", "Нет")

    val app = MainFrame
    app.display()

    app.addJobs()
}