package org.scalgebra

import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest._
import spire.implicits.IntAlgebra

import org.scalgebra.MatrixGen._

/**
  * @author Daniyar Itegulov
  */
class MatrixProps extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {
  property("hash code and equals properties are kept") {
    forAll((a: Matrix[Int], b: Matrix[Int]) => {
      if (a.hashCode != b.hashCode) a should not be b
      if (a == b) a.hashCode shouldBe b.hashCode
    })
  }

  property("iterator is the same as keyIterator -> valueIterator") {
    forAll((matrix: Matrix[Int]) => {
      val a = matrix.keysIterator.zip(matrix.valuesIterator)
      assert(matrix.iterator sameElements a)
    })
  }

  property("iterators have all elements") {
    forAll((matrix: Matrix[Int]) => {
      matrix.iterator.size shouldBe matrix.size
      matrix.valuesIterator.size shouldBe matrix.size
      matrix.keysIterator.size shouldBe matrix.size
    })
  }
}
