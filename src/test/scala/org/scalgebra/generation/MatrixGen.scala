package org.scalgebra.generation

import algebra.ring.Ring
import org.scalacheck.{Arbitrary, Gen}
import org.scalgebra.{DenseMatrix, Matrix}

import scala.reflect.ClassTag

/**
  * @author Daniyar Itegulov
  */
object MatrixGen {
  def genUnitMatrix[T: Ring : ClassTag]: Gen[Matrix[T]] =
    Gen.sized { size =>
      Gen.const(Matrix.unit(size))
    }

  def genZeroMatrix[T: Ring : ClassTag]: Gen[Matrix[T]] =
    Gen.sized { rows =>
      Gen.sized { cols =>
        Gen.const(Matrix.zeros[T](rows, cols))
      }
    }

  def genOneMatrix[T: Ring : ClassTag]: Gen[Matrix[T]] =
    Gen.sized { rows =>
      Gen.sized { cols =>
        Gen.const(Matrix.ones[T](rows, cols))
      }
    }

  implicit def arbitraryRingMatrix[T: Arbitrary : Ring : ClassTag]: Arbitrary[Matrix[T]] =
    Arbitrary {
      Gen.frequency[Matrix[T]](
        (80, DenseMatrixGen.genRingDenseMatrix),
        (5, DenseMatrixGen.genOneRowedMatrix),
        (5, DenseMatrixGen.genOneColumnMatrix),
        (1, DenseMatrixGen.genZeroDenseMatrix),
        (1, DenseMatrixGen.genUnitDenseMatrix),
        (1, DenseMatrixGen.genOneDenseMatrix),
        (1, genZeroMatrix),
        (1, genUnitMatrix),
        (1, genOneMatrix),
        (4, DenseMatrixGen.genOneElementMatrix)
      )
    }

}
