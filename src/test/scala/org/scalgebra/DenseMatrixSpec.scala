package org.scalgebra

import org.scalatest._
import org.scalgebra.Matrix._

class DenseMatrixSpec extends FlatSpec with Matchers {

  "A Matrix" should "be able to add up for Ring types" in {
    // Int is a Ring
    import spire.implicits.IntAlgebra
    val firstMatrix: Matrix[Int] = new Matrix[Int](Seq(Seq(1, 2), Seq(3, 4)))
    val secondMatrix: Matrix[Int] = new Matrix[Int](Seq(Seq(5, 6), Seq(7, 8)))
    firstMatrix + secondMatrix shouldBe new Matrix[Int](Seq(Seq(6, 8), Seq(10, 12)))
  }

  "A Matrix" should "be able to add up for Semiring types" in {
    // UInt is a Semiring
    import spire.math.UInt
    val firstMatrix: Matrix[UInt] = new Matrix[UInt](Seq(Seq(UInt(1), UInt(2)), Seq(UInt(3), UInt(4))))
    val secondMatrix: Matrix[UInt] = new Matrix[UInt](Seq(Seq(UInt(5), UInt(6)), Seq(UInt(7), UInt(8))))
    firstMatrix + secondMatrix shouldBe new Matrix[UInt](Seq(Seq(UInt(6), UInt(8)), Seq(UInt(10), UInt(12))))
  }

  "A Matrix" should "be able to subtract for Ring types" in {
    // Int is a Ring
    import spire.implicits.IntAlgebra
    val firstMatrix: Matrix[Int] = new Matrix[Int](Seq(Seq(1, 2), Seq(3, 4)))
    val secondMatrix: Matrix[Int] = new Matrix[Int](Seq(Seq(5, 6), Seq(7, 8)))
    firstMatrix - secondMatrix shouldBe new Matrix[Int](Seq(Seq(-4, -4), Seq(-4, -4)))
  }

  "A Matrix" should "not be able to subtract for Semiring types" in {
    // UInt is a Semiring
    import spire.math.UInt
    val firstMatrix: Matrix[UInt] = new Matrix[UInt](Seq(Seq(UInt(1), UInt(2)), Seq(UInt(3), UInt(4))))
    val secondMatrix: Matrix[UInt] = new Matrix[UInt](Seq(Seq(UInt(5), UInt(6)), Seq(UInt(7), UInt(8))))
    "firstMatrix - secondMatrix" shouldNot compile
  }

  "A Matrix" should "be able to multiply for Ring types" in {
    // Int is a Ring
    import spire.implicits.IntAlgebra
    val firstMatrix: Matrix[Int] = new Matrix[Int](Seq(Seq(1, 2), Seq(3, 4)))
    val secondMatrix: Matrix[Int] = new Matrix[Int](Seq(Seq(5, 6), Seq(7, 8)))
    firstMatrix * secondMatrix shouldBe new Matrix[Int](Seq(Seq(19, 22), Seq(43, 50)))
  }

  "A Matrix" should "be able to multiply for Semiring types" in {
    // UInt is a Semiring
    import spire.math.UInt
    val firstMatrix: Matrix[UInt] = new Matrix[UInt](Seq(Seq(UInt(1), UInt(2)), Seq(UInt(3), UInt(4))))
    val secondMatrix: Matrix[UInt] = new Matrix[UInt](Seq(Seq(UInt(5), UInt(6)), Seq(UInt(7), UInt(8))))
    firstMatrix * secondMatrix shouldBe new Matrix[UInt](Seq(Seq(UInt(19), UInt(22)), Seq(UInt(43), UInt(50))))
  }

}
