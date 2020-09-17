package jmetal.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.uma.jmetal.algorithm.singleobjective.particleswarmoptimization.StandardPSO2011;
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
public class StandardPSO2011WebPage extends StandardPSO2011{

	
	@Autowired
	private ResultRepository resultRepository;
	private WebPageExperiment experiment;

	private int iterations;
	private int maxEvaluations;

	/**
	 * Constructor
	 */
	public StandardPSO2011WebPage(ResultRepository resultRepository,WebPageExperiment experimentName,
			DoubleProblem problem, int swarmSize, int maxIterations,
			int numberOfParticlesToInform, SolutionListEvaluator<DoubleSolution> evaluator) {
		super(problem, swarmSize, maxIterations, numberOfParticlesToInform, evaluator);
		this.resultRepository=resultRepository;
		this.experiment = experimentName;
		this.maxEvaluations = maxIterations;
	}
	
	@Override
	public boolean isStoppingConditionReached() {
		return (iterations >= maxEvaluations);
	}

	@Override
	public void updateProgress() {
		rightResult();
		iterations++;

	}

	public void rightResult() {
		double roundedResult = Math.round((getResult().getObjective(0)) * 100.0) / 100.0;
		Result resultSave = new Result(roundedResult, experiment);
		resultRepository.save(resultSave);
	}
}
