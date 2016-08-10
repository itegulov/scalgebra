package org.scalgebra

import org.scalacheck.{Arbitrary, Gen}
import algebra.ring.Ring

import scala.reflect.ClassTag

/**
  * @author Daniyar Itegulov
  */
object MatrixGen {

  implicit def arbitrarySemiringMatrix[T: Arbitrary : Ring : ClassTag]: Arbitrary[Matrix[T]] =
    Arbitrary {
      Gen.frequency[DenseMatrix[T]](
        (90, DenseMatrixGen.genSemiringDenseMatrix),
        (5, DenseMatrixGen.genZeroDenseMatrix),
        (5, DenseMatrixGen.genUnitDenseMatrix)
      )
    }

}
