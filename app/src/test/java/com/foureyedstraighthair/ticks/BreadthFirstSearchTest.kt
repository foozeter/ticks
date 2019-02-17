package com.foureyedstraighthair.ticks

import org.junit.Test
import java.util.*

class BreadthFirstSearchTest {

    @Test
    fun test() {
        val tree = makeTree()
        breadthFirstSearch(tree) {
            System.out.println(it.value)
        }
    }

    fun breadthFirstSearch(tree: Node, onEvalute: (node: Node) -> Unit) {
        val queue = ArrayDeque<Node>()
        queue.add(tree)
        while (queue.isNotEmpty()) {
            val node = queue.poll()
            onEvalute(node)
            if (node is InternalNode) {
                queue.addAll(node.children)
            }
        }
    }

    /**
     * A
     * |--B
     * |  |--F
     * |  |  |--J
     * |  |  |--K
     * |  |  |__L
     * |  |
     * |  |__G
     * |     |--M
     * |     |__N
     * |
     * |--C
     * |  |--H
     * |  |__I
     * |
     * |--D
     * |
     * |__E
     */
    fun makeTree(): Node {
        val F = InternalNode('F', leaves('J', 'K', 'L'))
        val G = InternalNode('G', leaves('M', 'N'))
        val B = InternalNode('B', listOf(F, G))
        val C = InternalNode('C', leaves('H', 'I'))
        val D = LeafNode('D')
        val E = LeafNode('E')
        return InternalNode('A', listOf(B, C, D, E))
    }

    fun leaves(vararg chars: Char)
            = chars.map { LeafNode(it) }

    interface Node {
        val value: Char
    }

    data class InternalNode(
        override val value: Char,
        val children: List<Node>): Node

    data class LeafNode(
        override val value: Char): Node
}