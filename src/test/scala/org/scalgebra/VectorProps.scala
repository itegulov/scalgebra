package org.scalgebra

import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest._
import algebra.std.int._

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

  property("iterator is the same as keyIterator -> valueIterator") {
    forAll((vector: Vector[Int]) => {
      val a = vector.keysIterator.zip(vector.valuesIterator)
      assert(vector.iterator sameElements a)
    })
  }

  property("string representation contains all elements") {
    forAll((vector: Vector[Int]) => {
      val representation = vector.toString
      for (i <- 0 until vector.length)
        representation.contains(vector(i).toString)
    })
  }

  property("conversion to 2d array contains all elements") {
    forAll((vector: Vector[Int]) => {
      val array = vector.toArray
      for (i <- 0 until vector.length)
        vector(i) shouldBe array(i)
    })
  }
}
