package service.report;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import model.Book;
import view.model.SaleDTO;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class PdfReportService {

    public void exportBookReport(List<Book> books, File file)
    {
        Document document = new Document(PageSize.A4);

        try{
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph title = new Paragraph("Library Inventory Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            String[] headers = {"Book Title", "Sold to", "Price", "Date"};
            for(String header : headers)
            {
                PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
            addTableHeader(table);

            for(Book book : books)
            {
                table.addCell(book.getTitle());
                table.addCell(book.getAuthor());
                table.addCell(String.valueOf(book.getPrice()));
                table.addCell(String.valueOf(book.getStock()));
            }
            document.add(table);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally
        {
            document.close();
        }
    }

    public void exportSalesReport(List<SaleDTO> sales, File file) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Sales History Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            String[] headers = {"Book Title", "Sold To", "Price", "Date"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            for (SaleDTO sale : sales) {
                table.addCell(sale.getBookTitle());
                table.addCell(sale.getUsername());
                table.addCell(String.valueOf(sale.getPrice()));
                table.addCell(sale.getFormattedDate());
            }

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }



    private void addTableHeader(PdfPTable table)
    {
        String[] headers = {"Title", "Author", "Price", "Stock"};
        for(String header : headers)
        {
            PdfPCell headerCell = new PdfPCell(new Phrase(header));
            headerCell.setPhrase(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            headerCell.setBackgroundColor(Color.LIGHT_GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell);
        }
    }

}
