package controllers;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GenerationalGeneticAlgorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.SteadyStateGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.singleobjective.OneMax;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

public class ExperiementCreateController  {
	
	
	private String algorithm;
	private String problem;
	private int maxEvaluations;
	private int populationSize;
	private int specificProblemValue;
	private String crossoverOperator;
	private String mutationOperator;
	private String selectionOperator;
	
	public ExperiementCreateController(String algorithm, String problem, String crossoverOperator, 
			String mutationOperator, String selectionOperator, 
			int maxEvaluations, int populationSize, int specificProblemValue) {
		super();		
		
		this.problem = problem;
		this.algorithm = algorithm;
		this.maxEvaluations = maxEvaluations;
		this.populationSize = populationSize;
		this.specificProblemValue = specificProblemValue;
		this.crossoverOperator = crossoverOperator;
		this.mutationOperator = mutationOperator;
		this.selectionOperator = selectionOperator;
		
	}
	
	public String createExperimentAndRun() {
		String result = "0";
		switch (this.problem) {
		case "OneMax":
			BinaryProblem problem = new OneMax(this.specificProblemValue);
			CrossoverOperator<BinarySolution> crossoverOperator =  null;
			MutationOperator<BinarySolution> mutationOperator =  null;
			SelectionOperator<List<BinarySolution>, BinarySolution> selectionOperator =  null;
			Algorithm<BinarySolution> algorithm;
			
			switch (this.crossoverOperator) {
			case "SinglePointCrossover":
				crossoverOperator = new SinglePointCrossover(0.9) ;	
				break;
			default:
				break;
			}
			
			switch (this.mutationOperator) {
			case "BitFlipMutation":
				mutationOperator = new BitFlipMutation(1.0 / problem.getNumberOfBits(0)) ;
				break;

			default:
				break;
			}
			
			switch (this.selectionOperator) {
			case "BinaryTournamentSelection":
			    selectionOperator = new BinaryTournamentSelection<BinarySolution>();

				break;

			default:
				break;
			}
			
			switch (this.algorithm) {
			case "STEADY_STATE":
				algorithm = new SteadyStateGeneticAlgorithm<BinarySolution>(problem, maxEvaluations, populationSize,
				          crossoverOperator, mutationOperator, selectionOperator);
				
				new AlgorithmRunner.Executor(algorithm).execute() ;

			    BinarySolution solutionS = algorithm.getResult() ;
			   
//			    assertEquals(specificProblemValue, -1 * (int)solutionS.getObjective(0)) ;
			    result = solutionS.toString();
			    
				break;
				
			case "GENERATIONAL":
				algorithm = new GenerationalGeneticAlgorithm<BinarySolution>(problem, maxEvaluations, populationSize,
				          crossoverOperator, mutationOperator, selectionOperator, new SequentialSolutionListEvaluator<BinarySolution>());
				
				new AlgorithmRunner.Executor(algorithm).execute() ;

			    BinarySolution solutionG = algorithm.getResult() ;
			   
//			    assertEquals(specificProblemValue, -1 * (int)solutionG.getObjective(0)) ;
			    result = solutionG.toString();
			    
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}
		return result;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public int getMaxEvaluations() {
		return maxEvaluations;
	}

	public void setMaxEvaluations(int maxEvaluations) {
		this.maxEvaluations = maxEvaluations;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public String getCrossoverOperator() {
		return crossoverOperator;
	}

	public void setCrossoverOperator(String crossoverOperator) {
		this.crossoverOperator = crossoverOperator;
	}

	public String getMutationOperator() {
		return mutationOperator;
	}

	public void setMutationOperator(String mutationOperator) {
		this.mutationOperator = mutationOperator;
	}

	public String getSelectionOperator() {
		return selectionOperator;
	}

	public void setSelectionOperator(String selectionOperator) {
		this.selectionOperator = selectionOperator;
	}


	
	
}
