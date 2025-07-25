package ir.sk.adt.datastructure.array;

import ir.sk.algorithm.basic.RotationShift;
import ir.sk.helper.RecurrenceRelation;
import ir.sk.helper.complexity.TimeComplexity;
import ir.sk.helper.recursiontype.HeadRecursion;
import ir.sk.helper.recursiontype.TailRecursion;

/**
 * Unordered array data structure like ArrayList in java without using index. Duplicate is allowed.
 * When duplicate is not allowed, It's like Set in java
 * <p>
 * Unordered arrays offer fast insertion but slow searching and deletion.
 *
 * @author <a href="sad.keyvanfar@gmail.com">Saeid Keyvanfar</a> on 12/7/2017.
 */
public class Array {

    private int[] array;
    private int size;                        // number of data items

    public Array(int max) {
        array = new int[max];
        size = 0;
    }

    /**
     * Linear Search
     * @param searchKey
     * @return
     */
    @TimeComplexity("O(n)")
    public boolean find(int searchKey) {
        int j;
        for (j = 0; j < size; j++)
            if (array[j] == searchKey)
                break;
        if (j == size)
            return false;
        else
            return true;
    }

    /**
     * put element into array
     *
     * @param value
     */
    @TimeComplexity("O(1)")
    public void insert(int value) {
        array[size] = value;
        size++;
    }

    /**
     * @param index
     * @param value
     */
    @TimeComplexity("O(n)")
    public void insert(int index, int value) {
        RotationShift.rightShiftByWriteIndex(array, index, size);
        array[index] = value;
        size++;
    }

    /**
     * after delete element, move all elements after index location (shift)
     *
     * @param value
     * @return
     */
    @TimeComplexity("O(n)")
    public boolean delete(int value) {
        int j;
        for (j = 0; j < size; j++)
            if (value == array[j])
                break;
        if (j == size)
            return false;
        else {
            RotationShift.leftShift(array, j, size);
            return true;
        }
    }

    /**
     * displays array contents
     */
    public void display() {
        for (int j = 0; j < size; j++)
            System.out.print(array[j] + " ");
        System.out.println("");
    }

    ////////////////////////////////////////////// traverse

    /**
     *
     */
    @TimeComplexity("O(n)")
    public void traverseIterative() {
        for (int i = 0; i < size; i++)
            visit(array[i]);
    }

    /**
     * @param index
     */
    @RecurrenceRelation("T(n) = T(n-1) + O(1)")
    @TimeComplexity("O(n)")
    @TailRecursion
    public void traverseTailRecursive(int index) {
        if (index == size)
            return;
        visit(array[index]);
        traverseTailRecursive(index + 1);
    }

    /**
     * @param index
     */
    @RecurrenceRelation("T(n) = T(n-1) + O(1)")
    @TimeComplexity("O(n)")
    @HeadRecursion
    public void traverseHeadRecursive(int index) {
        if (index == size)
            return;
        traverseHeadRecursive(index + 1);
        visit(array[index]);
    }

    public void visit(int value) {
        System.out.println(value);
    }
}
