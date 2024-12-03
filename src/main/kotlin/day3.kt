package org.example

import java.io.File
import java.io.IOException

fun main() {

    val fileName = "mul.txt"
    try {
        val text = File(fileName).readText()
        val regex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
        val result = regex.findAll(text)
        var resultSum = 0
        for(match in result){
            resultSum += match.groupValues[1].toInt() * match.groupValues[2].toInt()
        }
        println("${resultSum}")
    } catch (e: IOException){
        println("Error reading file: ${e.message}")
    }

}