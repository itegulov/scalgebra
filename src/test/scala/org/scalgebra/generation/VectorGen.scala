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
        (90, DenseVectorGen.genSemiringDenseVector),
        (1, genZeroVector),
        (1, DenseVectorGen.genZeroDenseVector),
        (1, genOneVector),
        (1, DenseVectorGen.genOneDenseVector),
        (6, DenseVectorGen.genUnitDenseVector)
      )
    }
}
