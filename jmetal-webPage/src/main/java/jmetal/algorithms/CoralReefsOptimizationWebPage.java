package jmetal.algorithms;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.uma.jmetal.algorithm.singleobjective.coralreefsoptimization.CoralReefsOptimization;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
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
public class CoralReefsOptimizationWebPage <S extends Solution<?>> extends CoralReefsOptimization<S> {

	@Autowired
	private ResultRepository resultRepository;
	private WebPageExperiment experiment;

	private int maxEvaluations;
	private int evaluations;

	/**
	 * Constructor
	 */
	public CoralReefsOptimizationWebPage(ResultRepository resultRepository,WebPageExperiment experimentName,
			Problem<S> problem, int maxEvaluations, Comparator<S> comparator,
			SelectionOperator<List<S>, S> selectionOperator, CrossoverOperator<S> crossoverOperator,
			MutationOperator<S> mutationOperator, int n, int m, double rho, double fbs, double fa, double pd,
			int attemptsToSettle) {
		super(problem, maxEvaluations, comparator, selectionOperator, crossoverOperator, mutationOperator, n, m, rho, fbs, fa,
				pd, attemptsToSettle);
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
		Result resultSave = new Result((getResult().get(0).getObjective(0)), experiment);
		resultRepository.save(resultSave);
	}
}
