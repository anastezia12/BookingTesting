import testing.TestingClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;

public class Task3 {

    public static void main(String[] args) throws Exception {

        Class<?> testClass = TestingClass.class;
        run(testClass);


    }

    public static void run(Class testClass) throws IOException {
        int tests = 0;
        File myFile = new File("task3Results.txt");
        FileWriter writer = new FileWriter(myFile);

        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(org.junit.Test.class)) {
                tests++;
                writer.write(m.toString() + ", ");

            }
        }
        writer.close();
        System.out.printf("Count of tests: %d", tests);
    }
}
