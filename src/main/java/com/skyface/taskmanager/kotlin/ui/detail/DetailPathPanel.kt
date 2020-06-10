package com.skyface.taskmanager.kotlin.ui.detail

import com.skyface.taskmanager.kotlin.utils.*
import java.awt.Font
import java.awt.Rectangle
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JFileChooser.APPROVE_OPTION
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder
import javax.swing.filechooser.FileNameExtensionFilter

class DetailPathPanel : JPanel(null) {

    private val edtPath = JTextField()
    private val btnPath = JButton()
    private val chooser = JFileChooser()

    var path = ""

    init {
        initPathPanel()

        disablePath()

        setUpPathPanel()
    }

    fun enablePath(){
        btnPath.isEnabled = true
    }

    fun disablePath(){
        btnPath.isEnabled = false
    }

    fun updatePath(str: String){
        edtPath.text = str
        path = str
    }

    fun clearPath(){
        edtPath.text = ""
        path = ""
    }

    private fun setUpPathPanel() {
        chooser.dialogTitle = APP_NAME
        chooser.isAcceptAllFileFilterUsed = false
        chooser.fileFilter = FileNameExtensionFilter("EXE & LNK Files", "exe", "lnk")

        btnPath.apply {
            addActionListener {
                val returnVal = chooser.showOpenDialog(parent)
                if (returnVal == APPROVE_OPTION) {
                    path = chooser.selectedFile.path
                    edtPath.text = path
                }
            }
        }
    }

    private fun initPathPanel() {
        bounds = Rectangle(10, EDIT_NAME_HEIGHT + EDIT_DESCRIPTION_HEIGHT + TRIGGER_PANEL_HEIGHT + DATE_PANEL_HEIGHT + 30, PATH_PANEL_WIDTH, PATH_PANEL_HEIGHT)
        background = DEFAULT_BACKGROUND
        border = TitledBorder(
                LineBorder(BORDER_LINE_COLOR, 2, false),
                "Путь",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                Font(DEFAULT_FONT_NAME, Font.BOLD, 12),
                COLOR_WHITE
        )

        edtPath.apply {
            bounds = Rectangle(10, 25, PATH_PANEL_WIDTH - 45, 25)
            isEditable = false
        }

        btnPath.apply {
            bounds = Rectangle(edtPath.width + 10, 25, 25, 25)
            text = "..."
            isFocusable = false
        }

        add(edtPath)
        add(btnPath)
    }
}