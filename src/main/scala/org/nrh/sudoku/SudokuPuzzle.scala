package org.nrh.sudoku
import scala.collection.mutable.{ListBuffer}
import org.nrh.scream._
import org.nrh.scream.Domain._
import org.nrh.scream.IntervalImplicits._
import org.nrh.scream.Util._

class SudokuPuzzle(matrix:Matrix[Int]) {

  def solve:Matrix[BigInt] = solve(false)

  private def solve(generatorMode:Boolean):Matrix[BigInt] = {
    var p:Problem = null
    var puzzle: Matrix[Var] = null
    def printSudoku(state:State) {
      val vars = state.assignments.map(item => item._1).toList
      bind(vars){
	state.assignToVars
	println(new Matrix(puzzle.map(toInt)))
      }
    }
    def doNothing(state:State) = {}

    if(generatorMode){
      p = Problem.randomized(Math.MAX_INT,doNothing)
    }
    else {
      p = Problem.standard(Math.MAX_INT,doNothing)
    }

    val toVar = new VarTransformer(p)
    puzzle = new Matrix[Var](matrix.map(toVar))

    puzzle.squares.foreach(s => p.allDiff(s:_*))
    puzzle.rows.foreach(r => p.allDiff(r:_*))
    puzzle.columns.foreach(c => p.allDiff(c:_*))

    p.propogateConstraints
    p.firstSolution

    return new Matrix(puzzle.map(toInt))
  }

  private def toInt(v:Var):BigInt = {
    if(v.isAssigned)
      v.domain.toBigInt
    else 
      0
  }
  
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
    return puzzle.solve(true)
  }
    
}
