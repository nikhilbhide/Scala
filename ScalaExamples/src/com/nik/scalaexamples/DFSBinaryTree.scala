package com.nik.scalaexamples

case class Node (left:Node,right:Node,value:Int)

/**
 * <p>
 * It performs defth first serach in the binary tree by doing pre order traversal. 
 * </p>
 * 
 * @author nikhil.bhide $Date : Apr 25, 2017 $
 * 
 */
object DFSBinaryTree {
  
/**
 * Traverses binary tree in pre order and prints visited nodes. Its one kind of DFS.
 * Logic is implemented using recursion and break point 
 * 
 * @param node Node on the binary tree having references to left child and right child. A node without a child node is a leaf node.
 * @param height Keeps track of heigh of tree
 */
def findDFS(node:Node, height:Int) {
    println(s"Visited ${node.value} at level $height")    
    if(node.left!=null) {
      //visit left node of a binary tree
      findDFS(node.left, height+1)
    }
    if(node.right!=null) {
      //visit left node of a binary tree
      findDFS(node.right, height+1)
    }
  }

  def main(args:Array[String]) {
   //create a binary tree
   val n1 = Node(null,null,20)
   val n2 = Node(null,null,30)
   val root = Node (n1,n2,10)
   findDFS(root,0)
  }  
}