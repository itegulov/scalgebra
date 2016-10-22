package org.scalgebra

import org.scalatest._
import org.scalatest.prop._
import algebra.instances.int._

import org.scalgebra.generation.DenseVectorGen._

/**
  * @author Daniyar Itegulov
  */
class DenseVectorProps extends PropSpec with Matchers with GeneratorDrivenPropertyChecks  {
  property("has positive indexed elements") {
    forAll((vector: DenseVector[Int]) => {
      for (i <- 0 until vector.length)
        vector(i)
    })
  }

  property("has no out of bound elements") {
    forAll((vector: DenseVector[Int]) => {
      intercept[IndexOutOfBoundsException] {
        vector(vector.length)
      }
      intercept[IndexOutOfBoundsException] {
        vector(-vector.length)
      }
    })
  }

  property("can be added with zero vector") {
    forAll((vector: DenseVector[Int]) => {
      vector + DenseVector.zeros(vector.length) shouldBe vector
      DenseVector.zeros(vector.length) + vector shouldBe vector
    })
  }

  property("can be subtracted from zero vector") {
    forAll((vector: DenseVector[Int]) => {
      vector - DenseVector.zeros(vector.length) shouldBe vector
      DenseVector.zeros(vector.length) - vector shouldBe DenseVector(vector.iterator.map(-_).toArray)
    })
  }
}
