package org.scalgebra

import org.scalatest._
import org.scalgebra.DenseMatrix._

class DenseMatrixSpec extends FlatSpec with Matchers {

  "A DenseMatrix" should "be able to add up for Ring types" in {
    // Int is a Ring
    import spire.implicits.IntAlgebra
    val firstMatrix: DenseMatrix[Int] = DenseMatrix[Int](Seq(Seq(1, 2), Seq(3, 4)))
    val secondMatrix: DenseMatrix[Int] = DenseMatrix[Int](Seq(Seq(5, 6), Seq(7, 8)))
    firstMatrix + secondMatrix shouldBe DenseMatrix[Int](Seq(Seq(6, 8), Seq(10, 12)))
  }

  "A DenseMatrix" should "be able to add up for Semiring types" in {
    // UInt is a Semiring
    import spire.math.UInt
    val firstMatrix: DenseMatrix[UInt] = DenseMatrix[UInt](Seq(Seq(UInt(1), UInt(2)), Seq(UInt(3), UInt(4))))
    val secondMatrix: DenseMatrix[UInt] = DenseMatrix[UInt](Seq(Seq(UInt(5), UInt(6)), Seq(UInt(7), UInt(8))))
    firstMatrix + secondMatrix shouldBe DenseMatrix[UInt](Seq(Seq(UInt(6), UInt(8)), Seq(UInt(10), UInt(12))))
  }

  "A DenseMatrix" should "be able to subtract for Ring types" in {
    // Int is a Ring
    import spire.implicits.IntAlgebra
    val firstMatrix: DenseMatrix[Int] = DenseMatrix[Int](Seq(Seq(1, 2), Seq(3, 4)))
    val secondMatrix: DenseMatrix[Int] = DenseMatrix[Int](Seq(Seq(5, 6), Seq(7, 8)))
    firstMatrix - secondMatrix shouldBe DenseMatrix[Int](Seq(Seq(-4, -4), Seq(-4, -4)))
  }

  "A DenseMatrix" should "not be able to subtract for Semiring types" in {
    // UInt is a Semiring
    import spire.math.UInt
    val firstMatrix: DenseMatrix[UInt] = DenseMatrix[UInt](Seq(Seq(UInt(1), UInt(2)), Seq(UInt(3), UInt(4))))
    val secondMatrix: DenseMatrix[UInt] = DenseMatrix[UInt](Seq(Seq(UInt(5), UInt(6)), Seq(UInt(7), UInt(8))))
    "firstMatrix - secondMatrix" shouldNot compile
  }

  "A DenseMatrix" should "be able to multiply for Ring types" in {
    // Int is a Ring
    import spire.implicits.IntAlgebra
    val firstMatrix: DenseMatrix[Int] = DenseMatrix[Int](Seq(Seq(1, 2), Seq(3, 4)))
    val secondMatrix: DenseMatrix[Int] = DenseMatrix[Int](Seq(Seq(5, 6), Seq(7, 8)))
    firstMatrix * secondMatrix shouldBe DenseMatrix[Int](Seq(Seq(19, 22), Seq(43, 50)))
  }

  "A DenseMatrix" should "be able to multiply for Semiring types" in {
    // UInt is a Semiring
    import spire.math.UInt
    val firstMatrix: DenseMatrix[UInt] = DenseMatrix[UInt](Seq(Seq(UInt(1), UInt(2)), Seq(UInt(3), UInt(4))))
    val secondMatrix: DenseMatrix[UInt] = DenseMatrix[UInt](Seq(Seq(UInt(5), UInt(6)), Seq(UInt(7), UInt(8))))
    firstMatrix * secondMatrix shouldBe DenseMatrix[UInt](Seq(Seq(UInt(19), UInt(22)), Seq(UInt(43), UInt(50))))
  }

}
