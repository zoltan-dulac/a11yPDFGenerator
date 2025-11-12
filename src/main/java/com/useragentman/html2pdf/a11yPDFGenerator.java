package com.useragentman.html2pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

public class a11yPDFGenerator {
    /* private static final String fontbase = "src/main/resources/";
    private static final String bodyFontName = "OpenSans-VariableFont_wdth_wght.ttf";
    private static final String codeFontName = "SourceCodePro-VariableFont_wght.ttf";

    private static File bodyFontFile = new File(fontbase + bodyFontName);
    private static File codeFontFile = new File(fontbase + codeFontName); */

    public static void main(String[] args) {


        if (args.length < 2) {
            System.err.println("Usage: java -jar a11yPDFGenerator.jar <input.html> <output.pdf> [--lang en] [--title \"Doc Title\"]");
            System.exit(1);
        }

        String inputPath = args[0];
        String outputPath = args[1];
        String lang = "en";
        String title = null;

        for (int i = 2; i < args.length; i++) {
            if ("--lang".equals(args[i]) && i + 1 < args.length) {
                lang = args[++i];
            } else if ("--title".equals(args[i]) && i + 1 < args.length) {
                title = args[++i];
            }
        }

        File htmlFile = new File(inputPath);
        if (!htmlFile.isFile()) {
            System.err.println("Input HTML not found: " + inputPath);
            System.exit(2);
        }

        // compute base URI once
        String baseUri = (htmlFile.getParentFile() != null)
                ? htmlFile.getParentFile().toURI().toString()
                : new File(".").toURI().toString();
        System.out.println("baseUri is " + baseUri);

        try (OutputStream os = new FileOutputStream(outputPath)) {

            PdfRendererBuilder builder = new PdfRendererBuilder();
            /* builder.useFont(bodyFontFile, "BodyFont");
            builder.useFont(codeFontFile, "CodeFont"); */

            // These are the correct APIs for 1.0.10:
            String html = Files.readString(htmlFile.toPath());
            builder.withHtmlContent(html, baseUri);
            builder.toStream(os);

            builder.usePdfUaAccessbility(true); // enables tagging

            // If you want PDF/A conformance, uncomment one of:
            // builder.usePdfAConformance(PdfRendererBuilder.PdfAConformance.PDFA_1_A);
            builder.usePdfAConformance(PdfRendererBuilder.PdfAConformance.PDFA_3_A);
            builder.run();

            /* bodyFontFile.delete();
            codeFontFile.delete(); */

            // Add metadata using PDFBox
            try (PDDocument doc = PDDocument.load(new File(outputPath))) {
                PDDocumentInformation info = doc.getDocumentInformation();
                if (title != null) info.setTitle(title);
                info.setSubject("Generated with openhtmltopdf");
                info.setCustomMetadataValue("Language", lang);
                doc.setDocumentInformation(info);
                doc.getDocumentCatalog().setLanguage(lang);
                doc.save(outputPath);
            }

            System.out.println("✅ Generated accessible PDF: " + new File(outputPath).getAbsolutePath());

        } catch (Exception e) {
            System.err.println("❌ Error generating PDF: " + e.getMessage());
            e.printStackTrace();
            System.exit(3);
        }
    }
}
