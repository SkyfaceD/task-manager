package com.skyface.taskmanager.kotlin.utils

import com.skyface.taskmanager.kotlin.model.Task
import com.skyface.taskmanager.kotlin.ui.MainFrame
import java.awt.Color
import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JButton
import kotlin.random.Random

fun String.toImage(): Image {
    return ImageIcon(MainFrame::class.java.getResource("/icons/$this")).image
}

fun String.toIcon(): ImageIcon {
    return ImageIcon(MainFrame::class.java.getResource("/icons/$this"))
}

fun String.addMargin(
        top: Int = 0,
        bottom: Int = 0,
        left: Int = 0,
        right: Int = 0,
        fontSize: Int = 12
): String {
    return "<html><p style='margin-top:$top; margin-bottom:$bottom; margin-left:$left; margin-right:$right; font-size:$fontSize'>$this"
}

fun String.removeMargin(): String {
    return this.substringAfterLast(">")
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
                    null,
                    "Task ${Random.nextInt(99999)}",
                    "Description 1",
                    0,
                    "0 43 21 8 6 ? 2020",
                    "H:\\Soft\\UninstallToolPortable.exe"
            ),
            Task(
                    null,
                    "Task ${Random.nextInt(99999)}",
                    "Description 2",
                    1,
                    "0 43 21 ? * * *",
                    "H:\\Soft\\UninstallToolPortable.exe"
            ),
            Task(
                    null,
                    "Task ${Random.nextInt(99999)}",
                    "Description 3",
                    2,
                    "0 43 21 ? * 1,3,5 *",
                    "H:\\Soft\\UninstallToolPortable.exe"
            ),
            Task(
                    null,
                    "Task ${Random.nextInt(99999)}",
                    "Description 4",
                    3,
                    "0 43 21 ? 1,3,12 * *",
                    "H:\\Soft\\UninstallToolPortable.exe"
            )
    )
}
