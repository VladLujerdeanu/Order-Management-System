package presentation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import model.Product;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * This class generates a report for when a product is out of stock.
 */

public class OutOfStockReport {
    public static int version = 1;

    /**
     * The constructor for creating the out of stock report
     * @param p out of stock product
     */
    public OutOfStockReport(Product p) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Out of Stock Report no. " + version++ + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();

        Font helvetica18 = FontFactory.getFont(FontFactory.HELVETICA, 18, BaseColor.BLACK);

        try {
            Chunk header = new Chunk("Out of Stock", helvetica18);
            document.add(header);
            document.add(new Paragraph(""));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("The product: " + p.getName() + " (ID: " + p.getId() + ") is under-stocked.\nCurrent available quantity: " + p.getQuantity()));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }
}
