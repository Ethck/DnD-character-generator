import java.util.Arrays;
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
    String background, playerClass, alignment, playerName, race, personality, feats, bonds, flaws;
    int AC, initiative, speed, HP, hitDice, level, inspiration, proficiency, perception;
    private Random rand = new Random();

    public playerCharacter(){
        this.name = genName();
        this.strength = genStat();
        this.dexterity = genStat();
        this.constitution = genStat();
        this.intelligence = genStat();
        this.wisdom = genStat();
        this.charisma = genStat();
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

    private String genName() {
        return "Aeneid";
    }
}
