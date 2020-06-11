package com.skyface.taskmanager.kotlin.utils

class CronBuilder(
        var seconds: String = "0",
        var minutes: String = "*",
        var hours: String = "*",
        var dayOfMonth: String = "?",
        var month: String = "*",
        var dayOfWeek: String = "*",
        var year: String = "*"
) {
    fun empty(): String {
        return "?"
    }

    fun every(): String {
        return "*"
    }

    fun cron(): String {
        return "$seconds $minutes $hours $dayOfMonth $month $dayOfWeek $year"
    }

    fun time():String{
        return ""
    }

    fun date():String{
        return ""
    }
}