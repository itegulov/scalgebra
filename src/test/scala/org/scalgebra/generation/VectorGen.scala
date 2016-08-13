package org.scalgebra.generation

import algebra.ring.Ring
import org.scalacheck.{Arbitrary, Gen}
import org.scalgebra.Vector

import scala.reflect.ClassTag

/**
  * Generates Vectors.
  *
  * @author Daniyar Itegulov
  */
object VectorGen {
  implicit def arbitraryRingVector[T: Arbitrary : Ring : ClassTag]: Arbitrary[Vector[T]] =
    Arbitrary {
      Gen.frequency(
        (85, DenseVectorGen.genSemiringDenseVector),
        (5, DenseVectorGen.genZeroDenseVector),
        (5, DenseVectorGen.genOneDenseVector),
        (5, DenseVectorGen.genUnitDenseVector)
      )
    }
}
