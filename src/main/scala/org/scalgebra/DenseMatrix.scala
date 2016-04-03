package org.scalgebra

import spire.algebra._

import scala.language.experimental.macros
import scala.language.implicitConversions
import scala.reflect.ClassTag

/**
  * Represents dense (high ratio of non-zero values in DenseMatrix) matrices
  * with some operators implicitly created basing on spire type classes
  * (e.g. if T has Ring type class, then DenseMatrix[T] can be used with +,
  * -, * operators).
  *
  * @author Daniyar Itegulov
  */
final class DenseMatrix[T](val array: Array[Array[T]]) {
  val rows = array.length
  val cols = if (rows == 0) 0 else array.head.length

  assert(array.forall(row => row.length == cols), "Not all rows have equal number of columns")

  def apply(i: Int, j: Int): T = {
    assert(-rows to rows contains i, s"Tried to get $i-th row in matrix with $rows rows")
    assert(-cols to cols contains j, s"Tried to get $j-th col in matrix with $cols cols")
    val row = if (i < 0) rows + i else i
    val col = if (j < 0) cols + j else j
    array(row)(col)
  }

  override def equals(obj: Any): Boolean = obj match {
    case other: DenseMatrix[T] =>
      this.rows == other.rows && this.cols == other.cols && (array.deep equals other.array.deep)
    case _ => false
  }

  override def hashCode(): Int = array.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
}

object DenseMatrix {

  def apply[T](array: Array[Array[T]]): DenseMatrix[T] = new DenseMatrix[T](array)

  def zeros[V: ClassTag : AdditiveMonoid](n: Int, m: Int): DenseMatrix[V] =
    apply(Array.fill(n, m)(implicitly[AdditiveMonoid[V]].zero))

  implicit class DenseMatrixAdditiveMonoidOps[T: ClassTag : AdditiveMonoid](lhs: DenseMatrix[T]) {
    val monoid = implicitly[AdditiveMonoid[T]]

    def +(rhs: DenseMatrix[T]): DenseMatrix[T] = {
      assert(lhs.rows == rhs.rows, s"Tried to add matrix with ${rhs.rows} rows to matrix with ${lhs.rows} rows")
      assert(lhs.cols == rhs.cols, s"Tried to add matrix with ${rhs.cols} cols to matrix with ${lhs.cols} cols")
      val answer = Array.fill(lhs.rows, lhs.cols)(monoid.zero)

      var c = 0
      while (c < lhs.cols) {
        var r = 0
        while (r < lhs.rows) {
          answer(r)(c) = monoid.plus(lhs(r, c), rhs(r, c))
          r += 1
        }
        c += 1
      }
      new DenseMatrix[T](answer)
    }
  }

  implicit class DenseMatrixMultSemigroupAddMonoidOps[T: ClassTag : MultiplicativeSemigroup : AdditiveMonoid]
      (lhs: DenseMatrix[T]) {
    val additiveMonoid = implicitly[AdditiveMonoid[T]]
    val multiplicativeSemigroup = implicitly[MultiplicativeSemigroup[T]]

    def *(rhs: DenseMatrix[T]): DenseMatrix[T] = {
      assert(lhs.cols == rhs.rows, s"Tried to multiply matrix with ${lhs.cols} cols on matrix with ${rhs.rows} rows")
      val answer = Array.fill(lhs.rows, rhs.cols)(additiveMonoid.zero)

      var c = 0
      while (c < lhs.rows) {
        var r = 0
        while (r < rhs.cols) {
          var k = 0
          while (k < lhs.cols) {
            answer(c)(r) = additiveMonoid.plus(answer(c)(r), multiplicativeSemigroup.times(lhs(c, k), rhs(k, r)))
            k += 1
          }
          r += 1
        }
        c += 1
      }
      DenseMatrix[T](answer)
    }
  }

  implicit class DenseMatrixAdditiveGroupOps[T: ClassTag : AdditiveGroup](lhs: DenseMatrix[T]) {
    val group = implicitly[AdditiveGroup[T]]

    def -(rhs: DenseMatrix[T]): DenseMatrix[T] = {
      assert(lhs.rows == rhs.rows, s"Tried to subtract matrix with ${rhs.rows} rows from matrix with ${lhs.rows} rows")
      assert(lhs.cols == rhs.cols, s"Tried to subtract matrix with ${rhs.cols} cols from matrix with ${lhs.cols} cols")
      val answer = Array.fill(lhs.rows, lhs.cols)(group.zero)

      var c = 0
      while (c < lhs.cols) {
        var r = 0
        while (r < lhs.rows) {
          answer(r)(c) = group.minus(lhs(r, c), rhs(r, c))
          r += 1
        }
        c += 1
      }
      DenseMatrix[T](answer)
    }
  }

}
