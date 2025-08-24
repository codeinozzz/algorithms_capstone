package com.example.capstoneii.utils.datastructures

import kotlin.math.max

class AVLTree<K : Comparable<K?>?, V> {
    private var root: AVLNode<K?, V?>? = null

    fun insert(key: K?, value: V?) {
        if (key == null) return
        root = insertRecursive(root, key, value)
    }

    private fun insertRecursive(node: AVLNode<K?, V?>?, key: K?, value: V?): AVLNode<K?, V?>? {
        if (node == null) {
            return AVLNode<K?, V?>(key, value)
        }

        val comparison = key!!.compareTo(node.key)
        if (comparison < 0) {
            node.left = insertRecursive(node.left, key, value)
        } else if (comparison > 0) {
            node.right = insertRecursive(node.right, key, value)
        } else {
            node.value = value
            return node
        }


        updateHeight(node)


        val balance = getBalance(node)



        if (balance > 1 && node.left != null && key.compareTo(node.left!!.key) < 0) {
            return rightRotate(node)
        }


        if (balance < -1 && node.right != null && key.compareTo(node.right!!.key) > 0) {
            return leftRotate(node)
        }


        if (balance > 1 && node.left != null && key.compareTo(node.left!!.key) > 0) {
            node.left = leftRotate(node.left)
            return rightRotate(node)
        }


        if (balance < -1 && node.right != null && key.compareTo(node.right!!.key) < 0) {
            node.right = rightRotate(node.right)
            return leftRotate(node)
        }

        return node
    }

    fun search(key: K?): V? {
        if (key == null) return null
        return searchRecursive(root, key)
    }

    private fun searchRecursive(node: AVLNode<K?, V?>?, key: K?): V? {
        if (node == null) return null

        val comparison = key!!.compareTo(node.key)
        if (comparison == 0) return node.value
        else if (comparison < 0) return searchRecursive(node.left, key)
        else return searchRecursive(node.right, key)
    }

    fun clear() {
        root = null
    }

    val isEmpty: Boolean
        get() = root == null

    private fun rightRotate(y: AVLNode<K?, V?>?): AVLNode<K?, V?>? {
        if (y == null || y.left == null) return y // Safety check


        val x = y.left
        val T2 = x!!.right


        // Perform rotation
        x.right = y
        y.left = T2


        // Update heights
        updateHeight(y)
        updateHeight(x)

        return x
    }

    private fun leftRotate(x: AVLNode<K?, V?>?): AVLNode<K?, V?>? {
        if (x == null || x.right == null) return x // Safety check


        val y = x.right
        val T2 = y!!.left


        y.left = x
        x.right = T2


        updateHeight(x)
        updateHeight(y)

        return y
    }

    private fun getHeight(node: AVLNode<K?, V?>?): Int {
        return if (node == null) 0 else node.height
    }

    private fun updateHeight(node: AVLNode<K?, V?>?) {
        if (node != null) {
            node.height = 1 + max(getHeight(node.left), getHeight(node.right))
        }
    }

    private fun getBalance(node: AVLNode<K?, V?>?): Int {
        return if (node == null) 0 else getHeight(node.left) - getHeight(node.right)
    }

    private class AVLNode<K, V>(var key: K?, var value: V?) {
        var left: AVLNode<K?, V?>? = null
        var right: AVLNode<K?, V?>? = null
        var height: Int = 1
    }
}