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
  val maxMatrixSize = 100

  def genRingDenseMatrix[T: Arbitrary : Ring : ClassTag]: Gen[DenseMatrix[T]] =
    for {
      cols <- Gen.choose(0, maxMatrixSize)
      rows <- Gen.choose(0, maxMatrixSize)
      matrix <- Gen.containerOfN[Array, Array[T]](rows, Gen.containerOfN[Array, T](cols, Arbitrary.arbitrary[T]))
    } yield DenseMatrix(matrix)

  def genUnitDenseMatrix[T: Ring : ClassTag]: Gen[DenseMatrix[T]] = for {
    size <- Gen.choose(0, maxMatrixSize)
  } yield DenseMatrix.unit(size)

  def genZeroDenseMatrix[T: Ring : ClassTag]: Gen[DenseMatrix[T]] = for {
    n <- Gen.choose(0, maxMatrixSize)
    m <- Gen.choose(0, maxMatrixSize)
  } yield DenseMatrix.zeros[T](n, m)

  def genOneDenseMatrix[T: Ring : ClassTag]: Gen[DenseMatrix[T]] = for {
    n <- Gen.choose(0, maxMatrixSize)
    m <- Gen.choose(0, maxMatrixSize)
  } yield DenseMatrix.ones[T](n, m)

  def genOneRowedMatrix[T: Arbitrary : Ring : ClassTag]: Gen[DenseMatrix[T]] = for {
    size <- Gen.choose(0, maxMatrixSize)
    row <- Gen.containerOfN[Seq, T](size, Arbitrary.arbitrary[T])
  } yield DenseMatrix(row)

  def genOneColumnMatrix[T: Arbitrary : Ring : ClassTag]: Gen[DenseMatrix[T]] = for {
    size <- Gen.choose(0, maxMatrixSize)
    column <- Gen.containerOfN[Array, Seq[T]](size, Gen.containerOfN[Seq, T](1, Arbitrary.arbitrary[T]))
  } yield DenseMatrix(column)

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
