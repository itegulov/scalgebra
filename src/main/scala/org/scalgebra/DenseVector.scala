package org.scalgebra

import spire.algebra._

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
  override def apply(i: Int): T = array(i)

  override def length: Int = array.length
}

object DenseVector {
  def apply[V](values: Array[V]): DenseVector[V] =
    new DenseVector[V](values)

  def zeros[V: ClassTag: AdditiveMonoid](length: Int): DenseVector[V] =
    apply(Array.fill(length)(implicitly[AdditiveMonoid[V]].zero))

  implicit class DenseVectorAdditiveMonoidOps[T: ClassTag : AdditiveMonoid](lhs: DenseVector[T]) {
    val monoid = implicitly[AdditiveMonoid[T]]

    def +(rhs: DenseVector[T]): DenseVector[T] = {
      assert(lhs.length == rhs.length, s"Tried to add vector with len ${rhs.length} to vector with len ${lhs.length}")
      val answer = Array.fill(lhs.length)(monoid.zero)

      var i = 0
      while (i < lhs.length) {
        answer(i) = monoid.plus(lhs(i), rhs(i))
        i += 1
      }
      new DenseVector[T](answer)
    }
  }

  implicit class DenseVectorAdditiveGroupOps[T: ClassTag : AdditiveGroup](lhs: DenseVector[T]) {
    val group = implicitly[AdditiveGroup[T]]

    def -(rhs: DenseVector[T]): DenseVector[T] = {
      assert(lhs.length == rhs.length, s"Tried to add vector with len ${rhs.length} to vector with len ${lhs.length}")
      val answer = Array.fill(lhs.length)(group.zero)

      var i = 0
      while (i < lhs.length) {
        answer(i) = group.minus(lhs(i), rhs(i))
        i += 1
      }
      new DenseVector[T](answer)
    }
  }

  // TODO: think about multiplication with matrix
}