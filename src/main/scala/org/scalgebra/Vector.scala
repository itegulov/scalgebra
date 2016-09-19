package org.scalgebra

import algebra.ring.{AdditiveMonoid, MultiplicativeMonoid}

import scala.collection.AbstractSeq
import scala.reflect.ClassTag

/**
  * Represents mathematical data structure Vector.
  *
  * @author Daniyar Itegulov
  */
trait Vector[T] extends AbstractSeq[T] with Tensor[Int, T] {
  def iterator: Iterator[T] = Iterator.range(0, length).map(apply)

  override def toString: String = {
    val sb = new StringBuilder()
    for (i <- 0 until length) {
      sb += (if (i == 0) '[' else ' ')
      sb ++= apply(i).toString
      sb += (if (i == length - 1) ']' else '\n')
    }
    sb.toString
  }
}

object Vector {
  def zeros[V: ClassTag : AdditiveMonoid](length: Int): Vector[V] = DenseVector.zeros[V](length)

  def ones[V: ClassTag : MultiplicativeMonoid](length: Int): Vector[V] = DenseVector.ones[V](length)
}
