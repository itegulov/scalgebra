package org.scalgebra

import org.scalactic.{Equality, TolerantNumerics}
import org.scalatest.{FlatSpec, Matchers}

import scala.math.sqrt

/**
  * @author Daniyar Itegulov
  */
class DenseVectorDoubleSpec extends FlatSpec with Matchers {
  implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(1e-6)

  "DenseVector[Double]" should "be able to normalize" in {
    val vector: DenseVector[Double] = DenseVector(1D, 1D)
    vector.normalize === DenseVector(sqrt(2.0D) / 2.0D, sqrt(2.0D) / 2.0D)
  }

  "DenseVector[Double]" should "be able to calculate magnitude" in {
    val vector: DenseVector[Double] = DenseVector(1D, 1D)
    vector.magnitude === sqrt(2.0D)
  }
}
