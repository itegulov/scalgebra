package org.scalgebra

import spire.algebra._

import scala.collection.mutable
import scala.language.experimental.macros
import scala.language.implicitConversions

/**
  * Represents dense (high ratio of non-zero values in matrix) matrices
  * with some operators implicitly created basing on spire type classes
  * (e.g. if T has Ring type class, then Matrix[T] can be used with +,
  * -, * operators).
  *
  * @author Daniyar Itegulov
  */
final class Matrix[T](val array: Seq[Seq[T]]) {
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

  override def equals(obj: scala.Any): Boolean = obj match {
    case other: Matrix[T] => array == other.array
    case _ => false
  }
}

trait LowPriorityImplicits {
  // Semiring is weaker than Ring, so we should use MatrixRingOps if we can and try to use MatrixSemiringOps otherwise
  implicit class MatrixSemiringOps[T: Semiring](lhs: Matrix[T]) {
    val semiring = implicitly[Semiring[T]]

    def +(rhs: Matrix[T]): Matrix[T] = {
      assert(lhs.rows == rhs.rows, s"Tried to add matrix with ${rhs.rows} rows to matrix with ${lhs.rows} rows")
      assert(lhs.cols == rhs.cols, s"Tried to add matrix with ${rhs.cols} cols to matrix with ${lhs.cols} cols")
      val answer = mutable.Seq.fill(lhs.rows, lhs.cols)(semiring.zero)

      var c = 0
      while (c < lhs.cols) {
        var r = 0
        while (r < lhs.rows) {
          answer(r)(c) = semiring.plus(lhs(r, c), rhs(r, c))
          r += 1
        }
        c += 1
      }
      new Matrix[T](answer)
    }

    def *(rhs: Matrix[T]): Matrix[T] = {
      assert(lhs.cols == rhs.rows, s"Tried to multiply matrix with ${lhs.cols} cols on matrix with ${rhs.rows} rows")
      val answer = mutable.Seq.fill(lhs.rows, rhs.cols)(semiring.zero)

      var c = 0
      while (c < lhs.rows) {
        var r = 0
        while (r < rhs.cols) {
          var k = 0
          while (k < lhs.cols) {
            answer(r)(c) = semiring.plus(answer(r)(c), semiring.times(lhs(c, k), rhs(k, r)))
            k += 1
          }
          r += 1
        }
        c += 1
      }
      new Matrix[T](answer)
    }
  }
}

object Matrix extends LowPriorityImplicits {
  // TODO: Think about other algebraic primitives which can be used to define Matrix operations
  implicit class MatrixRingOps[T: Ring](lhs: Matrix[T]) {
    val ring = implicitly[Ring[T]]

    def +(rhs: Matrix[T]): Matrix[T] = {
      assert(lhs.rows == rhs.rows, s"Tried to add matrix with ${rhs.rows} rows to matrix with ${lhs.rows} rows")
      assert(lhs.cols == rhs.cols, s"Tried to add matrix with ${rhs.cols} cols to matrix with ${lhs.cols} cols")
      val answer = mutable.Seq.fill(lhs.rows, lhs.cols)(ring.zero)

      var c = 0
      while (c < lhs.cols) {
        var r = 0
        while (r < lhs.rows) {
          answer(r)(c) = ring.plus(lhs(r, c), rhs(r, c))
          r += 1
        }
        c += 1
      }
      new Matrix[T](answer)
    }

    def -(rhs: Matrix[T]): Matrix[T] = {
      assert(lhs.rows == rhs.rows, s"Tried to subtract matrix with ${rhs.rows} rows from matrix with ${lhs.rows} rows")
      assert(lhs.cols == rhs.cols, s"Tried to subtract matrix with ${rhs.cols} cols from matrix with ${lhs.cols} cols")
      val answer = mutable.Seq.fill(lhs.rows, lhs.cols)(ring.zero)

      var c = 0
      while (c < lhs.cols) {
        var r = 0
        while (r < lhs.rows) {
          answer(r)(c) = ring.minus(lhs(r, c), rhs(r, c))
          r += 1
        }
        c += 1
      }
      new Matrix[T](answer)
    }

    def *(rhs: Matrix[T]): Matrix[T] = {
      assert(lhs.cols == rhs.rows, s"Tried to multiply matrix with ${lhs.cols} cols on matrix with ${rhs.rows} rows")
      val answer = mutable.Seq.fill(lhs.rows, rhs.cols)(ring.zero)

      var c = 0
      while (c < lhs.rows) {
        var r = 0
        while (r < rhs.cols) {
          var k = 0
          while (k < lhs.cols) {
            answer(r)(c) = ring.plus(answer(r)(c), ring.times(lhs(c, k), rhs(k, r)))
            k += 1
          }
          r += 1
        }
        c += 1
      }
      new Matrix[T](answer)
    }
  }
}
