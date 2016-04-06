Scalgebra [![Build Status](https://travis-ci.org/itegulov/scalgebra.svg?branch=master)](https://travis-ci.org/itegulov/scalgebra) [![Coverage Status](https://coveralls.io/repos/github/itegulov/scalgebra/badge.svg?branch=master)](https://coveralls.io/github/itegulov/scalgebra?branch=master)
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
import spire.implicits.IntAlgebra
import org.scalgebra.DenseMatrix
import org.scalgebra.DenseMatrix._

val a = DenseMatrix[Int](Array(Array(1, 2), Array(3, 4)))
val b = DenseMatrix[Int](Array(Array(5, 6), Array(7, 8)))
println(a + b)
println(a - b)
println(a * b)
```

Documentation
-------------
There is no proper documentation for now, because library is too
incomplete for now.

We highly depend on [Spire](https://github.com/non/spire) type classes
and try to generate proper operations basing on algebraic structures
available for type instance (e.g. you can't subtract `Matrix[UInt]`
because `UInt` has a `Semiring[UInt]`, but not `Ring[UInt]`).

Basic types
-----------
Some basic types are:

 * `DenseMatrix` immutable, high ratio of non-zero elements
 * `DenseVector` immutable, high ratio of non-zero elements
                 isn't subtype of `DenseMatrix` for now
 * `Tensor` isn't implemented yet, but is planned as a part
            of linear algebra hierarchy.

Copyright 2016 Daniyar Itegulov