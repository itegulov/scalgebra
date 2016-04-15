Scalgebra [![Build Status](https://travis-ci.org/itegulov/scalgebra.svg?branch=master)](https://travis-ci.org/itegulov/scalgebra) [![Coverage status](https://codecov.io/github/itegulov/scalgebra/coverage.svg?branch=master)](https://codecov.io/github/itegulov/scalgebra?branch=master)
====
Overview
--------
A lightweight linear algebra library for Scala. No native dependencies,
no specialization, no benchmarks. It's as simple as it is should be.

Installation
------------
There is no public distribution yet. Just wait for a little longer.

Quick start
-----------
```scala
import algebra.std.int._
import org.scalgebra.DenseMatrix

val a = DenseMatrix[Int](Array(Array(1, 2), Array(3, 4)))
val b = DenseMatrix[Int](Array(Array(5, 6), Array(7, 8)))
println(a + b)
println(a - b)
println(a * b)
```

```scala
import algebra.std.set._
import org.scalgebra.DenseMatrix

val a = DenseMatrix[Set[Int]](Array(Array(Set(1), Set(2)), Array(Set(3), Set(4))))
val b = DenseMatrix[Set[Int]](Array(Array(Set(5), Set(6)), Array(Set(7), Set(8))))
println(a + b)
// Next string doesn't compile because Set doesn't have AdditiveGroup type class
// println(a - b)
println(a * b)
```

Documentation
-------------
There is no proper documentation for now, because library is too
incomplete for now.

We highly depend on [algebra](https://github.com/non/algebra) type classes
and try to generate proper operations basing on algebraic structures
available for type instance (e.g. you can't subtract `Matrix[Set[Int]]`
because `Set[Int]` has a `AdditiveMonoid[Set[Int]]`, but not `AdditiveGroup[Set[Int]]`).

Basic types
-----------
Some basic types are:

 * `DenseMatrix` immutable, high ratio of non-zero elements
 * `DenseVector` immutable, high ratio of non-zero elements
                 isn't subtype of `DenseMatrix` for now
 * `Tensor` isn't implemented yet, but is planned as a part
            of linear algebra hierarchy.

Roadmap
-------
We are going to add some neat DSL for creating matrices and vectors as a next step.

Copyright 2016 Daniyar Itegulov