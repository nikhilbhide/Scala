package com.nik.scalaexamples

import scala.collection.convert.decorateAsScala._
import java.util.concurrent.ConcurrentHashMap
import scala.collection.concurrent.Map
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Stack

case class Vertex (label:String)
case class Edge (sourceVertex:Vertex,targetVertex:Vertex, weight:Int)

/**
 * <p>
 * Graph path exploration figures out different paths available from source node to destination node for a given graph.
 * Graph is provided in the form of edges, vertices. Graph can be directed or undirected graph
 * Same algorithm with few changes can be used to find shortest path algorithm in between two vertices. 
 * </p>
 * 
 * @author nikhil.bhide $Date : Apr 25, 2017 $
 * 
 */
object GraphPathExploration {
	type connectionType = (Vertex,List[Edge])
			val results:ArrayBuffer[String] = new ArrayBuffer()

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
		 * 
	   */	
		def exploreAllPaths(sourceVertex:Vertex, targetVertex:Vertex,visitedNodes:Stack[String], currentPath:String, graph:Map[Vertex,List[Edge]]) {
		if(sourceVertex.label.equals(targetVertex.label)) {
			results+=currentPath
		}
		else {
			if(!visitedNodes.contains(sourceVertex.label)) {
				visitedNodes.push(sourceVertex.label)
				println(sourceVertex.label)
				graph.get(sourceVertex).foreach(edgesList=>edgesList.foreach{edge=>
				val destinationVertex = edge.targetVertex
				if(!visitedNodes.contains(destinationVertex.label)) {
					var newCurrentPath = currentPath.concat("->").concat(destinationVertex.label)
							println(newCurrentPath)
							exploreAllPaths(destinationVertex, targetVertex,visitedNodes,newCurrentPath,graph)
				}
				})
			}
			if(visitedNodes.length>0) {
				var poppedElement = visitedNodes.pop()
						println(s"Element is popped out ${poppedElement}")
			}
		}		
	}

	//directed graph example
	def exploreDirectedGraph() {
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
				exploreAllPaths(v1,v4, Stack[String]() ,"A", graph)
				println(s"Printing all paths in between source vertex ${graph.keys.head} and ${graph.keys.last}")
				for(path <- results) {
					println(path)
				}
	}

	//un-directed graph example
	def exploreUnDirectedGraph() {
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
				var e15 = Edge(v1,v5,10)
				var e51 = Edge(v5,v1,10)
				var e32 = Edge(v3,v2,10)
				var e34 = Edge(v3,v4,10)
				var e43 = Edge(v4,v3,10)
				var e45 = Edge(v4,v5,10)
				var e54 = Edge(v5,v4,10)
				var e13 = Edge(v1,v3,10)
				var e31 = Edge(v3,v1,10)

				graph.put(v1,List(e12,e15,e13))
				graph.put(v2,List(e21,e23))
				graph.put(v3,List(e32,e34,e31))
				graph.put(v4,List(e45,e43))
				graph.put(v5,List(e51,e54))
				var paths = new ArrayBuffer[String]()
				exploreAllPaths(v1,v5, Stack[String]() ,"A", graph)
				println(s"Printing all paths in between source vertex ${v1.label} and ${v5.label}")
				for(path <- results) {
					println(path)
				}
	}

	def main(args:Array[String]) {
		exploreDirectedGraph()
		exploreUnDirectedGraph()		
	}		
}