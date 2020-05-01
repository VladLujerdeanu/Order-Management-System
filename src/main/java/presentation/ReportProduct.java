package presentation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.query.dbProduct;
import model.Product;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * This class generates a report with all the products in the database.
 */

public class ReportProduct {
    public static int version = 1;

    /**
     * The constructor that creates a report with all the products in the database.
     */
    public ReportProduct() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Product Report no. " + version++ + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();

        Font helvetica18 = FontFactory.getFont(FontFactory.HELVETICA, 18, BaseColor.BLACK);

        try {
            Chunk header = new Chunk("Client Report", helvetica18);
            document.add(header);
            document.add(new Paragraph(""));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(4);
            addTableHeader(table);
            addRows(table);

            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Name", "Quantity", "Price").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }

    private void addRows(PdfPTable table) {
        dbProduct dbp = new dbProduct();
        ArrayList<Product> products = (ArrayList<Product>) dbp.findAll();
        for (Product p : products) {
            table.addCell(p.getId() + "");
            table.addCell(p.getName());
            table.addCell(p.getQuantity() + "");
            table.addCell(p.getPrice() + "");
        }
    }
}
