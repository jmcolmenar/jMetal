package jmetal.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GenerationalGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

import jmetal.javaclass.WebPageExperiment;
import jmetal.javaclass.Result;
import jmetal.javarepository.ResultRepository;

import java.util.List;

/**
 * 
 * @author Harender
 */

@SuppressWarnings("serial")
public class GenerationalGeneticAlgorithmWebFormet<S extends Solution<?>> extends GenerationalGeneticAlgorithm<S> {
	@Autowired
	private ResultRepository resultRepository;
	private WebPageExperiment experiment;
	
	private int maxEvaluations;
	private int evaluations;
	

	/**
	 * Constructor
	 */
	public GenerationalGeneticAlgorithmWebFormet(ResultRepository resultRepository,WebPageExperiment experimentName,
			Problem<S> problem, int maxEvaluations, int populationSize,
			CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
			SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator) {
		super(problem,maxEvaluations,populationSize,crossoverOperator,mutationOperator,selectionOperator,evaluator);
		this.resultRepository=resultRepository;
		this.experiment = experimentName;
		this.maxEvaluations = maxEvaluations;

	}
	
	@Override 
	protected boolean isStoppingConditionReached() {
	    return (evaluations >= maxEvaluations);
	  }
	
	@Override 
	public void updateProgress() {
		rightResult();
	    evaluations += getMaxPopulationSize();
	  }
	
	public void rightResult() {
		double roundedResult = Math.round((getResult().getObjective(0)) * 100.0) / 100.0;
		Result resultSave = new Result(roundedResult*-1, experiment);
		resultRepository.save(resultSave);
	}
}
