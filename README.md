Project #3: Hash Map Varients 

This project compares three custom HashMap implementations in Java:
    -HashMapLinear – Linear probing (open addressing)
    -HashMapLL – Separate chaining using linked lists
    -HashMapArrayList – Separate chaining using ArrayLists

Each implementation supports the basic HashMap operations:
    -put(K key, V value)
    -get(K key)
    -remove(K key)
    -automatic resizing (grow())
    - HashIt(HashMapEvaluator.Pair[] dataSet)
        - takes a data set of type Pair and hashes them into the respected hashmap then returns the finished hashmap 

HashMapEvaluator class(driver class):
    - A randomly generated dataset is hashed into each map, and performance (insertion time and memory usage) is recorded to compare the three collision-handling strategies
    - contains Pair class used to generate random KV pairs 

Based on the collected data:
    Time:
        - Fastest retrieval: HashMapLL (linked list chaining)
        - Medium retrieval: HashMapArrayList
        - Slowest retrieval: HashMapLinear due to clustering in linear probing
    Memory:
        - Lowest consumption: HashMapLinear
        - Medium consumption: HashMapLL
        - Highest consumption: HashMapArrayList

How To Run Program:
    1. javac *.java
    2. java HashMapEvaluator <Size of data sets > <number of tests (repetitions)> >results.csv