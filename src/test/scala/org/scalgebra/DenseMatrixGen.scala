package org.scalgebra

import org.scalacheck._
import spire.algebra._

import scala.reflect.ClassTag

/**
  * Generates DenseMatrices.
  *
  * @author Daniyar Itegulov
  */
object DenseMatrixGen {
  def genSemiringDenseMatrix[T: Arbitrary : Semiring : ClassTag]: Gen[DenseMatrix[T]] =
    for {
      cols <- Gen.choose(0, 100)
      rows <- Gen.choose(0, 100)
      matrix <- Gen.containerOfN[Array, Array[T]](rows, Gen.containerOfN[Array, T](cols, Arbitrary.arbitrary[T]))
    } yield DenseMatrix(matrix)

  implicit def arbitrarySemiringDenseMatrix[T: Arbitrary : Semiring : ClassTag]: Arbitrary[DenseMatrix[T]] =
    Arbitrary {
      genSemiringDenseMatrix
    }
}
