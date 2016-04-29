package org.scalgebra

import algebra.ring._

import scala.reflect.ClassTag

/**
  * Represents dense (high ratio of non-zero values in DenseVector) vectors
  * with some operators implicitly created basing on spire type classes
  * (e.g. if T has Ring type class, then DenseVector[T] can be used with +,
  * -, * operators).
  *
  * @author Daniyar Itegulov
  */
final class DenseVector[T](val array: Array[T]) extends Vector[T] {
  override def apply(i: Int): T = {
    if (i <= -length || i >= length) {
      throw new IndexOutOfBoundsException(s"Tried to get $i-th element in vector with $length elements")
    }
    val index = if (i < 0) length + i else i
    array(index)
  }

  override def length: Int = array.length
}

trait DenseVectorOps {

  implicit class DenseVecAddOps[T: ClassTag : AdditiveMonoid](lhs: DenseVector[T]) {
    val monoid = implicitly[AdditiveMonoid[T]]

    def +(rhs: DenseVector[T]): DenseVector[T] = {
      if (lhs.length != rhs.length) {
        throw new IllegalArgumentException(
          s"Tried to add vector with len ${rhs.length} to vector with len ${lhs.length}"
        )
      }
      val answer = Array.fill(lhs.length)(monoid.zero)

      var i = 0
      while (i < lhs.length) {
        answer(i) = monoid.plus(lhs(i), rhs(i))
        i += 1
      }
      new DenseVector[T](answer)
    }
  }

  implicit class DenseVecSubOps[T: ClassTag : AdditiveGroup](lhs: DenseVector[T]) {
    val group = implicitly[AdditiveGroup[T]]

    def -(rhs: DenseVector[T]): DenseVector[T] = {
      if (lhs.length != rhs.length) {
        throw new IllegalArgumentException(
          s"Tried to add vector with len ${rhs.length} to vector with len ${lhs.length}"
        )
      }
      val answer = Array.fill(lhs.length)(group.zero)

      var i = 0
      while (i < lhs.length) {
        answer(i) = group.minus(lhs(i), rhs(i))
        i += 1
      }
      new DenseVector[T](answer)
    }
  }

  implicit class DenseVecMultOps[T: ClassTag : MultiplicativeMonoid : AdditiveMonoid](lhs: DenseVector[T]) {
    val multMonoid = implicitly[MultiplicativeMonoid[T]]
    val addMonoid = implicitly[AdditiveMonoid[T]]

    def *(rhs: DenseMatrix[T]): T = {
      if (lhs.length != rhs.cols) {
        throw new IllegalArgumentException(
          s"Tried to multiplicate vector with len ${lhs.length} on matrix with ${rhs.cols} cols"
        )
      }
      if (rhs.rows != 1) {
        throw new IllegalArgumentException(
          s"Tried to multiplcate vector on matrix with ${rhs.rows} rows"
        )
      }
      var answer: T = addMonoid.zero
      var i = 0
      while (i < lhs.length) {
        answer = addMonoid.plus(answer, multMonoid.times(lhs(i), rhs(0, i)))
      }
      answer
    }
  }
}

object DenseVector {
  def apply[V](values: Array[V]): DenseVector[V] =
    new DenseVector[V](values)

  def apply[V: ClassTag](values: V*): DenseVector[V] = apply(values.toArray)

  def zeros[V: ClassTag : AdditiveMonoid](length: Int): DenseVector[V] =
    apply(Array.fill(length)(implicitly[AdditiveMonoid[V]].zero))

  def ones[V: ClassTag : MultiplicativeMonoid](length: Int): DenseVector[V] =
    apply(Array.fill(length)(implicitly[MultiplicativeMonoid[V]].one))
}
