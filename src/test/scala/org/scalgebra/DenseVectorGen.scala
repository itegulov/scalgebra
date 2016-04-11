package org.scalgebra

import org.scalacheck.{Arbitrary, Gen}
import spire.algebra.Semiring

import scala.reflect.ClassTag

/**
  * Generates DenseVectors.
  *
  * @author Daniyar Itegulov
  */
object DenseVectorGen {
  def genSemiringDenseVector[T: Arbitrary : Semiring : ClassTag]: Gen[DenseVector[T]] =
    for {
      length <- Gen.choose(0, 100)
      array <- Gen.containerOfN[Array, T](length, Arbitrary.arbitrary[T])
    } yield DenseVector(array)

  implicit def arbitrarySemiringDenseVector[T: Arbitrary : Semiring : ClassTag]: Arbitrary[DenseVector[T]] =
    Arbitrary {
      genSemiringDenseVector
    }
}
