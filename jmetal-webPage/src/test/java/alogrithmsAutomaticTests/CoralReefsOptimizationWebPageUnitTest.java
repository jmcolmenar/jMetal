package alogrithmsAutomaticTests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.BLXAlphaCrossover;
import org.uma.jmetal.operator.impl.mutation.CDGMutation;
import org.uma.jmetal.operator.impl.selection.BestSolutionSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.Rosenbrock;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.comparator.ObjectiveComparator;

import jmetal.algorithms.CoralReefsOptimizationWebPage;

public class CoralReefsOptimizationWebPageUnitTest {

	@Test
	public void CoralReefsOptimizationWebPageUnitTest() {
		Problem<DoubleSolution> problem;
	    CrossoverOperator<DoubleSolution> crossover = new BLXAlphaCrossover(1);
	    MutationOperator<DoubleSolution> mutation;
	    
	    problem = new Rosenbrock(64);

	    mutation = new CDGMutation();
	    SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
	    selection = new BestSolutionSelection<DoubleSolution>(new ObjectiveComparator<DoubleSolution>(0));

	    

	     Algorithm<List<DoubleSolution>> algorithm;
	     algorithm = new CoralReefsOptimizationWebPage<DoubleSolution>(null, null, problem, 100, new ObjectiveComparator<DoubleSolution>(0), selection, crossover, mutation, 5, 7, 1, 0.2, 0.5, 0.3, 1);
	     
	     new AlgorithmRunner.Executor(algorithm)
		            .execute() ;
	
	     List<DoubleSolution> solution = algorithm.getResult() ;
	    
	    assertEquals(true, (solution.get(0) != null));	 
	    System.out.println("Passed");
	}
}
