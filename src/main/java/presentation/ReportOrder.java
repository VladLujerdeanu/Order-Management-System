package presentation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.query.dbClient;
import database.query.dbOrder;
import database.query.dbOrderItems;
import database.query.dbProduct;
import model.Ordert;
import model.OrderItems;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * This class generates a report with all the orders in the database.
 */

public class ReportOrder {
    public static int version = 1;

    /**
     * The constructor that creates a report with all the orders in the database.
     */
    public ReportOrder() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Order Report no. " + version + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();

        Font helvetica18 = FontFactory.getFont(FontFactory.HELVETICA, 18, BaseColor.BLACK);

        try {
            Chunk header = new Chunk("Order Report", helvetica18);
            document.add(header);
            document.add(new Paragraph(""));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(5);
            addTableHeader(table);
            addRows(table);

            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Client ID", "Item ID", "Quantity", "Total").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }

    private void addRows(PdfPTable table) {
        dbOrder db = new dbOrder();
        dbOrderItems oi = new dbOrderItems();
        dbProduct dbp = new dbProduct();
        dbClient dbc = new dbClient();
        ArrayList<Ordert> orders = (ArrayList<Ordert>) db.findAll();
        for (Ordert o : orders) {
            OrderItems items = oi.findById(o.getId());
            table.addCell(o.getId() + "");
            table.addCell(o.getClientID() + "");
            table.addCell(dbp.findById(items.getProductID()).getName());
            table.addCell(items.getQuantity() + "");
            table.addCell(o.getTotal() + "");
        }
    }
}
