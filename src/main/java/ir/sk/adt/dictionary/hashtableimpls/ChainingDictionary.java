package ir.sk.adt.dictionary.hashtableimpls;

import ir.sk.adt.dictionary.Dictionary;
import ir.sk.helper.Default;
import ir.sk.helper.Remainder;
import ir.sk.helper.complexity.TimeComplexity;

/**
 * ChainingDictionary ADT (HashTable ADT) (HashMap ADT), Symbol table
 * Hash table is a generalization of array. With an array, we store the element whose key is k at a
 * position k of the array.That means, given a key k, we find the element whose key is k by just
 * looking in the kth position of the array. This is called direct addressing.
 * Direct addressing is applicable when we can afford to allocate an array with one position for
 * every possible key.
 * deals with collisions by Chaining
 * <p>
 * Created by sad.keyvanfar on 7/6/2020.
 */
@Default
public class ChainingDictionary<K, V> implements Dictionary<K, V> {

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_CAPACITY = 10;

    // bucketArray is used to store array of chains
    // instead of linklist we can use different data structure like array bst
    private ChainingHashNode<K, V>[] bucketArray;

    // Current capacity of array list (m) (n <= m <= 4n) (constant load factor)
    private int capacity;

    // Current size of array list (n)
    private int size;

    private final float maxLoadFactor;

    public ChainingDictionary() {
        this.maxLoadFactor = DEFAULT_LOAD_FACTOR;
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        bucketArray = new ChainingHashNode[capacity];
    }

    public ChainingDictionary(int initialCapacity, float maxLoadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        if (maxLoadFactor <= 0 || Float.isNaN(maxLoadFactor))
            throw new IllegalArgumentException("Illegal load factor: " + maxLoadFactor);
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = initialCapacity;
        this.size = 0;
        bucketArray = new ChainingHashNode[capacity];
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
        @Remainder
        int index = hashCode % capacity;
        return index;
    }

    /**
     * Search
     * Returns value for a key
     *
     * @param key
     * @return
     */
    @Override
    public V get(K key) {

        // Find head of chain for given key
        int bucketIndex = hashFuntion(key);
        ChainingHashNode<K, V> head = bucketArray[bucketIndex];

        // Search key in chain
        while (head != null) {
            if (head.key.equals(key))
                return head.value;
            head = head.next;
        }

        return null;
    }

    /**
     * Adds a key value pair to hash
     * <p>
     * Time Complexity on average is o(n) since reHash() when need more space
     * but Amortized Time Complexity is O(1)
     * size of new array = 2 * numBuckets (Table Doubling): It's the best size for growing the size of array
     * it operates on log n cost;, when m = 1,2,4,8,16,32,64,... 2^n
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        // Find head of chain for given key
        int bucketIndex = hashFuntion(key);
        ChainingHashNode<K, V> head = bucketArray[bucketIndex];

        // Check if key is already present
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }

        // Insert key in chain
        size++;
      //  head = bucketArray[bucketIndex];
        ChainingHashNode<K, V> newNode = new ChainingHashNode<>(key, value);
        newNode.next = head;
        bucketArray[bucketIndex] = newNode;

        //  when Load Factor (n/m) is 1, Time Complexity is exact O(1)
        double loadFactor = getLoadFactor();
        if (loadFactor >= this.maxLoadFactor) {
            // size of new array = 2 * numBuckets (Table Doubling): It's the best size for growing the size of array
            reHash(2 * capacity);
        }
    }

    private double getLoadFactor() {
        double loadFactor = ((1.0 * size) / capacity);
        return loadFactor;
    }

    /**
     * If load factor goes beyond threshold, then double hash table size
     */
    @TimeComplexity("O(n + m + m') = O(n)")
    private void reHash(int m) {
        capacity = m;
        size = 0;
        ChainingHashNode<K, V>[] temp = bucketArray;
        bucketArray = new ChainingHashNode[capacity];

        for (ChainingHashNode<K, V> headNode : temp) {
            while (headNode != null) {
                put(headNode.key, headNode.value);
                headNode = headNode.next;
            }
        }
    }

    /**
     * Method to remove a given key
     * <p>
     * Time Complexity on average is o(n) since shrink() when need more space
     * but Amortized Time Complexity is O(1)
     *
     * @param key
     * @return
     */
    @Override
    public void remove(K key) {
        // Apply hash function to find index for given key
        int bucketIndex = hashFuntion(key);

        // Get head of chain
        ChainingHashNode<K, V> head = bucketArray[bucketIndex];

        // Search for key in its chain
        ChainingHashNode<K, V> prev = null;
        while (head != null) {
            // If Key found
            if (head.key.equals(key))
                break;

            // Else keep moving in chain
            prev = head;
            head = head.next;
        }

        // If key was not there
        if (head == null)
            return;

        // Reduce size
        size--;

        // Remove key
        if (prev != null)
            prev.next = head.next;
        else
            bucketArray[bucketIndex] = head.next;

        // Shrink
        double loadFactor = getLoadFactor();
        if (loadFactor <= this.maxLoadFactor) {
            // size of new array = numBuckets / 4 (Table Shrink): It's the best size for shrinking the size of array
            reHash(capacity / 4);
        }

        return;
    }
}

class ChainingHashNode<K, V> {

    K key;
    V value;

    // Reference to next node
    ChainingHashNode<K, V> next;

    public ChainingHashNode() {
    }

    public ChainingHashNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

