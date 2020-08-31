package jmetal.algorithms;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.evolutionstrategy.CovarianceMatrixAdaptationEvolutionStrategy;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;

import jmetal.javaclass.Result;
import jmetal.javaclass.WebPageExperiment;
import jmetal.javarepository.ResultRepository;

/**
 * 
 * @author Harender
 *
 */

@SuppressWarnings("serial")
public class CovarianceMatrixAdaptationEvolutionStrategyWebPage /*extends CovarianceMatrixAdaptationEvolutionStrategy*/{

	@Autowired
	private ResultRepository resultRepository;
	private WebPageExperiment experiment;

	private int maxEvaluations;
	private int evaluations;
	private int lambda;

	/**
	 * Constructor
	 *//*
	public CovarianceMatrixAdaptationEvolutionStrategyWebPage(ResultRepository resultRepository,WebPageExperiment experimentName,
			Builder builder) {
		super(builder);
		this.resultRepository=resultRepository;
		this.experiment = experimentName;
	}

	@Override
	protected boolean isStoppingConditionReached() {
		return (evaluations >= maxEvaluations);
	}

	@Override
	public void updateProgress() {
		rightResult();
		evaluations += lambda;
	    updateInternalParameters();

	}

	public void rightResult() {
		Result resultSave = new Result((getResult().getObjective(0)*-1), experiment);
		resultRepository.save(resultSave);
	}*/
}
