package com.skyface.taskmanager.kotlin.database

import com.skyface.taskmanager.kotlin.model.Task
import com.skyface.taskmanager.kotlin.utils.DATABASE_NAME
import java.net.URL
import java.sql.Connection
import java.sql.DriverManager


class Database {
    private var connection: Connection?

    init {
        val path: URL = javaClass.getResource("/raw/$DATABASE_NAME.db")
        connection = DriverManager.getConnection("jdbc:sqlite::resource:$path")
    }

    fun getData(): MutableList<Task> {
        val list = mutableListOf<Task>()

        connection?.let { connection ->
            val query = """
                SELECT name, description, trigger, cron, path FROM task
            """.trimIndent()

            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(query)
            statement.closeOnCompletion()

            while (resultSet.next()) {
                list.add(Task(
                        null,
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("trigger"),
                        resultSet.getString("cron"),
                        resultSet.getString("path")
                ))
            }
            return list
        }
        return list
    }

    fun putData(task: Task) {
        connection?.let { connection ->
            val query = """
                INSERT INTO task (name, description, trigger, cron, path)
                VALUES ('${task.name}', '${task.description}', ${task.trigger}, '${task.cron}', '${task.path}')
            """.trimIndent()

            val statement = connection.createStatement()
            statement.executeUpdate(query)
            statement.closeOnCompletion()
        }
    }

    fun removeData(name: String) {
        connection?.let { connection ->
            val query = """
                DELETE FROM task WHERE name = '$name'
            """.trimIndent()

            val statement = connection.createStatement()
            statement.executeUpdate(query)
            statement.closeOnCompletion()
        }
    }

    fun close() {
        connection?.close()
    }
}