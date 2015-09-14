import org.scalatest.{Matchers, FunSuite}

class UnionFindTest extends FunSuite with Matchers {
  test("should correctly connect points") {
    var uf = new JUnionFind(4)

    uf.connected(0, 0) should be(true)
    uf.connected(0, 3) should be(false)
    uf.connected(3, 0) should be(false)
    uf.connected(1, 2) should be(false)
    uf.connected(0, 1) should be(false)
    uf.connected(1, 3) should be(false)

    uf = uf.union(0, 0)

    uf.connected(0, 0) should be(true)
    uf.connected(0, 3) should be(false)
    uf.connected(3, 0) should be(false)
    uf.connected(1, 2) should be(false)
    uf.connected(0, 1) should be(false)
    uf.connected(1, 3) should be(false)

    uf = uf.union(0, 3)
    uf = uf.union(1, 2)

    uf.connected(0, 0) should be(true)
    uf.connected(0, 3) should be(true)
    uf.connected(3, 0) should be(true)
    uf.connected(1, 2) should be(true)
    uf.connected(0, 1) should be(false)
    uf.connected(1, 3) should be(false)

    uf = uf.union(1, 0)

    uf.connected(0, 0) should be(true)
    uf.connected(0, 3) should be(true)
    uf.connected(3, 0) should be(true)
    uf.connected(1, 2) should be(true)
    uf.connected(0, 1) should be(true)
    uf.connected(1, 3) should be(true)

    uf = uf.union(2, 3)

    uf.connected(0, 0) should be(true)
    uf.connected(0, 3) should be(true)
    uf.connected(3, 0) should be(true)
    uf.connected(1, 2) should be(true)
    uf.connected(0, 1) should be(true)
    uf.connected(1, 3) should be(true)
  }
}
