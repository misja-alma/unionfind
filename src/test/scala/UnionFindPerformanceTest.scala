import org.scalameter.api._

import scala.util.Random

object UnionFindPerformanceTest extends Bench.LocalTime {
  val rnd = new Random()

  def generateConnections(size: Int, nrConnections: Int): Seq[(Int, Int)] = {
    (0 until nrConnections).map(_ => (random(size), random(size)))
  }

  def random(limit: Int): Int = rnd.nextInt(limit)

  def measurePerformance(ufImplementation: UnionFind, connections: Seq[(Int, Int)]) = {
    var uf = ufImplementation
    connections.foreach(c => uf = uf.union(c._1, c._2))
  }

  val sizes = Gen.range("size")(1000000, 2000000, 500000)
  val warmUps = 5
  val benchRuns = 7
  
  val iTestSets = for {
    size <- sizes
  } yield (new IUnionFind(size), generateConnections(size, size))

  performance of "IUnionFind" in {
    measure method "union" config (
        exec.maxWarmupRuns -> warmUps
      ) config (
        exec.benchRuns -> benchRuns
      ) in {
      using(iTestSets) in {
        s => measurePerformance(s._1, s._2)
      }
    }
  }

  val fTestSets = for {
    size <- sizes
  } yield (FUnionFind.create(size), generateConnections(size, size))

  performance of "FUnionFind" in {
    measure method "union" config (
      exec.maxWarmupRuns -> warmUps
      ) config (
      exec.benchRuns -> benchRuns
      ) in {
      using(fTestSets) in {
        s => measurePerformance(s._1, s._2)
      }
    }
  }

  val jTestSets = for {
    size <- sizes
  } yield (new JUnionFind(size), generateConnections(size, size))

  performance of "JUnionFind" in {
    measure method "union" config (
      exec.maxWarmupRuns -> warmUps
      ) config (
      exec.benchRuns -> benchRuns
      ) in {
      using(jTestSets) in {
        s => measurePerformance(s._1, s._2)
      }
    }
  }
}
