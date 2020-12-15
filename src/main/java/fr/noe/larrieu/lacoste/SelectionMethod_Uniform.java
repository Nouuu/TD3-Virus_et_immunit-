package fr.noe.larrieu.lacoste;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelectionMethod_Uniform implements ISelectionMethod {
    @Override
    public List<Individual> GetBestIndividus(List<Individual> individuals, Double percentage, String ImmunityCode) {
        List<Individual> selected = new ArrayList<>();
        double selectCountDouble = individuals.size() * percentage / 100;
        int index;
        while (selected.size() < (int) selectCountDouble) {
            index = App.randomBetween(0, individuals.size() - 1);
            int finalIndex = index;
            if (selected.stream().noneMatch(i -> i == individuals.get(finalIndex))) {
                selected.add(individuals.get(index));
            }
        }
        return selected;
    }
}
