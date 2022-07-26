package testing;

import org.junit.Test;

import java.io.IOException;

public class TestingClass extends BaseSelenium {

    @Test
    public void test1() throws IOException {
        String destinationValue = "New York";
        Test1 test1 = new Test1();
        test1.start(destinationValue);

    }

    @Test
    public void test2() throws IOException {
        Test2 test2 = new Test2();
        test2.accountCheck("New York");
    }


}
