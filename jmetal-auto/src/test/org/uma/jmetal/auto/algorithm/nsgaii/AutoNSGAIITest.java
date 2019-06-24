package org.uma.jmetal.auto.algorithm.nsgaii;

import org.junit.Test;
import org.uma.jmetal.auto.algorithm.EvolutionaryAlgorithm;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.List;

import static org.junit.Assert.*;

public class AutoNSGAIITest {
  @Test
  public void shouldNSGAIIWithStandardSettingsSolveTheZDT1() {
    String[] parameters =
        ("--problemName org.uma.jmetal.problem.multiobjective.zdt.ZDT1 "
                + "--referenceFrontFileName ZDT1.pf "
                + "--maximumNumberOfEvaluations 25000 "
                + "--algorithmResult population "
                + "--populationSize 100 "
                + "--offspringPopulationSize 100 "
                + "--createInitialSolutions random "
                + "--variation crossoverAndMutationVariation "
                + "--selection tournament "
                + "--selectionTournamentSize 2 "
                + "--crossover SBX "
                + "--crossoverProbability 0.9 "
                + "--crossoverRepairStrategy bounds "
                + "--sbxDistributionIndex 20.0 "
                + "--mutation polynomial "
                + "--mutationProbability 0.01 "
                + "--mutationRepairStrategy bounds "
                + "--polynomialMutationDistributionIndex 20.0 ")
            .split("\\s+");
    AutoNSGAII autoNSGAII = new AutoNSGAII();
    autoNSGAII.parseParameters(parameters);

    EvolutionaryAlgorithm<DoubleSolution> nsgaII = autoNSGAII.create();
    nsgaII.run();

    List<DoubleSolution> result = nsgaII.getResult() ;
    assertEquals(100, result.size());
  }
}
