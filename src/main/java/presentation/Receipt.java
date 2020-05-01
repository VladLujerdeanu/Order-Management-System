package presentation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.query.dbClient;
import database.query.dbOrderItems;
import database.query.dbProduct;
import model.Ordert;
import model.OrderItems;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.stream.Stream;

/**
 * This class generates a receipt for new orders.
 */

public class Receipt {
    public static int version = 1;

    /**
     * The constructor for creating a receipt for a given order
     * @param o given order
     */
    public Receipt(Ordert o) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Receipt no. " + o.getId() + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();

        Font helvetica18 = FontFactory.getFont(FontFactory.HELVETICA, 18, BaseColor.BLACK);

        try {
            Chunk header = new Chunk("Order summary", helvetica18);
            document.add(header);
            document.add(new Paragraph(""));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(5);
            addTableHeader(table);
            addRows(table, o);

            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Client ID", "Item", "Quantity", "Total").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }

    private void addRows(PdfPTable table, Ordert o) {
        dbOrderItems oi = new dbOrderItems();
        dbProduct dbp = new dbProduct();
        dbClient dbc = new dbClient();
        OrderItems items = oi.findById(o.getId());
        table.addCell(o.getId() + "");
        table.addCell(dbc.findById(o.getClientID()).getFirstName() + " " + dbc.findById(o.getClientID()).getLastName());
        table.addCell(dbp.findById(items.getProductID()).getName());
        table.addCell(items.getQuantity() + "");
        table.addCell(o.getTotal() + "");
    }
}
