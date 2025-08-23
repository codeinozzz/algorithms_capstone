package com.example.capstoneii.utils.datastructures;

import java.util.ArrayList;
import java.util.Objects;

public class HashNode <K,V>{
    K key;
    V value;
    final int hashCode;

    HashNode<K, V> next;

    public HashNode(K key, V value, int hashCode)
    {
        this.key = key;
        this.value = value;
        this.hashCode = hashCode;
    }
}
