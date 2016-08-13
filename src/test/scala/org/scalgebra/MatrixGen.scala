package org.scalgebra

import org.scalacheck.{Arbitrary, Gen}
import algebra.ring.Ring

import scala.reflect.ClassTag

/**
  * @author Daniyar Itegulov
  */
object MatrixGen {

  implicit def arbitraryRingMatrix[T: Arbitrary : Ring : ClassTag]: Arbitrary[Matrix[T]] =
    Arbitrary {
      Gen.frequency[DenseMatrix[T]](
        (75, DenseMatrixGen.genRingDenseMatrix),
        (5, DenseMatrixGen.genOneRowedMatrix),
        (5, DenseMatrixGen.genOneColumnMatrix),
        (5, DenseMatrixGen.genZeroDenseMatrix),
        (5, DenseMatrixGen.genUnitDenseMatrix),
        (5, DenseMatrixGen.genOneDenseMatrix)
      )
    }

}
