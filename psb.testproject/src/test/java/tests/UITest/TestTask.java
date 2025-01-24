package tests.UITest;

import org.junit.jupiter.api.Test;
import psb.testproject.selenium.Task;
import tests.UITest.BaseUITest;

public class TestTask extends BaseUITest {
    Task task;

    public TestTask() {
        task = BaseUITest.task;
    }

    @Test
    public void BuildTest() throws Exception {
        task.build();
    }

    @Test
    public void HeadLessTest() throws Exception {
        task.doHeadLessBuild();
    }

    @Test
    public void TimeoutTestY1() throws Exception {
        task.buildWithTimeoutY(100);
    }

    @Test
    public void TimeoutTestG1() throws Exception {
        task.buildWithTimeoutG(100);
    }
}
