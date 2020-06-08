package com.skyface.taskmanager.kotlin.ui.detail

import com.skyface.taskmanager.kotlin.utils.*
import java.awt.Rectangle
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JButton

class DetailCRUDButtons {

    val btnDeleteTask = JButton()
    val btnEditTask = JButton()
    val btnCreateTask = JButton()

    init {
        initButtons()
        setUpButtons()
    }

    private fun setUpButtons() {
        btnDeleteTask.apply {
            addMouseListener(object : MouseAdapter() {
                override fun mouseEntered(e: MouseEvent?) {
                    changeButtonBackgroundAndForeground(
                            this@apply,
                            DELETE_BUTTON_BACKGROUND_HOVER,
                            COLOR_WHITE
                    )
                }

                override fun mouseExited(e: MouseEvent?) {
                    changeButtonBackgroundAndForeground(
                            this@apply,
                            DELETE_BUTTON_BACKGROUND_IDLE,
                            COLOR_WHITE
                    )
                }
            })
        }

        btnEditTask.apply {
            addMouseListener(object : MouseAdapter() {
                override fun mouseEntered(e: MouseEvent?) {
                    changeButtonBackgroundAndForeground(
                            this@apply,
                            EDIT_BUTTON_BACKGROUND_HOVER,
                            COLOR_WHITE
                    )
                }

                override fun mouseExited(e: MouseEvent?) {
                    changeButtonBackgroundAndForeground(
                            this@apply,
                            EDIT_BUTTON_BACKGROUND_IDLE,
                            COLOR_WHITE
                    )
                }
            })
        }

        btnCreateTask.apply {
            addMouseListener(object : MouseAdapter() {
                override fun mouseEntered(e: MouseEvent?) {
                    changeButtonBackgroundAndForeground(
                            this@apply,
                            CREATE_BUTTON_BACKGROUND_HOVER,
                            COLOR_WHITE
                    )
                }

                override fun mouseExited(e: MouseEvent?) {
                    changeButtonBackgroundAndForeground(
                            this@apply,
                            CREATE_BUTTON_BACKGROUND_IDLE,
                            COLOR_WHITE
                    )
                }
            })
        }
    }

    private fun initButtons() {
        btnDeleteTask.icon = "/images/btn_delete.png".toIcon()
        btnDeleteTask.apply {
            bounds = Rectangle(12, DETAIL_HEIGHT - 50, 90, 40)
            background = DELETE_BUTTON_BACKGROUND_IDLE
            foreground = COLOR_WHITE
            border = null
            isFocusable = false
            text = "Удалить"
        }

        btnEditTask.icon = "/images/btn_edit.png".toIcon()
        btnEditTask.apply {
            bounds = Rectangle(105, DETAIL_HEIGHT - 50, 90, 40)
            background = EDIT_BUTTON_BACKGROUND_IDLE
            foreground = COLOR_WHITE
            border = null
            isFocusable = false
            text = "Изменить"
        }

        btnCreateTask.icon = "/images/btn_create.png".toIcon()
        btnCreateTask.apply {
            bounds = Rectangle(198, DETAIL_HEIGHT - 50, 90, 40)
            background = CREATE_BUTTON_BACKGROUND_IDLE
            foreground = COLOR_WHITE
            border = null
            isFocusable = false
            text = "Создать"
        }
    }
}