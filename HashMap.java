public interface HashMap<K,V> {
    V put(K key, V value);
    V get(K key);
    V remove(K key);
    int getBucketIndex(K key);
}
