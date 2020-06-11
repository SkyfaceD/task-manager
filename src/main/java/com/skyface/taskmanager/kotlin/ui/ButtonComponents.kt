package com.skyface.taskmanager.kotlin.ui

import com.skyface.taskmanager.kotlin.database.Database
import com.skyface.taskmanager.kotlin.ui.detail.DetailComponent
import com.skyface.taskmanager.kotlin.utils.*
import java.awt.Rectangle
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JButton
import javax.swing.JOptionPane.*

class ButtonComponents {

    private val mainFrame = MainFrame
    private val detailComponent = DetailComponent

    val btnDeleteTask = JButton()
    val btnEditTask = JButton()
    val btnCreateTask = JButton()

    init {
        initButtons()

        updateButtons()

        setUpButtons()
    }

    fun enableButtons() {
        btnDeleteTask.isEnabled = true
        btnEditTask.isEnabled = true
    }

    fun disableButtons() {
        btnDeleteTask.background = DEFAULT_BACKGROUND
        btnEditTask.background = DEFAULT_BACKGROUND
        btnDeleteTask.isEnabled = false
        btnEditTask.isEnabled = false
    }

    fun updateButtons() {
        if (mainFrame.taskList.isEmpty()) {
            btnDeleteTask.background = DEFAULT_BACKGROUND
            btnEditTask.background = DEFAULT_BACKGROUND
            btnDeleteTask.isEnabled = false
            btnEditTask.isEnabled = false
        }
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

            addActionListener {
                val result = showConfirmDialog(
                        mainFrame,
                        "Вы уверены ?",
                        "Удаления задачи",
                        YES_NO_OPTION,
                        QUESTION_MESSAGE
                )

                if (result == YES_OPTION) {
                    Database().removeData(mainFrame.picker.pickedItem.second)
                    mainFrame.picker.removeFromPicker()
                    updateButtons()
                }
            }
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

            addActionListener {
                if (mainFrame.createMode) {
                    showMessageDialog(
                            mainFrame,
                            "Если хотите создать новую задачу,отмените предыдущую!",
                            "Информация",
                            INFORMATION_MESSAGE
                    )
                } else {
                    mainFrame.createMode = true

                    disableButtons()

                    detailComponent.fields.clearFields()
                    detailComponent.triggers.clearTrigger()
                    detailComponent.datePanel.clearDatePanel()
                    detailComponent.pathPanel.clearPath()
                    detailComponent.buttons.buttonAccessibility(true)
                    detailComponent.setBorderTitle("Создание")
                    detailComponent.fields.enableFields()
                    detailComponent.triggers.enableTrigger()
                    detailComponent.datePanel.enableDate()
                    detailComponent.pathPanel.enablePath()
                }
            }
        }
    }

    private fun initButtons() {
        btnDeleteTask.icon = "btn_delete.png".toIcon()
        btnDeleteTask.apply {
            bounds = Rectangle(10, PICKER_HEIGHT + 40, BUTTON_WIDTH, BUTTON_HEIGHT)
            background = DELETE_BUTTON_BACKGROUND_IDLE
            foreground = COLOR_WHITE
            border = null
            isFocusable = false
            iconTextGap = 18
            text = "Удалить задачу"
        }

        btnEditTask.icon = "btn_edit.png".toIcon()
        btnEditTask.apply {
            bounds = Rectangle(10, PICKER_HEIGHT + BUTTON_HEIGHT + 40, BUTTON_WIDTH, BUTTON_HEIGHT)
            background = EDIT_BUTTON_BACKGROUND_IDLE
            foreground = COLOR_WHITE
            border = null
            isFocusable = false
            iconTextGap = 12
            text = "Изменить задачу"
        }

        btnCreateTask.icon = "btn_create.png".toIcon()
        btnCreateTask.apply {
            bounds = Rectangle(10, PICKER_HEIGHT + BUTTON_HEIGHT * 2 + 40, BUTTON_WIDTH, BUTTON_HEIGHT)
            background = CREATE_BUTTON_BACKGROUND_IDLE
            foreground = COLOR_WHITE
            border = null
            isFocusable = false
            iconTextGap = 18
            text = "Создать задачу"
        }
    }
}