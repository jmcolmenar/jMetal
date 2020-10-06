package jmetal.apicontroller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.BLXAlphaCrossover;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.crossover.HUXCrossover;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.crossover.NPointCrossover;
import org.uma.jmetal.operator.impl.crossover.NullCrossover;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.crossover.TwoPointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.mutation.CDGMutation;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.NonUniformMutation;
import org.uma.jmetal.operator.impl.mutation.NullMutation;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.SimpleRandomMutation;
import org.uma.jmetal.operator.impl.mutation.UniformMutation;
import org.uma.jmetal.operator.impl.selection.BestSolutionSelection;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.operator.impl.selection.NaryRandomSelection;
import org.uma.jmetal.operator.impl.selection.NaryTournamentSelection;
import org.uma.jmetal.operator.impl.selection.RandomSelection;
import org.uma.jmetal.operator.impl.selection.RankingAndCrowdingSelection;
import org.uma.jmetal.operator.impl.selection.RankingAndPreferenceSelection;
import org.uma.jmetal.operator.impl.selection.SpatialSpreadDeviationSelection;
import org.uma.jmetal.operator.impl.selection.TournamentSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.Griewank;
import org.uma.jmetal.problem.singleobjective.NIntegerMin;
import org.uma.jmetal.problem.singleobjective.OneMax;
import org.uma.jmetal.problem.singleobjective.Rastrigin;
import org.uma.jmetal.problem.singleobjective.Rosenbrock;
import org.uma.jmetal.problem.singleobjective.Sphere;
import org.uma.jmetal.problem.singleobjective.TSP;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.util.RepairDoubleSolutionAtBounds;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.binarySet.BinarySet;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.comparator.ObjectiveComparator;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.comparator.RankingAndSSDComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import jmetal.algorithms.CoralReefsOptimizationWebPage;
import jmetal.algorithms.CovarianceMatrixAdaptationEvolutionStrategyWebPage;
import jmetal.algorithms.CovarianceMatrixAdaptationEvolutionStrategyWebPage.Builder;
import jmetal.algorithms.DifferentialEvolutionWebPage;
import jmetal.algorithms.ElitistEvolutionStrategyWebPage;
import jmetal.algorithms.GenerationalGeneticAlgorithmWebFormet;
import jmetal.algorithms.NonElitistEvolutionStrategyWebPage;
import jmetal.algorithms.StandardPSO2007WebPage;
import jmetal.algorithms.StandardPSO2011WebPage;
import jmetal.algorithms.SteadyStateGeneticAlgorithmWebFormat;
import jmetal.javaclass.FinalResults;
import jmetal.javaclass.Parameter;
import jmetal.javaclass.WebPageAlgorithm;
import jmetal.javaclass.WebPageCrossover;
import jmetal.javaclass.WebPageExperiment;
import jmetal.javaclass.WebPageMutation;
import jmetal.javaclass.WebPageProblem;
import jmetal.javaclass.WebPageSelection;
import jmetal.javarepository.AlgorithmRepository;
import jmetal.javarepository.CrossoverRepository;
import jmetal.javarepository.ExperimentRepository;
import jmetal.javarepository.FinalResultsRepository;
import jmetal.javarepository.MutationRepository;
import jmetal.javarepository.ParameterRepository;
import jmetal.javarepository.ProblemRepository;
import jmetal.javarepository.ResultRepository;
import jmetal.javarepository.SelectionRepository;
import jmetal.problems.TSPWebPage;


public class ExperiementCreateController  {
	
	private WebPageExperiment experiment; 
	@Autowired
	private ExperimentRepository experimentRepository; 
	@Autowired
	private ResultRepository resultRepository; 
	@Autowired
	private FinalResultsRepository finalResultsRepository; 
	@Autowired
	private ProblemRepository problemRepository;
	@Autowired
	private AlgorithmRepository algorithmRepository;
	@Autowired
	private CrossoverRepository crossoverRepository;
	@Autowired
	private MutationRepository mutationRepository;
	@Autowired
	private SelectionRepository selectionRepository;
	@Autowired
	private ParameterRepository parameterRepository;
	
	private List<String> singleListSelection = new ArrayList<>();
	private List<String> dobleListSelection = new ArrayList<>();
	
	private WebPageAlgorithm algorithm = null;
	private WebPageProblem problem = null;
	private WebPageCrossover crossoverOperator = null;
	private WebPageMutation mutationOperator = null;
	private WebPageSelection selectionOperator = null;
	private Map<String, Parameter> parameter = new HashMap<>();
	List<Parameter> crossoverParam = new ArrayList<Parameter>();
	List<Parameter> mutationParam = new ArrayList<Parameter>();
	List<Parameter> selectionParam = new ArrayList<Parameter>();
	List<Parameter> algorithmParam = new ArrayList<Parameter>();
	List<Parameter> problemParam = new ArrayList<Parameter>();
	public ExperiementCreateController() {
		
	}
	
	
	public ExperiementCreateController(WebPageExperiment experiment, ExperimentRepository experimentRepository,
			ResultRepository resultRepository, FinalResultsRepository finalResultsRepository,
			ProblemRepository problemRepository, AlgorithmRepository algorithmRepository,
			CrossoverRepository crossoverRepository, MutationRepository mutationRepository,
			SelectionRepository selectionRepository, ParameterRepository parameterRepository) {
		super();
		this.experiment = experiment;
		this.experimentRepository = experimentRepository;
		this.resultRepository = resultRepository;
		this.finalResultsRepository = finalResultsRepository;
		this.problemRepository = problemRepository;
		this.algorithmRepository = algorithmRepository;
		this.crossoverRepository = crossoverRepository;
		this.mutationRepository = mutationRepository;
		this.selectionRepository = selectionRepository;
		this.parameterRepository = parameterRepository;
		
		setVariables();
	}
	

	private void setVariables() {
		singleListSelection.add("TournamentSelection");
		singleListSelection.add("BinaryTournamentSelection");
		singleListSelection.add("BestSolutionSelection");
		singleListSelection.add("SpatialSpreadDeviationSelection");
		singleListSelection.add("NaryTournamentSelection");
		singleListSelection.add("RandomSelection");
		
		dobleListSelection.add("DifferentialEvolutionSelection");
		dobleListSelection.add("NaryRandomSelection");
		dobleListSelection.add("RankingAndPreferenceSelection");
		dobleListSelection.add("RankingAndCrowdingSelection");
		dobleListSelection.add("RankingAndDirScoreSelection");
		
		algorithm = algorithmRepository.findOne(experiment.getAlgorithm().getId());
		algorithmParam = parameterRepository.findParametersByAlgorithmId(algorithm.getId());
		setParameters(algorithmParam);
		
		problem = problemRepository.findOne(experiment.getProblem().getId());
		problemParam = parameterRepository.findParametersByProblemId(problem.getId());
		setParameters(problemParam);
		
		if (experiment.getCrossover() != null) {
			crossoverOperator = crossoverRepository.findOne(experiment.getCrossover().getId());
			crossoverParam = parameterRepository.findParametersByCrossoverId(crossoverOperator.getId());
			setParameters(crossoverParam);
		}
		
		if (experiment.getMutation() != null) {
			mutationOperator = mutationRepository.findOne(experiment.getMutation().getId());
			mutationParam = parameterRepository.findParametersByMutationId(mutationOperator.getId());
			setParameters(mutationParam);
		}
		
		if (experiment.getSelectionOperator() != null) {
			selectionOperator = selectionRepository.findOne(experiment.getSelectionOperator().getId());
			selectionParam = parameterRepository.findParametersBySelectionId(selectionOperator.getId());
			setParameters(selectionParam);
			
		}
	}


	private void setParameters(List<Parameter> parameters) {
		for (Parameter parameter : parameters) {
			this.parameter.put(parameter.getParameterName(), parameter);
		}
	}


	public void createExperimentAndRun() {
		double result = 0;
		String[] problemType = this.problem.getSolutionType().split("-");
		String solutionType;
		if (problemType.length > 1) {
			solutionType = problemType[problemType.length-2];
		}else {
			solutionType = problemType[problemType.length-1];
		}
		switch (solutionType) {
		case "BinarySolution":
			List<BinarySet> populationBS = null;
			Helper<BinarySolution> helperBS = new Helper<>();
			BinaryProblem problemBS = (BinaryProblem) helperBS.setProblem(this.problem);
			CrossoverOperator<BinarySolution> crossoverOperatorBS =  null;
			MutationOperator<BinarySolution> mutationOperatorBS =  null;
			if (this.crossoverOperator != null) {
				crossoverOperatorBS =  helperBS.setCrossover(this.crossoverOperator);
			}
			if (this.mutationOperator != null) {
				mutationOperatorBS =  helperBS.setMutation(this.mutationOperator,problemBS);
			}
			
			if (this.selectionOperator != null) {
				if (singleListSelection.contains(this.selectionOperator.getSelectionName())) {
					SelectionOperator<List<BinarySolution>, BinarySolution> selectionOperatorBS =  helperBS.setSingleListSelection(this.selectionOperator,problemBS);				
					
					switch (this.algorithm.getAlgorithmName()) {
					case "SteadyStateGeneticAlgorithm":
						Algorithm<BinarySolution> algorithmSA = new SteadyStateGeneticAlgorithmWebFormat<BinarySolution>(resultRepository,experiment,problemBS,
								Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),Integer.parseInt(parameter.get("populationSize").getParameterValue()),
						          crossoverOperatorBS, mutationOperatorBS, selectionOperatorBS);
						
						new AlgorithmRunner.Executor(algorithmSA).execute() ;
	
						result = algorithmSA.getResult().getObjective(0);
						populationBS = algorithmSA.getResult().getVariables();
					    
						break;
						
					case "GenerationalGeneticAlgorithm":				
						Algorithm<BinarySolution> algorithmGA = new GenerationalGeneticAlgorithmWebFormet<BinarySolution>(resultRepository,experiment,problemBS,
								Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),Integer.parseInt(parameter.get("populationSize").getParameterValue()),
						          crossoverOperatorBS, mutationOperatorBS, selectionOperatorBS, new SequentialSolutionListEvaluator<BinarySolution>());
										
						new AlgorithmRunner.Executor(algorithmGA).execute() ;
	
					    result = algorithmGA.getResult().getObjective(0);
					    populationBS = algorithmGA.getResult().getVariables();
					    
						break;
						
					case "CoralReefsOptimization":				
						Algorithm<List<BinarySolution>> algorithmCOA = new CoralReefsOptimizationWebPage<BinarySolution>(resultRepository, experiment, problemBS,
								Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), new ObjectiveComparator<BinarySolution>(0), 
								selectionOperatorBS, crossoverOperatorBS, mutationOperatorBS, Integer.parseInt(parameter.get("n").getParameterValue()), Integer.parseInt(parameter.get("m").getParameterValue()), 
								Double.parseDouble(parameter.get("rho").getParameterValue()), Double.parseDouble(parameter.get("fbs").getParameterValue()), Double.parseDouble(parameter.get("fa").getParameterValue()),
								Double.parseDouble(parameter.get("pd").getParameterValue()), Integer.parseInt(parameter.get("attemptsToSettle").getParameterValue()));
										
						new AlgorithmRunner.Executor(algorithmCOA).execute() ;
	
					    result = algorithmCOA.getResult().get(0).getObjective(0);
					    populationBS = algorithmCOA.getResult().get(0).getVariables();
					    
						break;
						
					
					default:
						break;
					}
				}else if (dobleListSelection.contains(this.selectionOperator.getSelectionName())){
					SelectionOperator<List<BinarySolution>, List<BinarySolution>> selectionOperator =  helperBS.setdobleListSelection(this.selectionOperator,problemBS);
	//				switch (this.algorithm.getAlgorithmName()) {
	//				case "SteadyStateGeneticAlgorithm":
	//					algorithm = new SteadyStateGeneticAlgorithmWebFormat<BinarySolution>(resultRepository,experiment,problem,
	//							Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),Integer.parseInt(parameter.get("populationSize").getParameterValue()),
	//					          crossoverOperator, mutationOperator, selectionOperator);
	//					
	//					new AlgorithmRunner.Executor(algorithm).execute() ;
	//
	//					result = algorithm.getResult().getObjective(0);
	//					population = algorithm.getResult().getVariableValueString(0);
	//				    
	//					break;
	//					
	//				case "GenerationalGeneticAlgorithm":				
	//					algorithm = new GenerationalGeneticAlgorithmWebFormet<BinarySolution>(resultRepository,experiment,problem,
	//							Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),Integer.parseInt(parameter.get("populationSize").getParameterValue()),
	//					          crossoverOperator, mutationOperator, selectionOperator, new SequentialSolutionListEvaluator<BinarySolution>());
	//									
	//					new AlgorithmRunner.Executor(algorithm).execute() ;
	//
	//				    result = algorithm.getResult().getObjective(0);
	//				    population = algorithm.getResult().getVariableValueString(0);
	//				    
	//					break;
	//				default:
	//					break;
	//				}
				}
			}else {
				switch (this.algorithm.getAlgorithmName()) {
					case "ElitistEvolutionStrategy":				
						Algorithm<BinarySolution> algorithmESA = new ElitistEvolutionStrategyWebPage<BinarySolution>(resultRepository, experiment, problemBS, Integer.parseInt(parameter.get("mu").getParameterValue()),
								Integer.parseInt(parameter.get("lambda").getParameterValue()),Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), mutationOperatorBS);
										
						new AlgorithmRunner.Executor(algorithmESA).execute() ;
		
					    result = algorithmESA.getResult().getObjective(0);
					    populationBS = algorithmESA.getResult().getVariables();
					    
						break;
						
					case "NonElitistEvolutionStrategy":				
						Algorithm<BinarySolution> algorithmNESA = new NonElitistEvolutionStrategyWebPage<BinarySolution>(resultRepository, experiment, problemBS,Integer.parseInt(parameter.get("mu").getParameterValue()),
								Integer.parseInt(parameter.get("lambda").getParameterValue()),Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), mutationOperatorBS);
										
						new AlgorithmRunner.Executor(algorithmNESA).execute() ;
		
					    result = algorithmNESA.getResult().getObjective(0);
					    populationBS = algorithmNESA.getResult().getVariables();
					    
						break;
				}
			}
			String finalPopulationBS=populationBS.toString();
		    
		    FinalResults finalResultBS = new FinalResults(experiment, result, finalPopulationBS);
		    finalResultsRepository.save(finalResultBS);
		    
		    experiment.setFinalResult(finalResultBS);
		    experimentRepository.save(experiment);
			break;
			
		case"IntegerSolution":
			List<Integer> populationIS = null;
			Helper<IntegerSolution> helperIS = new Helper<>();
			IntegerProblem problemIS = (IntegerProblem) helperIS.setProblem(this.problem);
			CrossoverOperator<IntegerSolution> crossoverOperatorIS =  null;
			if (this.crossoverOperator != null) {
				crossoverOperatorIS = helperIS.setCrossover(this.crossoverOperator);
			}
			MutationOperator<IntegerSolution> mutationOperatorIS =  null;
			if (this.mutationOperator != null) {
				mutationOperatorIS =  helperIS.setMutation(this.mutationOperator,problemIS);
			}
			
			if (this.selectionOperator != null) {
				if (singleListSelection.contains(this.selectionOperator.getSelectionName())) {
					SelectionOperator<List<IntegerSolution>, IntegerSolution> selectionOperatorIS =  helperIS.setSingleListSelection(this.selectionOperator,problemIS);				
					
					switch (this.algorithm.getAlgorithmName()) {
					case "SteadyStateGeneticAlgorithm":
						Algorithm<IntegerSolution> algorithmSA = new SteadyStateGeneticAlgorithmWebFormat<IntegerSolution>(resultRepository,experiment,problemIS,
								Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),Integer.parseInt(parameter.get("populationSize").getParameterValue()),
						          crossoverOperatorIS, mutationOperatorIS, selectionOperatorIS);
						
						new AlgorithmRunner.Executor(algorithmSA).execute() ;
	
						result = algorithmSA.getResult().getObjective(0);
						populationIS = algorithmSA.getResult().getVariables();
					    
						break;
						
					case "GenerationalGeneticAlgorithm":				
						Algorithm<IntegerSolution> algorithmGA = new GenerationalGeneticAlgorithmWebFormet<IntegerSolution>(resultRepository,experiment,problemIS,
								Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),Integer.parseInt(parameter.get("populationSize").getParameterValue()),
						          crossoverOperatorIS, mutationOperatorIS, selectionOperatorIS, new SequentialSolutionListEvaluator<IntegerSolution>());
										
						new AlgorithmRunner.Executor(algorithmGA).execute() ;
	
					    result = algorithmGA.getResult().getObjective(0);
					    populationIS = algorithmGA.getResult().getVariables();
					    
						break;
						
					case "CoralReefsOptimization":				
						Algorithm<List<IntegerSolution>> algorithmCOA = new CoralReefsOptimizationWebPage<IntegerSolution>(resultRepository, experiment, problemIS,
								Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), new ObjectiveComparator<IntegerSolution>(0), 
								selectionOperatorIS, crossoverOperatorIS, mutationOperatorIS, Integer.parseInt(parameter.get("n").getParameterValue()), Integer.parseInt(parameter.get("m").getParameterValue()), 
								Double.parseDouble(parameter.get("rho").getParameterValue()), Double.parseDouble(parameter.get("fbs").getParameterValue()), Double.parseDouble(parameter.get("fa").getParameterValue()),
								Double.parseDouble(parameter.get("pd").getParameterValue()), Integer.parseInt(parameter.get("attemptsToSettle").getParameterValue()));
										
						new AlgorithmRunner.Executor(algorithmCOA).execute() ;
	
					    result = algorithmCOA.getResult().get(0).getObjective(0);
					    populationIS = algorithmCOA.getResult().get(0).getVariables();
					    
						break;
						
					
					default:
						break;
					}
				}else if(dobleListSelection.contains(this.selectionOperator.getSelectionName())) {
					SelectionOperator<List<IntegerSolution>, List<IntegerSolution>> selectionOperator =  helperIS.setdobleListSelection(this.selectionOperator,problemIS);
	//				switch (this.algorithm.getAlgorithmName()) {
	//				case "SteadyStateGeneticAlgorithm":
	//					algorithm = new SteadyStateGeneticAlgorithmWebFormat<BinarySolution>(resultRepository,experiment,problem,
	//							Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),Integer.parseInt(parameter.get("populationSize").getParameterValue()),
	//					          crossoverOperator, mutationOperator, selectionOperator);
	//					
	//					new AlgorithmRunner.Executor(algorithm).execute() ;
	//
	//					result = algorithm.getResult().getObjective(0);
	//					population = algorithm.getResult().getVariableValueString(0);
	//				    
	//					break;
	//					
	//				case "GenerationalGeneticAlgorithm":				
	//					algorithm = new GenerationalGeneticAlgorithmWebFormet<BinarySolution>(resultRepository,experiment,problem,
	//							Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),Integer.parseInt(parameter.get("populationSize").getParameterValue()),
	//					          crossoverOperator, mutationOperator, selectionOperator, new SequentialSolutionListEvaluator<BinarySolution>());
	//									
	//					new AlgorithmRunner.Executor(algorithm).execute() ;
	//
	//				    result = algorithm.getResult().getObjective(0);
	//				    population = algorithm.getResult().getVariableValueString(0);
	//				    
	//					break;
	//				default:
	//					break;
	//				}
				}
					
				}else {
					switch (this.algorithm.getAlgorithmName()) {
					case "ElitistEvolutionStrategy":				
						Algorithm<IntegerSolution> algorithmESA = new ElitistEvolutionStrategyWebPage<IntegerSolution>(resultRepository, experiment, problemIS, Integer.parseInt(parameter.get("mu").getParameterValue()),
								Integer.parseInt(parameter.get("lambda").getParameterValue()),Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), mutationOperatorIS);
										
						new AlgorithmRunner.Executor(algorithmESA).execute() ;
		
					    result = algorithmESA.getResult().getObjective(0);
					    populationIS = algorithmESA.getResult().getVariables();
					    
						break;
						
					case "NonElitistEvolutionStrategy":				
						Algorithm<IntegerSolution> algorithmNESA = new NonElitistEvolutionStrategyWebPage<IntegerSolution>(resultRepository, experiment, problemIS,Integer.parseInt(parameter.get("mu").getParameterValue()),
								Integer.parseInt(parameter.get("lambda").getParameterValue()),Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), mutationOperatorIS);
										
						new AlgorithmRunner.Executor(algorithmNESA).execute() ;
		
					    result = algorithmNESA.getResult().getObjective(0);
					    populationIS = algorithmNESA.getResult().getVariables();
					    
						break;
			
				}
			}
			String finalPopulationIS=populationIS.toString();
		    
		    FinalResults finalResultIS = new FinalResults(experiment, result, finalPopulationIS);
		    finalResultsRepository.save(finalResultIS);
		    
		    experiment.setFinalResult(finalResultIS);
		    experimentRepository.save(experiment);
			break;
		
		case"DoubleSolution":
			List<Double> populationDS = null;
			Helper<DoubleSolution> helperDS = new Helper<>();
			DoubleProblem problemDS = (DoubleProblem) helperDS.setProblem(this.problem);
			CrossoverOperator<DoubleSolution> crossoverOperatorDS = null;
			MutationOperator<DoubleSolution> mutationOperatorDS = null;
			if (this.crossoverOperator != null) {
				crossoverOperatorDS =  helperDS.setCrossover(this.crossoverOperator);
			}
			if (this.mutationOperator != null) {
				mutationOperatorDS =  helperDS.setMutation(this.mutationOperator,problemDS);
			}
			
			if (this.selectionOperator != null) {
				if (this.selectionOperator.getSelectionName().equals("DifferentialEvolutionSelection")) {
					DifferentialEvolutionSelection selectionOperatorDS;
					if(parameter.get("randomGenerator")!= null) {
						selectionOperatorDS = new DifferentialEvolutionSelection((a,b) -> JMetalRandom.getInstance().nextInt(a,b)); ;

					}else {
						selectionOperatorDS =  new DifferentialEvolutionSelection(); ;
					}
					switch (this.algorithm.getAlgorithmName()) {
					case "DifferentialEvolution":				
						Algorithm<DoubleSolution> algorithmDEA = new DifferentialEvolutionWebPage(resultRepository, experiment, problemDS, Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),
								Integer.parseInt(parameter.get("populationSize").getParameterValue()), (DifferentialEvolutionCrossover) crossoverOperatorDS, selectionOperatorDS, new SequentialSolutionListEvaluator<DoubleSolution>());
										
						new AlgorithmRunner.Executor(algorithmDEA).execute() ;
	
					    result = algorithmDEA.getResult().getObjective(0);
					    populationDS = algorithmDEA.getResult().getVariables();
					    
						break;
						
					}
				} else if (singleListSelection.contains(this.selectionOperator.getSelectionName())) {
					SelectionOperator<List<DoubleSolution>, DoubleSolution> selectionOperatorDS =  helperDS.setSingleListSelection(this.selectionOperator,problemDS);				
					
					switch (this.algorithm.getAlgorithmName()) {
					case "SteadyStateGeneticAlgorithm":
						Algorithm<DoubleSolution> algorithmSA = new SteadyStateGeneticAlgorithmWebFormat<DoubleSolution>(resultRepository,experiment,problemDS,
								Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),Integer.parseInt(parameter.get("populationSize").getParameterValue()),
						          crossoverOperatorDS, mutationOperatorDS, selectionOperatorDS);
						
						new AlgorithmRunner.Executor(algorithmSA).execute() ;
	
						result = algorithmSA.getResult().getObjective(0);
						populationDS = algorithmSA.getResult().getVariables();
					    
						break;
						
					case "GenerationalGeneticAlgorithm":				
						Algorithm<DoubleSolution> algorithmGA = new GenerationalGeneticAlgorithmWebFormet<DoubleSolution>(resultRepository,experiment,problemDS,
								Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),Integer.parseInt(parameter.get("populationSize").getParameterValue()),
						          crossoverOperatorDS, mutationOperatorDS, selectionOperatorDS, new SequentialSolutionListEvaluator<DoubleSolution>());
										
						new AlgorithmRunner.Executor(algorithmGA).execute() ;
	
					    result = algorithmGA.getResult().getObjective(0);
					    populationDS = algorithmGA.getResult().getVariables();
					    
						break;
						
					case "CoralReefsOptimization":				
						Algorithm<List<DoubleSolution>> algorithmCOA = new CoralReefsOptimizationWebPage<DoubleSolution>(resultRepository, experiment, problemDS,
								Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), new ObjectiveComparator<DoubleSolution>(0), 
								selectionOperatorDS, crossoverOperatorDS, mutationOperatorDS, Integer.parseInt(parameter.get("n").getParameterValue()), Integer.parseInt(parameter.get("m").getParameterValue()), 
								Double.parseDouble(parameter.get("rho").getParameterValue()), Double.parseDouble(parameter.get("fbs").getParameterValue()), Double.parseDouble(parameter.get("fa").getParameterValue()),
								Double.parseDouble(parameter.get("pd").getParameterValue()), Integer.parseInt(parameter.get("attemptsToSettle").getParameterValue()));
										
						new AlgorithmRunner.Executor(algorithmCOA).execute() ;
	
					    result = algorithmCOA.getResult().get(0).getObjective(0);
					    populationDS = algorithmCOA.getResult().get(0).getVariables();
					    
						break;
						
					
						
					}
				}else if(dobleListSelection.contains(this.selectionOperator.getSelectionName())){
					SelectionOperator<List<DoubleSolution>, List<DoubleSolution>> selectionOperatorDS =  helperDS.setdobleListSelection(this.selectionOperator,problemDS);
					
				}
			
			}else {
				
				switch (this.algorithm.getAlgorithmName()) {
					case "ElitistEvolutionStrategy":				
						Algorithm<DoubleSolution> algorithmESA = new ElitistEvolutionStrategyWebPage<DoubleSolution>(resultRepository, experiment, problemDS, Integer.parseInt(parameter.get("mu").getParameterValue()),
								Integer.parseInt(parameter.get("lambda").getParameterValue()),Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), mutationOperatorDS);
										
						new AlgorithmRunner.Executor(algorithmESA).execute() ;
		
					    result = algorithmESA.getResult().getObjective(0);
					    populationDS = algorithmESA.getResult().getVariables();
					    
						break;
						
					case "NonElitistEvolutionStrategy":				
						Algorithm<DoubleSolution> algorithmNESA = new NonElitistEvolutionStrategyWebPage<DoubleSolution>(resultRepository, experiment, problemDS,Integer.parseInt(parameter.get("mu").getParameterValue()),
								Integer.parseInt(parameter.get("lambda").getParameterValue()),Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), mutationOperatorDS);
										
						new AlgorithmRunner.Executor(algorithmNESA).execute() ;
		
					    result = algorithmNESA.getResult().getObjective(0);
					    populationDS = algorithmNESA.getResult().getVariables();
					    
						break;	
						
					case "CovarianceMatrixAdaptationEvolutionStrategy":	
						Algorithm<DoubleSolution> algorithmCMA;

						if((parameter.get("lambda")!= null) && parameter.get("maxEvaluations") != null && parameter.get("sigma") != null ) {
							Builder builder = new Builder(problemDS);
							
							builder.setLambda(Integer.parseInt(parameter.get("lambda").getParameterValue()));
							builder.setMaxEvaluations(Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()));
							builder.setSigma(Double.parseDouble(parameter.get("sigma").getParameterValue()));
							algorithmCMA = new CovarianceMatrixAdaptationEvolutionStrategyWebPage(resultRepository, experiment, builder);
						}else {
							Builder builder = new Builder(problemDS);
							algorithmCMA = new CovarianceMatrixAdaptationEvolutionStrategyWebPage(resultRepository, experiment, builder);
							
						}
						new AlgorithmRunner.Executor(algorithmCMA).execute() ;
		
					    result = algorithmCMA.getResult().getObjective(0);
					    populationDS = algorithmCMA.getResult().getVariables();
					    
						break;
						
					case "StandardPSO2007":	
						Algorithm<DoubleSolution> algorithmSP7A = new StandardPSO2007WebPage(resultRepository, experiment, problemDS, 
								Integer.parseInt(parameter.get("swarmSize").getParameterValue()), Integer.parseInt(parameter.get("maxIterations").getParameterValue()),
								Integer.parseInt(parameter.get("numberOfParticlesToInform").getParameterValue()), new SequentialSolutionListEvaluator<DoubleSolution>());
						new AlgorithmRunner.Executor(algorithmSP7A).execute() ;
		
					    result = algorithmSP7A.getResult().getObjective(0);
					    populationDS = algorithmSP7A.getResult().getVariables();
					    
						break;
						
					case "StandardPSO2011":	
						Algorithm<DoubleSolution> algorithmSP11A = new StandardPSO2011WebPage(resultRepository, experiment, problemDS, 
								Integer.parseInt(parameter.get("swarmSize").getParameterValue()), Integer.parseInt(parameter.get("maxIterations").getParameterValue()),
								Integer.parseInt(parameter.get("numberOfParticlesToInform").getParameterValue()), new SequentialSolutionListEvaluator<DoubleSolution>());
						new AlgorithmRunner.Executor(algorithmSP11A).execute() ;
		
					    result = algorithmSP11A.getResult().getObjective(0);
					    populationDS = algorithmSP11A.getResult().getVariables();
					    
						break;
				}
			}
			
			String finalPopulationDS=populationDS.toString();
		    
		    FinalResults finalResultDS = new FinalResults(experiment, result, finalPopulationDS);
		    finalResultsRepository.save(finalResultDS);
		
		    experiment.setFinalResult(finalResultDS);
		    experimentRepository.save(experiment);
			break;
		
		
		case"PermutationSolution":
			switch (problemType[problemType.length-1]) {
			case "Integer":
				List<Integer> populationPSI= null;
				Helper<PermutationSolution<Integer>> helperPSI= new Helper<>();
				Problem<PermutationSolution<Integer>> problemPSI= (Problem<PermutationSolution<Integer>>) helperPSI.setProblem(this.problem);
				CrossoverOperator<PermutationSolution<Integer>> crossoverOperatorPSI= null;
				MutationOperator<PermutationSolution<Integer>> mutationOperatorPSI= null;
				if (this.crossoverOperator != null) {
					crossoverOperatorPSI=  helperPSI.setCrossover(this.crossoverOperator);
				}
				if (this.mutationOperator != null) {
					mutationOperatorPSI=  helperPSI.setMutation(this.mutationOperator,problemPSI);
				}
				
				if (this.selectionOperator != null) {
					if (singleListSelection.contains(this.selectionOperator.getSelectionName())) {
						SelectionOperator<List<PermutationSolution<Integer>>, PermutationSolution<Integer>> selectionOperatorDS =  helperPSI.setSingleListSelection(this.selectionOperator,problemPSI);				
						
						switch (this.algorithm.getAlgorithmName()) {
						case "SteadyStateGeneticAlgorithm":
							Algorithm<PermutationSolution<Integer>> algorithmSA = new SteadyStateGeneticAlgorithmWebFormat<PermutationSolution<Integer>>(resultRepository,experiment,problemPSI,
									Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),Integer.parseInt(parameter.get("populationSize").getParameterValue()),
							          crossoverOperatorPSI, mutationOperatorPSI, selectionOperatorDS);
							
							new AlgorithmRunner.Executor(algorithmSA).execute() ;
		
							result = algorithmSA.getResult().getObjective(0);
							populationPSI= algorithmSA.getResult().getVariables();
						    
							break;
							
						case "GenerationalGeneticAlgorithm":				
							Algorithm<PermutationSolution<Integer>> algorithmGA = new GenerationalGeneticAlgorithmWebFormet<PermutationSolution<Integer>>(resultRepository,experiment,problemPSI,
									Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),Integer.parseInt(parameter.get("populationSize").getParameterValue()),
							          crossoverOperatorPSI, mutationOperatorPSI, selectionOperatorDS, new SequentialSolutionListEvaluator<PermutationSolution<Integer>>());
											
							new AlgorithmRunner.Executor(algorithmGA).execute() ;
		
						    result = algorithmGA.getResult().getObjective(0);
						    populationPSI= algorithmGA.getResult().getVariables();
						    
							break;
							
						case "CoralReefsOptimization":				
							Algorithm<List<PermutationSolution<Integer>>> algorithmCOA = new CoralReefsOptimizationWebPage<PermutationSolution<Integer>>(resultRepository, experiment, problemPSI,
									Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), new ObjectiveComparator<PermutationSolution<Integer>>(0), 
									selectionOperatorDS, crossoverOperatorPSI, mutationOperatorPSI, Integer.parseInt(parameter.get("n").getParameterValue()), Integer.parseInt(parameter.get("m").getParameterValue()), 
									Double.parseDouble(parameter.get("rho").getParameterValue()), Double.parseDouble(parameter.get("fbs").getParameterValue()), Double.parseDouble(parameter.get("fa").getParameterValue()),
									Double.parseDouble(parameter.get("pd").getParameterValue()), Integer.parseInt(parameter.get("attemptsToSettle").getParameterValue()));
											
							new AlgorithmRunner.Executor(algorithmCOA).execute() ;
		
						    result = algorithmCOA.getResult().get(0).getObjective(0);
						    populationPSI= algorithmCOA.getResult().get(0).getVariables();
						    
							break;
							
						
							
						}
					}else if(dobleListSelection.contains(this.selectionOperator.getSelectionName())){
						SelectionOperator<List<PermutationSolution<Integer>>, List<PermutationSolution<Integer>>> selectionOperatorDS =  helperPSI.setdobleListSelection(this.selectionOperator,problemPSI);
						
					}
				
				}else {
					
					switch (this.algorithm.getAlgorithmName()) {
						case "ElitistEvolutionStrategy":				
							Algorithm<PermutationSolution<Integer>> algorithmESA = new ElitistEvolutionStrategyWebPage<PermutationSolution<Integer>>(resultRepository, experiment, problemPSI, Integer.parseInt(parameter.get("mu").getParameterValue()),
									Integer.parseInt(parameter.get("lambda").getParameterValue()),Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), mutationOperatorPSI);
											
							new AlgorithmRunner.Executor(algorithmESA).execute() ;
			
						    result = algorithmESA.getResult().getObjective(0);
						    populationPSI= algorithmESA.getResult().getVariables();
						    
							break;
							
						case "NonElitistEvolutionStrategy":				
							Algorithm<PermutationSolution<Integer>> algorithmNESA = new NonElitistEvolutionStrategyWebPage<PermutationSolution<Integer>>(resultRepository, experiment, problemPSI,Integer.parseInt(parameter.get("mu").getParameterValue()),
									Integer.parseInt(parameter.get("lambda").getParameterValue()),Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), mutationOperatorPSI);
											
							new AlgorithmRunner.Executor(algorithmNESA).execute() ;
			
						    result = algorithmNESA.getResult().getObjective(0);
						    populationPSI= algorithmNESA.getResult().getVariables();
						    
							break;	
					}		
				}
				
				String finalPopulationPSI= populationPSI.toString();
			    
			    FinalResults finalResultPSI= new FinalResults(experiment, result, finalPopulationPSI);
			    finalResultsRepository.save(finalResultPSI);
			
			    experiment.setFinalResult(finalResultPSI);
			    experimentRepository.save(experiment);
				break;

			case "Double":
				List<Double> populationPSD = null;
				Helper<PermutationSolution<Double>> helperPSD = new Helper<>();
				PermutationProblem<PermutationSolution<Double>> problemPSD = (PermutationProblem<PermutationSolution<Double>>) helperPSD.setProblem(this.problem);
				CrossoverOperator<PermutationSolution<Double>> crossoverOperatorPSD = null;
				MutationOperator<PermutationSolution<Double>> mutationOperatorPSD = null;
				if (this.crossoverOperator != null) {
					crossoverOperatorPSD =  helperPSD.setCrossover(this.crossoverOperator);
				}
				if (this.mutationOperator != null) {
					mutationOperatorPSD =  helperPSD.setMutation(this.mutationOperator,problemPSD);
				}
				
				if (this.selectionOperator != null) {
					if (singleListSelection.contains(this.selectionOperator.getSelectionName())) {
						SelectionOperator<List<PermutationSolution<Double>>, PermutationSolution<Double>> selectionOperatorDS =  helperPSD.setSingleListSelection(this.selectionOperator,problemPSD);				
						
						switch (this.algorithm.getAlgorithmName()) {
						case "SteadyStateGeneticAlgorithm":
							Algorithm<PermutationSolution<Double>> algorithmSA = new SteadyStateGeneticAlgorithmWebFormat<PermutationSolution<Double>>(resultRepository,experiment,problemPSD,
									Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),Integer.parseInt(parameter.get("populationSize").getParameterValue()),
							          crossoverOperatorPSD, mutationOperatorPSD, selectionOperatorDS);
							
							new AlgorithmRunner.Executor(algorithmSA).execute() ;
		
							result = algorithmSA.getResult().getObjective(0);
							populationPSD = algorithmSA.getResult().getVariables();
						    
							break;
							
						case "GenerationalGeneticAlgorithm":				
							Algorithm<PermutationSolution<Double>> algorithmGA = new GenerationalGeneticAlgorithmWebFormet<PermutationSolution<Double>>(resultRepository,experiment,problemPSD,
									Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()),Integer.parseInt(parameter.get("populationSize").getParameterValue()),
							          crossoverOperatorPSD, mutationOperatorPSD, selectionOperatorDS, new SequentialSolutionListEvaluator<PermutationSolution<Double>>());
											
							new AlgorithmRunner.Executor(algorithmGA).execute() ;
		
						    result = algorithmGA.getResult().getObjective(0);
						    populationPSD = algorithmGA.getResult().getVariables();
						    
							break;
							
						case "CoralReefsOptimization":				
							Algorithm<List<PermutationSolution<Double>>> algorithmCOA = new CoralReefsOptimizationWebPage<PermutationSolution<Double>>(resultRepository, experiment, problemPSD,
									Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), new ObjectiveComparator<PermutationSolution<Double>>(0), 
									selectionOperatorDS, crossoverOperatorPSD, mutationOperatorPSD, Integer.parseInt(parameter.get("n").getParameterValue()), Integer.parseInt(parameter.get("m").getParameterValue()), 
									Double.parseDouble(parameter.get("rho").getParameterValue()), Double.parseDouble(parameter.get("fbs").getParameterValue()), Double.parseDouble(parameter.get("fa").getParameterValue()),
									Double.parseDouble(parameter.get("pd").getParameterValue()), Integer.parseInt(parameter.get("attemptsToSettle").getParameterValue()));
											
							new AlgorithmRunner.Executor(algorithmCOA).execute() ;
		
						    result = algorithmCOA.getResult().get(0).getObjective(0);
						    populationPSD = algorithmCOA.getResult().get(0).getVariables();
						    
							break;
							
						
							
						}
					}else if(dobleListSelection.contains(this.selectionOperator.getSelectionName())){
						SelectionOperator<List<PermutationSolution<Double>>, List<PermutationSolution<Double>>> selectionOperatorDS =  helperPSD.setdobleListSelection(this.selectionOperator,problemPSD);
						
					}
				
				}else {
					
					switch (this.algorithm.getAlgorithmName()) {
						case "ElitistEvolutionStrategy":				
							Algorithm<PermutationSolution<Double>> algorithmESA = new ElitistEvolutionStrategyWebPage<PermutationSolution<Double>>(resultRepository, experiment, problemPSD, Integer.parseInt(parameter.get("mu").getParameterValue()),
									Integer.parseInt(parameter.get("lambda").getParameterValue()),Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), mutationOperatorPSD);
											
							new AlgorithmRunner.Executor(algorithmESA).execute() ;
			
						    result = algorithmESA.getResult().getObjective(0);
						    populationPSD = algorithmESA.getResult().getVariables();
						    
							break;
							
						case "NonElitistEvolutionStrategy":				
							Algorithm<PermutationSolution<Double>> algorithmNESA = new NonElitistEvolutionStrategyWebPage<PermutationSolution<Double>>(resultRepository, experiment, problemPSD,Integer.parseInt(parameter.get("mu").getParameterValue()),
									Integer.parseInt(parameter.get("lambda").getParameterValue()),Integer.parseInt(parameter.get("maxEvaluations").getParameterValue()), mutationOperatorPSD);
											
							new AlgorithmRunner.Executor(algorithmNESA).execute() ;
			
						    result = algorithmNESA.getResult().getObjective(0);
						    populationPSD = algorithmNESA.getResult().getVariables();
						    
							break;	
					}		
				}
				
				String finalPopulationPSD = populationPSD.toString();
			    
			    FinalResults finalResultPSD = new FinalResults(experiment, result, finalPopulationPSD);
			    finalResultsRepository.save(finalResultPSD);
			
			    experiment.setFinalResult(finalResultPSD);
			    experimentRepository.save(experiment);
				break;

			default:
				break;
			}
			

		}
		
	}


	public WebPageExperiment getExperiment() {
		return experiment;
	}


	public void setExperiment(WebPageExperiment experiment) {
		this.experiment = experiment;
	}
	
	
	private class Helper<T> {

		@SuppressWarnings("unchecked")
		public Problem<T> setProblem(WebPageProblem problem){
			Problem<T> problemToReturn = null;
			switch (problem.getProblemName()) {
			case "OneMax":
				if (parameter.get("numberOfBits") != null) {
					problemToReturn = (Problem<T>) new OneMax(Integer.parseInt(parameter.get("numberOfBits").getParameterValue()));
				} else {
					problemToReturn = (Problem<T>) new OneMax();
				}
				break;

			case "Griewank":
				problemToReturn = (Problem<T>) new Griewank(Integer.parseInt(parameter.get("numberOfVariables").getParameterValue()));
				break;

			case "Rastrigin":
				problemToReturn = (Problem<T>) new Rastrigin(Integer.parseInt(parameter.get("numberOfVariables").getParameterValue()));
				break;

			case "Rosenbrock":
				problemToReturn = (Problem<T>) new Rosenbrock(Integer.parseInt(parameter.get("numberOfVariables").getParameterValue()));
				break;

			case "Sphere":
				problemToReturn = (Problem<T>) new Sphere(Integer.parseInt(parameter.get("numberOfVariables").getParameterValue()));
				break;

			case "TSP":
				if (parameter.get("distanceFile").getParameterValue().equals("none")) {
					try {
						problemToReturn = (Problem<T>) new TSP("/tspInstances/kroA100.tsp");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						Path FILES_FOLDER = Paths.get(System. getProperty("user.dir"), "TSPFiles");
						String path = FILES_FOLDER.toString();
						problemToReturn = (Problem<T>) new TSPWebPage(path+"/"+parameter.get("distanceFile").getParameterValue());
					} catch (IOException e) {
						e.printStackTrace();
						
//						showErrorMessage(model,e);
					}
				}
				break;

			case "NIntegerMin":
				problemToReturn = (Problem<T>) new NIntegerMin(Integer.parseInt(parameter.get("numberOfVariables").getParameterValue()),
						Integer.parseInt(parameter.get("n").getParameterValue()),Integer.parseInt(parameter.get("lowerBound").getParameterValue()),
						Integer.parseInt(parameter.get("upperBound").getParameterValue()));
				break;
			}
			
			return problemToReturn;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public CrossoverOperator<T> setCrossover(WebPageCrossover crossover){
			CrossoverOperator<T> crossoverToReturn = null;
			switch (crossover.getCrossoverName()) {
			case "SinglePointCrossover":
				if((parameter.get("crossoverProbability")!= null) && parameter.get("crossoverRandomGenerator") != null && parameter.get("pointRandomGenerator") != null ) {
					crossoverToReturn = (CrossoverOperator<T>) new SinglePointCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),
							() -> JMetalRandom.getInstance().nextDouble(),(a, b) -> JMetalRandom.getInstance().nextInt(a, b));
				}else if((parameter.get("crossoverProbability")!= null) && parameter.get("crossoverRandomGenerator") != null ) {
					crossoverToReturn = (CrossoverOperator<T>) new SinglePointCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),
							() -> JMetalRandom.getInstance().nextDouble());
				}else{
					crossoverToReturn = (CrossoverOperator<T>) new SinglePointCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()));
				}
				
				break;
			case"BLXAlphaCrossover":
				if((parameter.get("crossoverProbability")!= null) && parameter.get("alpha") != null && parameter.get("crossoverSolutionRepair") != null && parameter.get("crossoverRandomGenerator") != null ) {
					crossoverToReturn = (CrossoverOperator<T>) new BLXAlphaCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("alpha").getParameterValue()), new RepairDoubleSolutionAtBounds(), () -> JMetalRandom.getInstance().nextDouble());					
				}else if((parameter.get("crossoverProbability")!= null) && parameter.get("alpha") != null && parameter.get("crossoverSolutionRepair") != null ) {
					crossoverToReturn = (CrossoverOperator<T>) new BLXAlphaCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("alpha").getParameterValue()), new RepairDoubleSolutionAtBounds());					
				}else if((parameter.get("crossoverProbability")!= null) && parameter.get("alpha") != null  ) {
					crossoverToReturn = (CrossoverOperator<T>) new BLXAlphaCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("alpha").getParameterValue()));
				}else {
					crossoverToReturn = (CrossoverOperator<T>) new BLXAlphaCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()));
				}
				break;
				
			case"DifferentialEvolutionCrossover":
				if((parameter.get("crossoverProbability")!= null) && parameter.get("f") != null && parameter.get("k") != null && parameter.get("variant") != null ) {
					if (parameter.get("variant").getParameterValue().equals("")) {
						crossoverToReturn = (CrossoverOperator<T>) new DifferentialEvolutionCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("f").getParameterValue()), Double.parseDouble(parameter.get("k").getParameterValue()), "rand/1/bin");					
					} else {
						crossoverToReturn = (CrossoverOperator<T>) new DifferentialEvolutionCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("f").getParameterValue()), Double.parseDouble(parameter.get("k").getParameterValue()), parameter.get("variant").getParameterValue());					
					}
				}else if((parameter.get("crossoverProbability")!= null) && parameter.get("f") != null && parameter.get("variant") != null && parameter.get("jRandomGenerator") != null && parameter.get("crRandomGenerator") != null) {
					if (parameter.get("variant").getParameterValue().equals("")) {
						crossoverToReturn = (CrossoverOperator<T>) new DifferentialEvolutionCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("f").getParameterValue()), "rand/1/bin", (a, b) -> JMetalRandom.getInstance().nextInt(a, b), (a,b) -> JMetalRandom.getInstance().nextDouble(a,b));					
					} else {
						crossoverToReturn = (CrossoverOperator<T>) new DifferentialEvolutionCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("f").getParameterValue()), parameter.get("variant").getParameterValue(), (a, b) -> JMetalRandom.getInstance().nextInt(a, b), (a,b) -> JMetalRandom.getInstance().nextDouble(a,b));					
					}
				}else if((parameter.get("crossoverProbability")!= null) && parameter.get("f") != null && parameter.get("variant") != null && parameter.get("crossoverRandomGenerator") != null) {
					if (parameter.get("variant").getParameterValue().equals("")) {
						crossoverToReturn = (CrossoverOperator<T>) new DifferentialEvolutionCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("f").getParameterValue()), "rand/1/bin", () -> JMetalRandom.getInstance().nextDouble());					
					} else {
						crossoverToReturn = (CrossoverOperator<T>) new DifferentialEvolutionCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("f").getParameterValue()), parameter.get("variant").getParameterValue(), () -> JMetalRandom.getInstance().nextDouble());					
					}
				}else if((parameter.get("crossoverProbability")!= null) && parameter.get("f") != null && !parameter.get("variant").getParameterValue().equals("")) {
					crossoverToReturn = (CrossoverOperator<T>) new DifferentialEvolutionCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("f").getParameterValue()),parameter.get("variant").getParameterValue());					
				}else if((parameter.get("crossoverProbability")!= null) && parameter.get("f") != null ) {
					crossoverToReturn = (CrossoverOperator<T>) new DifferentialEvolutionCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("f").getParameterValue()),"rand/1/bin");					
				}else	{
					crossoverToReturn = (CrossoverOperator<T>) new DifferentialEvolutionCrossover();
				}
				break;
				
			case"SBXCrossover":
				if((parameter.get("crossoverProbability")!= null) && parameter.get("distributionIndex") != null && parameter.get("crossoverSolutionRepair") != null && parameter.get("crossoverRandomGenerator") != null ) {
					crossoverToReturn = (CrossoverOperator<T>) new SBXCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("distributionIndex").getParameterValue()), new RepairDoubleSolutionAtBounds(), () -> JMetalRandom.getInstance().nextDouble());					
				}else if(parameter.get("crossoverProbability") != null && parameter.get("distributionIndex") != null && parameter.get("crossoverRandomGenerator") != null){
					crossoverToReturn = (CrossoverOperator<T>) new SBXCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("distributionIndex").getParameterValue()), () -> JMetalRandom.getInstance().nextDouble());					
				}else if(parameter.get("crossoverProbability") != null && parameter.get("distributionIndex") != null && parameter.get("crossoverSolutionRepair") != null){
					crossoverToReturn = (CrossoverOperator<T>) new SBXCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("distributionIndex").getParameterValue()),new RepairDoubleSolutionAtBounds());					
				}else {
					crossoverToReturn = (CrossoverOperator<T>) new SBXCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("distributionIndex").getParameterValue()));					
				}
				break;
				
			case"HUXCrossover":
				if((parameter.get("crossoverProbability")!= null) && parameter.get("crossoverRandomGenerator") != null) {
					crossoverToReturn = (CrossoverOperator<T>) new HUXCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),() -> JMetalRandom.getInstance().nextDouble());					
				}else{
					crossoverToReturn = (CrossoverOperator<T>) new HUXCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()));					
				}
				break;
				
			case"IntegerSBXCrossover":
				if((parameter.get("crossoverProbability")!= null) && parameter.get("distributionIndex") != null && parameter.get("cuttingPointRandomGenerator") != null) {
					crossoverToReturn = (CrossoverOperator<T>) new IntegerSBXCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("distributionIndex").getParameterValue()), () -> JMetalRandom.getInstance().nextDouble());					
				}else{
					crossoverToReturn = (CrossoverOperator<T>) new IntegerSBXCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Double.parseDouble(parameter.get("distributionIndex").getParameterValue()));					
					}
				break;
				
			case"PMXCrossover":
				if((parameter.get("crossoverProbability")!= null) && parameter.get("crossoverRandomGenerator") != null && parameter.get("cuttingPointRandomGenerator") != null) {
					crossoverToReturn = (CrossoverOperator<T>) new PMXCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),() -> JMetalRandom.getInstance().nextDouble(), (a, b) -> JMetalRandom.getInstance().nextInt(a, b));					
				}else if(parameter.get("crossoverProbability") != null && parameter.get("crossoverRandomGenerator") != null){
					crossoverToReturn = (CrossoverOperator<T>) new PMXCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),() -> JMetalRandom.getInstance().nextDouble());					
				}else {
					crossoverToReturn = (CrossoverOperator<T>) new PMXCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()));					
				}
				break;
				
			case"TwoPointCrossover":
				crossoverToReturn = new TwoPointCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()));
				break;
				
			case"NPointCrossover":
				if((parameter.get("crossoverProbability")!= null)&& parameter.get("numberOfCrossovers") != null) {
					crossoverToReturn = new NPointCrossover(Double.parseDouble(parameter.get("crossoverProbability").getParameterValue()),Integer.parseInt(parameter.get("numberOfCrossovers").getParameterValue()));					
				}else if(parameter.get("numberOfCrossovers") != null){
					crossoverToReturn = new NPointCrossover(Integer.parseInt(parameter.get("numberOfCrossovers").getParameterValue()));					
				}
				break;
				
			case"NullCrossover":
				crossoverToReturn = new NullCrossover();
				break;
			}
			
			return crossoverToReturn;
		}
		
		@SuppressWarnings("unchecked")
		public MutationOperator<T> setMutation(WebPageMutation mutation, Problem<T> mutationProblem){
			MutationOperator<T> mutationToReturn = null;
			switch (mutation.getMutationName()) {
			case "BitFlipMutation":
				if((parameter.get("mutationProbability")!= null) && parameter.get("mutationRandomGenerator") != null) {
					mutationToReturn = (MutationOperator<T>) new BitFlipMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), () -> JMetalRandom.getInstance().nextDouble());
				}else {
					mutationToReturn = (MutationOperator<T>) new BitFlipMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue())) ;
				}
				
				break;
				
			case"PermutationSwapMutation":
				if((parameter.get("mutationProbability")!= null) && (parameter.get("mutationRandomGenerator") != null) && (parameter.get("positionRandomGenerator") != null)) {
					mutationToReturn = (MutationOperator<T>) new PermutationSwapMutation<>(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), () -> JMetalRandom.getInstance().nextDouble(), (a,b) -> JMetalRandom.getInstance().nextInt(a,b));
				}else if((parameter.get("mutationProbability")!= null) && (parameter.get("mutationRandomGenerator") != null)) {
					mutationToReturn = (MutationOperator<T>) new PermutationSwapMutation<>(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), () -> JMetalRandom.getInstance().nextDouble());
				}else {
					mutationToReturn = (MutationOperator<T>) new PermutationSwapMutation<>(Double.parseDouble(parameter.get("mutationProbability").getParameterValue())) ;
				}
				
				break;
				
			case"NullMutation":
				mutationToReturn = new NullMutation<>();
				break;
				
			case"CDGMutation":
				if((parameter.get("mutationProbability")!= null) && (parameter.get("delta") != null) && (parameter.get("mutationSolutionRepair") != null)) {
					mutationToReturn =  (MutationOperator<T>) new CDGMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), Double.parseDouble(parameter.get("delta").getParameterValue()), new RepairDoubleSolutionAtBounds());
				}else if((parameter.get("mutationProbability")!= null) && (parameter.get("delta") != null)) {
					mutationToReturn =  (MutationOperator<T>) new CDGMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), Double.parseDouble(parameter.get("delta").getParameterValue()));
				}else if((experiment.getProblem().getSolutionType().equals("DoubleSolution")) && (parameter.get("delta") != null) && (parameter.get("CDGproblem") != null)) {
					mutationToReturn =  (MutationOperator<T>) new CDGMutation((DoubleProblem) mutationProblem, Double.parseDouble(parameter.get("delta").getParameterValue()));
				}else {
					mutationToReturn = (MutationOperator<T>) new CDGMutation() ;
				}
				break;
				
			case"NonUniformMutation":
				if((parameter.get("mutationProbability")!= null) && (parameter.get("perturbation") != null) && (parameter.get("maxIterations") != null) && (parameter.get("randomGenenerator") != null)) {
					mutationToReturn =  (MutationOperator<T>) new NonUniformMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), Double.parseDouble(parameter.get("perturbation").getParameterValue()), Integer.parseInt(parameter.get("maxIterations").getParameterValue()), () -> JMetalRandom.getInstance().nextDouble());
				}else {
					mutationToReturn =  (MutationOperator<T>) new NonUniformMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), Double.parseDouble(parameter.get("perturbation").getParameterValue()), Integer.parseInt(parameter.get("maxIterations").getParameterValue()));
				}
				break;
				
			case"PolynomialMutation":
				if((experiment.getProblem().getSolutionType().equals("DoubleSolution"))  && (parameter.get("PMproblem") != null) && (parameter.get("distributionIndex") != null) && (parameter.get("mutationRandomGenerator") != null)) {
					mutationToReturn =  (MutationOperator<T>) new PolynomialMutation((DoubleProblem) mutationProblem, Double.parseDouble(parameter.get("distributionIndex").getParameterValue()), () -> JMetalRandom.getInstance().nextDouble());
				}else if((experiment.getProblem().getSolutionType().equals("DoubleSolution"))  && (parameter.get("PMproblem") != null) && (parameter.get("distributionIndex") != null)) {
					mutationToReturn =  (MutationOperator<T>) new PolynomialMutation((DoubleProblem) mutationProblem, Double.parseDouble(parameter.get("distributionIndex").getParameterValue()));
				}else 
					if((parameter.get("mutationProbability")!= null) && (parameter.get("distributionIndex") != null) && (parameter.get("mutationSolutionRepair") != null) && (parameter.get("mutationRandomGenerator") != null)) {
					mutationToReturn =  (MutationOperator<T>) new PolynomialMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), Double.parseDouble(parameter.get("distributionIndex").getParameterValue()), new RepairDoubleSolutionAtBounds(), () -> JMetalRandom.getInstance().nextDouble());
				}else if((parameter.get("mutationProbability")!= null) && (parameter.get("distributionIndex") != null) && (parameter.get("mutationSolutionRepair") != null)) {
					mutationToReturn =  (MutationOperator<T>) new PolynomialMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), Double.parseDouble(parameter.get("distributionIndex").getParameterValue()), new RepairDoubleSolutionAtBounds());
				}else if((parameter.get("mutationProbability")!= null) && (parameter.get("distributionIndex") != null) && (parameter.get("mutationRandomGenerator") != null)) {
					mutationToReturn =  (MutationOperator<T>) new PolynomialMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), Double.parseDouble(parameter.get("distributionIndex").getParameterValue()), () -> JMetalRandom.getInstance().nextDouble());
				}else {
					mutationToReturn =  (MutationOperator<T>) new PolynomialMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), Double.parseDouble(parameter.get("distributionIndex").getParameterValue()));
				}
				break;
				
			case"SimpleRandomMutation":
				if((parameter.get("mutationProbability")!= null) && (parameter.get("mutationRandomGenerator") != null)) {
					mutationToReturn =  (MutationOperator<T>) new SimpleRandomMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), () -> JMetalRandom.getInstance().nextDouble());
				}else {
					mutationToReturn =  (MutationOperator<T>) new SimpleRandomMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()));
				}
				break;
				
			case"UniformMutation":
				if((parameter.get("mutationProbability")!= null) && (parameter.get("perturbation") != null) && (parameter.get("randomGenenerator") != null)) {
					mutationToReturn =  (MutationOperator<T>) new UniformMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), Double.parseDouble(parameter.get("perturbation").getParameterValue()), () -> JMetalRandom.getInstance().nextDouble());
				}else {
					mutationToReturn =  (MutationOperator<T>) new UniformMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), Double.parseDouble(parameter.get("perturbation").getParameterValue()));				
				}
				break;
				
			case"IntegerPolynomialMutation":
				if((problem.getSolutionType() == "IntegerSolution") && (parameter.get("distributionIndex") != null)) {
					mutationToReturn =  (MutationOperator<T>) new IntegerPolynomialMutation( (IntegerProblem) mutationProblem, Double.parseDouble(parameter.get("distributionIndex").getParameterValue()));
				}else if((parameter.get("mutationProbability")!= null) && (parameter.get("distributionIndex") != null) && (parameter.get("mutationSolutionRepair") != null) && (parameter.get("mutationRandomGenerator") != null)) {
					mutationToReturn =  (MutationOperator<T>) new IntegerPolynomialMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), Double.parseDouble(parameter.get("distributionIndex").getParameterValue()), new RepairDoubleSolutionAtBounds(), () -> JMetalRandom.getInstance().nextDouble());
				}else if((parameter.get("mutationProbability")!= null) && (parameter.get("distributionIndex") != null) && (parameter.get("mutationSolutionRepair") != null) ) {
					mutationToReturn =  (MutationOperator<T>) new IntegerPolynomialMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), Double.parseDouble(parameter.get("distributionIndex").getParameterValue()), new RepairDoubleSolutionAtBounds());
				}else {
					mutationToReturn =  (MutationOperator<T>) new IntegerPolynomialMutation(Double.parseDouble(parameter.get("mutationProbability").getParameterValue()), Double.parseDouble(parameter.get("distributionIndex").getParameterValue()));
				}
				break;
			}
			
			return mutationToReturn;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public SelectionOperator<List<T>, T> setSingleListSelection(WebPageSelection selection, Problem<T> selectionProblem){
			SelectionOperator<List<T>, T> selectionToReturn = null;
			switch (selection.getSelectionName()) {
				case "BinaryTournamentSelection":
					if((parameter.get("comparator")!= null)) {
						selectionToReturn =  new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator<Solution<T>>()); ;
					}else {
						selectionToReturn =  new BinaryTournamentSelection(); ;
					}
					
					break;
					
				case"BestSolutionSelection":
						selectionToReturn =  new BestSolutionSelection(new ObjectiveComparator<Solution<T>>(0));
					break;
					
				case"TournamentSelection":
					if((parameter.get("comparator")!= null) && (parameter.get("n_arity")!= null)) {
						selectionToReturn =  new TournamentSelection(new DominanceComparator<Solution<T>>(), Integer.parseInt(parameter.get("n_arity").getParameterValue())); ;
					}else {
						selectionToReturn =  new TournamentSelection(Integer.parseInt(parameter.get("n_arity").getParameterValue())); ;
					}
					break;
					
				case"SpatialSpreadDeviationSelection":
					if((parameter.get("comparator")!= null) && (parameter.get("numberOfTournaments")!= null)) {
						selectionToReturn =  new SpatialSpreadDeviationSelection(new ObjectiveComparator<Solution<T>>(0),Integer.parseInt(parameter.get("numberOfTournaments").getParameterValue())); ;
					}else {
						selectionToReturn =  new SpatialSpreadDeviationSelection(Integer.parseInt(parameter.get("numberOfTournaments").getParameterValue())); ;
					}
					break;
					
				case"NaryTournamentSelection":
					if((parameter.get("numberOfSolutionsToBeReturned")!= null) && (parameter.get("comparator")!= null)) {
						selectionToReturn =  new NaryTournamentSelection(Integer.parseInt(parameter.get("numberOfSolutionsToBeReturned").getParameterValue()),  new DominanceComparator<Solution<T>>()); ;
					}else {
						selectionToReturn =  new NaryTournamentSelection();
					}
					break;
					
				case"RandomSelection":
					selectionToReturn =  new RandomSelection<T>();
					break;
			}
			
			return selectionToReturn;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public SelectionOperator<List<T>, List<T>> setdobleListSelection(WebPageSelection selection, Problem<T> selectionProblem){
			SelectionOperator<List<T>, List<T>> selectionToReturn = null;
			switch (selection.getSelectionName()) {
			case"NaryRandomSelection":
				if((parameter.get("numberOfSolutionsToBeReturned")!= null)) {
					selectionToReturn = new NaryRandomSelection(Integer.parseInt(parameter.get("numberOfSolutionsToBeReturned").getParameterValue())); ;
				}else {
					selectionToReturn =  new NaryRandomSelection(); ;
				}
				break;
				
			case"RankingAndPreferenceSelection":
				if (parameter.get("interestPoint") != null) {
					String[] list = parameter.get("interestPoint").getParameterValue().split(",");
					List<Double> interestPoint = new ArrayList<>();
					for (String l : list) {
						interestPoint.add(Double.parseDouble(l));
					}
					selectionToReturn = new RankingAndPreferenceSelection(Integer.parseInt(parameter.get("solutionsToSelect").getParameterValue()), interestPoint, Double.parseDouble(parameter.get("epsilon").getParameterValue())); ;
				}else {
					
				}
				break;
			
			case"RankingAndCrowdingSelection":
				if((parameter.get("solutionsToSelect")!= null) && (parameter.get("dominanceComparator")!= null)) {
					selectionToReturn = new RankingAndCrowdingSelection(Integer.parseInt(parameter.get("solutionsToSelect").getParameterValue()), new DominanceComparator<Solution<T>>()); ;
				}else {
					selectionToReturn = new RankingAndCrowdingSelection(Integer.parseInt(parameter.get("solutionsToSelect").getParameterValue())); ;
				}
				break;
				
			case"RankingAndDirScoreSelection":
				if(parameter.get("referenceVectors") != null) {
//					double[][] referenceVectors = VectorFileUtils.readVectors("MOEAD_Weights/W" + problem.getNumberOfObjectives() + "D_" + populationSize + ".dat");
//					selectionToReturn = new RankingAndDirScoreSelection(Integer.parseInt(parameter.get("solutionsToSelect").getParameterValue()),  new DominanceComparator<>(), referenceVectors); ;
//
//				}else {
//					
//					selectionToReturn = new RankingAndDirScoreSelection(Integer.parseInt(parameter.get("solutionsToSelect").getParameterValue()),  new DominanceComparator<>(), Double.parseDouble(parameter.get("epsilon").getParameterValue())); ;
				}
				break;
			}
			
			return selectionToReturn;
		}
		
	}
	
}
