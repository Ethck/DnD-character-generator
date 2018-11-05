import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class playerCharacter {
    String name;
    int strength;
    int dexterity;
    int constitution;
    int intelligence;
    int wisdom;
    int charisma;
    int strMod, dexMod, conMod, intMod, wisMod, chaMod;
    String background, playerClass, alignment, playerName, race, personality, feats, bonds, flaws;
    int AC, initiative, speed, HP, hitDice, level, inspiration, proficiency, perception;
    private Random rand = new Random();

    public playerCharacter() throws IOException{
        this.strength = genStat();
        this.dexterity = genStat();
        this.constitution = genStat();
        this.intelligence = genStat();
        this.wisdom = genStat();
        this.charisma = genStat();

        this.strMod = genMod(this.strength);
        this.dexMod = genMod(this.dexterity);
        this.conMod = genMod(this.constitution);
        this.intMod = genMod(this.intelligence);
        this.wisMod = genMod(this.wisdom);
        this.chaMod = genMod(this.charisma);

        this.playerClass = genClass();
        this.race = genRace();
        this.name = genName();
        this.background = genBackground();
    }

    private int genStat() {
        int[] total = new int[4];
        for (int i = 0; i < 4; i++) {
            total[i] += rand.nextInt((6 - 1) + 1) + 1;
        }

        // Sort, set lowest number to 0.
        Arrays.sort(total);
        total[Arrays.binarySearch(total, total[0])] = 0;

        // return total of all elements in total
        return IntStream.of(total).sum();
    }

    private int genMod(int statVal) {
        return (int) Math.floor((statVal - 10) / 2);
    }

    private String genClass() {
        List<String> classes = new ArrayList<String>(Arrays.asList(
                "Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk",
                "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard"));
        return classes.get(rand.nextInt(classes.size()));
    }

    private String genRace() {
        List<String> races = new ArrayList<String>(Arrays.asList(
                "Dragonborn", "Dwarf", "Elf", "Gnome", "Half-Elf",
                "Half-Orc", "Halfling", "Human", "Tiefling"));
        return races.get(rand.nextInt(races.size()));
    }

    private String genBackground() {
        List<String> backgrounds = new ArrayList<String>(Arrays.asList(
                "Acolyte", "Anthropologist", "Archaeologist", "Charlatan",
                "CityWatch", "Clan Crafter", "Cloistered Scholar", "Courtier",
                "Criminal", "Entertainer", "Faction Agent", "Far Traveler",
                "Folk Hero", "Gladiator", "Guild Artisan", "Guild Merchant",
                "Haunted One", "Hermit", "Inheritor", "Investigator", "Knight",
                "Knight of the Order", "Mercenary Veteran", "Noble",
                "Outlander", "Pirate", "Sage", "Sailor", "Soldier", "Spy",
                "Urban Bounty Hunter", "Urchin", "Uthgardt Tribe Member",
                "Waterdhavian Noble"));
        return backgrounds.get(rand.nextInt(backgrounds.size()));
    }

    private String genName() throws IOException {
       File[] data = new File("data/").listFiles();

       if (data != null) {
           for (File s : data) {
               // If filename before Names.* is the race name, read it.
               if (s.getName().replaceAll("Names.*", "")
                       .equalsIgnoreCase(this.race)){
                   List<String> lines = Files.readAllLines(
                           Paths.get("data/" + this.race.toLowerCase()
                                   + "Names.txt"));
                   return lines.get(rand.nextInt(lines.size()));
               }
           }
       }

       return "Something's not quite right...";

    }
}
