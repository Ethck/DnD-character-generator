import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class main {
    public static void main (String[] args) throws IOException {
        playerCharacter PC = new playerCharacter();
        File blankCharacterSheet = new File("/home/ethck/IdeaProjects/characterSheetMaker/src/_blank.pdf");
        PDDocument doc = PDDocument.load(blankCharacterSheet);
        PDPage titlePage = doc.getPage(0);
        PDPageContentStream titleContentStream = new PDPageContentStream(doc, titlePage, PDPageContentStream.AppendMode.APPEND, true);
        titleContentStream.beginText();
        // Character name
        titleContentStream.setFont(PDType1Font.TIMES_ROMAN, 18);
        addText(titleContentStream, 70, 715, PC.name);

        titleContentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        addStatText(titleContentStream, 50, 595, PC.strength);
        addStatText(titleContentStream, 50, 523, PC.dexterity);
        addStatText(titleContentStream, 50, 451, PC.constitution);
        addStatText(titleContentStream, 50, 379, PC.intelligence);
        addStatText(titleContentStream, 50,308, PC.wisdom);
        addStatText(titleContentStream, 50, 236, PC.charisma);
        titleContentStream.endText();
        titleContentStream.close();



        doc.save("/home/ethck/IdeaProjects/characterSheetMaker/test.pdf");
        doc.close();
    }

    // pre: No values are null
    // post: Adds text to page. Resets coordinates back to 0,0.
    public static void addText(PDPageContentStream stream, int x, int y, String text) throws IOException {
        stream.newLineAtOffset(x,y);
        stream.showText(text);
        stream.newLineAtOffset(-x, -y);
    }

    // pre: No values are null.
    // post: Adds text to the page, if stat is one digit, it is shifted to the center.
    public static void addStatText(PDPageContentStream stream, int x, int y, int stat) throws IOException {
        if (stat < 10) { x += 4;}
        stream.newLineAtOffset(x,y);
        stream.showText(String.valueOf(stat));
        stream.newLineAtOffset(-x, -y);
    }
}
