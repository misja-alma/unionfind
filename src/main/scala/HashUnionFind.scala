object HashUnionFind {
  def create(size: Int): HashUnionFind = {
    val map = (0 until size).map(v => v -> List(v)).toMap
    HashUnionFind(map.values.toSet, map)
  }
}

case class HashUnionFind(partitions: Set[List[Int]], partitionLookup: Map[Int, List[Int]]) extends UnionFind {

  def union(t1: Int, t2: Int): HashUnionFind = {
    if (t1 == t2) this

    val firstPartition = partitionLookup(t1)
    if (firstPartition.contains(t2)) this

    val secondPartition = partitionLookup(t2)
    val newPartition = firstPartition ++ secondPartition

    HashUnionFind(
      partitions - firstPartition - secondPartition + newPartition,
      partitionLookup
        .updated(t1, newPartition)
        .updated(t2, newPartition))
  }

  def connected(t1: Int, t2: Int): Boolean =
    partitions
    .find(_.contains(t1))
    .getOrElse(throw new NoSuchElementException(s"t1 $t1 not in graph"))
    .contains(t2)
}
