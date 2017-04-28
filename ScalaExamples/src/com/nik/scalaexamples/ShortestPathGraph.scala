package com.nik.scalaexamples

import scala.collection.convert.decorateAsScala._
import scala.collection.mutable.ListBuffer
import java.util.concurrent.ConcurrentHashMap
import scala.collection.concurrent.Map
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Stack

/**
 * <p>
 * Shortest path algorithm 
 * Graph is provided in the form of edges, vertices. Graph can be directed or undirected graph
 * All possible paths are explored, and while exploring add edge weights. Store the results in the format of path and weight of the path. 
 * Finally, once all paths are explored, sort the results by weight of the path in ascending order.
 * </p>
 * 
 * @author nikhil.bhide $Date : Apr 28, 2017 $
 * 
 */
object ShortestPathGraph {
case class Vertex (label:String)
case class Edge (sourceVertex:Vertex,targetVertex:Vertex, weight:Int)
type pathWeightStruct = (String,Int) 
type connectionType = (Vertex,List[Edge])

/**
 * <p>
 * Explores graph based on starting vertex and target vertex. 
 * It uses recursion and break point is a point at which either destination vertex is found out or all reachable vertices are visited 
 * </p>
 * 
 * @param sourceVertex The starting point of graph exploration
 * @param targetVertex The destination point of graph exploration
 * @param visitedNodes The visited nodes which belong to a path
 * @param currentPath Keeps track of path under exploration
 * @param graph Holds edges, vertices
 * @param traversedPaths traversed paths stored in the form of path and overall cost of traversal
 * 
 */	
def exploreAllPaths(sourceVertex:Vertex, targetVertex:Vertex,visitedNodes:Stack[String], currentPathAndWeight:pathWeightStruct, graph:Map[Vertex,List[Edge]],traversedPaths:ArrayBuffer[pathWeightStruct]) {
	if(sourceVertex.label.equals(targetVertex.label)) {
		traversedPaths+=currentPathAndWeight
	}
	else {
		if(!visitedNodes.contains(sourceVertex.label)) {
			visitedNodes.push(sourceVertex.label)
			println(sourceVertex.label)
			graph.get(sourceVertex).foreach(edgesList=>edgesList.foreach{edge=>
			val destinationVertex = edge.targetVertex
			if(!visitedNodes.contains(destinationVertex.label)) {
				var newCurrentPathWeight:pathWeightStruct = (currentPathAndWeight._1.concat("->").concat(destinationVertex.label),currentPathAndWeight._2+edge.weight)
						exploreAllPaths(destinationVertex, targetVertex,visitedNodes,newCurrentPathWeight,graph,traversedPaths)
			}
			})
		}
		if(visitedNodes.length>0) {
			var poppedElement = visitedNodes.pop()
					println(s"Element is popped out ${poppedElement}")
		}
	}		
}

/**
 * <p>
 * Finds shortest path among all possible traversed paths based on cost of traversal sorted in ascending order. 
 * </p>
 * 
 * @param traversedPaths traversed paths stored in the form of path and overall cost of traversal
 * 
 */	
def findShortestPath(traversedPaths:ArrayBuffer[pathWeightStruct]) {
	val sortedTraversedPathsByCost = traversedPaths.sortBy(x=>(x._2))
	println(s"Shortest path is ${sortedTraversedPathsByCost.head}")
}

//directed graph example
def exploreDirectedGraph():ArrayBuffer[pathWeightStruct]= {
	var graph = new ConcurrentHashMap[Vertex,List[Edge]].asScala
			var v1 = Vertex("A")
			var v2 = Vertex("B")
			var v3 = Vertex("C")
			var v4 = Vertex("D")
			var e1 = Edge(v1,v2,10)
			var e2 = Edge(v2,v3,20)
			var e3 = Edge(v3,v4,30)
			var e4 = Edge(v1,v4,10)
			var e5 = Edge(v1,v3,50)
			graph.put(v1,List(e1,e4,e5))
			graph.put(v2,List(e2))
			graph.put(v3,List(e3))
			graph.put(v4,List())
			var paths = new ArrayBuffer[String]()
			var traversedPaths=new ArrayBuffer[pathWeightStruct]()
			exploreAllPaths(v1,v4, Stack[String]() ,("A",0), graph,	traversedPaths)
			println(s"Printing all paths in between source vertex ${graph.keys.head} and ${graph.keys.last}")
			for(path <- traversedPaths) {
				println(path)
			}
	    traversedPaths
}

//un-directed graph example
def exploreUnDirectedGraph():ArrayBuffer[pathWeightStruct]= {
	var graph = new ConcurrentHashMap[Vertex,List[Edge]].asScala
			var v1 = Vertex("A")
			var v2 = Vertex("B")
			var v3 = Vertex("C")
			var v4 = Vertex("D")
			var v5 = Vertex("E")
			var e12 = Edge(v1,v2,10)
			var e21 = Edge(v2,v1,10)
			var e23 = Edge(v2,v3,20)
			var e41 = Edge(v4,v1,5)
			var e15 = Edge(v1,v5,5)
			var e51 = Edge(v5,v1,5)
			var e32 = Edge(v3,v2,10)
			var e34 = Edge(v3,v4,1)
			var e43 = Edge(v4,v3,1)
			var e45 = Edge(v4,v5,1)
			var e54 = Edge(v5,v4,1)
			var e13 = Edge(v1,v3,2)
			var e31 = Edge(v3,v1,2)

			graph.put(v1,List(e12,e15,e13))
			graph.put(v2,List(e21,e23))
			graph.put(v3,List(e32,e34,e31))
			graph.put(v4,List(e45,e43))
			graph.put(v5,List(e51,e54))
			var paths = new ArrayBuffer[String]()
			var traversedPaths=new ArrayBuffer[pathWeightStruct]()
			exploreAllPaths(v1,v5, Stack[String](),("A",0), graph,traversedPaths)
			println(s"Printing all paths in between source vertex ${v1.label} and ${v5.label}")
			for(path <- traversedPaths) {
				println(path)
			}
	    traversedPaths
}

def main(args:Array[String]) {
	val traversedPathsDirectGraph = exploreDirectedGraph()
	findShortestPath(traversedPathsDirectGraph)
	val traversedPathsUndirectedGraph = exploreUnDirectedGraph()
	findShortestPath(traversedPathsUndirectedGraph)
}		
}