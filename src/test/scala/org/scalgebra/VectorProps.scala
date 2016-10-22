package org.scalgebra

import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest._
import algebra.instances.int._

import org.scalgebra.generation.VectorGen._

/**
  * @author Daniyar Itegulov
  */
class VectorProps extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {
  property("hash code and equals properties are kept") {
    forAll((a: Vector[Int], b: Vector[Int]) => {
      if (a.hashCode != b.hashCode) a should not be b
      if (a == b) a.hashCode shouldBe b.hashCode
    })
  }

  property("string representation contains all elements") {
    forAll((vector: Vector[Int]) => {
      val representation = vector.toString
      for (i <- vector.indices)
        representation.contains(vector(i).toString)
    })
  }

  property("conversion to 2d array contains all elements") {
    forAll((vector: Vector[Int]) => {
      val array = vector.toArray
      for (i <- vector.indices)
        vector(i) shouldBe array(i)
    })
  }

  property("all elements in vector exist inside of it") {
    forAll((vector: Vector[Int]) => {
      for (i <- vector.indices)
        vector.contains(vector(i)) shouldBe true
    })
  }

  property("folds are equivalent for addition") {
    forAll((vector: Vector[Int]) => {
      vector.sum shouldBe vector.toArray.sum
      vector.sum shouldBe vector.sum
      vector.sum shouldBe vector.sum
    })
  }

  property("reduces are equivalent for addition") {
    forAll((vector: Vector[Int]) => {
      if (vector.nonEmpty) {
        vector.sum shouldBe vector.toArray.sum
        vector.sum shouldBe vector.sum
        vector.sum shouldBe vector.sum
      }
    })
  }
}
