package org.scalgebra

import org.scalactic.{Equality, TolerantNumerics}
import org.scalatest.{FlatSpec, Matchers}

import scala.math.sqrt

/**
  * @author Daniyar Itegulov
  */
class DenseVectorFloatSpec extends FlatSpec with Matchers {
  def tolerantDenseVectorFloatEquality(implicit equalityFloat: Equality[Float]): Equality[DenseVector[Float]] =
    new Equality[DenseVector[Float]] {
      override def areEqual(a: DenseVector[Float], b: Any): Boolean = b match {
        case bVec: DenseVector[_] if a.length == bVec.length =>
          (0 until a.length).forall(i => equalityFloat.areEqual(a(i), bVec(i)))
        case _ =>
          false
      }
    }

  implicit val floatEq = TolerantNumerics.tolerantFloatEquality(1e-3F)
  implicit val denseVectorFloatEq = tolerantDenseVectorFloatEquality

  "DenseVector[Float]" should "be able to normalize" in {
    val vector: DenseVector[Float] = DenseVector(1F, 1F)
    assert(vector.normalize === DenseVector(sqrt(2.0F).toFloat / 2.0F, sqrt(2.0F).toFloat / 2.0F))
  }

  "DenseVector[Float]" should "be able to calculate magnitude" in {
    val vector: DenseVector[Float] = DenseVector(1F, 1F)
    assert(vector.magnitude === sqrt(2.0F).toFloat)
  }
}
