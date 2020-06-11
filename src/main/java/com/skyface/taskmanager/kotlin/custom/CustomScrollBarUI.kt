package com.skyface.taskmanager.kotlin.custom

import java.awt.*
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.plaf.basic.BasicScrollBarUI

class CustomScrollBarUI(private val isVertical: Boolean = true) : BasicScrollBarUI() {
    override fun createDecreaseButton(orientation: Int): JButton {
        return createInvisibleButton()
    }

    override fun createIncreaseButton(orientation: Int): JButton {
        return createInvisibleButton()
    }

    private fun createInvisibleButton(): JButton {
        return object : JButton() {
            override fun getPreferredSize(): Dimension {
                return Dimension()
            }
        }
    }

    override fun paintTrack(g: Graphics?, c: JComponent?, trackBounds: Rectangle?) {}

    override fun paintThumb(g: Graphics?, c: JComponent, thumbBounds: Rectangle) {
        val graphic = g as Graphics2D

        graphic.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        )

        graphic.paint = if (!c.isEnabled) {
            return
        } else if (isDragging) {
            Color.WHITE
        } else if (isThumbRollover) {
            Color.LIGHT_GRAY
        } else {
            Color.GRAY
        }

        if (isVertical) {
            graphic.drawRoundRect(
                    thumbBounds.x,
                    thumbBounds.y,
                    thumbBounds.width - 6,
                    thumbBounds.height - 4,
                    12,
                    12
            )
        } else {
            graphic.drawRoundRect(
                    thumbBounds.x,
                    thumbBounds.y + 2,
                    thumbBounds.width - 2,
                    thumbBounds.height - 4,
                    12,
                    12
            )
        }
    }
}