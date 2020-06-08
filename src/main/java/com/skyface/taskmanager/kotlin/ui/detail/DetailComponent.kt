package com.skyface.taskmanager.kotlin.ui.detail

import com.skyface.taskmanager.kotlin.utils.*
import java.awt.Font
import java.awt.Rectangle
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.EmptyBorder
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder
import javax.swing.border.TitledBorder.DEFAULT_POSITION
import javax.swing.border.TitledBorder.RIGHT

object DetailComponent : JPanel(null) {
    val fields = DetailEditTexts()
    val buttons = DetailCRUDButtons()
    val radioButtons = DetailRadioButtons()
    val datePanel = DetailDatePanel()

    var trigger = 0

    init {
        initDetail()
    }

    private fun initDetail() {
        bounds = Rectangle(PICKER_WIDTH, TOOLBAR_HEIGHT, DETAIL_WIDTH, DETAIL_HEIGHT)
        background = DETAIL_BACKGROUND
        border = BorderFactory.createCompoundBorder(
                EmptyBorder(4, 4, 4, 4),
                TitledBorder(
                        LineBorder(BORDER_LINE_COLOR, 2, true),
                        "Информация",
                        RIGHT,
                        DEFAULT_POSITION,
                        Font(DEFAULT_FONT_NAME, Font.BOLD, 14),
                        COLOR_WHITE
                )
        )

        add(buttons.btnDeleteTask)
        add(buttons.btnEditTask)
        add(buttons.btnCreateTask)

        add(fields.edtName)
        add(fields.edtDescription)

        add(radioButtons.rbPanel)
        add(datePanel)
    }


}