package com.skyface.taskmanager.kotlin.utils

import com.skyface.taskmanager.kotlin.model.Task
import com.skyface.taskmanager.kotlin.ui.MainFrame
import java.awt.Color
import java.awt.Image
import java.util.*
import javax.swing.*

fun String.toImage(): Image {
    return ImageIcon(MainFrame::class.java.getResource(this)).image
}

fun String.toIcon(): ImageIcon {
    return ImageIcon(MainFrame::class.java.getResource(this))
}

fun String.addMargin(
        top: Int = 0,
        bottom: Int = 0,
        left: Int = 0,
        right: Int = 0
): String {
    return "<html><p style='margin-top:$top; margin-bottom:$bottom; margin-left:$left; margin-right:$right'>$this"
}

fun changeButtonBackgroundAndForeground(
        btn: JButton,
        bg: Color,
        fg: Color
) {
    if (!btn.isEnabled) return

    btn.background = bg
    btn.foreground = fg
}

//TODO replace to main method
fun validateComponents(
        edtName: JTextField,
        edtDescription: JTextArea,
        trigger: Int,
        weekList: List<JCheckBox>,
        monthList: List<JCheckBox>,
        path: String?
): Boolean {
    if (edtName.text.isEmpty())
        return false

    if (edtDescription.text.isEmpty())
        return false

    when (trigger) {
        2 -> {
            var b1 = false
            for (item in weekList) {
                if (item.isSelected) {
                    b1 = true
                    break
                }
            }
            if (!b1) return false
        }
        3 -> {
            var b2 = false
            for (item in monthList) {
                if (item.isSelected) {
                    b2 = true
                    break
                }
            }
            if (!b2) return false
        }
    }

    if (path.isNullOrEmpty())
        return false

    return true
}

/**
 * Cron help
 *
 * Default construction - '* * * ? * * *'
 *
 * 1st param - seconds
 * 2nd param - minutes
 * 3rd param - hours
 * 4th param - day of month
 * 5th param - month
 * 6th param - day of week
 * 7th param - year
 *
 * Example 1 - '0 43 21 8 JUN ? 2020'
 * At 21:43:00pm, on the 8th day, in June, in 2020
 *
 * Example 2 - '0 43 21 ? * * *'
 * At 21:43:00pm every day
 *
 * Example 3 - '0 43 21 ? * SUN,TUE,THU *' or '0 43 21 ? * 1,3,5 *'
 * At 21:43:00pm, on every Sunday, Tuesday and Thursday, every month
 *
 * Example 4 - '0 43 21 ? JAN,APR,DEC * *' or '0 43 21 ? 1,3,12 * *'
 * At 21:43:00pm, in January, April and December
 *
 */
fun populateList(): List<Task> {
    return arrayListOf(
            Task(
                    Random().nextInt(500),
                    "Task 1",
                    "Description 1",
                    0,
                    "0 43 21 8 6 ? 2020",
                    "H:\\Soft\\UninstallToolPortable.exe"
            ),
            Task(
                    Random().nextInt(500),
                    "Task 2",
                    "Description 2",
                    1,
                    "0 43 21 ? 6 * 2020",
                    "H:\\Soft\\UninstallToolPortable.exe"
            ),
            Task(
                    Random().nextInt(500),
                    "Task 3",
                    "Description 3",
                    2,
                    "0 43 21 ? * SUN,TUE,THU *",
                    "H:\\Soft\\UninstallToolPortable.exe"
            ),
            Task(
                    Random().nextInt(500),
                    "Task 4",
                    "Description 4",
                    3,
                    "0 43 21 ? JAN,APR,DEC * *",
                    "H:\\Soft\\UninstallToolPortable.exe"
            )
    )
}
