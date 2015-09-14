import org.scalatest.{Matchers, FunSuite}

import scala.util.Random


// FUnionFind is about 30% slower. That can be because of the extra garbage collection of discarded Nodes.
// Or it might be because Vector is more inefficient than Array?
// TODO check Vector's 'get' implementation. It is supposed to give fast random access though.
// TODO and maybe experiment with an Object pool for Nodes for FUnionFind? Just to check if that causes the delay.
// TODO Also profiling might reveal some info ..
// TODO using case classes for the Nodes in FUnionFind does not make things slower, might even make things faster ..! How can this be?
class UnionFindPerformanceTest extends FunSuite with Matchers {
  val rnd = new Random()

  def generateConnections(size: Int, nrConnections: Int): Seq[(Int, Int)] = {
    (0 until nrConnections).map(_ => (random(size), random(size)))
  }

  def random(limit: Int): Int = rnd.nextInt(limit)

  def measurePerformance(ufImplementation: UnionFind, nodes: Int) = {
    val connections = generateConnections(nodes, nodes)

    var uf = ufImplementation
    val start = System.currentTimeMillis

    connections.foreach(c => uf = uf.union(c._1, c._2))

    val time = System.currentTimeMillis() - start
    println("Time (ms): " + time)
    time
  }

  val NODES = 2000000

  test("Test the performance of IUnionFind") {
    val time = measurePerformance(new IUnionFind(NODES), NODES)
    
    //time should be < 2000L
  }

  test("Test the performance of FUnionFind") {
    val time = measurePerformance(FUnionFind.create(NODES), NODES)

    //time should be < 5000L
  }

  test("Test the performance of JUnionFind") {
    val time = measurePerformance(new JUnionFind(NODES), NODES)

    //time should be < 5000L
  }
}
