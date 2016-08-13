package org.scalgebra

import org.scalacheck._
import algebra.ring._

import scala.reflect.ClassTag

/**
  * Generates DenseMatrices.
  *
  * @author Daniyar Itegulov
  */
object DenseMatrixGen {
  def genRingDenseMatrix[T: Arbitrary : Ring : ClassTag]: Gen[DenseMatrix[T]] =
    Gen.sized { rows =>
      Gen.sized { cols =>
        for {
          matrix <- Gen.containerOfN[Array, Array[T]](rows, Gen.containerOfN[Array, T](cols, Arbitrary.arbitrary[T]))
        } yield DenseMatrix(matrix)
      }
    }

  def genUnitDenseMatrix[T: Ring : ClassTag]: Gen[DenseMatrix[T]] =
    Gen.sized { size =>
      Gen.const(DenseMatrix.unit(size))
    }

  def genZeroDenseMatrix[T: Ring : ClassTag]: Gen[DenseMatrix[T]] =
    Gen.sized { rows =>
      Gen.sized { cols =>
        Gen.const(DenseMatrix.zeros[T](rows, cols))
      }
    }

  def genOneDenseMatrix[T: Ring : ClassTag]: Gen[DenseMatrix[T]] =
    Gen.sized { rows =>
      Gen.sized { cols =>
        Gen.const(DenseMatrix.ones[T](rows, cols))
      }
    }

  def genOneRowedMatrix[T: Arbitrary : Ring : ClassTag]: Gen[DenseMatrix[T]] =
    Gen.sized { size =>
      for {
        matrix <- Gen.containerOfN[Seq, T](size, Arbitrary.arbitrary[T])
      } yield DenseMatrix(matrix)
    }

  def genOneColumnMatrix[T: Arbitrary : Ring : ClassTag]: Gen[DenseMatrix[T]] =
    Gen.sized { size =>
      for {
        matrix <- Gen.containerOfN[Array, Seq[T]](size, Gen.containerOfN[Seq, T](1, Arbitrary.arbitrary[T]))
      } yield DenseMatrix(matrix)
    }

  implicit def arbitraryRingDenseMatrix[T: Arbitrary : Ring : ClassTag]: Arbitrary[DenseMatrix[T]] =
    Arbitrary {
      Gen.frequency[DenseMatrix[T]](
        (75, genRingDenseMatrix),
        (5, genOneRowedMatrix),
        (5, genOneColumnMatrix),
        (5, genZeroDenseMatrix),
        (5, genUnitDenseMatrix),
        (5, genOneDenseMatrix)
      )
    }

  def arbitraryOneRowedDenseMatrix[T: Arbitrary : Ring : ClassTag]: Arbitrary[DenseMatrix[T]] =
    Arbitrary {
      genOneRowedMatrix
    }

  def arbitraryOneColumnDenseMatrix[T: Arbitrary : Ring : ClassTag]: Arbitrary[DenseMatrix[T]] =
    Arbitrary {
      genOneColumnMatrix
    }
}
