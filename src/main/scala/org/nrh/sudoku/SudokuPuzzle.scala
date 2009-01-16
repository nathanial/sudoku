package org.nrh.sudoku
import scala.collection.mutable.{ListBuffer}
import org.nrh.scream.{Matrix,Problem,Var}
import org.nrh.scream.Domain._
import org.nrh.scream.IntervalImplicits._
import org.nrh.scream.Util._

class SudokuPuzzle(matrix:Matrix[Int]) {

  def solve:Matrix[BigInt] = {
    val p = Problem.sudoku
    val toVar = new VarTransformer(p)
    val puzzle = new Matrix[Var](matrix.map(toVar))

    puzzle.squares.foreach(s => p.allDiff(s:_*))
    puzzle.rows.foreach(r => p.allDiff(r:_*))
    puzzle.columns.foreach(c => p.allDiff(c:_*))

    p.propogateConstraints
    p.firstSolution

    return new Matrix(puzzle.map(toInt))
  }

  private def toInt(v:Var):BigInt = v.domain.toBigInt
  
  private class VarTransformer(p:Problem) extends Function[Int,Var]   {
    var (x,y) = (0,0)
      
    def apply(i:Int):Var = {
      val (_x,_y) = (x,y)
	if(x == 8){
	  x = 0
	  y += 1
	}
	else {
	  x += 1
	}
      if(i > 0){
	val v = p.newVar("("+_x+","+_y+")",singleton(i))
	return v
      }
      else {
	val v = p.newVar("("+_x+","+_y+")",domain(1 upto 9))
	return v
      }    
    }
  }

  override def toString = matrix.toString
}

object SudokuPuzzle {
  def apply(els:Seq[Int]):SudokuPuzzle = {
    new SudokuPuzzle(new Matrix(els))
  }
  
  def generate:SudokuPuzzle = {
    val a = Array(
      0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0)
    
    return SudokuPuzzle(a)
  }

  def generateComplete:Matrix[BigInt] = {
    val puzzle = generate
    println(puzzle.toString)
    return puzzle.solve
  }
    
}
