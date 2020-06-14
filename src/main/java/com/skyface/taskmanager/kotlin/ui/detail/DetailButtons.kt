package com.skyface.taskmanager.kotlin.ui.detail

import com.skyface.taskmanager.kotlin.cron.CronBuilder
import com.skyface.taskmanager.kotlin.database.Database
import com.skyface.taskmanager.kotlin.model.Task
import com.skyface.taskmanager.kotlin.ui.MainFrame
import com.skyface.taskmanager.kotlin.utils.*
import java.awt.Rectangle
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JButton
import javax.swing.JOptionPane

class DetailButtons {

    private val detailComponent = DetailComponent
    private val mainFrame = MainFrame

    val btnCancel = JButton()
    val btnConfirm = JButton()

    init {
        initDetailButtons()
        setUpDetailButtons()
    }

    private fun setUpDetailButtons() {
        btnCancel.apply {
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
                            DEFAULT_BACKGROUND,
                            COLOR_WHITE
                    )
                }
            })

            addActionListener {
                if (mainFrame.createMode) {
                    val result = JOptionPane.showConfirmDialog(
                            mainFrame,
                            "Вы уверены?",
                            "Отмена",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    )

                    if (result == JOptionPane.YES_OPTION) {
                        mainFrame.createMode = false

                        mainFrame.buttons.updateButtons()

                        detailComponent.fields.clearFields()
                        detailComponent.triggers.clearTrigger()
                        detailComponent.datePanel.clearDatePanel()
                        detailComponent.pathPanel.clearPath()
                        detailComponent.buttons.buttonAccessibility()
                        detailComponent.setBorderTitle("Просмотр")
                        detailComponent.fields.disableFields()
                        detailComponent.triggers.disableTrigger()
                        detailComponent.datePanel.disableDate()
                        detailComponent.pathPanel.disablePath()
                        mainFrame.picker.checkPicker()
                    }
                }
            }
        }

        btnConfirm.apply {
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
                            DEFAULT_BACKGROUND,
                            COLOR_WHITE
                    )
                }
            })
            addActionListener {
                with(detailComponent) {
                    if (validateComponents()) {
                        if (validName()) {
                            val name = fields.fieldName.text.trim()
                            val description = fields.fieldDescription.text.trim()

                            val cronBuilder = CronBuilder().apply {
                                minutes = datePanel.timePicker.time.minute.toString()
                                hours = datePanel.timePicker.time.hour.toString()
                            }

                            val cron = when (triggers.trigger) {
                                0 -> {
                                    cronBuilder.apply {
                                        dayOfMonth = datePanel.datePicker.date.dayOfMonth.toString()
                                        month = datePanel.datePicker.date.monthValue.toString()
                                        dayOfWeek = empty()
                                        year = datePanel.datePicker.date.year.toString()
                                    }
                                    cronBuilder.cron()
                                }
                                1 -> {
                                    cronBuilder.apply {
                                        dayOfMonth = empty()
                                        month = every()
                                        dayOfWeek = every()
                                        year = every()
                                    }
                                    cronBuilder.cron()
                                }
                                2 -> {
                                    cronBuilder.apply {
                                        dayOfMonth = empty()
                                        month = every()
                                        dayOfWeek = datePanel.getGluedWeek()
                                        year = every()
                                    }
                                    cronBuilder.cron()
                                }
                                3 -> {
                                    cronBuilder.apply {
                                        dayOfMonth = empty()
                                        month = datePanel.getGluedMonth()
                                        dayOfWeek = every()
                                        year = every()
                                    }
                                    cronBuilder.cron()
                                }
                                else -> "* * * ? * * *"
                            }

                            val task = Task(null, name, description, triggers.trigger, cron, pathPanel.path)

                            val result = JOptionPane.showConfirmDialog(
                                    mainFrame,
                                    "Вы уверены?",
                                    "Сохранение",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE
                            )

                            if (result == JOptionPane.YES_OPTION) {
                                mainFrame.createMode = false
                                Database().putData(task)
                                mainFrame.picker.addToPicker(task)
                                mainFrame.buttons.enableButtons()
                                detailComponent.fields.clearFields()
                                detailComponent.triggers.clearTrigger()
                                detailComponent.datePanel.clearDatePanel()
                                detailComponent.pathPanel.clearPath()
                                detailComponent.buttons.buttonAccessibility()
                                detailComponent.setBorderTitle("Просмотр")
                                detailComponent.fields.disableFields()
                                detailComponent.triggers.disableTrigger()
                                detailComponent.datePanel.disableDate()
                                detailComponent.pathPanel.disablePath()

                                mainFrame.addJob()
                            }
                        } else {
                            JOptionPane.showMessageDialog(mainFrame, "Данное имя уже занято", "Ошибка", JOptionPane.ERROR_MESSAGE)
                        }
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, detailComponent.sb, "Ошибка", JOptionPane.ERROR_MESSAGE)
                    }
                }
            }
        }
    }

    private fun validName(): Boolean {
        var b = true

        for (item in mainFrame.taskList.map { it.name }) {
            if (item == detailComponent.fields.fieldName.text.trim()) {
                b = false
                break
            }
        }

        return b
    }

    fun buttonAccessibility(isEnabled: Boolean = false) {
        btnCancel.background = DEFAULT_BACKGROUND
        btnConfirm.background = DEFAULT_BACKGROUND
        btnCancel.isEnabled = isEnabled
        btnConfirm.isEnabled = isEnabled
    }

    private fun initDetailButtons() {
        btnCancel.icon = "btn_close.png".toIcon()
        btnCancel.apply {
            bounds = Rectangle(
                    15,
                    EDIT_NAME_HEIGHT + EDIT_DESCRIPTION_HEIGHT + TRIGGER_PANEL_HEIGHT + DATE_PANEL_HEIGHT + PATH_PANEL_HEIGHT + 50,
                    DETAIL_WIDTH / 2 - 20,
                    40
            )
            background = DEFAULT_BACKGROUND
            foreground = COLOR_WHITE
            border = null
            isFocusable = false
            isEnabled = false
            iconTextGap = 6
            text = "Отменить"
        }

        btnConfirm.icon = "btn_confirm.png".toIcon()
        btnConfirm.apply {
            bounds = Rectangle(
                    btnCancel.width + 25,
                    EDIT_NAME_HEIGHT + EDIT_DESCRIPTION_HEIGHT + TRIGGER_PANEL_HEIGHT + DATE_PANEL_HEIGHT + PATH_PANEL_HEIGHT + 50,
                    DETAIL_WIDTH / 2 - 20,
                    40
            )
            background = DEFAULT_BACKGROUND
            foreground = COLOR_WHITE
            border = null
            iconTextGap = 6
            isFocusable = false
            isEnabled = false
            text = "Сохранить"
        }
    }


}