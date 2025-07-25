package ir.sk.algorithm.basic;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by sad.kayvanfar on 9/16/2020.
 */
public class RotationShiftTest {

    private int[] array;

    @Before
    public void setUp() throws Exception {
        array = new int[]{1, 2, 3, 4, 5, 6};
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void rightRotate() {
        int[] expected = new int[]{1, 5, 2, 3, 4, 6};
        System.out.println(Arrays.toString(array));
        long start = System.currentTimeMillis();
        RotationShift.rightRotate(array, 1, 4);
        long end = System.currentTimeMillis();
        System.out.println("rightRotate took " + (end - start) + " MilliSeconds");
        System.out.println(Arrays.toString(array));
        Assert.assertArrayEquals(expected, array);
    }

    @Test
    public void leftRotate() {
        int[] expected = new int[]{1, 3, 4, 5, 2, 6};
        System.out.println(Arrays.toString(array));
        long start = System.currentTimeMillis();
        RotationShift.leftRotate(array, 1, 4);
        long end = System.currentTimeMillis();
        System.out.println("leftRotate took " + (end - start) + " MilliSeconds");
        System.out.println(Arrays.toString(array));
        Assert.assertArrayEquals(expected, array);
    }

    @Test
    public void testRightRotate() { // TODO: 7/14/2020
        System.out.println(Arrays.toString(array));
        long start = System.currentTimeMillis();
        RotationShift.rightRotate(array, 1, 4, 2);
        long end = System.currentTimeMillis();
        System.out.println("rightRotate took " + (end - start) + " MilliSeconds");
        System.out.println(Arrays.toString(array));
    }

    @Test
    public void testLeftRotate() {
        int[] expected = new int[]{1, 4, 5, 2, 3, 6};
        System.out.println(Arrays.toString(array));
        long start = System.currentTimeMillis();
        RotationShift.leftRotate(array, 1, 4, 2);
        long end = System.currentTimeMillis();
        System.out.println("Logic shift took " + (end - start) + " MilliSeconds");
        System.out.println(Arrays.toString(array));
        Assert.assertArrayEquals(expected, array);
    }

    @Test
    public void rightShiftByWriteIndex() {
        int[] expected = new int[]{1, 2, 2, 3, 4, 6};
        System.out.println(Arrays.toString(array));
        long start = System.currentTimeMillis();
        RotationShift.rightShiftByWriteIndex(array, 1, 4);
        long end = System.currentTimeMillis();
        System.out.println("rightShiftByWriteIndex took " + (end - start) + " MilliSeconds");
        System.out.println(Arrays.toString(array));
        Assert.assertArrayEquals(expected, array);
    }

    @Test
    public void rightShiftByReadIndex() {
        int[] expected = new int[]{1, 2, 2, 3, 4, 6};
        System.out.println(Arrays.toString(array));
        long start = System.currentTimeMillis();
        RotationShift.rightShiftByReadIndex(array, 1, 4);
        long end = System.currentTimeMillis();
        System.out.println("rightShiftByReadIndex took " + (end - start) + " MilliSeconds");
        System.out.println(Arrays.toString(array));
        Assert.assertArrayEquals(expected, array);
    }
}