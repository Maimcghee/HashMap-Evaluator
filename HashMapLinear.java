public class HashMapLinear<K,V> implements HashMap<K,V>{

    /**
     * Node class for storing key-value pairs in linear probing.
     * Includes isDeleted flag for lazy deletion.
     */
    private static class HashNode<K,V>{
        protected K key;
        protected V value;
        protected boolean isDeleated;

        public HashNode(){
            this.key = null;
            this.value = null;
            this.isDeleated = false;
        }

        public HashNode(K key, V value){
            this.key = key;
            this.value = value;
            this.isDeleated = false;
        }
    }
    
    protected HashNode<K,V>[] bucketArray;
    protected int numBuckets;
    protected int size;
    private float maxThreshold;

    /**
    * Constructs a HashMapLinear with default numBuckets (11) and default max threshold (0.5).
    */
    public HashMapLinear(){
        this(11,0.5f);
    }

    /**
    * Constructs a HashMapLinear with specified capacity and default max threshold (0.5).
    *
    * @param numBuckets the initial capacity of the hash map
    */
    public HashMapLinear(int numBuckets){
        this(numBuckets,0.5f);
    }

    /**
    * Constructs a HashMapLinear with specified capacity and max threshold.
    *
    * @param numBuckets the initial capacity of the hash map
    * @param max_threshold the load factor threshold for resizing
    */
    public HashMapLinear(int numBuckets, float maxThreshold){
        this.bucketArray = new HashNode[numBuckets];
        this.numBuckets = numBuckets;
        this.size = 0;
        this.maxThreshold = maxThreshold;
   }

   /**
    * Computes the bucket index for the given key.
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
     * Inserts or updates a key-value pair in the hash map.
     * If the key already exists, its value is overwritten.
     *
     * @param key the key to insert
     * @param value the value to associate with the key
     * @return the previous value associated with the key, or null if there was no mapping
     */
    @Override
    public V put(K key, V value){
        if((size + 1)/numBuckets >= maxThreshold){
            grow();
        }
        int index = getBucketIndex(key);
        while(bucketArray[index] != null && !bucketArray[index].isDeleated){
            if(bucketArray[index].key.equals(key)){
                V oldVal = bucketArray[index].value;
                bucketArray[index].value = value;
                return oldVal;
            }
            index = (index + 1) % numBuckets;
        }
        bucketArray[index] = new HashNode<>(key,value);
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
    public V get(K key){
        int index = getBucketIndex(key);
        int startIndex = index;

        while(bucketArray[index] != null){
            if(!bucketArray[index].isDeleated && bucketArray[index].key.equals(key)){
                return bucketArray[index].value;
            }
            index = (index + 1) % numBuckets;

            if(index == startIndex){
                break;
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
        int startIndex = index;

        while(bucketArray[index] != null){
            if(!bucketArray[index].isDeleated && bucketArray[index].key.equals(key)){
                V oldVal = bucketArray[index].value;
                bucketArray[index].isDeleated = true;
                size--;
                return oldVal;
            }
            index = (index + 1) % numBuckets;

            if(index == startIndex){
                break;
            }
        }
        return null;
    }

    /**
     * Helper method:
     * Doubles the capacity of the hash map and rehashes all entries.
    */
    private void grow(){
        HashNode<K,V>[] oldBucketArray = bucketArray;
        numBuckets = numBuckets *2;
        bucketArray = new HashNode[numBuckets];
        size = 0;

        for(HashNode<K,V> node : oldBucketArray){
            if(node != null && !node.isDeleated){
                put(node.key,node.value);
            }
        }
    }

    /**
     * Takes in a dataset and Makes the hashMap from it
     * @param data set to be hashed 
     * @return Hashmap
     */
    public static HashMapLinear<String,Integer> hashIt(HashMapEvaluator.Pair[] dataSet){
        HashMapLinear<String, Integer> map = new HashMapLinear<>();

        if(dataSet == null){
            return map;
        }

        for(int i = 0; i < dataSet.length;i++){
            map.put(dataSet[i].key,dataSet[i].value);
        }
        return map;
    }
}