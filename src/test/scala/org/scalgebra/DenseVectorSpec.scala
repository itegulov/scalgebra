package org.scalgebra

import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Daniyar Itegulov
  */
class DenseVectorSpec extends FlatSpec with Matchers {
  "A DenseVector" should "be able to add up for Ring types" in {
    // Int is a Ring
    import algebra.std.int._
    val firstVector: DenseVector[Int] = DenseVector(1, 2, 3, 4)
    val secondVector: DenseVector[Int] = DenseVector(5, 6, 7, 8)
    firstVector + secondVector shouldBe DenseVector(6, 8, 10, 12)
  }

  "A DenseVector" should "be able to add up for Semiring types" in {
    // Set is a Semiring
    import algebra.std.set._
    val firstVector: DenseVector[Set[Int]] = DenseVector(Set(1), Set(2), Set(3), Set(4))
    val secondVector: DenseVector[Set[Int]] = DenseVector(Set(5), Set(6), Set(7), Set(8))
    val sum: DenseVector[Set[Int]] = DenseVector(Set(1, 5), Set(2, 6), Set(3, 7), Set(4, 8))
    firstVector + secondVector shouldBe sum
  }

  "A DenseVector" should "be able to subtract for Ring types" in {
    // Int is a Ring
    import algebra.std.int._
    val firstVector: DenseVector[Int] = DenseVector(1, 2, 3, 4)
    val secondVector: DenseVector[Int] = DenseVector(5, 6, 7, 8)
    firstVector - secondVector shouldBe DenseVector(-4, -4, -4, -4)
  }

  "A DenseVector" should "not be able to subtract for Semiring types" in {
    // Set is a Semiring
    import algebra.std.set._
    val firstVector: DenseVector[Set[Int]] = DenseVector(Set(1), Set(2), Set(3), Set(4))
    val secondVector: DenseVector[Set[Int]] = DenseVector(Set(5), Set(6), Set(7), Set(8))
    "firstVector - secondVector" shouldNot compile
  }

  "A DenseVector" should "not be summed for vectors with different length" in {
    // Int is a Ring
    import algebra.std.int._
    val firstVector: DenseVector[Int] = DenseVector(1, 2, 3, 4)
    val secondVector: DenseVector[Int] = DenseVector(5, 6)
    assertThrows[IllegalArgumentException] {
      firstVector + secondVector
    }
  }

  "A DenseVector" should "not be subtracted for vectors with different length" in {
    // Int is a Ring
    import algebra.std.int._
    val firstVector: DenseVector[Int] = DenseVector(1, 2, 3, 4)
    val secondVector: DenseVector[Int] = DenseVector(5, 6)
    assertThrows[IllegalArgumentException] {
      firstVector - secondVector
    }
  }

  "A DenseVector" should "be able to multiply for Ring types" in {
    // Int is a Ring
    import algebra.std.int._
    val firstVector: DenseVector[Int] = DenseVector(1, 2, 3, 4)
    val secondMatrix: DenseMatrix[Int] = DenseMatrix((5, 6, 7, 8))
    firstVector * secondMatrix shouldBe (5 + 12 + 21 + 32)
  }

  "A DenseVector" should "not be multiplied for non-row matrix" in {
    // Int is a Ring
    import algebra.std.int._
    val firstVector: DenseVector[Int] = DenseVector(1, 2, 3, 4)
    val secondMatrix: DenseMatrix[Int] = DenseMatrix((5, 6, 7, 8), (1, 2, 3, 4))
    assertThrows[IllegalArgumentException] {
      firstVector * secondMatrix
    }
  }

  "A DenseVector" should "not be multiplied for matrix with different column number" in {
    // Int is a Ring
    import algebra.std.int._
    val firstVector: DenseVector[Int] = DenseVector(1, 2, 3, 4)
    val secondMatrix: DenseMatrix[Int] = DenseMatrix((5, 6, 7))
    assertThrows[IllegalArgumentException] {
      firstVector * secondMatrix
    }
  }
}
