package fr.noe.larrieu.lacoste;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Ca foncitonne !!! mais pas eu le temps d'optimiser donc des fois ça peut mouliner treeees longtemps (surtout avec la mutation
 */

public class App {
    static Random random = new Random();

    public static void main(String[] args) {
        String immunisedCode = "00000000";
        Integer maxIndividuals = 1000;
        Integer initialIndividualCount = 20;
        Integer stepsBeforeDead = 2;
        Double selectionPercentage = 25.;
        List<Individual> individuals = genInitialIndividuals(initialIndividualCount);
        List<Individual> toRemove = new ArrayList<>();
        ISelectionMethod selectionMethod = new SelectionMethod_Rank();
        int infected = 1;
        int step = 0;
        infectRandom(individuals, immunisedCode);
        selectionMethod.GetBestIndividus(individuals, selectionPercentage, immunisedCode);

        while (!individuals.isEmpty() && infected > 0) {
            algoGen(individuals, immunisedCode, selectionPercentage, maxIndividuals, selectionMethod);

            toRemove.clear();
            for (Individual individual : individuals) {
                if (!individual.isInfected()) {
                    if (individual.tryInfect(immunisedCode)) {
                        infected++;
                    }
                } else if (individual.getInfectedSince() < stepsBeforeDead) {
                    individual.incInfectedStep();
                } else {
                    toRemove.add(individual);
                    infected--;
                }
            }
            for (Individual individual : toRemove) {
                individuals.remove(individual);
            }
            step++;
        }

        if (individuals.size() == 0) {
            System.out.println("Tout le monde est mort :'(");
        } else {
            System.out.println("Virus éradiqué, survivant(s) : " + individuals.size());
        }
        System.out.println("Nombre de tours : " + step);
    }


    public static List<Individual> genInitialIndividuals(Integer initialCount) {
        List<Individual> individuals = new ArrayList<>();
        for (int i = 0; i < initialCount; i++) {
            individuals.add(new Individual(Individual.genRandomCode()));
        }
        return individuals;
    }

    public static Integer randomBetween(Integer min, Integer max) {
        return random.nextInt(max + 1 - min) + min;
    }

    public static void infectRandom(List<Individual> individuals, String immunityCode) {
        int index = randomBetween(0, individuals.size() - 1);
        while (!individuals.get(index).tryInfect(immunityCode)) {
            index = randomBetween(0, individuals.size() - 1);
        }
    }

    public static void algoGen(List<Individual> individuals, String immunityCode, Double selectionPercentage,
                               Integer maxIndividuals, ISelectionMethod selectionMethod) {

        List<Individual> selectedIndividuals = selectionMethod.GetBestIndividus(individuals, selectionPercentage, immunityCode);

        for (int i = 0; i < selectedIndividuals.size(); i += 2) {
            if (individuals.size() >= maxIndividuals) {
                break;
            }
            if (i < selectedIndividuals.size() - 1) {
                individuals.add(Individual.getChildDouble(selectedIndividuals.get(i), selectedIndividuals.get(i + 1)));
            }
        }
    }

}
