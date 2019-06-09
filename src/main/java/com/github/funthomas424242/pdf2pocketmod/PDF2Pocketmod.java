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
        final PDPage page = new PDPage();
        pocketmodPdf.addPage( page );

        final PDDocument doc1 = loadPage( Paths.get(".","/src/test/java/com/github/funthomas424242/pdf2pocketmod/"), "Seite", "pdf", 1);

        //Instantiating the PDFRenderer class
        final PDFRenderer renderer = new PDFRenderer(doc1);
        //Rendering an image from the PDF document
        final BufferedImage image = renderer.renderImage(0);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        baos.flush();
        byte[] bytes = baos.toByteArray();

        //Writing the image to a file
//        ImageIO.write(image, "JPEG", new File("/src/test/java/com/github/funthomas424242/pdf2pocketmod/Seite1.png"));


        PDImageXObject pdImage = PDImageXObject.createFromByteArray(pocketmodPdf, bytes, "myImage Seite1");
//        PDImageXObject pdImage = PDImageXObject.createFromFile("/src/test/java/com/github/funthomas424242/pdf2pocketmod/Seite1.png", doc);



        System.out.println("Image created");

//        pocketmodPdf.addPage(page1);


        //Creating PDImageXObject object
//        PDImageXObject pdImage = PDImageXObject.createFromFile("C:/PdfBox_Examples/logo.png", doc);

        //creating the PDPageContentStream object
        PDPageContentStream contents = new PDPageContentStream(pocketmodPdf, page);

        //Drawing the image in the PDF document
        contents.drawImage(pdImage, 1, 1);
//        contents.drawImage(pdImage, 70, 250);

        System.out.println("Image inserted");

        //Closing the PDPageContentStream object
        contents.close();

        //Saving the document
        pocketmodPdf.save("Pocketmod.pdf");

        //Closing the document
        pocketmodPdf.close();

    }


    protected PDDocument loadPage(final Path folder, String filePrefix, String fileExtension, int pageNum) throws IOException {
        //Loading page x
        final File seite1 = Paths.get(folder.toAbsolutePath().toString(), filePrefix+pageNum+"."+fileExtension).toFile();
        System.out.println(seite1.getAbsolutePath());
        return PDDocument.load(seite1);
    }
}



