package org.scalgebra

import org.scalacheck._
import org.scalacheck.util.Buildable
import spire.algebra._

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

/**
  * Generates DenseMatrices.
  *
  * @author Daniyar Itegulov
  */
object DenseMatrixGen {

  private implicit def buildableArray[T: ClassTag]: Buildable[T, Array[T]] = new Buildable[T, Array[T]] {
    override def builder: mutable.Builder[T, Array[T]] = new mutable.Builder[T, Array[T]] {
      private val arrayBuffer = new ArrayBuffer[T]()

      override def +=(elem: T): this.type = {
        arrayBuffer += elem
        this
      }

      override def result(): Array[T] = arrayBuffer.toArray

      override def clear(): Unit = arrayBuffer.clear()
    }
  }

  implicit def arbitrarySemiringMatrix[T: Arbitrary : Semiring : ClassTag]: Arbitrary[DenseMatrix[T]] =
    Arbitrary {
      def genSemiringMatrix: Gen[DenseMatrix[T]] =
        for {
          cols <- Arbitrary.arbitrary[Int] suchThat (_ < 100)
          rows <- Arbitrary.arbitrary[Int] suchThat (_ < 100)
          matrix <- Gen.containerOfN[Array, Array[T]](rows, Gen.containerOfN[Array, T](cols, Arbitrary.arbitrary[T]))
        } yield DenseMatrix(matrix)

      genSemiringMatrix
    }
}
