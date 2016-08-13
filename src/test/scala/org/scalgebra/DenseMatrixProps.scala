package org.scalgebra

import org.scalatest._
import org.scalatest.prop._
import org.scalacheck.Prop.BooleanOperators
import algebra.std.int._

import org.scalgebra.generation.DenseMatrixGen._

/**
  * @author Daniyar Itegulov
  */
class DenseMatrixProps extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {
  property("has positive indexed elements") {
    forAll((matrix: DenseMatrix[Int]) => {
      for (i <- 0 until matrix.rows)
        for (j <- 0 until matrix.cols)
          matrix(i, j)
    })
  }

  property("has negative indexed elements") {
    forAll { (matrix: DenseMatrix[Int]) =>
      for (i <- 1 until matrix.rows)
        for (j <- 1 until matrix.cols)
          matrix(-i, -j) shouldBe matrix(matrix.rows - i, matrix.cols - j)
    }
  }

  property("has no out of bound elements") {
    forAll { (matrix: DenseMatrix[Int]) =>
      for (i <- 0 until matrix.rows) {
        intercept[IndexOutOfBoundsException] {
          matrix(i, matrix.cols)
        }
        intercept[IndexOutOfBoundsException] {
          matrix(i, -matrix.cols)
        }
      }

      for (j <- 0 until matrix.cols) {
        intercept[IndexOutOfBoundsException] {
          matrix(matrix.rows, j)
        }
        intercept[IndexOutOfBoundsException] {
          matrix(-matrix.rows, j)
        }
      }
    }
  }

  property("can be added with zero matrix") {
    forAll((matrix: DenseMatrix[Int]) => {
      matrix + DenseMatrix.zeros(matrix.rows, matrix.cols) shouldBe matrix
      DenseMatrix.zeros(matrix.rows, matrix.cols) + matrix shouldBe matrix
    })
  }

  property("can be added with ones matrix") {
    forAll((matrix: DenseMatrix[Int]) => {
      assert(matrix + DenseMatrix.ones(matrix.rows, matrix.cols) !== matrix)
      assert(DenseMatrix.ones(matrix.rows, matrix.cols) + matrix !== matrix)
    })
  }

  property("zero matrix is a subtraction identity") {
    forAll((matrix: DenseMatrix[Int]) => {
      matrix - DenseMatrix.zeros(matrix.rows, matrix.cols) shouldBe matrix
    })
  }

  property("commutativity of matrix addition") {
    forAll((x: DenseMatrix[Int], y: DenseMatrix[Int]) => {
      (x.rows == y.rows && x.cols == y.cols) ==> (x + y == y + x)
    })
  }

  property("associativity of matrix addition") {
    forAll((x: DenseMatrix[Int], y: DenseMatrix[Int], z: DenseMatrix[Int]) => {
      (x.rows == y.rows && y.rows == z.rows && x.cols == y.cols && y.cols == z.cols) ==> ((x + y) + z == x + (y + z))
    })
  }
}
