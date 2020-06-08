package com.skyface.taskmanager.kotlin.model

data class Task(
        val id: Int,
        val name: String,
        val description: String,
        val trigger: Int,
        val cron: String,
        val path: String
)