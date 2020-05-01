package presentation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.query.dbClient;
import model.Client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * This class generates a report with all the clients in the database.
 */

public class ReportClient {
    public static int version = 1;

    /**
     * The constructor that creates a report with all the clients in the database.
     */
    public ReportClient() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Client Report no. " + version++ + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            System.out.println("Cannot create Client Report.");
            e.printStackTrace();
        }

        document.open();

        Font helvetica18 = FontFactory.getFont(FontFactory.HELVETICA, 18, BaseColor.BLACK);

        Chunk header = new Chunk("Client Report", helvetica18);
        try {
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
        Stream.of("ID", "First Name", "Last Name", "Address").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(1);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }

    private void addRows(PdfPTable table) {
        dbClient dbc = new dbClient();
        ArrayList<Client> clients = (ArrayList<Client>) dbc.findAll();
        for (Client c : clients) {
            table.addCell(c.getId() + "");
            table.addCell(c.getFirstName());
            table.addCell(c.getLastName());
            table.addCell(c.getAddress());
        }
    }
}
