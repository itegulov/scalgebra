package org.scalgebra

import org.scalacheck.Arbitrary
import algebra.ring.Semiring

import scala.reflect.ClassTag

/**
  * @author Daniyar Itegulov
  */
object MatrixGen {

  implicit def arbitrarySemiringMatrix[T: Arbitrary : Semiring : ClassTag]: Arbitrary[Matrix[T]] =
    Arbitrary {
      DenseMatrixGen.genSemiringDenseMatrix
    }

}
