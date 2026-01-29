/**
 * HashMap implementation using separate chaining with linked lists for collision resolution.
 * Each bucket contains a linked list of entries that hash to the same index.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public class HashMapLL<K,V> implements HashMap<K,V> {
    /**
    * Node class for storing key-value pairs in a linked list chain.
    * Includes next pointer for chaining.
    */
    private static class HashNode<K,V>{
        protected K key;
        protected V value;
        protected HashNode<K,V> next;

        public HashNode(){
            this.key = null;
            this.value = null;
            this.next = null;
        }

        public HashNode(K key, V val){
            this.key = key;
            this.value = val;
            this.next = null;
        }
    }

    protected HashNode<K,V>[] bucketArray;
    protected int numBuckets;
    protected int size;
    protected float maxThreshold;

    /**
     * Constructs a HashMapLL with default numBuckets = 11 and default max threshold = 0.75.
     */
    public HashMapLL(){
        this(11,0.75f);
    }

   /**
     * Constructs a HashMapLL with specified capacity and default max threshold = 0.75.
     *
     * @param numBuckets the initial capacity of the hash map
     */
    public HashMapLL(int numBucks){
        this(numBucks,0.75f);
    }

    /**
     * Constructs a HashMapLL with specified capacity and max threshold.
     *
     * @param numBuckets the initial capacity of the hash map
     * @param max_threshold the load factor threshold for resizing
     */
    public HashMapLL(int numBucks, float maxThresh){
        this.numBuckets = numBucks;
        this.maxThreshold = maxThresh;
        this.size = 0;
        this.maxThreshold = maxThresh;
        this.bucketArray = new HashNode[numBucks];
    }

    /**
     * Computes the bucket index for the given key.
     *
     * @param key the key to hash
     * @return the bucket index
    */
   @Override
    public int getBucketIndex(Object key){
        int hashCode = key.hashCode();
        return Math.abs(hashCode % numBuckets);
    }

    /**
     * Inserts or updates a key-value pair in the hash map.
     * If the key already exists, its value is overwritten.
     *
     * @param key the key to insert
     * @param value the value to associate with the key
     * @return the previous value associated with the key, or null if there was no mapping
     */
    @Override
    public V put(K key,V val){
        //check if we need to grow
        if(size/numBuckets >= maxThreshold){
            grow();
        }
        int index = getBucketIndex(key);
        HashNode<K,V> head = bucketArray[index];

        HashNode<K,V> curr = head;
        while(curr != null){
            if(curr.key.equals(key)){
                V oldVal = curr.value;
                curr.value = val;
                return oldVal;
            }
            curr = curr.next;
        }
    
        HashNode<K,V> newNode = new HashNode<>(key,val);
        newNode.next = head;
        bucketArray[index] = newNode;
        size++;
        return null;
    }

    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key the key whose value is to be retrieved
     * @return the value associated with the key, or null if not found
     */
    @Override
    public V get(Object key){
        int index = getBucketIndex(key);
        HashNode<K,V> curr = bucketArray[index];

        while(curr != null){
            if(curr.key.equals(key)){
                return curr.value;
            }
            curr = curr.next;
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
    public V remove(Object key){
        int index = getBucketIndex(key);
        HashNode<K,V> curr = bucketArray[index];
        HashNode<K,V> prev = null;

        while(curr != null){
            if(curr.key.equals(key)){
                if(prev == null){
                    bucketArray[index] = curr.next;
                }
                else{
                    prev.next = curr.next;
                }
                size--;
                return curr.value;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    /**
     * Helper Function:
     * Doubles the capacity of the hash map and rehashes all entries.
    */
    private void grow(){
        HashNode<K,V>[] oldBuckets = bucketArray;
        numBuckets = numBuckets *2;
        bucketArray = new HashNode[numBuckets];
        size = 0;
    
        for(HashNode<K,V> head : oldBuckets){
            HashNode<K,V> curr = head;
            while(curr != null){
                put(curr.key,curr.value);
                curr = curr.next;
            }

        }

    } 

    /**
     * Takes in a dataset and Makes the hashMap from it
     * @param data set to be hashed 
     * @return Hashmap
     */
    public static HashMapLL<String,Integer> hashIt(HashMapEvaluator.Pair[] dataSet){
        HashMapLL<String,Integer> map = new HashMapLL<>();

        if(dataSet == null){
            return map;
        }

        for(int i = 0; i < dataSet.length;i++){
            map.put(dataSet[i].key,dataSet[i].value);
        }
        return map;
    }
}
