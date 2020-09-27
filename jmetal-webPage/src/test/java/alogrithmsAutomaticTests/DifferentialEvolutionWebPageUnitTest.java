package alogrithmsAutomaticTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.mutation.CDGMutation;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.singleobjective.Rosenbrock;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import jmetal.algorithms.DifferentialEvolutionWebPage;

public class DifferentialEvolutionWebPageUnitTest {

	@Test
	public void DifferentialEvolutionWebPageUnitTest() {
		DoubleProblem problem;
		problem = new Rosenbrock(64);
		DifferentialEvolutionCrossover crossover = new DifferentialEvolutionCrossover();

		DifferentialEvolutionSelection selection;
		selection = new DifferentialEvolutionSelection();

		Algorithm<DoubleSolution> algorithm;
		algorithm = new DifferentialEvolutionWebPage(null, null, problem, 1000, 100, crossover, selection,
				new SequentialSolutionListEvaluator<DoubleSolution>());

		new AlgorithmRunner.Executor(algorithm).execute();

		DoubleSolution solution = algorithm.getResult();

		assertEquals(true, (solution != null));
		System.out.println("Passed");
	}
}
