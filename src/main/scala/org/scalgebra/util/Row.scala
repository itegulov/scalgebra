package org.scalgebra.util

import shapeless.HList
import shapeless.ops.{hlist, tuple}
import shapeless.syntax.std.tuple._

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

    def length(row: Array[V]) = row.length
  }

  implicit def rowFromSeq[V]: Row[Seq[V], V] = new Row[Seq[V], V] {
    def foreach(row: Seq[V], f: (V, Int) => Unit) =
      row.zipWithIndex.foreach(f.tupled)

    def length(row: Seq[V]) = row.length
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

  implicit def rowFromTuple3[V] : Row[(V, V, V), V] = new Row[(V, V, V), V] {
    def foreach(tup: (V, V, V), f: (V, Int) => Unit) = {
      f(tup._1, 0)
      f(tup._2, 1)
      f(tup._3, 2)
    }

    def length(tup : (V, V, V)) = 3
  }

  implicit def rowFromTuple4[V] : Row[(V, V, V, V), V] = new Row[(V, V, V, V), V] {
    def foreach(tup: (V, V, V, V), f: (V, Int) => Unit) = {
      f(tup._1, 0)
      f(tup._2, 1)
      f(tup._3, 2)
      f(tup._4, 3)
    }

    def length(tup : (V, V, V, V)) = 4
  }

  implicit def rowFromHList[V, L <: HList : hlist.ZipWithIndex]: Row[L, V] = new Row[L, V] {
    override def foreach(row: L, f: (V, Int) => Unit): Unit = row.zipWithIndex

    override def length(row: L): Int = row.runtimeLength
  }

  // TODO: use shapeless with arity abstraction to generate rows from TupleN[V]
}
