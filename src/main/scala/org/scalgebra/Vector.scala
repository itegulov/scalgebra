package org.scalgebra

import algebra.ring.AdditiveMonoid

import scala.reflect.ClassTag

/**
  * Represents mathematical data structure Vector.
  *
  * @author Daniyar Itegulov
  */
trait Vector[T] {
  def apply(i: Int): T

  def length: Int

  // Iterators

  def iterator: Iterator[(Int, T)] = Iterator.range(0, length).map { i => i -> apply(i) }

  def valuesIterator: Iterator[T] = Iterator.range(0, length).map { i => apply(i) }

  def keysIterator: Iterator[Int] = Iterator.range(0, length)

  // Collection operations

  def exists(predicate: T => Boolean): Boolean = valuesIterator.exists(predicate)

  def forall(predicate: T => Boolean): Boolean = valuesIterator.forall(predicate)

  def fold[U >: T](z: U)(op: (U, U) => U): U = valuesIterator.fold(z)(op)

  def foldLeft[U >: T](z: U)(op: (U, T) => U): U = valuesIterator.foldLeft(z)(op)

  def foldRight[U >: T](z: U)(op: (T, U) => U): U = valuesIterator.foldRight(z)(op)

  def reduce[U >: T](op: (U, U) => U): U = valuesIterator.reduce(op)

  def reduceLeft[U >: T](op: (U, T) => U): U = valuesIterator.reduceLeft(op)

  def reduceRight[U >: T](op: (T, U) => U): U = valuesIterator.reduceRight(op)

  // Conversions

  def toArray(implicit cm: ClassTag[T]): Array[T] = {
    val result = new Array[T](length)
    var i = 0
    while (i < length) {
      result(i) = apply(i)
      i += 1
    }
    result
  }


  override def toString: String = {
    val sb = new StringBuilder()
    for (i <- 0 until length) {
      sb += (if (i == 0) '[' else ' ')
      sb ++= apply(i).toString
      sb += (if (i == length - 1) ']' else '\n')
    }
    sb.toString
  }

  override def equals(obj: Any): Boolean = obj match {
    case v: Vector[_] => this.length == v.length && (valuesIterator sameElements v.valuesIterator)
    case _ => false
  }

  override def hashCode(): Int = valuesIterator.map(_.hashCode()).foldLeft(0)((a, b) => a * 31 + b)
}

object Vector {
  def zeros[V: ClassTag : AdditiveMonoid](length: Int): Vector[V] = DenseVector.zeros[V](length)
}
