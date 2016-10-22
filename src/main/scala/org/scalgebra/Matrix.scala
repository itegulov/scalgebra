package org.scalgebra

import algebra.ring.{AdditiveMonoid, MultiplicativeMonoid}

import scala.collection.AbstractSeq
import scala.language.higherKinds
import scala.reflect.ClassTag

/**
  * Represents mathematical data structure Matrix.
  *
  * @author Daniyar Itegulov
  */
trait Matrix[+V[_] <: Vector[_], T] extends AbstractSeq[V[T]] with Tensor[(Int, Int), T] {
  def apply(key: (Int, Int)): T = apply(key._1, key._2)

  def apply(i: Int, j: Int): T

  def rows: Int

  def cols: Int

  def length: Int = cols

  // Iterators

  override def iterator: Iterator[V[T]] =
    for (i <- Iterator.range(0, cols))
      yield apply(i)

  def keysIterator: Iterator[(Int, Int)] =
    for (i <- Iterator.range(0, rows); j <- Iterator.range(0, cols))
    yield (i, j)

  def rowsIterator: Iterator[Iterator[T]] =
    for (i <- Iterator.range(0, rows))
    yield for (j <- Iterator.range(0, cols))
          yield apply(i, j)

  def colsIterator: Iterator[Iterator[T]] =
    for (j <- Iterator.range(0, cols))
    yield for (i <- Iterator.range(0, rows))
          yield apply(i, j)

  // Collection operations

//  def exists(predicate: T => Boolean): Boolean = valuesIterator.exists(predicate)
//
//  def forall(predicate: T => Boolean): Boolean = valuesIterator.forall(predicate)
//
//  def fold[U >: T](z: U)(op: (U, U) => U): U = valuesIterator.fold(z)(op)
//
//  def foldLeft[U >: T](z: U)(op: (U, T) => U): U = valuesIterator.foldLeft(z)(op)
//
//  def foldRight[U >: T](z: U)(op: (T, U) => U): U = valuesIterator.foldRight(z)(op)
//
//  def reduce[U >: T](op: (U, U) => U): U = valuesIterator.reduce(op)
//
//  def reduceLeft[U >: T](op: (U, T) => U): U = valuesIterator.reduceLeft(op)
//
//  def reduceRight[U >: T](op: (T, U) => U): U = valuesIterator.reduceRight(op)

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

  def flatten(): Vector[T]

  def transpose(): Matrix[V, T]
}

object Matrix {
  def zeros[V: ClassTag : AdditiveMonoid](n: Int, m: Int): Matrix[Vector, V] = DenseMatrix.zeros(n, m)

  def ones[V: ClassTag : MultiplicativeMonoid](n: Int, m: Int): Matrix[Vector, V] = DenseMatrix.ones(n, m)

  def unit[V: ClassTag : MultiplicativeMonoid : AdditiveMonoid](n: Int): Matrix[Vector, V] = DenseMatrix.unit(n)
}
