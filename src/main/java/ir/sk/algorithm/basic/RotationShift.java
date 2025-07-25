package ir.sk.algorithm.basic;

import ir.sk.helper.complexity.SpaceComplexity;
import ir.sk.helper.complexity.TimeComplexity;

/**
 * Created by sad.kayvanfar on 9/16/2020.
 */
public class RotationShift {

    /**
     * the read index is used in the loop
     * @param array
     * @param startIndex inclusive
     * @param endIndex exclusive
     */
    public static void rightShiftByReadIndex(int[] array, int startIndex, int endIndex) {
        for (int i = endIndex - 1; i >= startIndex; i--)
            array[i + 1] = array[i];
    }

    /**
     * write index is used in the loop
     * @param startIndex inclusive
     * @param endIndex exclusive
     */
    public static void rightShiftByWriteIndex(int[] array, int startIndex, int endIndex) {
        for (int i = endIndex; i > startIndex; i--)
            array[i] = array[i -1];
    }

    /**
     * @param startIndex inclusive
     * @param endIndex exclusive
     */
    public static void leftShift(int[] array, int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++)
            array[i] = array[i + 1];
    }

    /**
     * @param array
     * @param startIndex inclusive
     * @param endIndex exclusive
     * @param unit the number of units to shift
     */
    public static void leftShift(int[] array, int startIndex, int endIndex, int unit) {
        for (int i = startIndex; i + unit < endIndex; i++)
            array[i] = array[i + unit];
    }

    /**
     * shifting - right rotation just one unit
     *
     * @param array      the source array.
     * @param startIndex starting position
     * @param endIndex   finishing position,
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(1)")
    public static void rightRotate(int[] array, int startIndex, int endIndex) {
        int temp = array[endIndex];
        rightShiftByWriteIndex(array, startIndex, endIndex);
        array[startIndex] = temp;
    }

    // another way of rotation is Circular Array
    /**
     * rotation - left rotation just one unit
     *
     * @param array      the source array.
     * @param startIndex starting position
     * @param endIndex   finishing position
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(1)")
    public static void leftRotate(int[] array, int startIndex, int endIndex) {
        int temp = array[startIndex];
        leftShift(array, startIndex, endIndex);
        array[endIndex] = temp;
    }

    /**
     * rotation - right rotation unit number
     *
     * @param array      the source array.
     * @param startEndex starting position
     * @param endIndex   finishing position
     * @param unit       the number of rotations
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(unit)")
    public static void rightRotate(int[] array, int startEndex, int endIndex, int unit) { // TODO: 7/14/2020 incorrect
        int m = startEndex;
        int[] temp = new int[unit];
        for (int k = endIndex + 1 - unit; k <= endIndex; k++) {
            temp[m] = array[k];
            m++;
        }

        int k = endIndex - unit;
        while (k >= startEndex) {
            array[k + unit] = array[k];
            k--;
        }

        for (int l = 0; l < unit; l++) {
            array[l] = temp[l];
        }
    }

    /**
     * rotation - left rotation unit number
     *
     * @param array      the source array.
     * @param startIndex starting position
     * @param endIndex   finishing position
     * @param unit       the number of rotations
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(unit)")
    public static void leftRotate(int[] array, int startIndex, int endIndex, int unit) {
        int[] temp = new int[unit];

        // move unit number from startIndex of array into a temp array
        for (int k = 0; k < unit; k++) {
            temp[k] = array[startIndex + k];
        }

        // shift other elements by unit
        leftShift(array, startIndex, endIndex, unit);
        int k = startIndex;

        while ((k + unit) <= endIndex) {
            array[k] = array[k + unit];
            k++;
        }


        // move all elements from temp into a latest elements array until endIndex
        for (int l = 0; l < unit; l++) {
            array[k + l] = temp[l];
        }
    }

    public static int[] leftRotateArray(int[] arr, int d) {
        // Because the constraints state d < n, we need not concern ourselves with shifting > n units.
        int n = arr.length;

        // Create new array for rotated elements:
        int[] rotated = new int[n];

        // Copy segments of shifted elements to rotated array:
        System.arraycopy(arr, d, rotated, 0, n - d);
        System.arraycopy(arr, 0, rotated, n - d, d);

        return rotated;
    }

    /**
     * @param arr
     * @param init
     * @return
     */
    public static int[] rotateArray(int[] arr, int init) {
        // Because the constraints state d < n, we need not concern ourselves with shifting > n units.
        int n = arr.length;

        // Create new array for rotated elements:
        int[] rotated = new int[n];

        // Copy segments of shifted elements to rotated array:
        System.arraycopy(arr, init, rotated, 0, n - init);
        System.arraycopy(arr, 0, rotated, n - init, init);

        return rotated;
    }
}
