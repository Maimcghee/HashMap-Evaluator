import java.util.Random;
/** 
 * A class for generating random Key-Value datasets and evaluating the preformance(time and memory) of the different vairents of hashmaps 
 */
public class HashMapEvaluator {
    /**
     * Represents a simple key-value pair.
     */
    public class Pair{
        String key;
        int value;

        /**
         * Constructs a Pair object with a string key and integer value.
         *
         * @param key   the key for the pair
         * @param value the value associated with the key
         */
        public Pair(String key, Integer value){
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Generates a dataset containing a specified number of random key-value pairs of type p.
     * Each key is a randomly generated 10-character lowercase string.
     * The value is a random integer between 0 and 9999.
     *
     * @param dataSetSize the number of key-value pairs to generate
     * @return an array of randomly generated Key-Value Pair objects
     */
    public Pair[] makeDataSet(int dataSetSize){
        Pair[] dataSet = new Pair[dataSetSize];
        Random rand = new Random();

        for(int i = 0; i < dataSetSize; i++){
            StringBuilder sb = new StringBuilder();

            for(int j = 0; j < 10; j++){
                char c = (char)('a' + rand.nextInt(26));
                sb.append(c);
            }

            String key = sb.toString();
            Integer value = rand.nextInt(10000);
            Pair p = new Pair(key,value);
            dataSet[i] = p;
        }
        return dataSet;
    }


    /**
     * Tests preformance of different HashMap varients based on the number of rounds specified from the command line
     * @param args
     */
    public static void main(String[] args){
        int dataSetSize = Integer.parseInt(args[0]);
        int reps = Integer.parseInt(args[1]);
        HashMapEvaluator hME = new HashMapEvaluator();

        for(int i = 0; i < reps; i++){
            Pair[] dataSet = hME.makeDataSet(dataSetSize);

            //                          LINEAR PROBING:
            HashMapLinear<String,Integer> linear = HashMapLinear.hashIt(dataSet);
            System.gc();
            long memoryConsuption = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            long start = System.nanoTime();
            for(int j = 0; j< dataSet.length;j++){
                linear.get(dataSet[j].key);
            }
            long end = System.nanoTime();
            long timeTaken = end - start;
            System.out.println("HashMap Linear " + timeTaken + " " + memoryConsuption);

            //                            LINKED LIST:
            HashMapLL<String,Integer> linkedList = HashMapLL.hashIt(dataSet);
            System.gc();
            memoryConsuption = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            start = System.nanoTime();
            for(int j = 0; j< dataSet.length;j++){
                linkedList.get(dataSet[j].key);
            }
            end = System.nanoTime();
            timeTaken = end - start;
            System.out.println("HashMap Linked List " + timeTaken + "," + memoryConsuption);

            //                             ARRAY LIST:
            HashMapArrayList<String,Integer> arrayList = HashMapArrayList.hashIt(dataSet);
            System.gc();
            memoryConsuption = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            start = System.nanoTime();
            for(int j = 0; j< dataSet.length;j++){
                arrayList.get(dataSet[j].key);
            }
            end = System.nanoTime();
            timeTaken = end - start;
            System.out.println("HashMap Array List " + timeTaken + ", " + memoryConsuption);
        }
    }
}
