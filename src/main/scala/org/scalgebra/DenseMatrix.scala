package org.scalgebra

import algebra.ring._
import org.scalgebra.util.Row

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
final class DenseMatrix[T: ClassTag](val array: Array[Array[T]]) extends Matrix[T] {
  override val rows = array.length
  override val cols = if (rows == 0) 0 else array.head.length

  if (!array.forall(row => row.length == cols)) {
    throw new IllegalArgumentException("Not all rows have equal number of columns")
  }

  override def apply(i: Int, j: Int): T = {
    if (i < 0 || i >= rows) {
      throw new IndexOutOfBoundsException(s"Tried to get $i-th row in matrix with $rows rows")
    }
    if (j < 0 || j >= cols) {
      throw new IndexOutOfBoundsException(s"Tried to get $j-th col in matrix with $cols cols")
    }
    val row = if (i < 0) rows + i else i
    val col = if (j < 0) cols + j else j
    array(row)(col)
  }

  override def flatten(): DenseVector[T] = DenseVector(array.flatten)

  override def transpose(): DenseMatrix[T] = DenseMatrix(colsIterator.map(_.toSeq).toSeq: _*)
}

trait DenseMatrixOps {
  implicit class DenseMatAddOps[T: ClassTag : AdditiveMonoid](lhs: DenseMatrix[T]) {
    val monoid = implicitly[AdditiveMonoid[T]]

    def +(rhs: DenseMatrix[T]): DenseMatrix[T] = {
      if (lhs.rows != rhs.rows) {
        throw new IllegalArgumentException(
          s"Tried to add matrix with ${rhs.rows} rows to matrix with ${lhs.rows} rows"
        )
      }
      if (lhs.cols != rhs.cols) {
        throw new IllegalArgumentException(
          s"Tried to add matrix with ${rhs.cols} cols to matrix with ${lhs.cols} cols"
        )
      }
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
      new DenseMatrix(answer)
    }
  }

  implicit class DenseMatMultOps[T: ClassTag : MultiplicativeSemigroup : AdditiveMonoid](lhs: DenseMatrix[T]) {
    val additiveMonoid = implicitly[AdditiveMonoid[T]]
    val multiplicativeSemigroup = implicitly[MultiplicativeSemigroup[T]]

    def *(rhs: DenseMatrix[T]): DenseMatrix[T] = {
      if (lhs.cols != rhs.rows) {
        throw new IllegalArgumentException(
          s"Tried to multiply matrix with ${lhs.cols} cols on matrix with ${rhs.rows} rows"
        )
      }
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
      new DenseMatrix(answer)
    }

    def *(rhs: DenseVector[T]): DenseVector[T] = {
      if (lhs.cols != rhs.length) {
        throw new IllegalArgumentException(
          s"Tried to multiplicate matrix with cols ${lhs.cols} on vector with length ${rhs.length}"
        )
      }
      val answer = Array.fill(lhs.rows)(additiveMonoid.zero)
      var r = 0
      while (r < lhs.rows) {
        var c = 0
        var result: T = additiveMonoid.zero
        while (c < lhs.cols) {
          result = additiveMonoid.plus(result, multiplicativeSemigroup.times(lhs(r, c), rhs(c)))
          c += 1
        }
        answer(r) = result
        r += 1
      }
      DenseVector(answer)
    }
  }

  implicit class DenseMatSubOps[T: ClassTag : AdditiveGroup](lhs: DenseMatrix[T]) {
    val group = implicitly[AdditiveGroup[T]]

    def -(rhs: DenseMatrix[T]): DenseMatrix[T] = {
      if (lhs.rows != rhs.rows) {
        throw new IllegalArgumentException(
          s"Tried to subtract matrix with ${rhs.rows} rows from matrix with ${lhs.rows} rows"
        )
      }
      if (lhs.cols != rhs.cols) {
        throw new IllegalArgumentException(
          s"Tried to subtract matrix with ${rhs.cols} cols from matrix with ${lhs.cols} cols"
        )
      }
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
      new DenseMatrix(answer)
    }
  }
}

object DenseMatrix {
  def apply[R, V: ClassTag](rows: R*)(implicit r: Row[R, V]): DenseMatrix[V] = {
    val rowsCount = rows.length
    val colsCount = if (rows.isEmpty) 0 else r.length(rows(0))
    if (!rows.forall(row => r.length(row) == colsCount)) {
      throw new IllegalArgumentException("Not all rows have equal number of columns")
    }
    val array = Array.ofDim[V](rowsCount, colsCount)
    rows.zipWithIndex.foreach({ case (row, i) => r.foreach(row, (v, j) => array(i)(j) = v)})
    new DenseMatrix[V](array)
  }

  def zeros[V: ClassTag : AdditiveMonoid](rows: Int, cols: Int): DenseMatrix[V] =
    new DenseMatrix[V](Array.fill(rows, cols)(implicitly[AdditiveMonoid[V]].zero))

  def ones[V: ClassTag : MultiplicativeMonoid](rows: Int, cols: Int): DenseMatrix[V] =
    new DenseMatrix[V](Array.fill(rows, cols)(implicitly[MultiplicativeMonoid[V]].one))

  def unit[V: ClassTag : AdditiveMonoid : MultiplicativeMonoid](side: Int): DenseMatrix[V] = {
    val addMonoid = implicitly[AdditiveMonoid[V]]
    val multMonoid = implicitly[MultiplicativeMonoid[V]]

    val array = Array.fill(side, side)(addMonoid.zero)
    for (i <- 0 until side)
      array(i)(i) = multMonoid.one
    new DenseMatrix[V](array)
  }
}
