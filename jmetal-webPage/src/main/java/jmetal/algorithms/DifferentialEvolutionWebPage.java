package jmetal.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.uma.jmetal.algorithm.singleobjective.differentialevolution.DifferentialEvolution;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

import jmetal.javaclass.Result;
import jmetal.javaclass.WebPageExperiment;
import jmetal.javarepository.ResultRepository;

/**
 * 
 * @author Harender
 *
 */

@SuppressWarnings("serial")
public class DifferentialEvolutionWebPage extends DifferentialEvolution{

	@Autowired
	private ResultRepository resultRepository;
	private WebPageExperiment experiment;

	private int maxEvaluations;
	private int evaluations;

	/**
	 * Constructor
	 */
	public DifferentialEvolutionWebPage(ResultRepository resultRepository,WebPageExperiment experimentName,
			DoubleProblem problem, int maxEvaluations, int populationSize,
			DifferentialEvolutionCrossover crossoverOperator, DifferentialEvolutionSelection selectionOperator,
			SolutionListEvaluator<DoubleSolution> evaluator) {
		super(problem, maxEvaluations, populationSize, crossoverOperator, selectionOperator, evaluator);
		this.resultRepository=resultRepository;
		this.experiment = experimentName;
		this.maxEvaluations = maxEvaluations;
	}
	
	public DifferentialEvolutionWebPage(
			DoubleProblem problem, int maxEvaluations, int populationSize,
			DifferentialEvolutionCrossover crossoverOperator, DifferentialEvolutionSelection selectionOperator,
			SolutionListEvaluator<DoubleSolution> evaluator) {
		super(problem, maxEvaluations, populationSize, crossoverOperator, selectionOperator, evaluator);
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
		Result resultSave = new Result((getResult().getObjective(0)), experiment);
		resultRepository.save(resultSave);
	}
}
