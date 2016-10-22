package org.scalgebra.generation

import algebra.ring.{MultiplicativeMonoid, Ring, Semiring}
import org.scalacheck.{Arbitrary, Gen}
import org.scalgebra.Vector

import scala.reflect.ClassTag

/**
  * Generates Vectors.
  *
  * @author Daniyar Itegulov
  */
object VectorGen {
  def genZeroVector[T: Arbitrary : Semiring : ClassTag]: Gen[Vector[T]] =
    Gen.sized { size =>
      Gen.const(Vector.zeros(size))
    }

  def genOneVector[T: Arbitrary : MultiplicativeMonoid : ClassTag]: Gen[Vector[T]] =
    Gen.sized { size =>
      Gen.const(Vector.ones(size))
    }

  implicit def arbitraryRingVector[T: Arbitrary : Ring : ClassTag]: Arbitrary[Vector[T]] =
    Arbitrary {
      Gen.frequency(
        (90, DenseVectorGen.genSemiringDenseVector[T]),
        (1, genZeroVector[T]),
        (1, DenseVectorGen.genZeroDenseVector[T]),
        (1, genOneVector[T]),
        (1, DenseVectorGen.genOneDenseVector[T]),
        (6, DenseVectorGen.genUnitDenseVector[T])
      )
    }
}
