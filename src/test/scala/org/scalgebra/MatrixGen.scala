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
        (90, DenseMatrixGen.genRingDenseMatrix),
        (3, DenseMatrixGen.genZeroDenseMatrix),
        (3, DenseMatrixGen.genUnitDenseMatrix),
        (3, DenseMatrixGen.genOneDenseMatrix)
      )
    }

}
