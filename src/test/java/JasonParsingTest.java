import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JasonParsingTest {
    private  ClassLoader cl = FileParsingAndReadingTest.class.getClassLoader();

    @Test
    void testJsonParsing() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = cl.getResourceAsStream("certificate.json");
             InputStreamReader isr = new InputStreamReader(is)) {

            CertificateTest certificateTest = mapper.readValue(isr, CertificateTest.class);

            Assertions.assertEquals("Vano", certificateTest.userId);
            Assertions.assertEquals("Developer", certificateTest.jobTitle);
            Assertions.assertEquals("Ivan", certificateTest.firstName);
            Assertions.assertEquals("Ivanov", certificateTest.lastName);
            Assertions.assertEquals("E1", certificateTest.employeeCode);
            Assertions.assertEquals("123456", certificateTest.phoneNumber);
            Assertions.assertEquals(List.of("Web", "Mobile"), certificateTest.access);
        }
    }
}

