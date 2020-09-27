package alogrithmsAutomaticTests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.OneMax;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.AlgorithmRunner;

import jmetal.algorithms.SteadyStateGeneticAlgorithmWebFormat;

public class SteadyStateGeneticAlgorithmWebFormatUnitTest {

	@Test
	public void SteadyStateGeneticAlgorithmWebFormatUnitTest() {
		Problem<BinarySolution> problem;
		problem = new OneMax(64);
		CrossoverOperator<BinarySolution> crossover = new SinglePointCrossover(0.9);

		MutationOperator<BinarySolution> mutation;
		mutation = new BitFlipMutation(1);

		SelectionOperator<List<BinarySolution>, BinarySolution> selection;
		selection = new BinaryTournamentSelection<>();

		Algorithm<BinarySolution> algorithm;
		algorithm = new SteadyStateGeneticAlgorithmWebFormat<BinarySolution>(null, null, problem, 2500, 100, crossover,
				mutation, selection);

		new AlgorithmRunner.Executor(algorithm).execute();

		BinarySolution solution = algorithm.getResult();

		assertEquals(true, (solution != null));
		System.out.println("Passed");
	}
}
