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

  def genSemiringDenseMatrix[T: Arbitrary : Ring : ClassTag]: Gen[DenseMatrix[T]] =
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

  implicit def arbitraryRingDenseMatrix[T: Arbitrary : Ring : ClassTag]: Arbitrary[DenseMatrix[T]] =
    Arbitrary {
      Gen.frequency[DenseMatrix[T]](
        (90, genSemiringDenseMatrix),
        (5, genZeroDenseMatrix),
        (5, genUnitDenseMatrix)
      )
    }
}
