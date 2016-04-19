package org.scalgebra

import org.scalatest._

class DenseMatrixSpec extends FlatSpec with Matchers {

  "A DenseMatrix" should "be able to add up for Ring types" in {
    // Int is a Ring
    import algebra.std.int._
    val firstMatrix: DenseMatrix[Int] = DenseMatrix((1, 2), (3, 4))
    val secondMatrix: DenseMatrix[Int] = DenseMatrix((5, 6), (7, 8))
    firstMatrix + secondMatrix shouldBe DenseMatrix((6, 8), (10, 12))
  }

  "A DenseMatrix" should "be able to add up for Semiring types" in {
    // Set is a Semiring
    import algebra.std.set._
    val firstMatrix: DenseMatrix[Set[Int]] = DenseMatrix((Set(1), Set(2)), (Set(3), Set(4)))
    val secondMatrix: DenseMatrix[Set[Int]] = DenseMatrix((Set(5), Set(6)), (Set(7), Set(8)))
    val sum: DenseMatrix[Set[Int]] = DenseMatrix((Set(1, 5), Set(2, 6)), (Set(3, 7), Set(4, 8)))
    firstMatrix + secondMatrix shouldBe sum
  }

  "A DenseMatrix" should "be able to subtract for Ring types" in {
    // Int is a Ring
    import algebra.std.int._
    val firstMatrix: DenseMatrix[Int] = DenseMatrix((1, 2), (3, 4))
    val secondMatrix: DenseMatrix[Int] = DenseMatrix((5, 6), (7, 8))
    firstMatrix - secondMatrix shouldBe DenseMatrix((-4, -4), (-4, -4))
  }

  "A DenseMatrix" should "not be able to subtract for Semiring types" in {
    // Set is a Semiring
    import algebra.std.set._
    val firstMatrix: DenseMatrix[Set[Int]] = DenseMatrix((Set(1), Set(2)), (Set(3), Set(4)))
    val secondMatrix: DenseMatrix[Set[Int]] = DenseMatrix((Set(5), Set(6)), (Set(7), Set(8)))
    "firstMatrix - secondMatrix" shouldNot compile
  }

  "A DenseMatrix" should "be able to multiply for Ring types" in {
    // Int is a Ring
    import algebra.std.int._
    val firstMatrix: DenseMatrix[Int] = DenseMatrix((1, 2), (3, 4))
    val secondMatrix: DenseMatrix[Int] = DenseMatrix((5, 6), (7, 8))
    firstMatrix * secondMatrix shouldBe DenseMatrix((19, 22), (43, 50))
  }
}
