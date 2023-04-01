import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.InvalidArgumentException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileParsingAndReadingTest {

    private ClassLoader cl = FileParsingAndReadingTest.class.getClassLoader();


    private ZipInputStream getStreamFromArchive(String filename) throws IOException {
        ZipEntry entry;
        ZipInputStream zis;
        InputStream is = cl.getResourceAsStream("file-examples.zip");
        assert is != null;
        zis = new ZipInputStream(is);
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().endsWith(filename)) return zis;
        }
        is.close();
        zis.close();
        throw new InvalidArgumentException("ERROR");
    }


    @Test
    void zipPdfTest() throws Exception {
        try (InputStream inputStream = getStreamFromArchive(".pdf")) {
                    PDF pdf = new PDF(inputStream);
                    Assertions.assertTrue(pdf.text.startsWith("Lorem ipsum"));
                }
            }

    @Test
    void zipXlsxTest() throws Exception {
        try (InputStream inputStream = getStreamFromArchive(".xls")) {
            XLS xls = new XLS(inputStream);
            Assertions.assertEquals(xls.excel.getSheetAt(0).getRow(2).getCell(4)
                    .getStringCellValue(), "Great Britain");
        }
    }
    @Test
    void zipCsvTest() throws Exception {
         try (InputStream inputStream = getStreamFromArchive(".csv")) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
            List<String[]> content = csvReader.readAll();
                    Assertions.assertArrayEquals(new String[]{"2", "Mara", "Hashimoto", "Female", "Great Britain", "25", "16/08/2016", "1582"}, content.get(2));
        }
    }
}

