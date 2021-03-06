import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class GenerateCharacter {
    public static void main (String[] args) throws IOException {
        PlayerCharacter PC = new PlayerCharacter();
        File blankCharacterSheet = new File("src/_blank.pdf");
        PDDocument doc = PDDocument.load(blankCharacterSheet);
        PDPage titlePage = doc.getPage(0);
        PDPageContentStream titleStream = new PDPageContentStream(doc,
                titlePage, PDPageContentStream.AppendMode.APPEND, true);

        titleStream.beginText();

        // Class & Level
        titleStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        addText(titleStream, 270, 730, PC.playerClass + "  " + PC.level);

        // Race
        addText(titleStream, 270, 704, PC.race);



        // Background
        addText(titleStream, 383, 730, PC.background);

        // Character name
        titleStream.setFont(PDType1Font.TIMES_ROMAN, 18);
        addText(titleStream, 70, 715, PC.name);
        System.out.println("Creating " + PC.name + " the " + PC.background +
                " " + PC.race + " " +PC.playerClass + " now!");

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

        // Add other large numbers
        addText(titleStream, 300, 630, "" + PC.initiative);
        addText(titleStream, 103, 609, "" + PC.proficiency);

        // Back to small text
        titleStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        addText(titleStream, 260, 465, "" + PC.hitDice);

        // The personality box allows for 32 character long lines.
        addTextWithWordWrap(titleStream, 415, 645, 32, PC.personality);
        addTextWithWordWrap(titleStream, 415, 575, 32, PC.ideals);
        addTextWithWordWrap(titleStream, 415, 520, 32, PC.bonds);
        addTextWithWordWrap(titleStream, 415, 465, 32, PC.flaws);

        titleStream.endText();
        titleStream.close();

        File characterFolder = new File("generatedCharacters/");
        if (!characterFolder.exists()) {
            characterFolder.mkdir();
        }

        doc.save(characterFolder.getName() + "/" + PC.name + ".pdf");
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

    private static void addTextWithWordWrap(PDPageContentStream stream, int x, int y, int maxLength, String text)
                                            throws IOException {
        String temp = "";
        while (text.length() > 0) {
            if (text.length() > maxLength) {
                temp = text.substring(0, maxLength);
                text = text.substring(temp.length());
            } else {
                temp = text;
                text = "";
            }

            stream.newLineAtOffset(x,y);
            stream.showText(temp);
            stream.newLineAtOffset(-x,-y);
            y -= 12;

        }
    }
}
