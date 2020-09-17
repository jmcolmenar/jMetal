package jmetal.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.uma.jmetal.algorithm.singleobjective.evolutionstrategy.ElitistEvolutionStrategy;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

import jmetal.javaclass.Result;
import jmetal.javaclass.WebPageExperiment;
import jmetal.javarepository.ResultRepository;

/**
 * 
 * @author Harender
 *
 */

@SuppressWarnings("serial")
public class ElitistEvolutionStrategyWebPage<S extends Solution<?>>  extends ElitistEvolutionStrategy<S>{

	@Autowired
	private ResultRepository resultRepository;
	private WebPageExperiment experiment;

	private int maxEvaluations;
	private int evaluations;
	private int lambda;
	/**
	 * Constructor
	 */
	public ElitistEvolutionStrategyWebPage(ResultRepository resultRepository,WebPageExperiment experimentName,
			Problem<S> problem, int mu, int lambda, int maxEvaluations,	MutationOperator<S> mutation) {
		super(problem, mu, lambda, maxEvaluations, mutation);
		this.resultRepository=resultRepository;
		this.experiment = experimentName;
		this.maxEvaluations = maxEvaluations;
		this.lambda = lambda;
	}
	
	@Override
	protected boolean isStoppingConditionReached() {
		return (evaluations >= maxEvaluations);
	}

	@Override
	public void updateProgress() {
		rightResult();
		evaluations += lambda;

	}

	public void rightResult() {
		double roundedResult = Math.round((getResult().getObjective(0)) * 100.0) / 100.0;
		Result resultSave = new Result(roundedResult, experiment);
		resultRepository.save(resultSave);
	}
}
