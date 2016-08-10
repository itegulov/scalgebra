package org.scalgebra

/**
  * @author Daniyar Itegulov
  */
trait Tensor[K, V] {
  def apply(key: K): V
  def iterator: Iterator[(K, V)]
  def valuesIterator: Iterator[V]
  def keysIterator: Iterator[K]
}
