import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.sqlDAO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/DownloadPDFServlet")
public class DownloadPDFServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String fullname = (String) session.getAttribute("fullname");

        if (fullname == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.pdf");

        try (Connection connection = sqlDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM transactions WHERE fullname = ? ORDER BY transaction_date DESC LIMIT 10")) {
            
            preparedStatement.setString(1, fullname);
            ResultSet resultSet = preparedStatement.executeQuery();

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            // Add bank logo
            ServletContext context = getServletContext();
            String imagePath = context.getRealPath("/imgs/logo.jpg");
            Path path = Paths.get(imagePath);
            if (Files.exists(path)) {
                Image img = Image.getInstance(imagePath);
                img.setAlignment(Element.ALIGN_CENTER);
                img.scaleToFit(150, 150); // Adjust size as needed
                document.add(img);
                document.add(new Paragraph(" ")); // Add space after the image
            }

            document.add(new Paragraph(" "));
            
            Font subTitleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLACK);
            Paragraph subTitle = new Paragraph("Transaction History", subTitleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
            
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Font headFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            Font cellFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);

            // Header Cells
            PdfPCell hcell;

            hcell = new PdfPCell(new Phrase("Transaction ID", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(BaseColor.DARK_GRAY);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Full Name", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(BaseColor.DARK_GRAY);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Amount", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(BaseColor.DARK_GRAY);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Transaction Type", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(BaseColor.DARK_GRAY);
            table.addCell(hcell);

            // Data Rows
            while (resultSet.next()) {
                PdfPCell cell;

                cell = new PdfPCell(new Phrase(resultSet.getString("transaction_id"), cellFont));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorderColor(BaseColor.GRAY);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(resultSet.getString("fullname"), cellFont));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorderColor(BaseColor.GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(resultSet.getDouble("amount")), cellFont));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorderColor(BaseColor.GRAY);
                table.addCell(cell);

                String type = resultSet.getString("transaction_type");
                String displayType;

                if (type.contains("Transfer")) {
                    // Handle transfers
                        displayType = type;       
                } else {
                    // Handle regular credit and debit
                    displayType = "credit".equals(type) ? "Credited" : "Debited";
                }
                cell = new PdfPCell(new Phrase(displayType, cellFont));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorderColor(BaseColor.GRAY);
                table.addCell(cell);
            }

            document.add(table);
            document.close();

        } catch (SQLException | DocumentException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
