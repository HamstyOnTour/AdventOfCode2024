package org.example

import java.io.File
import java.io.IOException

fun main() {
    val fileName = "xmas.txt"
    try {
        val text = File(fileName).readText()
        val forward = """XMAS""".toRegex()
        val backward = """SAMX""".toRegex()
        var result = forward.findAll(text).count() + backward.findAll(text).count()

        val lines = text.split("\n")
        var columns = ""

        for (i in 0 until lines.maxOf { it.length }) {
            val column = lines.map { line -> line.getOrNull(i) ?: ' ' }.joinToString("")
            columns += "\n"
            columns += column;
        }

        result += forward.findAll(columns).count()
        result += backward.findAll(columns).count()

        val textArray =text.split("\n").toTypedArray()
        val rowCount = textArray.size
        val colCount = textArray[0].length

        var diagonals = ""

        for (col in 0 until colCount) {
            var row = 0
            var column = col
            val diagonal = StringBuilder()
            while (row < rowCount && column < colCount) {
                diagonal.append(textArray[row][column])
                row++
                column++
            }
            diagonals += '\n'
            diagonals += diagonal.toString()
        }

        for (col in colCount - 1 downTo 0) {
            var row = 0
            var column = col
            val diagonal = StringBuilder()
            while (row < rowCount && column >= 0) {
                diagonal.append(textArray[row][column])
                row++
                column--
            }
            diagonals += '\n'
            diagonals += diagonal.toString()
        }

        for (row in 1 until rowCount) {
            var col = colCount - 1
            var r = row
            val diagonal = StringBuilder()
            while (r < rowCount && col >= 0) {
                diagonal.append(textArray[r][col])
                r++
                col--
            }
            diagonals += '\n'
            diagonals += diagonal.toString()
        }


        for (row in 1 until rowCount) {
            var col = 0
            var r = row
            val diagonal = StringBuilder()
            while (r < rowCount && col < colCount) {
                diagonal.append(textArray[r][col])
                r++
                col++
            }
            diagonals += '\n'
            diagonals += diagonal.toString()
        }
        result += forward.findAll(diagonals).count()
        result += backward.findAll(diagonals).count()

        println("${result}")
    } catch (e: IOException) {
        println("Error reading file: ${e.message}")
    }
}
