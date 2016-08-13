package org.scalgebra

import org.scalactic.{Equality, TolerantNumerics}
import org.scalatest.{FlatSpec, Matchers}

import scala.math.sqrt

/**
  * @author Daniyar Itegulov
  */
class DenseVectorFloatSpec extends FlatSpec with Matchers {
  implicit val floatEq = TolerantNumerics.tolerantFloatEquality(1e-3f)

  "DenseVector[Float]" should "be able to normalize" in {
    val vector: DenseVector[Float] = DenseVector(1F, 1F)
    vector.normalize === DenseVector(sqrt(2.0F) / 2.0F, sqrt(2.0F) / 2.0F)
  }

  "DenseVector[Float]" should "be able to calculate magnitude" in {
    val vector: DenseVector[Float] = DenseVector(1F, 1F)
    vector.magnitude === sqrt(2.0F)
  }
}
