package org.scalgebra

import org.scalactic.{Equality, TolerantNumerics}
import org.scalatest.{FlatSpec, Matchers}

import scala.math.sqrt

/**
  * @author Daniyar Itegulov
  */
class DenseVectorDoubleSpec extends FlatSpec with Matchers {
  def tolerantDenseVectorDoubleEquality(implicit equalityDouble: Equality[Double]): Equality[DenseVector[Double]] =
    new Equality[DenseVector[Double]] {
      override def areEqual(a: DenseVector[Double], b: Any): Boolean = b match {
        case bVec: DenseVector[_] if a.length == bVec.length =>
          (0 until a.length).forall(i => equalityDouble.areEqual(a(i), bVec(i)))
        case _ =>
          false
      }
    }

  implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(1e-6)
  implicit val denseVectorDoubleEq = tolerantDenseVectorDoubleEquality

  "DenseVector[Double]" should "be able to normalize" in {
    val vector: DenseVector[Double] = DenseVector(1D, 1D)
    assert(vector.normalize === DenseVector(sqrt(2.0D) / 2.0D, sqrt(2.0D) / 2.0D))
  }

  "DenseVector[Double]" should "be able to calculate magnitude" in {
    val vector: DenseVector[Double] = DenseVector(1D, 1D)
    assert(vector.magnitude === sqrt(2.0D))
  }
}
