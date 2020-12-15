package fr.noe.larrieu.lacoste;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SelectionMethod_Rank implements ISelectionMethod {

    @Override
    public List<Individual> GetBestIndividus(List<Individual> individuals, Double percentage, String ImmunityCode) {
        List<Individual> selected = new ArrayList<>();
        List<Individual> sorted = individuals.stream()
                .sorted(Comparator.comparingDouble(i -> Individual.immunityPercentage(Individual.compareGen(i.getGenCode(), ImmunityCode))))
                .collect(Collectors.toList());

/*
        individuals.stream()
                .sorted(Comparator.comparingDouble(i -> Individual.immunityPercentage(Individual.compareGen(i.getGenCode(), ImmunityCode))))
                .collect(Collectors.toList())
                .forEach(i -> System.out.println(i.getGenCode()+": "+Individual.immunityPercentage(Individual.compareGen(i.getGenCode(), ImmunityCode))));

*/

        double selectCountDouble = individuals.size() * percentage / 100;
        for (int i = 0; i < (int) selectCountDouble; i++) {
            selected.add(sorted.get(sorted.size() - 1 - i));
        }

        return selected;
    }
}
