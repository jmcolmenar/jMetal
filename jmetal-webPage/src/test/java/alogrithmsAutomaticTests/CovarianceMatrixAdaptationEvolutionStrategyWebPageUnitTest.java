package alogrithmsAutomaticTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.singleobjective.Rosenbrock;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;

import jmetal.algorithms.CovarianceMatrixAdaptationEvolutionStrategyWebPage;
import jmetal.algorithms.CovarianceMatrixAdaptationEvolutionStrategyWebPage.Builder;

public class CovarianceMatrixAdaptationEvolutionStrategyWebPageUnitTest {

	@Test
	public void CovarianceMatrixAdaptationEvolutionStrategyWebPageUnitTest() {
		DoubleProblem problem;

		problem = new Rosenbrock(64);

		Algorithm<DoubleSolution> algorithm;
		Builder builder = new Builder(problem);
		algorithm = new CovarianceMatrixAdaptationEvolutionStrategyWebPage(null, null, builder);


		new AlgorithmRunner.Executor(algorithm).execute();

		DoubleSolution solution = algorithm.getResult();

		assertEquals(true, (solution != null));

	    System.out.println("Passed");
	}
}
