package org.scalgebra.generation

import algebra.ring.{MultiplicativeMonoid, Ring, Semiring}
import org.scalacheck.{Arbitrary, Gen}
import org.scalgebra.DenseVector

import scala.reflect.ClassTag

/**
  * Generates DenseVectors.
  *
  * @author Daniyar Itegulov
  */
object DenseVectorGen {
  def genSemiringDenseVector[T: Arbitrary : Semiring : ClassTag]: Gen[DenseVector[T]] =
    Gen.sized { size =>
      for {
        array <- Gen.containerOfN[Array, T](size, Arbitrary.arbitrary[T])
      } yield DenseVector(array)
    }

  def genZeroDenseVector[T: Arbitrary : Semiring : ClassTag]: Gen[DenseVector[T]] =
    Gen.sized { size =>
      Gen.const(DenseVector.zeros(size))
    }

  def genOneDenseVector[T: Arbitrary : MultiplicativeMonoid : ClassTag]: Gen[DenseVector[T]] =
    Gen.sized { size =>
      Gen.const(DenseVector.ones(size))
    }

  def genUnitDenseVector[T: Arbitrary : Semiring : ClassTag]: Gen[DenseVector[T]] =
    for {
      value <- Arbitrary.arbitrary[T]
    } yield DenseVector(value)

  implicit def arbitrarySemiringDenseVector[T: Arbitrary : Ring : ClassTag]: Arbitrary[DenseVector[T]] =
    Arbitrary {
      Gen.frequency(
        (85, genSemiringDenseVector),
        (5, genZeroDenseVector),
        (5, genOneDenseVector),
        (5, genUnitDenseVector)
      )
    }
}
