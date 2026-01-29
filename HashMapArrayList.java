import java.util.ArrayList;

/**
 * HashMap implementation using separate chaining with ArrayLists for collision resolution.
 * Each bucket contains an ArrayList of entries that hash to the same index.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public class HashMapArrayList<K,V> implements HashMap<K,V> {

    /**
     * Node class for storing key-value pairs in an ArrayList.
     * Simple key-value container with no pointers needed.
    */
    private static class HashNode<K,V>{
        protected K key;
        protected V value;

        public HashNode(){
            this.key = null;
            this.value = null;
        }

        public HashNode(K key, V val){
            this.key = key;
            this.value = val;
        }
    }

    protected ArrayList<HashNode<K,V>>[] bucketArray;
    protected int numBuckets;
    protected int size;
    protected float maxThreshold;

    /**
     * Constructs a HashMapArrayList with defualt numBuckets = 11 and defualt maxThreshold = 0.75
     */
    public HashMapArrayList(){
        this(11,0.75f);
    }

    /**
     * Coonstructs a HashMapArrayList with specified numBuckets and defualt maxThreshold
     */
    public HashMapArrayList(int numBuckets){
        this(numBuckets,0.75f);
    }

    /**
     * Constructs a HashMapArrayList w/ specified numBuckets and max Threshold
     */
    public HashMapArrayList(int numBuckets,float maxThresh){
        this.bucketArray = new ArrayList[numBuckets];
        this.numBuckets = numBuckets;
        this.maxThreshold = maxThresh;
        this.size = 0;

        for(int i = 0; i < numBuckets; i++){
            bucketArray[i] = new ArrayList<>();
        }
    }

    /**
     * Computes the bucket index for the given array
     * 
     * @param key the key to hash
     * @return the bucket index
     */
    @Override
    public int getBucketIndex(K key){
        int hashCode = key.hashCode();
        return Math.abs(hashCode % numBuckets);
    }

    /**
     * Adds KV pair in the hashmap
     * if the key already exists -> update it with new value
     * 
     * @param key the key to insert
     * @param value the value associated with the given key
     * @return the previouse value assosiated the the given key
     */
    @Override
    public V put(K key, V val){
        if(size/numBuckets >= maxThreshold){
            grow();
        }
        int index = getBucketIndex(key);
        ArrayList<HashNode<K,V>> bucket = bucketArray[index];

        for(HashNode<K,V> node: bucket){
            if(node.key.equals(key)){
                V oldVal = node.value;
                node.value = val;
                return oldVal;
            }
        }
        bucket.add(new HashNode<>(key,val));
        size++;
        return null;
    }

    /**
     * Gets the value associated w the specific key
     * 
     * @param key key whos val is supposed to be retireved
     * @return the value associated w the key, or null if its not found
     */
    @Override
    public V get(K key){
        int index = getBucketIndex(key);
        ArrayList<HashNode<K,V>> bucket = bucketArray[index];

        for(HashNode<K,V> n : bucket){
            if(n.key.equals(key)){
                return n.value;
            }
        }
        return null;
    }

    /**
     * Removes the key-value pair associated with the specified key.
     *
     * @param key the key to remove
     * @return the value that was associated with the key, or null if not found
     */
    @Override
    public V remove(K key){
        int index = getBucketIndex(key);
        ArrayList<HashNode<K,V>> bucket = bucketArray[index];

        for(int i = 0; i < bucket.size(); i++){
            HashNode<K,V> node = bucket.get(i);
            if(node.key.equals(key)){
                bucket.remove(i);
                size--;
                return node.value;
            }
        }
        return null;
    }

    /**
     * Helper Method to grow arrayList when it needs more space
     */
    public void grow(){
        ArrayList<HashNode<K,V>>[] oldBucket = bucketArray;
        numBuckets = numBuckets*2;
        bucketArray = new ArrayList[numBuckets];

        for(int i = 0; i < numBuckets; i++){
            bucketArray[i] = new ArrayList<>();
        }

        for(ArrayList<HashNode<K,V>> bucket : oldBucket){
            for(HashNode<K,V> node : bucket){
                put(node.key, node.value);
            }

        }
    }

    /**
     * Takes in a dataset and Makes the hashMap from it
     * @param data set to be hashed 
     * @return Hashmap
     */
    public static HashMapArrayList<String,Integer> hashIt(HashMapEvaluator.Pair[] dataSet){
        HashMapArrayList map = new HashMapArrayList<>();

        if(dataSet == null){
            return map;
        }

        for(int i = 0; i < dataSet.length;i++){
            map.put(dataSet[i].key,dataSet[i].value);
        }
        return map;
    }
}
