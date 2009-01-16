package org.nrh
import org.scalatest._
import org.nrh.scream._
import org.nrh.sudoku._

object SudokuTest {
  def main(args: Array[String]){
    (new SudokuTest).execute()
  }
}

class SudokuTest extends Suite with Logging {
  def testSudoku1() {
    logger.info("begin test Sudoku1")
    val puzzle = SudokuPuzzle(Array(
      0, 0, 0, 0, 8, 0, 0, 0, 0,
      0, 0, 0, 1, 0, 6, 5, 0, 7,
      4, 0, 2, 7, 0, 0, 0, 0, 0,
      0, 8, 0, 3, 0, 0, 1, 0, 0,
      0, 0, 3, 0, 0, 0, 8, 0, 0,
      0, 0, 5, 0, 0, 9, 0, 7, 0,
      0, 5, 0, 0, 0, 8, 0, 0, 6,
      3, 0, 1, 2, 0, 4, 0, 0, 0,
      0, 0, 6, 0, 1, 0, 0, 0, 0))

    val answer = puzzle.solve

    val correct_answer = new Matrix[Int](Array(
      5, 6, 7, 4, 8, 3, 2, 9, 1,
      9, 3, 8, 1, 2, 6, 5, 4, 7,
      4, 1, 2, 7, 9, 5, 3, 6, 8,
      6, 8, 9, 3, 7, 2, 1, 5, 4,
      7, 4, 3, 6, 5, 1, 8, 2, 9,
      1, 2, 5, 8, 4, 9, 6, 7, 3,
      2, 5, 4, 9, 3, 8, 7, 1, 6,
      3, 7, 1, 2, 6, 4, 9, 8, 5,
      8, 9, 6, 5, 1, 7, 4, 3, 2))

    logger.info(answer.toString)
    assert(answer sameAs correct_answer)
  }

  def testSudoku2() {
    logger.info("begin test Sudoku2")
    val puzzle = SudokuPuzzle(Array(
      0, 0, 6, 1, 7, 0, 2, 9, 0,
      0, 0, 1, 3, 8, 0, 4, 6, 0,
      3, 9, 0, 0, 6, 0, 0, 0, 0,
      0, 0, 0, 6, 1, 0, 0, 2, 0,
      0, 1, 8, 0, 0, 0, 6, 7, 0,
      0, 5, 0, 0, 2, 7, 0, 0, 0,
      0, 0, 0, 0, 4, 0, 0, 8, 2,
      0, 7, 2, 0, 9, 8, 3, 0, 0,
      0, 4, 5, 0, 3, 6, 7, 0, 0))

    val answer = puzzle.solve

    val correct_answer = new Matrix[Int](Array(
      4, 8, 6, 1, 7, 5, 2, 9, 3,
      5, 2, 1, 3, 8, 9, 4, 6, 7,
      3, 9, 7, 4, 6, 2, 1, 5, 8,
      7, 3, 9, 6, 1, 4, 8, 2, 5,
      2, 1, 8, 9, 5, 3, 6, 7, 4,
      6, 5, 4, 8, 2, 7, 9, 3, 1,
      9, 6, 3, 7, 4, 1, 5, 8, 2,
      1, 7, 2, 5, 9, 8, 3, 4, 6,
      8, 4, 5, 2, 3, 6, 7, 1, 9))

    logger.info(answer.toString)
    assert(answer sameAs correct_answer)
  }
   

  def testSudoku3() {
    logger.info("begin test Sudoku3")
    val puzzle = SudokuPuzzle(Array(
      0, 4, 0, 0, 5, 3, 0, 0, 0, 
      0, 0, 0, 2, 0, 0, 0, 3, 8,
      0, 0, 0, 7, 0, 0, 6, 5, 0,
      0, 0, 6, 8, 0, 0, 0, 0, 1,
      0, 8, 0, 0, 0, 0, 0, 7, 0, 
      2, 0, 0, 0, 0, 7, 4, 0, 0,
      0, 3, 7, 0, 0, 9, 0, 0, 0,
      9, 5, 0, 0, 0, 2, 0, 0, 0,
      0, 0, 0, 4, 3, 0, 0, 1, 0))

    val answer = puzzle.solve

    val correct_answer = new Matrix[Int](Array(
      6, 4, 8, 9, 5, 3, 1, 2, 7,
      7, 1, 5, 2, 4, 6, 9, 3, 8,
      3, 2, 9, 7, 1, 8, 6, 5, 4,
      5, 7, 6, 8, 2, 4, 3, 9, 1,
      4, 8, 3, 5, 9, 1, 2, 7, 6,
      2, 9, 1, 3, 6, 7, 4, 8, 5,
      1, 3, 7, 6, 8, 9, 5, 4, 2, 
      9, 5, 4, 1, 7, 2, 8, 6, 3,
      8, 6, 2, 4, 3, 5, 7, 1, 9))


    logger.info(answer.toString)
    assert(answer sameAs correct_answer)
  }

  def testSudoku4() {
    logger.info("begin test Sudoku4")
    val puzzle = SudokuPuzzle(Array(
      0, 7, 0, 0, 0, 0, 0, 0, 1,
      0, 0, 0, 0, 2, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0,
      9, 0, 0, 0, 0, 0, 5, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 9, 0, 0, 8, 0, 0, 3, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0))

    val answer = puzzle.solve
    
    logger.info(answer.toString)
  }

/*  def testSudoku5() {
    logger.info("begin test Sudoku5")
    val puzzle = SudokuPuzzle.generate
    logger.info("puzzle")
    logger.info(puzzle.toString)
    val answer = puzzle.solve
    logger.info("answer")
    logger.info(answer.toString)
  }

  def testSudoku7() {
    logger.info("begin test Sudoku7")
    val puzzle = SudokuPuzzle(Array(
      0, 0, 0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 3, 0, 8, 5, 
      0, 0, 1, 0, 2, 0, 0, 0, 0, 
      0, 0, 0, 5, 0, 7, 0, 0, 0, 
      0, 0, 4, 0, 0, 0, 1, 0, 0, 
      0, 9, 0, 0, 0, 0, 0, 0, 0, 
      5, 0, 0, 0, 0, 0, 0, 7, 3, 
      0, 0, 2, 0, 1, 0, 0, 0, 0, 
      0, 0, 0, 0, 4, 0, 0, 0, 9))

    val answer = puzzle.solve
    
    logger.info(answer.toString)
  }
      

  def testSudoku6() {
   logger.info("begin test Sudoku6")
    for(_ <- 1 to 10) {
      logger.info(SudokuPuzzle.generateComplete.toString)
    }
  }
  */
}
