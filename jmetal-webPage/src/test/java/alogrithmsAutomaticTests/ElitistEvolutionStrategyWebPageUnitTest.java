package alogrithmsAutomaticTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.mutation.CDGMutation;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.singleobjective.Rosenbrock;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;

import jmetal.algorithms.ElitistEvolutionStrategyWebPage;

public class ElitistEvolutionStrategyWebPageUnitTest {

	@Test
	public void ElitistEvolutionStrategyWebPageUnitTest() {
		DoubleProblem problem;
		MutationOperator<DoubleSolution> mutation;

		problem = new Rosenbrock(64);

		mutation = new CDGMutation();

		Algorithm<DoubleSolution> algorithm;
		algorithm = new ElitistEvolutionStrategyWebPage<DoubleSolution>(null, null, problem, 1, 10, 250, mutation);

		new AlgorithmRunner.Executor(algorithm).execute();

		DoubleSolution solution = algorithm.getResult();

		assertEquals(true, (solution != null));
		System.out.println("Passed");
	}
}
