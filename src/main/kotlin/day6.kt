package org.example

import java.io.File
import java.io.IOException

class MyClass {
    companion object {
        var staticVariable: Int = 0
    }
}

fun main() {
    val fileName = "guard.txt"
    try {
        val text = File(fileName).readText()
        var theMatrix: Array<Array<Char>> = text.split("\n")
            .map { line -> line.toCharArray().toTypedArray() }
            .toTypedArray()
        val charToFind = '^'
        var theOriginal = theMatrix;
        var guardPosition = findElement(theMatrix, charToFind)
        theMatrix [guardPosition?.first!!][guardPosition.second] = '.'
        println(guardPosition ?: "Element not found")
        var objectToTurn = guardPosition
        while (objectToTurn?.first != -1 || objectToTurn.second != -1) {
            objectToTurn = getcrashElementPositon(guardPosition, theMatrix)
            guardPosition = theMatrix.size - objectToTurn.second -1  to objectToTurn.first +1
            theMatrix = rotateMatrixCounterclockwise(theMatrix)
        }
        println("${MyClass.staticVariable}")
    } catch (e: IOException) {
        println("Error reading file: ${e.message}")
    }
}

fun rotateMatrixCounterclockwise(matrix: Array<Array<Char>>): Array<Array<Char>> {
    val n = matrix.size
    val rotated = Array(n) { Array(n) { ' ' } }
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            rotated[n - 1 - j][i] = matrix[i][j]
        }
    }
    return rotated
}


fun getcrashElementPositon(guardPosition: Pair<Int, Int>?, theMatrix: Array<Array<Char>>): Pair<Int, Int> {
    var element = theMatrix[guardPosition?.first!!][guardPosition.second]
    var first = guardPosition?.first!!
    try {
        while (element == '.' || element == 'X') {
            if (element == '.'){
                theMatrix[first][guardPosition.second] = 'X'
                MyClass.staticVariable++
            }
            first--
            element = theMatrix[first][guardPosition.second]
        }
    } catch (e: Exception) {
        return first++ to -1
    }
    return first to guardPosition.second
}

fun findElement(matrix: Array<Array<Char>>, target: Char): Pair<Int, Int>? {
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            if (matrix[i][j] == target) {
                return i to j
            }
        }
    }
    return null
}