package jmetal.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.uma.jmetal.algorithm.impl.AbstractGeneticAlgorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.SteadyStateGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.ObjectiveComparator;

import jmetal.javaclass.Experiment;
import jmetal.javaclass.Result;
import jmetal.javaropository.ResultRepository;

/**
 * 
 * @author Harender
 */

@SuppressWarnings("serial")
public class SteadyStateGeneticAlgorithmWebFormat<S extends Solution<?>> extends SteadyStateGeneticAlgorithm<S> {
	@Autowired
	private ResultRepository resultRepository;
	private Experiment experiment;

	private int maxEvaluations;
	private int evaluations;

	/**
	 * Constructor
	 */
	public SteadyStateGeneticAlgorithmWebFormat(ResultRepository resultRepository,Experiment experimentName,
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
		Result resultSave = new Result((getResult().getObjective(0)*-1), experiment);
		resultRepository.save(resultSave);
	}
}
