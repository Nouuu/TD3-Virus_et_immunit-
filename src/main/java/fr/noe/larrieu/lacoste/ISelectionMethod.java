package fr.noe.larrieu.lacoste;

import java.util.List;

public interface ISelectionMethod {
    public List<Individual> GetBestIndividus(List<Individual> individuals, Double percentage, String ImmunityCode);
}
