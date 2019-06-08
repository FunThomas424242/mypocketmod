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

import java.io.File;

public class PDF2Pocketmod {

    public static void main(String args[]) throws Exception {
        //Loading an existing document
        File file = new File("C:/PdfBox_Examples/sample.pdf");
        PDDocument doc = PDDocument.load(file);

        //Retrieving the page
        PDPage page = doc.getPage(0);

        //Creating PDImageXObject object
        PDImageXObject pdImage = PDImageXObject.createFromFile("C:/PdfBox_Examples/logo.png", doc);

        //creating the PDPageContentStream object
        PDPageContentStream contents = new PDPageContentStream(doc, page);

        //Drawing the image in the PDF document
        contents.drawImage(pdImage, 70, 250);

        System.out.println("Image inserted");

        //Closing the PDPageContentStream object
        contents.close();

        //Saving the document
        doc.save("C:/PdfBox_Examples/sample.pdf");

        //Closing the document
        doc.close();

    }
}



