package jmetal.algorithms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.SteadyStateGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

import jmetal.javaclass.WebPageExperiment;
import jmetal.javaclass.Result;
import jmetal.javarepository.ResultRepository;

/**
 * 
 * @author Harender
 */

@SuppressWarnings("serial")
public class SteadyStateGeneticAlgorithmWebFormat<S extends Solution<?>> extends SteadyStateGeneticAlgorithm<S> {
	@Autowired
	private ResultRepository resultRepository;
	private WebPageExperiment experiment;

	private int maxEvaluations;
	private int evaluations;

	/**
	 * Constructor
	 */
	public SteadyStateGeneticAlgorithmWebFormat(ResultRepository resultRepository,WebPageExperiment experimentName,
			Problem<S> problem, int maxEvaluations, int populationSize,
			CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
			SelectionOperator<List<S>, S> selectionOperator) {
		super(problem,maxEvaluations,populationSize,crossoverOperator,mutationOperator,selectionOperator);
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
		evaluations++;

	}

	public void rightResult() {
		double roundedResult = Math.round((getResult().getObjective(0)) * 100.0) / 100.0;
		Result resultSave = new Result(roundedResult*-1, experiment);
		resultRepository.save(resultSave);
	}
}
