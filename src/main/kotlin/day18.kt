package org.example

import java.io.File
import java.util.*

fun main() {
    var fileName = "day18"
    try {
        val text = File(fileName).readText()
        var theMatrix = Array(71) { Array(71) { '.' } }
        text.split("\n")
            .forEach {
                val (first, second) = it.split(",").map { it.trim().toInt() }
                theMatrix[first][second] = '#'
            }
        theMatrix.forEach { row ->
            println(row.joinToString(" "))
        }

        val shortestPathCost = dijkstra(theMatrix, Pair(0, 0), Pair(70, 70)) - 1
        println("Kürzeste Wegkosten: $shortestPathCost")
    } catch (e: Exception) {
      println(e)
    }
}
data class Cell(val row: Int, val col: Int, val cost: Int) : Comparable<Cell> {
    override fun compareTo(other: Cell): Int = this.cost - other.cost
}

fun dijkstra(matrix: Array<Array<Char>>, start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
    val rows = matrix.size
    val cols = matrix[0].size
    val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))
    val distances = Array(rows) { IntArray(cols) { Int.MAX_VALUE } }
    val priorityQueue = PriorityQueue<Cell>()
    priorityQueue.add(Cell(start.first, start.second, inputCost(matrix, start)))
    while (priorityQueue.isNotEmpty()) {
        val current = priorityQueue.poll()
        val (row, col, cost) = current
        if (row == end.first && col == end.second) {
            return cost
        }
        for ((dr, dc) in directions) {
            val newRow = row + dr
            val newCol = col + dc
            if (newRow in 0 until rows && newCol in 0 until cols) {
                val newCost = cost +  inputCost(matrix, Pair(newRow,newCol))
                if (newCost < distances[newRow][newCol]) {
                    distances[newRow][newCol] = newCost
                    priorityQueue.add(Cell(newRow, newCol, newCost))
                }
            }
        }
    }
    return -1
}

fun inputCost (matrix: Array<Array<Char>>, start: Pair<Int, Int>): Int {
    val input = matrix[start.first][start.second]
    if(input == '.')
       return 1
    else
      return Int.MAX_VALUE
}