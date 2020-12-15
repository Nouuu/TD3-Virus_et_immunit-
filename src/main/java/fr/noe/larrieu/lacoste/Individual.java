package fr.noe.larrieu.lacoste;

import java.util.Random;

public class Individual {
    private final String genCode;
    private boolean infected;
    private Integer infectedSince;

    public Individual(String genCode) {
        this.genCode = genCode;
        infected = false;
        infectedSince = 0;
    }

    public Individual(String genCode, boolean infected) {
        this.genCode = genCode;
        this.infected = infected;
        infectedSince = 0;
    }

    public String getGenCode() {
        return genCode;
    }

    public boolean isInfected() {
        return infected;
    }

    public Integer getInfectedSince() {
        return infectedSince;
    }

    public void incInfectedStep() {
        infectedSince++;
    }

    public static Integer compareGen(String code1, String code2) {
        Integer common = 0;
        for (int i = 0; i < 8; i++) {
            if (code1.charAt(i) == code2.charAt(i)) {
                common++;
            }
        }
        return common;
    }

    public static Double immunityPercentage(Integer commonGen) {
        return commonGen.doubleValue() / 8. * 100;
    }

    public boolean tryInfect(String immunityCode) {
        Integer roll = App.randomBetween(0, 99);
        if (roll > immunityPercentage(compareGen(immunityCode, getGenCode()))) {
            infected = true;
        }
        return infected;
    }

    public static String genRandomCode() {
        StringBuilder genCode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            genCode.append(random.nextInt(2));
        }

        return genCode.toString();
    }

    public static Individual getChildSimple(Individual i1, Individual i2) {
        String newGen = i1.genCode.substring(0, 4) +
                i2.genCode.substring(4, 8);
        newGen = mutateGenCode(newGen);
        return new Individual(newGen);
    }

    public static Individual getChildDouble(Individual i1, Individual i2) {
        String newGen = i1.genCode.substring(0, 2) +
                i2.genCode.substring(2, 6) +
                i1.genCode.substring(6, 8);
        newGen = mutateGenCode(newGen);
        return new Individual(newGen);
    }

    public static String mutateGenCode(String genCode) {
        int index = App.randomBetween(0, 7);
        StringBuilder newString = new StringBuilder(genCode);
        if (newString.charAt(index) == '1') {
            newString.setCharAt(index, '0');
        } else {
            newString.setCharAt(index, '1');
        }
        return newString.toString();
    }
}
