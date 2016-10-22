package org.scalgebra

import algebra.ring._

import scala.collection.generic._
import scala.collection.{ mutable, SeqLike }
import scala.reflect.ClassTag

/**
  * Represents dense (high ratio of non-zero values in DenseVector) vectors
  * with some operators implicitly created basing on spire type classes
  * (e.g. if T has Ring type class, then DenseVector[T] can be used with +,
  * -, * operators).
  *
  * @author Daniyar Itegulov
  */
final class DenseVector[T](val array: Array[T]) extends Vector[T]
  with GenericTraversableTemplate[T, DenseVector]
  with SeqLike[T, DenseVector[T]] {
  override def apply(i: Int): T = {
    if (i < 0 || i >= length) {
      throw new IndexOutOfBoundsException(s"Tried to get $i-th element in vector with $length elements")
    }
    array(i)
  }

  override def length: Int = array.length

  override def companion: GenericCompanion[DenseVector] = DenseVector
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
        i += 1
      }
      answer
    }
  }

  implicit class DenseVecDouble(lhs: DenseVector[Double]) {
    def magnitude: Double = Math.pow(lhs.array.map(a => a * a).sum, 1.0D / lhs.length)

    def normalize: DenseVector[Double] = DenseVector(lhs.array.map(_ / lhs.magnitude))
  }

  implicit class DenseVecFloat(lhs: DenseVector[Float]) {
    def magnitude: Float = Math.pow(lhs.array.map(a => a * a).sum, 1.0D / lhs.length).toFloat

    def normalize: DenseVector[Float] = DenseVector(lhs.array.map(_ / lhs.magnitude))
  }
}

object DenseVector extends GenSeqFactory[DenseVector] {
  def apply[V](values: Array[V]): DenseVector[V] =
    new DenseVector[V](values)

  def apply[V: ClassTag](values: V*): DenseVector[V] = apply(values.toArray)

  def zeros[V: ClassTag : AdditiveMonoid](length: Int): DenseVector[V] =
    apply(Array.fill(length)(implicitly[AdditiveMonoid[V]].zero))

  def ones[V: ClassTag : MultiplicativeMonoid](length: Int): DenseVector[V] =
    apply(Array.fill(length)(implicitly[MultiplicativeMonoid[V]].one))

  override def newBuilder[A]: mutable.Builder[A, DenseVector[A]] =
    new DenseVectorBuilder[A]()

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, DenseVector[A]] =
    ReusableCBF.asInstanceOf[GenericCanBuildFrom[A]]
}


final class DenseVectorBuilder[T]() extends mutable.Builder[T, DenseVector[T]] {
  private var innerArray = Array.ofDim[AnyRef](32)
  private var index = 0

  override def +=(elem: T): this.type = {
    if (index >= innerArray.length) {
      val oldArray = innerArray
      innerArray = Array.ofDim[AnyRef](innerArray.length * 2)
      oldArray.copyToArray(innerArray)
    }
    innerArray(index) = elem.asInstanceOf[AnyRef]
    index = index + 1
    this
  }

  override def clear(): Unit = {
    innerArray = Array.ofDim[AnyRef](32)
    index = 0
  }

  override def result(): DenseVector[T] = {
    val array = Array.ofDim[AnyRef](index)
    innerArray.copyToArray(array, 0, index)
    new DenseVector[T](array.asInstanceOf[Array[T]])
  }
}
