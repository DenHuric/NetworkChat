import org.example.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoggerTest {
    @Test
    public void TestWriteEventToLog() {
        String testMsg = "Test Massage";
        boolean actual = Logger.GetLoggerInstance().WriteEventToLog(testMsg);
        boolean expected = true;

        Assertions.assertEquals(expected, actual);
    }
}
