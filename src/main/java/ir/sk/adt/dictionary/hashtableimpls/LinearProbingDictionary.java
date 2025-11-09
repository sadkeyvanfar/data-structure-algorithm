package ir.sk.adt.dictionary.hashtableimpls;

import ir.sk.adt.dictionary.Dictionary;
import ir.sk.helper.complexity.TimeComplexity;

/**
 * deals with collisions by Open Addressing - Linear Probing
 * <p>
 * In Open Addressing, all elements are stored in the hash table itself.
 * So at any point, size of table must be greater than or equal to total number of keys (M>=n)
 *
 * @author <a href="sad.keyvanfar@gmail.com">Saeid Keyvanfar</a> on 7/7/2020.
 */
public class LinearProbingDictionary<K, V> implements Dictionary<K, V> {

    private static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final int DEFAULT_CAPACITY = 10;

    // bucketArray is used to store array
    private OpenAddressingHashNode<K, V>[] array;

    // Current capacity of array list (m) (n <= m <= 4n) (constant load factor)
    private int capacity;

    // Current size of array list (n)
    private int size;

    private final float maxLoadFactor;

    // dummy node as deleted flag
    OpenAddressingHashNode<K, V> deletedNode;

    public LinearProbingDictionary() {
        this.maxLoadFactor = DEFAULT_LOAD_FACTOR;
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        array = new OpenAddressingHashNode[capacity];
        deletedNode = new OpenAddressingHashNode<>();
    }

    public LinearProbingDictionary(int initialCapacity, float maxLoadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        if (maxLoadFactor <= 0 || Float.isNaN(maxLoadFactor))
            throw new IllegalArgumentException("Illegal load factor: " + maxLoadFactor);
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = initialCapacity;
        this.size = 0;
        array = new OpenAddressingHashNode[capacity];
        deletedNode = new OpenAddressingHashNode<>();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * This implements hash function to find index for a key
     *
     * @param key
     * @return
     */
    private int hashFuntion(K key) {
        // prehashing
        int hashCode = key.hashCode();
        // hashing
        int index = hashCode % capacity;
        return index;
    }

    /**
     * Keep probing until an empty slot is found. Once an empty slot is found, insert k.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        OpenAddressingHashNode<K, V> newNode = new OpenAddressingHashNode<>(key, value);
        int hashIndex = hashFuntion(key);

        //find next free space
        while (array[hashIndex] != null && array[hashIndex].key != key) {
            hashIndex++;
            hashIndex %= capacity; // wrap around
        }

        //if new node to be inserted increase the current size
        if (array[hashIndex] == null)
            size++;
        array[hashIndex] = newNode;

        //  when Load Factor (n/m) is 1, Time Complexity is exact O(1)
        double loadFactor = ((1.0 * size) / capacity);
        if (loadFactor >= this.maxLoadFactor) {
            // size of new array = 2 * numBuckets (Table Doubling): It's the best size for growing the size of array
            reHash(2 * capacity);
        }
    }

    /**
     * Keep probing until slot’s key doesn’t become equal to k or an empty slot is reached.
     *
     * @param key
     * @return
     */
    @Override
    public V get(K key) {
        // Apply hash function to find index for given key
        int hashIndex = hashFuntion(key);
        int counter = 0;
        //finding the node with given key
        while (array[hashIndex] != null) {
            if (counter++ > capacity)  //to avoid infinite loop
                return null;
            //if node found return its value
            if (array[hashIndex].key == key)
                return array[hashIndex].value;
            hashIndex++;
            hashIndex %= capacity;
        }

        //If not found return null
        return null;
    }

    /**
     * Delete operation is interesting. If we simply delete a key, then search may fail.
     * So slots of deleted keys are marked specially as “deleted”
     *
     * @param key
     * @return
     */
    @Override
    public void remove(K key) {
        // Apply hash function to find index for given key
        int hashIndex = hashFuntion(key);

        //finding the node with given key
        while (array[hashIndex] != null) {
            //if node found
            if (array[hashIndex].key == key) {
                OpenAddressingHashNode<K, V> temp = array[hashIndex];

                //Insert dummy node here for further use
                array[hashIndex] = deletedNode;

                // Reduce size
                size--;

                // Shrink
                double loadFactor = ((1.0 * size) / capacity);
                if (loadFactor <= this.maxLoadFactor) {
                    // size of new array = numBuckets / 4 (Table Shrink): It's the best size for shrinking the size of array
                    reHash(capacity / 4);
                }

                return;
            }
            hashIndex++;
            hashIndex %= capacity;

        }

        //If not found return
        return;
    }

    /**
     * If load factor goes beyond threshold, then double hash table size
     */
    @TimeComplexity("O(n + m + m') = O(n)")
    private void reHash(int m) {
        capacity = m;
        size = 0;
        OpenAddressingHashNode<K, V>[] temp = array;
        array = new OpenAddressingHashNode[capacity];

        for (OpenAddressingHashNode<K, V> headNode : temp) {
            while (headNode != null) {
                put(headNode.key, headNode.value);
            }
        }
    }
}

