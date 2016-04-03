package org.scalgebra

import org.scalatest._
import org.scalgebra.DenseMatrix._

class DenseMatrixSpec extends FlatSpec with Matchers {

  "A DenseMatrix" should "be able to add up for Ring types" in {
    // Int is a Ring
    import spire.implicits.IntAlgebra
    val firstMatrix: DenseMatrix[Int] = DenseMatrix[Int](Array(Array(1, 2), Array(3, 4)))
    val secondMatrix: DenseMatrix[Int] = DenseMatrix[Int](Array(Array(5, 6), Array(7, 8)))
    firstMatrix + secondMatrix shouldBe DenseMatrix[Int](Array(Array(6, 8), Array(10, 12)))
  }

  "A DenseMatrix" should "be able to add up for Semiring types" in {
    // UInt is a Semiring
    import spire.math.UInt
    val firstMatrix: DenseMatrix[UInt] = DenseMatrix[UInt](Array(Array(UInt(1), UInt(2)), Array(UInt(3), UInt(4))))
    val secondMatrix: DenseMatrix[UInt] = DenseMatrix[UInt](Array(Array(UInt(5), UInt(6)), Array(UInt(7), UInt(8))))
    firstMatrix + secondMatrix shouldBe DenseMatrix[UInt](Array(Array(UInt(6), UInt(8)), Array(UInt(10), UInt(12))))
  }

  "A DenseMatrix" should "be able to subtract for Ring types" in {
    // Int is a Ring
    import spire.implicits.IntAlgebra
    val firstMatrix: DenseMatrix[Int] = DenseMatrix[Int](Array(Array(1, 2), Array(3, 4)))
    val secondMatrix: DenseMatrix[Int] = DenseMatrix[Int](Array(Array(5, 6), Array(7, 8)))
    firstMatrix - secondMatrix shouldBe DenseMatrix[Int](Array(Array(-4, -4), Array(-4, -4)))
  }

  "A DenseMatrix" should "not be able to subtract for Semiring types" in {
    // UInt is a Semiring
    import spire.math.UInt
    val firstMatrix: DenseMatrix[UInt] = DenseMatrix[UInt](Array(Array(UInt(1), UInt(2)), Array(UInt(3), UInt(4))))
    val secondMatrix: DenseMatrix[UInt] = DenseMatrix[UInt](Array(Array(UInt(5), UInt(6)), Array(UInt(7), UInt(8))))
    "firstMatrix - secondMatrix" shouldNot compile
  }

  "A DenseMatrix" should "be able to multiply for Ring types" in {
    // Int is a Ring
    import spire.implicits.IntAlgebra
    val firstMatrix: DenseMatrix[Int] = DenseMatrix[Int](Array(Array(1, 2), Array(3, 4)))
    val secondMatrix: DenseMatrix[Int] = DenseMatrix[Int](Array(Array(5, 6), Array(7, 8)))
    firstMatrix * secondMatrix shouldBe DenseMatrix[Int](Array(Array(19, 22), Array(43, 50)))
  }

  "A DenseMatrix" should "be able to multiply for Semiring types" in {
    // UInt is a Semiring
    import spire.math.UInt
    val firstMatrix: DenseMatrix[UInt] = DenseMatrix[UInt](Array(Array(UInt(1), UInt(2)), Array(UInt(3), UInt(4))))
    val secondMatrix: DenseMatrix[UInt] = DenseMatrix[UInt](Array(Array(UInt(5), UInt(6)), Array(UInt(7), UInt(8))))
    firstMatrix * secondMatrix shouldBe DenseMatrix[UInt](Array(Array(UInt(19), UInt(22)), Array(UInt(43), UInt(50))))
  }

}
