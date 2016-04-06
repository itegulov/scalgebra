package org.scalgebra

import spire.algebra.AdditiveMonoid

import scala.reflect.ClassTag

/**
  * Represents mathematical data structure Matrix.
  *
  * @author Daniyar Itegulov
  */
trait Matrix[T] {
  def apply(i: Int, j: Int): T

  def rows: Int

  def cols: Int

  def size: Int = rows * cols

  // Iterators

  def iterator: Iterator[((Int, Int), T)] =
    for (i <- Iterator.range(0, rows); j <- Iterator.range(0, cols)) yield (i, j) -> apply(i, j)

  def valuesIterator: Iterator[T] =
    for (i <- Iterator.range(0, rows); j <- Iterator.range(0, cols)) yield apply(i, j)

  def keysIterator: Iterator[(Int, Int)] =
    for (i <- Iterator.range(0, rows); j <- Iterator.range(0, cols)) yield (i, j)

  // Conversions

  def to2DArray(implicit classTag: ClassTag[T]): Array[Array[T]] = {
    val result: Array[Array[T]] = Array.ofDim(rows, cols)
    var i = 0
    while (i < rows) {
      var j = 0
      while (j < cols) {
        result(i)(j) = apply(i, j)
        j += 1
      }
      i += 1
    }
    result
  }

  override def toString: String = {
    val maxWidth: Seq[Int] = {
      val result: Array[Int] = Array.ofDim(cols)
      for (i <- 0 until cols)
        for (j <- 0 until rows)
          result(i) = result(i) max apply(j, i).toString.length
      result.toSeq
    }
    val sb = new StringBuilder()
    for (i <- 0 until rows) {
      sb += (if (i == 0) '[' else ' ')
      for (j <- 0 until cols) {
        sb ++= apply(i, j).toString.padTo(maxWidth(j), ' ')
        if (j == cols - 1) {
          sb += (if (i == rows - 1) ']' else '\n')
        } else {
          sb += ' '
        }
      }
    }
    sb.toString
  }

  override def equals(obj: Any): Boolean = obj match {
    case other: Matrix[_] =>
      rows == other.rows && cols == other.cols && (valuesIterator sameElements other.valuesIterator)
    case _ => false
  }

  override def hashCode(): Int = valuesIterator.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
}

object Matrix {
  def zeros[V: ClassTag : AdditiveMonoid](n: Int, m: Int): Matrix[V] = DenseMatrix.zeros(n, m)
}