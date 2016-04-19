package org.scalgebra.util

/**
  * Row from a Matrix with some V-typed values in it.
  *
  * @author Daniyar Itegulov
  */
trait Row[R, V] {
  def foreach(row: R, f: (V, Int) => Unit): Unit

  def length(row: R): Int
}

object Row {
  implicit def rowFromArray[V]: Row[Array[V], V] = new Row[Array[V], V] {
    def foreach(row: Array[V], f: (V, Int) => Unit) =
      row.zipWithIndex.foreach(f.tupled)

    def length(arr: Array[V]) = arr.length
  }

  // TODO: add construction from other collections

  implicit def rowFromTuple1[V] : Row[Tuple1[V], V] = new Row[Tuple1[V], V] {
    def foreach(tup: Tuple1[V], f: (V, Int) => Unit) = {
      f(tup._1, 0)
    }

    def length(tup : Tuple1[V]) = 1
  }

  implicit def rowFromTuple2[V] : Row[(V, V), V] = new Row[(V, V), V] {
    def foreach(tup: (V, V), f: (V, Int) => Unit) = {
      f(tup._1, 0)
      f(tup._2, 1)
    }

    def length(tup : (V, V)) = 2
  }

  // TODO: generate code for other tuples
}
