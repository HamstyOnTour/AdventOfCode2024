package org.example

import java.io.File

fun main() {
    var fileName = "day20Input"
    try {
        val text = File(fileName).readText()
        val lines = text.split("\n")

        val array2D: Array<Array<String>> = lines
            .map { line -> line.map { it.toString() }.toTypedArray() }
            .toTypedArray()

        for (row in array2D) {
            println(row.joinToString(" "))
        }

        val startSymbol = 'S'
        val endSymbol = "E"
        var endPosition = findSymbol(array2D, endSymbol)

        println("$endPosition")
        var counter = 0
        var symbol = endSymbol
        do {
            endPosition = goStep(array2D, endPosition, startSymbol.toString())
            symbol = array2D[endPosition!!.first][endPosition.second]
            counter++
            if (array2D[endPosition!!.first][endPosition.second] != startSymbol.toString()){
                array2D[endPosition.first][endPosition.second] = counter.toString()
            }
        } while (startSymbol.toString() != symbol)

        for (row in array2D) {
            println(row.joinToString(" "))
        }

        val cheats = checkCheatCodes(array2D, counter)
        println("$cheats")
    } catch (e: Exception) {
        println(e)
    }
}

private const val minDistanceToCheat = 100

fun checkCheatCodes(array2D: Array<Array<String>>, counter: Int): Any {
    var distance = 0
    var cheatsWithMinDistance = 0
    for (i in 1 until array2D.size - 1) {
        for (j in 1 until array2D[i].size - 1) {
           if(array2D[i][j] == wallSymbol){
               if (array2D[i][j - 1] != wallSymbol && array2D[i][j + 1] != wallSymbol) {
                   distance = getChatDistance(array2D[i][j - 1], array2D[i][j + 1], counter)
                   if (distance < 0) {
                       distance *= -1
                   }
                   distance = distance - 2
                   if(distance >= minDistanceToCheat){
                       cheatsWithMinDistance++
                   }
               }
               if (array2D[i-1][j] != wallSymbol && array2D[i+1][j] != wallSymbol) {
                   distance = getChatDistance(array2D[i-1][j], array2D[i+1][j], counter)
                   if (distance < 0) {
                       distance *= -1
                   }
                   distance = distance - 2
                   if(distance >= minDistanceToCheat){
                       cheatsWithMinDistance++
                   }
               }
           }
        }
    }

    return cheatsWithMinDistance;
}

fun getChatDistance(s: String, s1: String, counter: Int): Int {
    var from = s
    var to = s1
    if (s == startSymbol) {
        from = counter.toString()
    }
    if (s == endSymbol){
        from = "0"
    }
    if (s1 == startSymbol){
        to = counter.toString()
    }
    if (s1 == endSymbol)
        to = "0"
    return from.toInt()-to.toInt()
}

private const val trackSymbol = "."
private const val wallSymbol = "#"
private const val startSymbol = "S"
private const val endSymbol = "E"

fun goStep(array2D: Array<Array<String>>, endPosition: Pair<Int, Int>?, startSymbol: String): Pair<Int, Int>? {
    if (array2D[endPosition!!.first][endPosition.second - 1] == trackSymbol || array2D[endPosition.first][endPosition.second - 1] == startSymbol) {
        return Pair(endPosition.first, (endPosition.second - 1))
    }
    if (array2D[endPosition!!.first - 1][endPosition.second] == trackSymbol || array2D[endPosition.first - 1][endPosition.second] == startSymbol) {
        return Pair(endPosition.first - 1, (endPosition.second))
    }
    if (array2D[endPosition!!.first][endPosition.second + 1] == trackSymbol || array2D[endPosition.first][endPosition.second + 1] == startSymbol) {
        return Pair(endPosition.first, (endPosition.second + 1))
    }
    if (array2D[endPosition!!.first + 1][endPosition.second] == trackSymbol || array2D[endPosition.first + 1][endPosition.second] == startSymbol) {
        return Pair(endPosition.first + 1, (endPosition.second))
    }
    return null
}


fun findSymbol(array2D: Array<Array<String>>, symbol: String): Pair<Int, Int>? {
    for (i in array2D.indices) {
        for (j in array2D[i].indices) {
            if (array2D[i][j] == symbol.toString()) {
                return Pair(i, j)
            }
        }
    }
    return null
}