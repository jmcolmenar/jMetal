package jmetal.apicontroller;

import java.text.StringCharacterIterator;
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
import org.w3c.dom.Text;

import jmetal.algorithms.GenerationalGeneticAlgorithmWebFormet;
import jmetal.algorithms.SteadyStateGeneticAlgorithmWebFormat;
import jmetal.javaclass.Experiment;
import jmetal.javaclass.FinalResults;
import jmetal.javaropository.ExperimentRepository;
import jmetal.javaropository.FinalResultsRepository;
import jmetal.javaropository.ResultRepository;


public class ExperiementCreateController  {
	
	
	private String algorithm;
	private String problem;
	private double crossoverValue;
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
	@Autowired
	private FinalResultsRepository finalResultsRepository; 
	
	public ExperiementCreateController() {
		
	}
	
	public ExperiementCreateController(Experiment experiment,ExperimentRepository experimentRepository, ResultRepository resultRepository,
			FinalResultsRepository finalResultsRepository,String algorithm, String problem, String crossoverOperator, 
			double crossoverValue, String mutationOperator, String selectionOperator,
			int maxEvaluations, int populationSize, int specificProblemValue) {
		super();		
		this.experiment = experiment;
		this.experimentRepository = experimentRepository;
		this.resultRepository = resultRepository;
		this.finalResultsRepository = finalResultsRepository;
		this.problem = problem;
		this.algorithm = algorithm;
		this.maxEvaluations = maxEvaluations;
		this.populationSize = populationSize;
		this.specificProblemValue = specificProblemValue;
		this.crossoverOperator = crossoverOperator;
		this.crossoverValue = crossoverValue;
		this.mutationOperator = mutationOperator;
		this.selectionOperator = selectionOperator;
	}
	
	public void createExperimentAndRun() {
		double result = 0;
		String population = null;
		switch (this.problem) {
		case "OneMax":
			BinaryProblem problem = new OneMax(this.specificProblemValue);
			CrossoverOperator<BinarySolution> crossoverOperator =  null;
			MutationOperator<BinarySolution> mutationOperator =  null;
			SelectionOperator<List<BinarySolution>, BinarySolution> selectionOperator =  null;
			Algorithm<BinarySolution> algorithm;
			
			switch (this.crossoverOperator) {
			case "SinglePointCrossover":
				crossoverOperator = new SinglePointCrossover(this.crossoverValue) ;	
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
				algorithm = new SteadyStateGeneticAlgorithmWebFormat<BinarySolution>(resultRepository,experiment,problem, maxEvaluations, populationSize,
				          crossoverOperator, mutationOperator, selectionOperator);
				
				new AlgorithmRunner.Executor(algorithm).execute() ;

				result = algorithm.getResult().getObjective(0);
				population = algorithm.getResult().getVariableValueString(0);
			    
				break;
				
			case "GENERATIONAL":				
				algorithm = new GenerationalGeneticAlgorithmWebFormet<BinarySolution>(resultRepository,experiment,problem, maxEvaluations, populationSize,
				          crossoverOperator, mutationOperator, selectionOperator, new SequentialSolutionListEvaluator<BinarySolution>());
								
				new AlgorithmRunner.Executor(algorithm).execute() ;

			    result = algorithm.getResult().getObjective(0);
			    population = algorithm.getResult().getVariableValueString(0);
			    
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}
		
		
		String finalPopulation="";
	    for (int i = 0; i < population.length()-1; i++) {
			finalPopulation +=(population.charAt(i)/*+","*/);
		}
	    finalPopulation += population.charAt(population.length()-1);
	    
//	    Text textPopulation = null;
//	    textPopulation.setData(finalPopulation);
	    FinalResults finalResult = new FinalResults(experiment, result*-1, finalPopulation);
	    finalResultsRepository.save(finalResult);
	    
	    experiment.setFinalResult(finalResult);
	    experimentRepository.save(experiment);
//		String[] arrayResult = result.split(" ");
//		int iterator = arrayResult.length - 1;
//
//		while (!arrayResult[iterator].startsWith("-")) {
//			iterator--;
//		}
//		Double resultValue = Double.parseDouble(arrayResult[iterator]);
//		Result resultSave = new Result((resultValue*-1), experiment);
//		resultRepository.save(resultSave);
		
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

	public double getCrossoverValue() {
		return crossoverValue;
	}

	public void setCrossoverValue(double crossoverValue) {
		this.crossoverValue = crossoverValue;
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
