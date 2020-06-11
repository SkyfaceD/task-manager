package com.skyface.taskmanager.kotlin.ui

import com.skyface.taskmanager.kotlin.custom.CustomScrollBarUI
import com.skyface.taskmanager.kotlin.model.Task
import com.skyface.taskmanager.kotlin.ui.detail.DetailComponent
import com.skyface.taskmanager.kotlin.utils.*
import java.awt.Font
import java.awt.Rectangle
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import javax.swing.BorderFactory
import javax.swing.JList
import javax.swing.JList.VERTICAL
import javax.swing.JOptionPane
import javax.swing.JScrollPane
import javax.swing.border.EmptyBorder
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder

class PickerComponent : JScrollPane() {
    private val mainFrame = MainFrame
    private val detailComponent = DetailComponent

    private val listPicker = JList<String>()

    lateinit var pickedItem: Pair<Int, String>

    var taskNames = mainFrame.taskList.map {
        it.name.addMargin(4, 4, 4, 4)
    }.toTypedArray()

    init {
        initPicker()
        setUpPicker()
    }

    fun addToPicker(task: Task) {
        mainFrame.taskList.add(task)
        checkPicker()
    }

    fun removeFromPicker(name: Task? = null) {
        if (name != null) {
            mainFrame.taskList.remove(name)
        } else {
            mainFrame.taskList.removeAt(pickedItem.first)
        }
        checkPicker()
    }

    fun checkPicker() {
        taskNames = mainFrame.taskList.map {
            it.name.addMargin(4, 4, 4, 4)
        }.toTypedArray()

        if (taskNames.isNotEmpty()) {
            listPicker.setListData(taskNames)
            listPicker.selectedIndex = 0
            listPicker.isEnabled = true
            pickedItem = 0 to mainFrame.taskList[0].name
            someMethod(0)
        } else {
            listPicker.setListData(arrayOf("Нет задач".addMargin(PICKER_HEIGHT / 3 + 25, 0, 4, 4, 32)))
            listPicker.isEnabled = false
            detailComponent.fields.clearFields()
            detailComponent.triggers.clearTrigger()
            detailComponent.datePanel.clearDatePanel()
            detailComponent.pathPanel.clearPath()
        }
    }

    private fun setUpPicker() {
        setViewportView(listPicker)

        if (taskNames.isNotEmpty()) {
            listPicker.setListData(taskNames)
            listPicker.selectedIndex = 0
            pickedItem = 0 to mainFrame.taskList[0].name
        } else {
            listPicker.setListData(arrayOf("Нет задач".addMargin(PICKER_HEIGHT / 3 + 25, 0, 4, 4, 32)))
            listPicker.isEnabled = false
        }

        listPicker.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent) {
                val list = evt.source as JList<*>
                val index = list.locationToIndex(evt.point)

                if (taskNames.isNotEmpty()) {
                    pickedItem = index to taskNames[index].removeMargin()

                    if (evt.clickCount == 2) {
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

                                mainFrame.buttons.enableButtons()

                                detailComponent.fields.clearFields()
                                detailComponent.triggers.clearTrigger()
                                detailComponent.datePanel.clearDatePanel()
                                detailComponent.pathPanel.clearPath()
                                detailComponent.buttons.buttonAccessibility(true)
                                detailComponent.setBorderTitle("Просмотр")
                                detailComponent.fields.disableFields()
                                detailComponent.triggers.disableTrigger()
                                detailComponent.datePanel.disableDate()
                                detailComponent.pathPanel.disablePath()

                                someMethod(index)
                            }
                        } else {
                            someMethod(index)
                        }
                    }
                }
            }
        })

        listPicker.addKeyListener(object : KeyListener {
            override fun keyTyped(e: KeyEvent?) {
            }

            override fun keyPressed(e: KeyEvent) {
                if (e.keyCode == KeyEvent.VK_ENTER) {

                }
            }

            override fun keyReleased(e: KeyEvent?) {
            }
        })
    }

    private fun someMethod(index: Int) {
        val task = mainFrame.taskList[index]
        val brokenCron = task.cron.split(" ")

        val minutes = brokenCron[1]
        val hours = brokenCron[2]
        val day = if (brokenCron[3] == "?") {
            SimpleDateFormat("dd").format(Date(System.currentTimeMillis()))
        } else {
            brokenCron[3]
        }
        var months = listOf<Int>()
        val month = if (brokenCron[4] == "?" || brokenCron[4] == "*") {
            SimpleDateFormat("MMM").format(Date(System.currentTimeMillis()))
        } else {
            months = brokenCron[4].split(',').map {
                it.toInt().dec()
            }
            DateFormatSymbols().months[brokenCron[4].substringBefore(",").toInt().dec()]
        }
        val weeks = if (brokenCron[5] == "?" || brokenCron[5] == "*") {
            emptyList()
        } else {
            brokenCron[5].split(',').map {
                it.toInt().dec()
            }
        }
        val year = if (brokenCron[6] == "*")
            SimpleDateFormat("yyyy").format(Date(System.currentTimeMillis()))
        else
            brokenCron[6]

        detailComponent.datePanel.datePicker.text = "$day $month $year г."
        detailComponent.datePanel.timePicker.text = "$hours:$minutes"
        detailComponent.fields.fieldName.text = task.name
        detailComponent.fields.fieldDescription.text = task.description
        detailComponent.triggers.updateTrigger(task.trigger)
        detailComponent.datePanel.updateCheckBoxes(task.trigger, weeks, months)
        detailComponent.pathPanel.updatePath(task.path)
    }

    private fun initPicker() {
        bounds = Rectangle(0, TOOLBAR_HEIGHT, PICKER_WIDTH, PICKER_HEIGHT)
        background = PICKER_BACKGROUND
        border = BorderFactory.createCompoundBorder(
                EmptyBorder(4, 4, 4, 4),
                TitledBorder(
                        LineBorder(BORDER_LINE_COLOR, 2, true),
                        "Задачи",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        Font(DEFAULT_FONT_NAME, Font.BOLD, 14),
                        COLOR_WHITE
                )
        )
        verticalScrollBar.setUI(CustomScrollBarUI())
        verticalScrollBar.background = background

        listPicker.apply {
            background = this@PickerComponent.background
            foreground = COLOR_WHITE
            selectionBackground = PICKER_ITEM_SELECTION_BACKGROUND
            selectionForeground = PICKER_ITEM_SELECTION_FOREGROUND
            font = Font(DEFAULT_FONT_NAME, Font.BOLD, 12)
            layoutOrientation = VERTICAL
            border = EmptyBorder(0, 6, 0, 6)
        }
    }
}