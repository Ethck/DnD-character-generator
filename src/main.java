import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class main {
    public static void main (String[] args) throws IOException {
        playerCharacter PC = new playerCharacter();
        File blankCharacterSheet = new File("src/_blank.pdf");
        PDDocument doc = PDDocument.load(blankCharacterSheet);
        PDPage titlePage = doc.getPage(0);
        PDPageContentStream titleStream = new PDPageContentStream(doc,
                titlePage, PDPageContentStream.AppendMode.APPEND, true);

        titleStream.beginText();

        // Character name
        titleStream.setFont(PDType1Font.TIMES_ROMAN, 18);
        addText(titleStream, 70, 715, PC.name);
        System.out.println("Creating " + PC.name + " now!");

        titleStream.setFont(PDType1Font.TIMES_ROMAN, 12);

        // Write all the stats
        int singleStatOffset = 4;
        addNumTextWithOffset(titleStream, 50, 595, PC.strength, singleStatOffset);
        addNumTextWithOffset(titleStream, 50, 523, PC.dexterity, singleStatOffset);
        addNumTextWithOffset(titleStream, 50, 451, PC.constitution, singleStatOffset);
        addNumTextWithOffset(titleStream, 50, 379, PC.intelligence, singleStatOffset);
        addNumTextWithOffset(titleStream, 50,308, PC.wisdom, singleStatOffset);
        addNumTextWithOffset(titleStream, 50, 237, PC.charisma, singleStatOffset);

        // Write all the modifiers
        titleStream.setFont(PDType1Font.TIMES_ROMAN, 18);
        singleStatOffset = 2;
        addNumTextWithOffset(titleStream, 50, 618, PC.strMod, singleStatOffset);
        addNumTextWithOffset(titleStream, 50, 546, PC.dexMod, singleStatOffset);
        addNumTextWithOffset(titleStream, 50, 474, PC.conMod, singleStatOffset);
        addNumTextWithOffset(titleStream, 50, 402, PC.intMod, singleStatOffset);
        addNumTextWithOffset(titleStream, 50, 331, PC.wisMod, singleStatOffset);
        addNumTextWithOffset(titleStream, 50, 262, PC.chaMod, singleStatOffset);

        // Class & Level
        titleStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        addText(titleStream, 270, 730, PC.playerClass + "  1");

        // Race
        addText(titleStream, 270, 704, PC.race);

        // Background
        addText(titleStream, 383, 730, PC.background);
        titleStream.endText();
        titleStream.close();



        doc.save("generatedCharacters/" + PC.name + ".pdf");
        doc.close();
    }

    // pre: No values are null
    // post: Adds text to page. Resets coordinates back to 0,0.
    private static void addText(PDPageContentStream stream, int x, int y,
                                            String text) throws IOException {
        stream.newLineAtOffset(x,y);
        stream.showText(text);
        stream.newLineAtOffset(-x, -y);
    }

    // pre: No values are null.
    // post: Adds text to the page, if stat is one digit, it is shifted by offset.
    private static void addNumTextWithOffset(PDPageContentStream stream, int x,
                                            int y, int stat, int offset)
                                            throws IOException {
        String response = "";
        if (stat < 10 && stat >=0) {
            x += offset;
        } else if (stat < 0){
            x -= offset;
        }
        response += String.valueOf(stat);
        stream.newLineAtOffset(x,y);
        stream.showText(response);
        stream.newLineAtOffset(-x, -y);
    }
}
