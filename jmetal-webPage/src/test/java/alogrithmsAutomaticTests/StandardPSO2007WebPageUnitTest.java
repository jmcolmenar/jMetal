package alogrithmsAutomaticTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.singleobjective.Rosenbrock;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import jmetal.algorithms.StandardPSO2007WebPage;

public class StandardPSO2007WebPageUnitTest {

	@Test
	public void StandardPSO2007WebPageUnitTest() {
		DoubleProblem problem;

		problem = new Rosenbrock(64);

		Algorithm<DoubleSolution> algorithm;
		algorithm = new StandardPSO2007WebPage(null, null, problem, 
				10, 150,7, new SequentialSolutionListEvaluator<DoubleSolution>());
		
		new AlgorithmRunner.Executor(algorithm).execute();

		DoubleSolution solution = algorithm.getResult();

		assertEquals(true, (solution != null));
		System.out.println("Passed");
	}
}
