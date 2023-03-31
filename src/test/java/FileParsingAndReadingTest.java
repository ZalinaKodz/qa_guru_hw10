import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileParsingAndReadingTest {

    private ClassLoader cl = FileParsingAndReadingTest.class.getClassLoader();

    @Test
    void zipPdfTest() throws Exception {
        try (InputStream zipFile = cl.getResourceAsStream("file-examples.zip");
             ZipInputStream zm = new ZipInputStream(zipFile)) {
            ZipEntry entry;
            while ((entry = zm.getNextEntry()) != null) {
                if (entry.getName().contains(".pdf")) {
                    PDF pdf = new PDF(zm);
                    Assertions.assertTrue(entry.getName().contains("file-example_PDF_500_kB.pdf"));
                    Assertions.assertTrue(pdf.text.startsWith("Lorem ipsum"));
                }
            }
        }
    }

    @Test
    void zipXlsxTest() throws Exception {
        try (InputStream zipFile = cl.getResourceAsStream("file-examples.zip");
             ZipInputStream filesFromZip = new ZipInputStream(zipFile)) {
            ZipEntry entry;
            while ((entry = filesFromZip.getNextEntry()) != null) {
                if (entry.getName().contains(".xlsx")) {
                    XLS xls = new XLS(filesFromZip);
                    Assertions.assertTrue(entry.getName().contains("file_example_XLS_50.xls"));
                    Assertions.assertEquals(xls.excel.getSheetAt(0).getRow(15).getCell(16)
                            .getStringCellValue(), "Great Britain");
                }
            }
        }
    }

    @Test
    void zipCsvTest() throws Exception {
        try (InputStream zipFile = cl.getResourceAsStream("file-examples.zip");
             ZipInputStream zm = new ZipInputStream(zipFile)) {
            ZipEntry entry;
            while ((entry = zm.getNextEntry()) != null) {
                if (entry.getName().contains(".csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zm));
                    List<String[]> csvRow = csvReader.readAll();
                    Assertions.assertTrue(entry.getName().contains("file_example_CSV_5000.csv"));
                    Assertions.assertArrayEquals(new String[]{"2", "Mara", "Hashimoto", "Female", "Great Britain", "25", "16/08/2016", "1582"}, csvRow.get(2));
                }
            }

        }
    }
}

