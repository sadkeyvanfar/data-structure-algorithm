package ir.sk.algorithm.others;

import ir.sk.algorithm.others.SumOfCubes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="sad.keyvanfar@gmail.com">Saeed Kayvanfar</a> on 8/22/2020.
 */
public class SumOfCubesTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void sumOfCubesNative() {
        int number = 10;
        SumOfCubes.sumOfCubesNative(number);
    }

    @Test
    public void sumOfCubesNativeOptimize() {
        int number = 10;
        SumOfCubes.sumOfCubesNativeOptimize(number);
    }

    @Test
    public void sumOfCubesNativeOptimize2() {
        int number = 10;
        SumOfCubes.sumOfCubesNativeOptimize2(number);
    }

    @Test
    public void sumOfCubesOptimizeByHashing() {
        int number = 10;
        SumOfCubes.sumOfCubesOptimizeByHashing(number);
    }
}