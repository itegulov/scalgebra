Scalgebra [![Build Status](https://travis-ci.org/itegulov/scalgebra.svg?branch=master)](https://travis-ci.org/itegulov/scalgebra) [![Coverage status](https://codecov.io/github/itegulov/scalgebra/coverage.svg?branch=master)](https://codecov.io/github/itegulov/scalgebra?branch=master)
====
Overview
--------
A lightweight linear algebra library for Scala. No native dependencies,
no specialization, no benchmarks. It's as simple as it is should be.

Installation
------------
Just add this to your build.sbt:
```
resolvers += Resolver.bintrayRepo("itegulov", "maven")
libraryDependencies += "me.itegulov" %% "scalgebra" % "0.1"
```
Quick start
-----------
```scala
import algebra.std.int._
import org.scalgebra.DenseMatrix

val a: DenseMatrix[Int] = DenseMatrix((1, 2), (3, 4))
val b: DenseMatrix[Int] = DenseMatrix((5, 6), (7, 8))
println(a + b)
println(a - b)
println(a * b)
```

```scala
import algebra.std.set._
import org.scalgebra.DenseMatrix

val a: DenseMatrix[Set[Int]] = DenseMatrix((Set(1), Set(2)), (Set(3), Set(4)))
val b: DenseMatrix[Set[Int]] = DenseMatrix((Set(5), Set(6)), (Set(7), Set(8)))
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
Enrich matrix api: add rows looping, folds, reduces and other fp stuff

Copyright 2016 Daniyar Itegulov