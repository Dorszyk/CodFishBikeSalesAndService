package com.codfish.bikeSalesAndService.business;

import com.codfish.bikeSalesAndService.api.dto.InvoiceDTO;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] generateInvoicePdf(InvoiceDTO invoice) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);

        document.open();

        // Fonty z obsługą polskich znaków
        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font headerFont = new Font(baseFont, 22, Font.BOLD, new Color(0, 51, 102));
        Font titleFont = new Font(baseFont, 16, Font.BOLD);
        Font normalFont = new Font(baseFont, 10, Font.NORMAL);
        Font boldFont = new Font(baseFont, 10, Font.BOLD);
        Font smallFont = new Font(baseFont, 8, Font.NORMAL);

        // Logo i Nagłówek
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{1, 1});

        // Lewa strona nagłówka - Dane firmy
        PdfPCell companyCell = new PdfPCell();
        companyCell.setBorder(Rectangle.NO_BORDER);
        companyCell.addElement(new Paragraph("Codfish Bike Sales & Service", titleFont));
        companyCell.addElement(new Paragraph("ul. Akacjowa 12", normalFont));
        companyCell.addElement(new Paragraph("10-001 Olsztyn, Polska", normalFont));
        companyCell.addElement(new Paragraph("NIP: 123-456-78-90", normalFont));
        companyCell.addElement(new Paragraph("Email: contact@codfishbike.pl", normalFont));
        headerTable.addCell(companyCell);

        // Prawa strona nagłówka - Tytuł i numer
        PdfPCell invoiceTitleCell = new PdfPCell();
        invoiceTitleCell.setBorder(Rectangle.NO_BORDER);
        invoiceTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        Paragraph p = new Paragraph("FAKTURA", headerFont);
        p.setAlignment(Element.ALIGN_RIGHT);
        invoiceTitleCell.addElement(p);
        Paragraph p2 = new Paragraph("Nr: " + invoice.getInvoiceNumber(), titleFont);
        p2.setAlignment(Element.ALIGN_RIGHT);
        invoiceTitleCell.addElement(p2);
        Paragraph p3 = new Paragraph("Data wystawienia: " + invoice.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), normalFont);
        p3.setAlignment(Element.ALIGN_RIGHT);
        invoiceTitleCell.addElement(p3);
        headerTable.addCell(invoiceTitleCell);

        document.add(headerTable);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph(new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1f, 100, Color.BLACK, Element.ALIGN_CENTER, -2))));
        document.add(new Paragraph("\n"));

        // Sekcja: Nabywca i Sprzedawca
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setSpacingAfter(20);

        // Sprzedawca
        PdfPCell sCell = new PdfPCell();
        sCell.setBorder(Rectangle.NO_BORDER);
        sCell.addElement(new Paragraph("SPRZEDAWCA:", boldFont));
        if (invoice.getSalesman() != null) {
            sCell.addElement(new Paragraph("Kod sprzedawcy: " + invoice.getSalesman().getCodeNameSurname(), normalFont));
        } else if (invoice.getServiceRequest() != null && invoice.getServiceRequest().getTechnicians() != null && !invoice.getServiceRequest().getTechnicians().isEmpty()) {
            String techs = String.join(", ", invoice.getServiceRequest().getTechnicians());
            sCell.addElement(new Paragraph("Kod technika: " + techs, normalFont));
        } else {
            sCell.addElement(new Paragraph("Serwis Rowerowy", normalFont));
        }
        infoTable.addCell(sCell);

        // Nabywca
        PdfPCell nCell = new PdfPCell();
        nCell.setBorder(Rectangle.NO_BORDER);
        nCell.addElement(new Paragraph("NABYWCA:", boldFont));
        nCell.addElement(new Paragraph(invoice.getCustomer().getName() + " " + invoice.getCustomer().getSurname(), normalFont));
        nCell.addElement(new Paragraph("Email: " + invoice.getCustomer().getEmail(), normalFont));
        nCell.addElement(new Paragraph("Tel: " + invoice.getCustomer().getPhone(), normalFont));
        nCell.addElement(new Paragraph("Adres: " + invoice.getCustomer().getAddress(), normalFont));
        infoTable.addCell(nCell);

        document.add(infoTable);

        // Tabela z przedmiotami
        PdfPTable itemTable = new PdfPTable(4);
        itemTable.setWidthPercentage(100);
        itemTable.setWidths(new float[]{1, 4, 1, 2});
        itemTable.setSpacingAfter(20);

        // Nagłówki tabeli
        addHeaderCell(itemTable, "Lp.", boldFont);
        addHeaderCell(itemTable, "Nazwa towaru/usługi", boldFont);
        addHeaderCell(itemTable, "Ilość", boldFont);
        addHeaderCell(itemTable, "Wartość brutto", boldFont);

        int lp = 1;
        if (invoice.getBike() != null) {
            // Rower
            itemTable.addCell(new PdfPCell(new Paragraph(String.valueOf(lp++), normalFont)));
            PdfPCell descriptionCell = new PdfPCell();
            descriptionCell.addElement(new Paragraph(invoice.getBike().getBrand() + " " + invoice.getBike().getModel(), normalFont));
            descriptionCell.addElement(new Paragraph("Nr seryjny: " + invoice.getBike().getSerial(), smallFont));
            descriptionCell.addElement(new Paragraph("Rok: " + invoice.getBike().getYear() + ", Kolor: " + invoice.getBike().getColor(), smallFont));
            itemTable.addCell(descriptionCell);

            PdfPCell qtyCell = new PdfPCell(new Paragraph("1 szt.", normalFont));
            qtyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemTable.addCell(qtyCell);

            PdfPCell priceCell = new PdfPCell(new Paragraph(invoice.getBike().getPrice() + " PLN", normalFont));
            priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            itemTable.addCell(priceCell);
        }

        if (invoice.getServiceRequest() != null) {
            // Usługi
            for (var service : invoice.getServiceRequest().getServices()) {
                itemTable.addCell(new PdfPCell(new Paragraph(String.valueOf(lp++), normalFont)));
                PdfPCell descriptionCell = new PdfPCell();
                descriptionCell.addElement(new Paragraph(service.getDescription(), normalFont));
                descriptionCell.addElement(new Paragraph("Kod usługi: " + service.getServiceCode(), smallFont));
                itemTable.addCell(descriptionCell);

                PdfPCell qtyCell = new PdfPCell(new Paragraph("1 usł.", normalFont));
                qtyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                itemTable.addCell(qtyCell);

                PdfPCell priceCell = new PdfPCell(new Paragraph(service.getPrice() + " PLN", normalFont));
                priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                itemTable.addCell(priceCell);
            }
            // Części
            for (var part : invoice.getServiceRequest().getParts()) {
                itemTable.addCell(new PdfPCell(new Paragraph(String.valueOf(lp++), normalFont)));
                PdfPCell descriptionCell = new PdfPCell();
                descriptionCell.addElement(new Paragraph(part.getDescription(), normalFont));
                descriptionCell.addElement(new Paragraph("Nr seryjny: " + part.getSerialNumber(), smallFont));
                itemTable.addCell(descriptionCell);

                PdfPCell qtyCell = new PdfPCell(new Paragraph("1 szt.", normalFont));
                qtyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                itemTable.addCell(qtyCell);

                PdfPCell priceCell = new PdfPCell(new Paragraph(part.getPrice() + " PLN", normalFont));
                priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                itemTable.addCell(priceCell);
            }
        }

        document.add(itemTable);

        // Podsumowanie
        PdfPTable summaryTable = new PdfPTable(2);
        summaryTable.setWidthPercentage(40);
        summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

        PdfPCell tLabel = new PdfPCell(new Paragraph("RAZEM DO ZAPŁATY:", boldFont));
        tLabel.setBorder(Rectangle.NO_BORDER);
        summaryTable.addCell(tLabel);

        java.math.BigDecimal totalPrice = java.math.BigDecimal.ZERO;
        if (invoice.getBike() != null) {
            totalPrice = totalPrice.add(invoice.getBike().getPrice());
        }
        if (invoice.getServiceRequest() != null) {
            totalPrice = totalPrice.add(invoice.getServiceRequest().getTotalPrice());
        }

        PdfPCell tValue = new PdfPCell(new Paragraph(totalPrice + " PLN", titleFont));
        tValue.setBorder(Rectangle.NO_BORDER);
        tValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        summaryTable.addCell(tValue);

        document.add(summaryTable);

        // Stopka
        Paragraph footer = new Paragraph("\nDziękujemy za zakupy w naszym sklepie!", normalFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
        return baos.toByteArray();
    }

    private void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
        cell.setBackgroundColor(new Color(230, 230, 230));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);
    }

    private void addCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }
}
