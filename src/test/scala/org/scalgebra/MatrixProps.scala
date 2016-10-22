package org.scalgebra

import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest._
import algebra.std.int._

import org.scalgebra.generation.MatrixGen._

/**
  * @author Daniyar Itegulov
  */
class MatrixProps extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {
  property("hash code and equals properties are kept") {
    forAll((a: Matrix[Vector, Int], b: Matrix[Vector, Int]) => {
      if (a.hashCode != b.hashCode) a should not be b
      if (a == b) a.hashCode shouldBe b.hashCode
    })
  }

  property("iterators have all elements") {
    forAll((matrix: Matrix[Vector, Int]) => {
      matrix.iterator.size shouldBe matrix.cols
      matrix.keysIterator.size shouldBe matrix.cols * matrix.rows
    })
  }

  property("colsIterator has all elements") {
    forAll((matrix: Matrix[Vector, Int]) => {
      val colsIteratorElements = matrix.colsIterator.flatten.toSet
      assert(matrix.flatten().forall(colsIteratorElements.contains))
    })
  }

  property("rowsIterator has all elements") {
    forAll((matrix: Matrix[Vector, Int]) => {
      val rowsIteratorElements = matrix.rowsIterator.flatten.toSet
      assert(matrix.flatten().forall(rowsIteratorElements.contains))
    })
  }

  property("string representation contains all elements") {
    forAll((matrix: Matrix[Vector, Int]) => {
      val representation = matrix.toString
      for (i <- 0 until matrix.rows)
        for (j <- 0 until matrix.cols)
          representation.contains(matrix(i, j).toString)
    })
  }

  property("conversion to 2d array contains all elements") {
    forAll((matrix: Matrix[Vector, Int]) => {
      val array = matrix.to2DArray
      for (i <- 0 until matrix.rows)
        for (j <- 0 until matrix.cols)
          matrix(i, j) shouldBe array(i)(j)
    })
  }
}
