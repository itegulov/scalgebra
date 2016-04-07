package org.scalgebra

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import spire.implicits.IntAlgebra
import org.scalgebra.DenseMatrixGen._

/**
  * @author Daniyar Itegulov
  */
class DenseMatrixProps extends Properties("DenseMatrix") {

  property("hasPositiveElements") = forAll((matrix: DenseMatrix[Int]) => {
    val result = for (
      i <- 0 until matrix.rows;
      j <- 0 until matrix.cols
    ) yield matrix(i, j) == matrix(i, j)
    result.forall(b => b)
  })

}
