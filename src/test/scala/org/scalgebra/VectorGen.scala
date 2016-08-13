package org.scalgebra

import org.scalacheck.{Arbitrary, Gen}
import algebra.ring.Semiring

import scala.reflect.ClassTag

/**
  * Generates Vectors.
  *
  * @author Daniyar Itegulov
  */
object VectorGen {
  implicit def arbitrarySemiringVector[T: Arbitrary : Semiring : ClassTag]: Arbitrary[Vector[T]] =
    Arbitrary {
      Gen.frequency(
        (85, DenseVectorGen.genSemiringDenseVector),
        (5, DenseVectorGen.genZeroDenseVector),
        (5, DenseVectorGen.genOneDenseVector),
        (5, DenseVectorGen.genUnitDenseVector)
      )
    }
}
