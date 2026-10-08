# HashMap Collision Strategy Benchmark

A comparison of three custom HashMap implementations in Java, each handling collisions differently, benchmarked on insertion time and memory usage.

## Implementations

| Class | Collision strategy |
|---|---|
| `HashMapLinear` | Open addressing with linear probing |
| `HashMapLL` | Separate chaining with linked lists |
| `HashMapArrayList` | Separate chaining with ArrayLists |

`HashMap.java` defines the shared interface all three implement.

Each implementation supports:
- `put(K key, V value)`
- `get(K key)`
- `remove(K key)`
- Automatic resizing via `grow()`
- `hashIt(Pair[] dataSet)`: hashes a full dataset into the map and returns it

## How the benchmark works

`HashMapEvaluator` is the driver class. It generates a random dataset of key-value pairs (using its `Pair` class), hashes the same data into each implementation, and records insertion time and memory usage across repeated runs.

## Results

**Speed (fastest → slowest)**
1. `HashMapLL`: linked-list chaining
2. `HashMapArrayList`
3. `HashMapLinear`: slowed by clustering, where filled slots bunch together and probing takes longer

**Memory (lowest → highest)**
1. `HashMapLinear`: no extra node or list objects
2. `HashMapLL`
3. `HashMapArrayList`

**Takeaway:** linear probing uses the least memory but slows down as clusters form. Chaining is faster but costs more memory. That's the classic speed-vs-memory trade-off.

Raw data is in `results.csv`, and charts are in the PDF.

## How to run

```bash
javac *.java
java HashMapEvaluator <number_of_repetitions> > results.csv
```
