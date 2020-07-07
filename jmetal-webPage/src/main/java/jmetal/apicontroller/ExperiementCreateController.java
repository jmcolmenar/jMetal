package jmetal.apicontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.uma.jmetal.algorithm.Algorithm;
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

import jmetal.algorithms.GenerationalGeneticAlgorithm;
import jmetal.algorithms.SteadyStateGeneticAlgorithm;
import jmetal.javaclass.Experiment;
import jmetal.javaclass.Result;
import jmetal.javaropository.ExperimentRepository;
import jmetal.javaropository.ResultRepository;



public class ExperiementCreateController  {
	
	
	private String algorithm;
	private String problem;
	private int maxEvaluations;
	private int populationSize;
	private int specificProblemValue;
	private String crossoverOperator;
	private String mutationOperator;
	private String selectionOperator;
	
	private Experiment experiment; 
	@Autowired
	private ExperimentRepository experimentRepository; 
	@Autowired
	private ResultRepository resultRepository; 
	
	public ExperiementCreateController() {
		
	}
	
	public ExperiementCreateController(Experiment experiment,ExperimentRepository experimentRepository, ResultRepository resultRepository,String algorithm, String problem, String crossoverOperator, 
			String mutationOperator, String selectionOperator, 
			int maxEvaluations, int populationSize, int specificProblemValue) {
		super();		
		this.experiment = experiment;
		this.experimentRepository = experimentRepository;
		this.resultRepository = resultRepository;
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
				algorithm = new SteadyStateGeneticAlgorithm<BinarySolution>(resultRepository,experiment,problem, maxEvaluations, populationSize,
				          crossoverOperator, mutationOperator, selectionOperator);
				
				new AlgorithmRunner.Executor(algorithm).execute() ;

				result = algorithm.getResult().toString() ;
			    
				break;
				
			case "GENERATIONAL":				
				algorithm = new GenerationalGeneticAlgorithm<BinarySolution>(resultRepository,experiment,problem, maxEvaluations, populationSize,
				          crossoverOperator, mutationOperator, selectionOperator, new SequentialSolutionListEvaluator<BinarySolution>());
								
				new AlgorithmRunner.Executor(algorithm).execute() ;

			    result = algorithm.getResult().toString() ;
			    
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}
		
		String[] arrayResult = result.split(" ");
		int iterator = arrayResult.length - 1;

		while (!arrayResult[iterator].startsWith("-")) {
			iterator--;
		}
		Double resultValue = Double.parseDouble(arrayResult[iterator]);
		Result resultSave = new Result((resultValue*-1), experiment);
		resultRepository.save(resultSave);
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
