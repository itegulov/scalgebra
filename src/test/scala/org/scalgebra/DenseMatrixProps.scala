package org.scalgebra

import org.scalatest._
import org.scalatest.prop._
import org.scalgebra.DenseMatrixGen._
import spire.implicits.IntAlgebra

/**
  * @author Daniyar Itegulov
  */
class DenseMatrixProps extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {

  property("has positive indexed elements") {
    forAll((matrix: DenseMatrix[Int]) => {
      for (i <- 0 until matrix.rows)
        for (j <- 0 until matrix.cols)
          matrix(i, j) shouldBe matrix(i, j)
    })
  }

  property("has negative indexed elements") {
    forAll { (matrix: DenseMatrix[Int]) =>
      for (i <- 0 until matrix.rows)
        for (j <- 0 until matrix.cols)
          matrix(-i, -j) shouldBe matrix(matrix.rows - 1 - i, matrix.cols - 1 - j)
    }
  }

}
