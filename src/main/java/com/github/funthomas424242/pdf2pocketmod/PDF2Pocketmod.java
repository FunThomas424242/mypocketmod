package com.github.funthomas424242.pdf2pocketmod;

/*-
 * #%L
 * pdf2pocketmod
 * %%
 * Copyright (C) 2018 - 2019 PIUG
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PDF2Pocketmod {

    public static void main(String args[]) throws Exception {
        final PDF2Pocketmod app = new PDF2Pocketmod();
        app.run();
    }

    public void run() throws IOException {
        final PDDocument pocketmodPdf = new PDDocument();
        pocketmodPdf.addPage(new PDPage());

        //creating the PDPageContentStream object
        PDPageContentStream contents = new PDPageContentStream(pocketmodPdf, pocketmodPdf.getPage(0));

        for (int pageNum = 1; pageNum < 3; pageNum++) {
            final PDImageXObject seite = getPDFPageAsImage(pocketmodPdf, pageNum);
            addImageToPage0(contents, seite);
        }
        //Closing the PDPageContentStream object
        contents.close();


        //Saving the document
        pocketmodPdf.save("Pocketmod.pdf");

        //Closing the document
        pocketmodPdf.close();

    }

    public byte[] getPDFPageAsBytes(final Path absoluteFilePath) throws IOException {
        final PDDocument seite = loadPage(absoluteFilePath);
        final BufferedImage image = getPDFAsImageBytes(seite);
        return convertImage2Bytes(image);
    }

    protected PDImageXObject getPDFPageAsImage(PDDocument pocketmodPdf, int pageNum) throws IOException {
        final PDDocument seite = loadPage(Paths.get(".", "/src/test/java/com/github/funthomas424242/pdf2pocketmod/"), "Seite", "pdf", pageNum);
        final BufferedImage image = getPDFAsImageBytes(seite);
        final byte[] bytes = convertImage2Bytes(image);
        final PDImageXObject pdImage = PDImageXObject.createFromByteArray(pocketmodPdf, bytes, "myImage Seite" + pageNum);
        System.out.println("Image created for page " + pageNum);
        return pdImage;
    }

    protected void addImageToPage0(PDPageContentStream contents, PDImageXObject pdImage) throws IOException {
        //Drawing the image in the PDF document
        contents.drawImage(pdImage, 1, 1);
        System.out.println("Image inserted");
    }

    protected byte[] convertImage2Bytes(final BufferedImage image) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        baos.flush();
        return baos.toByteArray();
    }

    private BufferedImage getPDFAsImageBytes(PDDocument doc1) throws IOException {
        //Instantiating the PDFRenderer class
        final PDFRenderer renderer = new PDFRenderer(doc1);
        //Rendering an image from the PDF document
        return renderer.renderImage(0);
    }


    protected PDDocument loadPage(final Path folder, String filePrefix, String fileExtension, int pageNum) throws IOException {
        return loadPage(Paths.get(folder.toAbsolutePath().toString(), filePrefix + pageNum + "." + fileExtension));
    }

    protected PDDocument loadPage(final Path absoluteFilePath) throws IOException {
        //Loading page x
        final File seite1 = absoluteFilePath.toFile();
        System.out.println(seite1.getAbsolutePath());
        return PDDocument.load(seite1);
    }
}



