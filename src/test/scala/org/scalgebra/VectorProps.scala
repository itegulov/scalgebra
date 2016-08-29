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

  property("all elements in vector exist inside of it") {
    forAll((vector: Vector[Int]) => {
      for (i <- 0 until vector.length)
        vector.exists(_ == vector(i)) shouldBe true
    })
  }

  property("folds are equivalent for addition") {
    forAll((vector: Vector[Int]) => {
      vector.fold(0)(_ + _) shouldBe vector.toArray.sum
      vector.fold(0)(_ + _) shouldBe vector.foldLeft(0)(_ + _)
      vector.fold(0)(_ + _) shouldBe vector.foldRight(0)(_ + _)
    })
  }

  property("reduces are equivalent for addition") {
    forAll((vector: Vector[Int]) => {
      if (vector.length != 0) {
        vector.reduce(_ + _) shouldBe vector.toArray.sum
        vector.reduce(_ + _) shouldBe vector.reduceLeft(_ + _)
        vector.reduce(_ + _) shouldBe vector.reduceRight(_ + _)
      }
    })
  }
}
