
import scala.collection.mutable.Stack
import scala.collection.mutable.PriorityQueue
import scala.math._
import scala.math.Ordering.Implicits._

object TestDataStructures {
	def diff(t2:(Int,Int)) = math.abs(t2._1-t2._2)

			def main(args:Array[String]) {
		val nm = Stack[String]()
				nm.push("abc","xyz")
				println(nm)
				println(nm.pop())

				val nm1 = PriorityQueue[(Int,Int)]()(Ordering.by(diff))
				println(nm1)

				import scala.util.Sorting
				var pairs = List(("a", 5, 2), ("c", 3, 1), ("b", 1, 3))

				type test = (String,Int,Int)
				// sort by 2nd element
				pairs = pairs.sortWith((x:test,y:test)=>(x._1>y._1))
				pairs = pairs.sorted


				for(pair<- pairs) 
				  println(pair)
				//println(pairs)


	}
}