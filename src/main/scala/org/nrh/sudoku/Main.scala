package org.nrh.sudoku

object Main {
  def main(args:Array[String]){
    val numberOfPuzzles = Integer.parseInt(args(0))
    for(_ <- 1 to numberOfPuzzles)
      println(SudokuPuzzle.generateComplete.toString)
  }

}
