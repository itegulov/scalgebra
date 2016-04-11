package org.scalgebra

import org.scalacheck.Arbitrary
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
      DenseVectorGen.genSemiringDenseVector
    }
}
