package jmetal.database;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jmetal.javaclass.Parameter;
import jmetal.javaclass.WebPageAlgorithm;
import jmetal.javaclass.WebPageCrossover;
import jmetal.javaclass.WebPageMutation;
import jmetal.javaclass.WebPageProblem;
import jmetal.javaclass.WebPageSelection;
import jmetal.javarepository.AlgorithmRepository;
import jmetal.javarepository.CrossoverRepository;
import jmetal.javarepository.MutationRepository;
import jmetal.javarepository.ParameterRepository;
import jmetal.javarepository.ProblemRepository;
import jmetal.javarepository.SelectionRepository;

/**
 * 
 * @author Harender
 *
 */

@RestController
public class DatabaseInformationLoader {

	@Autowired
	private ParameterRepository parameterRepository;
	@Autowired
	private ProblemRepository problemRepository;
	@Autowired
	private CrossoverRepository crossoverRepository;
	@Autowired
	private MutationRepository mutationRepository;
	@Autowired
	private SelectionRepository selectionRepository;
	@Autowired
	private AlgorithmRepository algorithmRepository;

	@PostConstruct
	private void initDatabase() {

		createProblemsData();
		createCrossoverData();
		createMutationData();
		createSelectionData();
		createAlgoritmsData();
	}

	private void createAlgoritmsData() {
		if (algorithmRepository.count() == 0) {
			Parameter par2 = new Parameter("SelectionOperator-singleList", "selectionOperator");
			Parameter par3 = new Parameter("CrossoverOperator", "crossoverOperator");
			Parameter par4 = new Parameter("MutationOperator", "mutationOperator");
			Parameter par5 = new Parameter("int", "maxEvaluations");
			Parameter par6 = new Parameter("int", "n");
			Parameter par7 = new Parameter("int", "m");
			Parameter par8 = new Parameter("double", "rho");
			Parameter par9 = new Parameter("double", "fbs");
			Parameter par10 = new Parameter("double", "fa");
			Parameter par11 = new Parameter("double", "pd");
			Parameter par12 = new Parameter("int", "attemptsToSettle");

			parameterRepository.save(par2);
			parameterRepository.save(par3);
			parameterRepository.save(par4);
			parameterRepository.save(par5);
			parameterRepository.save(par6);
			parameterRepository.save(par7);
			parameterRepository.save(par8);
			parameterRepository.save(par9);
			parameterRepository.save(par10);
			parameterRepository.save(par11);
			parameterRepository.save(par12);

			List<Parameter> parameterLists = new LinkedList<Parameter>();
			parameterLists.add(par2);
			parameterLists.add(par3);
			parameterLists.add(par4);
			parameterLists.add(par5);
			parameterLists.add(par6);
			parameterLists.add(par7);
			parameterLists.add(par8);
			parameterLists.add(par9);
			parameterLists.add(par10);
			parameterLists.add(par11);
			parameterLists.add(par12);

			String description = "The Coral Reefs Optimization (CRO) algorithm is a novel global-search method based on corals' biology and coral reefs formation. The algorithm is based on a reef of solutions to a given optimization problem (corals), that reproduce in a similar way as corals do in Nature (Broadcast spawning, brooding and budding operators are implemented). In addition, the larvae (new individuals) fight for space in the reef against other existing corals, and even weak solutions have the possibility of surviving, if there is enough room in the reef.";
			// --------------------------------------CoralReefsOptimization--------------------------------------
			
			WebPageAlgorithm ag1 = new WebPageAlgorithm("CoralReefsOptimization", "GENERIC", 12, parameterLists, description);
			algorithmRepository.save(ag1);

			// --------------------------------------ElitistEvolutionStrategy--------------------------------------

			Parameter par14 = new Parameter("int", "mu");
			Parameter par15 = new Parameter("int", "lambda");
			Parameter par16 = new Parameter("int", "maxEvaluations");
			Parameter par17 = new Parameter("MutationOperator", "mutationOperator");
			parameterRepository.save(par14);
			parameterRepository.save(par15);
			parameterRepository.save(par16);
			parameterRepository.save(par17);
			parameterLists.clear();
			parameterLists.add(par14);
			parameterLists.add(par15);
			parameterLists.add(par16);
			parameterLists.add(par17);
			description = "Evolutionary algorithms have been applied successfully in many fields. However, evolutionary algorithms cannot find an optimal solution on many occasions because the balance between exploration and exploitation is lost in runs. A practical variant of the general process of building a new population is to allow the best organisms from the current generation to move on to the next, undisturbed. This strategy is known as elitist selection and ensures that the quality of the solution obtained by the GA will not diminish from one generation to the next.";
			WebPageAlgorithm ag2 = new WebPageAlgorithm("ElitistEvolutionStrategy", "GENERIC", 5, parameterLists, description);
			algorithmRepository.save(ag2);

			// --------------------------------------NonElitistEvolutionStrategy--------------------------------------

			Parameter par19 = new Parameter("int", "mu");
			Parameter par20 = new Parameter("int", "lambda");
			Parameter par21 = new Parameter("int", "maxEvaluations");
			Parameter par22 = new Parameter("MutationOperator", "mutationOperator");
			parameterRepository.save(par19);
			parameterRepository.save(par20);
			parameterRepository.save(par21);
			parameterRepository.save(par22);
			parameterLists.clear();
			parameterLists.add(par19);
			parameterLists.add(par20);
			parameterLists.add(par21);
			parameterLists.add(par22);
			description = "Evolutionary strategies are a type of evolutionary algorithms that are mainly characterized by: The selection of individuals for recombination is impartial and is a deterministic process, they differ from the rest of the Evolutionary Algorithms mainly by the form of the mutation operator and are mainly applied in continuous optimization problems where the representation is through vectors of real numbers. Evolutionary algorithms have been applied successfully in many fields. the strategy known as elitist selection that ensures that the quality of the solution obtained by the GA will not decrease from one generation to the next is not used in NonElitistEvolutionStrategy.";
			WebPageAlgorithm ag3 = new WebPageAlgorithm("NonElitistEvolutionStrategy", "GENERIC", 5, parameterLists, description);
			algorithmRepository.save(ag3);

			// --------------------------------------GenerationalGeneticAlgorithm--------------------------------------

			Parameter par24 = new Parameter("SelectionOperator-singleList", "selectionOperator");
			Parameter par25 = new Parameter("CrossoverOperator", "crossoverOperator");
			Parameter par26 = new Parameter("MutationOperator", "mutationOperator");
			Parameter par27 = new Parameter("int", "maxEvaluations");
			Parameter par28 = new Parameter("int", "populationSize");
			parameterRepository.save(par24);
			parameterRepository.save(par25);
			parameterRepository.save(par26);
			parameterRepository.save(par27);
			parameterRepository.save(par28);
			parameterLists.clear();
			parameterLists.add(par24);
			parameterLists.add(par25);
			parameterLists.add(par26);
			parameterLists.add(par27);
			parameterLists.add(par28);
			description = "Genetic algorithms simulate the process of natural selection which means those species who can adapt to changes in their environment are able to survive and reproduce and go to next generation. In a generational GA the entire population gets replaced each generation (through the processes of selection and reproduction). There a temporal or intermediate population is used. Within the generation t, it is initializated with a fixed number of individuals from the previous population (elitism) and the offspring is insert there until the maximum size is reached. At that point this temporal population should the new population (at generation t+1), a new temporary populatiion is created and the process starts once again.";
			WebPageAlgorithm ag4 = new WebPageAlgorithm("GenerationalGeneticAlgorithm", "GENERIC", 6, parameterLists, description);
			algorithmRepository.save(ag4);

			// --------------------------------------SteadyStateGeneticAlgorithm--------------------------------------

			Parameter par30 = new Parameter("SelectionOperator-singleList", "selectionOperator");
			Parameter par31 = new Parameter("CrossoverOperator", "crossoverOperator");
			Parameter par32 = new Parameter("MutationOperator", "mutationOperator");
			Parameter par33 = new Parameter("int", "maxEvaluations");
			Parameter par34 = new Parameter("int", "populationSize");
			parameterRepository.save(par30);
			parameterRepository.save(par31);
			parameterRepository.save(par32);
			parameterRepository.save(par33);
			parameterRepository.save(par34);
			parameterLists.clear();
			parameterLists.add(par30);
			parameterLists.add(par31);
			parameterLists.add(par32);
			parameterLists.add(par33);
			parameterLists.add(par34);
			description = "Genetic algorithms simulate the process of natural selection which means those species who can adapt to changes in their environment are able to survive and reproduce and go to next generation. The steady state GA, in it two parents are selected and crossed obtaining two offspring that are mutated and inserted in the population. There is only one population where the offspring is inserted.";
			WebPageAlgorithm ag5 = new WebPageAlgorithm("SteadyStateGeneticAlgorithm", "GENERIC", 6, parameterLists, description);
			algorithmRepository.save(ag5);

			// --------------------------------------DifferentialEvolution--------------------------------------

			Parameter par35 = new Parameter("DifferentialEvolutionCrossover", "crossoverOperator");
			Parameter par36 = new Parameter("DifferentialEvolutionSelection", "selectionOperator");
			Parameter par37 = new Parameter("int", "maxEvaluations");
			Parameter par38 = new Parameter("int", "populationSize");
			parameterRepository.save(par35);
			parameterRepository.save(par36);
			parameterRepository.save(par37);
			parameterRepository.save(par38);
			parameterLists.clear();
			parameterLists.add(par35);
			parameterLists.add(par36);
			parameterLists.add(par37);
			parameterLists.add(par38);
			description = "Differential evolution algorithm is a novel evolutionary algorithm on the basis of genetic algorithms. Its main idea is to generate a temporary individual based on individual differences within populations and then randomly restructure population evolutionary. The algorithm has better global convergence and robustness, very suitable for solving a variety of numerical optimization problems.";
			WebPageAlgorithm ag6 = new WebPageAlgorithm("DifferentialEvolution", "DoubleSolution", 5, parameterLists, description);
			algorithmRepository.save(ag6);

			// --------------------------------------CovarianceMatrixAdaptationEvolutionStrategy--------------------------------------

			Parameter par45 = new Parameter("int-opcional", "lambda");
			Parameter par46 = new Parameter("int-opcional", "maxEvaluations");
			Parameter par47 = new Parameter("double-opcional", "sigma");
			parameterRepository.save(par45);
			parameterRepository.save(par46);
			parameterRepository.save(par47);
			parameterLists.clear();
			parameterLists.add(par45);
			parameterLists.add(par46);
			parameterLists.add(par47);
			description = "CMA-ES belongs to Evolution Strategy, its a derivative-free method which is a practical optimization tool for continuous optimization problems. It optimizes a black-box objective function over a well-defined parameter space in which feature functions are often defined manually. Therefore, the performance of those techniques strongly depends on the quality of the chosen features or the underlying parametric function space.";
			WebPageAlgorithm ag7 = new WebPageAlgorithm("CovarianceMatrixAdaptationEvolutionStrategy", "DoubleSolution",
					0, parameterLists, description);
			algorithmRepository.save(ag7);

			// --------------------------------------StandardPSO2007--------------------------------------

			Parameter par39 = new Parameter("int", "swarmSize");
			Parameter par40 = new Parameter("int", "maxIterations");
			Parameter par41 = new Parameter("int", "numberOfParticlesToInform");
			parameterRepository.save(par39);
			parameterRepository.save(par40);
			parameterRepository.save(par41);
			parameterLists.clear();
			parameterLists.add(par39);
			parameterLists.add(par40);
			parameterLists.add(par41);
			description = "PSO is a computational method that optimizes a problem by iteratively trying to improve a candidate solution with regard to a given measure of quality. A basic variant of the PSO algorithm works by having a population (called a swarm) of candidate solutions (called particles). These particles are moved around in the search-space according to a few simple formulae. The movements of the particles are guided by their own best known position in the search-space as well as the entire swarm's best known position. When improved positions are being discovered these will then come to guide the movements of the swarm. The process is repeated and by doing so it is hoped, but not guaranteed, that a satisfactory solution will eventually be discovered. SPSO2007 automatically calculates by a formula depending on the dimension of the search space.";
			WebPageAlgorithm ag8 = new WebPageAlgorithm("StandardPSO2007", "DoubleSolution", 4, parameterLists, description);
			algorithmRepository.save(ag8);

			// --------------------------------------StandardPSO2011--------------------------------------

			Parameter par42 = new Parameter("int", "swarmSize");
			Parameter par43 = new Parameter("int", "maxIterations");
			Parameter par44 = new Parameter("int", "numberOfParticlesToInform");
			parameterRepository.save(par42);
			parameterRepository.save(par43);
			parameterRepository.save(par44);
			parameterLists.clear();
			parameterLists.add(par42);
			parameterLists.add(par43);
			parameterLists.add(par44);
			description = "PSO is a computational method that optimizes a problem by iteratively trying to improve a candidate solution with regard to a given measure of quality. A basic variant of the PSO algorithm works by having a population (called a swarm) of candidate solutions (called particles). These particles are moved around in the search-space according to a few simple formulae. The movements of the particles are guided by their own best known position in the search-space as well as the entire swarm's best known position. When improved positions are being discovered these will then come to guide the movements of the swarm. The process is repeated and by doing so it is hoped, but not guaranteed, that a satisfactory solution will eventually be discovered. SPSO2011 is a major improvement with its adaptive random topology and rotational invariance.";
			WebPageAlgorithm ag9 = new WebPageAlgorithm("StandardPSO2011", "DoubleSolution", 4, parameterLists, description);
			algorithmRepository.save(ag9);
		}
	}

	private void createSelectionData() {
		if (selectionRepository.count() == 0) {
			Parameter par1 = new Parameter("Comparator-GENERIC", "comparator");
			parameterRepository.save(par1);

			List<Parameter> parameterLists = new LinkedList<Parameter>();
			parameterLists.add(par1);

			// ---------------------------------BinaryTournamentSelection----------------------------------
			WebPageSelection sl1 = new WebPageSelection("BinaryTournamentSelection", "GENERIC", "singleList", 0);
			selectionRepository.save(sl1);
			WebPageSelection sl2 = new WebPageSelection("BinaryTournamentSelection", "GENERIC", "singleList", 1,
					parameterLists);
			selectionRepository.save(sl2);

			// ---------------------------------BestSolutionSelection----------------------------------

			Parameter par2 = new Parameter("Comparator-GENERIC", "comparator");
			parameterRepository.save(par2);
			parameterLists.clear();
			parameterLists.add(par2);
			WebPageSelection sl3 = new WebPageSelection("BestSolutionSelection", "GENERIC", "singleList", 1,
					parameterLists);
			selectionRepository.save(sl3);

			// ---------------------------------TournamentSelection----------------------------------

			Parameter par3 = new Parameter("int", "n_arity");
			parameterRepository.save(par3);

			parameterLists.clear();
			parameterLists.add(par3);
			WebPageSelection sl4 = new WebPageSelection("TournamentSelection", "GENERIC", "singleList", 1,
					parameterLists);
			selectionRepository.save(sl4);

			Parameter par4 = new Parameter("Comparator-GENERIC", "comparator");
			Parameter par5 = new Parameter("int", "n_arity");
			parameterRepository.save(par4);
			parameterRepository.save(par5);
			parameterLists.clear();
			parameterLists.add(par4);
			parameterLists.add(par5);
			WebPageSelection sl5 = new WebPageSelection("TournamentSelection", "GENERIC", "singleList", 2,
					parameterLists);
			selectionRepository.save(sl5);

			// ---------------------------------SpatialSpreadDeviationSelection----------------------------------

			Parameter par7 = new Parameter("Comparator-GENERIC", "comparator");
			Parameter par8 = new Parameter("int", "numberOfTournaments");
			parameterRepository.save(par7);
			parameterRepository.save(par8);
			parameterLists.clear();
			parameterLists.add(par7);
			parameterLists.add(par8);
			WebPageSelection sl7 = new WebPageSelection("SpatialSpreadDeviationSelection", "GENERIC", "singleList", 2,
					parameterLists);
			selectionRepository.save(sl7);

			// ---------------------------------DifferentialEvolutionSelection----------------------------------

			WebPageSelection sl8 = new WebPageSelection("DifferentialEvolutionSelection", "DoubleSolution", "dobleList",
					0);
			selectionRepository.save(sl8);

			Parameter par9 = new Parameter("BoundedRandomGenerator-Integer", "jRandomGenerator");
			parameterRepository.save(par9);
			parameterLists.clear();
			parameterLists.add(par9);
			WebPageSelection sl9 = new WebPageSelection("DifferentialEvolutionSelection", "DoubleSolution", "dobleList",
					1, parameterLists);
			selectionRepository.save(sl9);
			// ---------------------------------NaryRandomSelection----------------------------------

			WebPageSelection sl10 = new WebPageSelection("NaryRandomSelection", "GENERIC", "dobleList", 0);
			selectionRepository.save(sl10);

			Parameter par10 = new Parameter("int", "numberOfSolutionsToBeReturned");
			parameterRepository.save(par10);
			parameterLists.clear();
			parameterLists.add(par10);
			WebPageSelection sl11 = new WebPageSelection("NaryRandomSelection", "GENERIC", "dobleList", 1,
					parameterLists);
			selectionRepository.save(sl11);

			// ---------------------------------NaryTournamentSelection----------------------------------

			WebPageSelection sl12 = new WebPageSelection("NaryTournamentSelection", "GENERIC", "singleList", 0);
			selectionRepository.save(sl12);

			Parameter par11 = new Parameter("Comparator-GENERIC", "comparator");
			Parameter par12 = new Parameter("int", "numberOfSolutionsToBeReturned");
			parameterRepository.save(par11);
			parameterRepository.save(par12);
			parameterLists.clear();
			parameterLists.add(par11);
			parameterLists.add(par12);
			WebPageSelection sl13 = new WebPageSelection("NaryTournamentSelection", "GENERIC", "singleList", 1,
					parameterLists);
			selectionRepository.save(sl13);

			// ---------------------------------RandomSelection----------------------------------

			WebPageSelection sl14 = new WebPageSelection("RandomSelection", "GENERIC", "singleList", 0);
			selectionRepository.save(sl14);

			// ---------------------------------RankingAndPreferenceSelection----------------------------------

			Parameter par13 = new Parameter("int", "solutionsToSelect");
			Parameter par14 = new Parameter("List-Double", "interestPoint");
			Parameter par15 = new Parameter("double", "epsilon");
			parameterRepository.save(par13);
			parameterRepository.save(par14);
			parameterRepository.save(par15);
			parameterLists.clear();
			parameterLists.add(par13);
			parameterLists.add(par14);
			parameterLists.add(par15);
			WebPageSelection sl15 = new WebPageSelection("RankingAndPreferenceSelection", "GENERIC", "dobleList", 3,
					parameterLists);
			selectionRepository.save(sl15);

			// ---------------------------------RankingAndCrowdingSelection----------------------------------

			Parameter par16 = new Parameter("int", "solutionsToSelect");
			parameterRepository.save(par16);
			parameterLists.clear();
			parameterLists.add(par16);
			WebPageSelection sl16 = new WebPageSelection("RankingAndCrowdingSelection", "GENERIC", "dobleList", 1,
					parameterLists);
			selectionRepository.save(sl16);

			Parameter par17 = new Parameter("Comparator-GENERIC", "comparator");
			Parameter par18 = new Parameter("int", "solutionsToSelect");
			parameterRepository.save(par17);
			parameterRepository.save(par18);
			parameterLists.clear();
			parameterLists.add(par17);
			parameterLists.add(par18);
			WebPageSelection sl17 = new WebPageSelection("RankingAndCrowdingSelection", "GENERIC", "dobleList", 1,
					parameterLists);
			selectionRepository.save(sl17);
			// ---------------------------------RankingAndDirScoreSelection----------------------------------

			Parameter par19 = new Parameter("Comparator-GENERIC", "comparator");
			Parameter par20 = new Parameter("int", "solutionsToSelect");
			Parameter par21 = new Parameter("double[][]", "referenceVectors");
			parameterRepository.save(par19);
			parameterRepository.save(par20);
			parameterRepository.save(par21);
			parameterLists.clear();
			parameterLists.add(par19);
			parameterLists.add(par20);
			parameterLists.add(par21);
			WebPageSelection sl18 = new WebPageSelection("RankingAndDirScoreSelection", "GENERIC", "dobleList", 1,
					parameterLists);
			selectionRepository.save(sl18);
		}
	}

	private void createMutationData() {
		if (mutationRepository.count() == 0) {
			Parameter par1 = new Parameter("double", "mutationProbability");
			parameterRepository.save(par1);

			// ---------------------------------BitFlipMutation----------------------------------

			List<Parameter> parameterLists = new LinkedList<Parameter>();
			parameterLists.add(par1);
			WebPageMutation mu1 = new WebPageMutation("BitFlipMutation", "BinarySolution", 1, parameterLists);
			mutationRepository.save(mu1);

			Parameter par2 = new Parameter("double", "mutationProbability");
			Parameter par3 = new Parameter("RandomGenerator-Double", "mutationRandomGenerator");
			parameterRepository.save(par2);
			parameterRepository.save(par3);
			parameterLists.clear();
			parameterLists.add(par2);
			parameterLists.add(par3);
			WebPageMutation mu2 = new WebPageMutation("BitFlipMutation", "BinarySolution", 2, parameterLists);
			mutationRepository.save(mu2);

			// ---------------------------------PermutationSwapMutation----------------------------------

			Parameter par4 = new Parameter("double", "mutationProbability");
			parameterRepository.save(par4);
			parameterLists.clear();
			parameterLists.add(par4);
			WebPageMutation mu3 = new WebPageMutation("PermutationSwapMutation", "PermutationSolution", 1, parameterLists);
			mutationRepository.save(mu3);

			Parameter par5 = new Parameter("double", "mutationProbability");
			Parameter par6 = new Parameter("RandomGenerator-Double", "mutationRandomGenerator");
			parameterRepository.save(par5);
			parameterRepository.save(par6);
			parameterLists.clear();
			parameterLists.add(par5);
			parameterLists.add(par6);
			WebPageMutation mu4 = new WebPageMutation("PermutationSwapMutation", "PermutationSolution", 2, parameterLists);
			mutationRepository.save(mu4);

			Parameter par10 = new Parameter("double", "mutationProbability");
			Parameter par11 = new Parameter("RandomGenerator-Double", "mutationRandomGenerator");
			Parameter par12 = new Parameter("BoundedRandomGenerator-Integer", "jRandomGenerator");
			parameterRepository.save(par10);
			parameterRepository.save(par11);
			parameterRepository.save(par12);
			parameterLists.clear();
			parameterLists.add(par10);
			parameterLists.add(par11);
			parameterLists.add(par12);
			WebPageMutation mu5 = new WebPageMutation("PermutationSwapMutation", "PermutationSolution", 3, parameterLists);
			mutationRepository.save(mu5);

			// ---------------------------------NullMutation----------------------------------

			WebPageMutation mu6 = new WebPageMutation("NullMutation", "Generic", 0);
			mutationRepository.save(mu6);

			// ---------------------------------CDGMutation----------------------------------

			WebPageMutation mu7 = new WebPageMutation("CDGMutation", "DoubleSolution", 0);
			mutationRepository.save(mu7);

			Parameter par13 = new Parameter("DoubleProblem", "CDGproblem");
			Parameter par14 = new Parameter("double", "delta");
			parameterRepository.save(par13);
			parameterRepository.save(par14);

			parameterLists.clear();
			parameterLists.add(par13);
			parameterLists.add(par14);
			WebPageMutation mu8 = new WebPageMutation("CDGMutation", "DoubleSolution", 2, parameterLists);
			mutationRepository.save(mu8);

			Parameter par15 = new Parameter("double", "mutationProbability");
			Parameter par16 = new Parameter("double", "delta");
			parameterRepository.save(par15);
			parameterRepository.save(par16);
			parameterLists.clear();
			parameterLists.add(par15);
			parameterLists.add(par16);
			WebPageMutation mu9 = new WebPageMutation("CDGMutation", "DoubleSolution", 2, parameterLists);
			mutationRepository.save(mu9);

			Parameter par17 = new Parameter("double", "mutationProbability");
			Parameter par18 = new Parameter("double", "delta");
			Parameter par19 = new Parameter("RepairDoubleSolution", "mutationSolutionRepair");
			parameterRepository.save(par17);
			parameterRepository.save(par18);
			parameterRepository.save(par19);
			parameterLists.clear();
			parameterLists.add(par17);
			parameterLists.add(par18);
			parameterLists.add(par19);
			WebPageMutation mu10 = new WebPageMutation("CDGMutation", "DoubleSolution", 3, parameterLists);
			mutationRepository.save(mu10);

			// ---------------------------------NonUniformMutation----------------------------------

			Parameter par20 = new Parameter("double", "mutationProbability");
			Parameter par21 = new Parameter("double", "perturbation");
			Parameter par22 = new Parameter("int", "maxIterations");
			parameterRepository.save(par20);
			parameterRepository.save(par21);
			parameterRepository.save(par22);
			parameterLists.clear();
			parameterLists.add(par20);
			parameterLists.add(par21);
			parameterLists.add(par22);
			WebPageMutation mu11 = new WebPageMutation("NonUniformMutation", "DoubleSolution", 3, parameterLists);
			mutationRepository.save(mu11);

			Parameter par23 = new Parameter("double", "mutationProbability");
			Parameter par24 = new Parameter("double", "perturbation");
			Parameter par25 = new Parameter("int", "maxIterations");
			Parameter par26 = new Parameter("RandomGenerator-Double", "mutationRandomGenerator");
			parameterRepository.save(par23);
			parameterRepository.save(par24);
			parameterRepository.save(par25);
			parameterRepository.save(par26);
			parameterLists.clear();
			parameterLists.add(par23);
			parameterLists.add(par24);
			parameterLists.add(par25);
			parameterLists.add(par26);
			WebPageMutation mu12 = new WebPageMutation("NonUniformMutation", "DoubleSolution", 4, parameterLists);
			mutationRepository.save(mu12);

			// ---------------------------------PolynomialMutation----------------------------------
			Parameter par27 = new Parameter("DoubleProblem", "PMproblem");
			Parameter par28 = new Parameter("double", "distributionIndex");
			parameterRepository.save(par27);
			parameterRepository.save(par28);
			parameterLists.clear();
			parameterLists.add(par27);
			parameterLists.add(par28);
			WebPageMutation mu13 = new WebPageMutation("PolynomialMutation", "DoubleSolution", 2, parameterLists);
			mutationRepository.save(mu13);

			Parameter par29 = new Parameter("DoubleProblem", "PMproblem");
			Parameter par30 = new Parameter("double", "distributionIndex");
			Parameter par31 = new Parameter("RandomGenerator-Double", "mutationRandomGenerator");
			parameterRepository.save(par29);
			parameterRepository.save(par30);
			parameterRepository.save(par31);
			parameterLists.clear();
			parameterLists.add(par29);
			parameterLists.add(par30);
			parameterLists.add(par31);
			WebPageMutation mu14 = new WebPageMutation("PolynomialMutation", "DoubleSolution", 3, parameterLists);
			mutationRepository.save(mu14);

			Parameter par32 = new Parameter("double", "mutationProbability");
			Parameter par33 = new Parameter("double", "distributionIndex");
			parameterRepository.save(par32);
			parameterRepository.save(par33);
			parameterLists.clear();
			parameterLists.add(par32);
			parameterLists.add(par33);
			WebPageMutation mu15 = new WebPageMutation("PolynomialMutation", "DoubleSolution", 2, parameterLists);
			mutationRepository.save(mu15);

			Parameter par34 = new Parameter("double", "mutationProbability");
			Parameter par35 = new Parameter("double", "distributionIndex");
			Parameter par36 = new Parameter("RandomGenerator-Double", "mutationRandomGenerator");
			parameterRepository.save(par34);
			parameterRepository.save(par35);
			parameterRepository.save(par36);
			parameterLists.clear();
			parameterLists.add(par34);
			parameterLists.add(par35);
			parameterLists.add(par36);
			WebPageMutation mu16 = new WebPageMutation("PolynomialMutation", "DoubleSolution", 3, parameterLists);
			mutationRepository.save(mu16);

			Parameter par37 = new Parameter("double", "mutationProbability");
			Parameter par38 = new Parameter("double", "distributionIndex");
			Parameter par39 = new Parameter("RepairDoubleSolution", "mutationSolutionRepair");
			parameterRepository.save(par37);
			parameterRepository.save(par38);
			parameterRepository.save(par39);
			parameterLists.clear();
			parameterLists.add(par37);
			parameterLists.add(par38);
			parameterLists.add(par39);
			WebPageMutation mu17 = new WebPageMutation("PolynomialMutation", "DoubleSolution", 3, parameterLists);
			mutationRepository.save(mu17);

			Parameter par40 = new Parameter("double", "mutationProbability");
			Parameter par41 = new Parameter("double", "distributionIndex");
			Parameter par42 = new Parameter("RepairDoubleSolution", "mutationSolutionRepair");
			Parameter par43 = new Parameter("RandomGenerator-Double", "mutationRandomGenerator");
			parameterRepository.save(par40);
			parameterRepository.save(par41);
			parameterRepository.save(par42);
			parameterRepository.save(par43);
			parameterLists.clear();
			parameterLists.add(par40);
			parameterLists.add(par41);
			parameterLists.add(par42);
			parameterLists.add(par43);
			WebPageMutation mu18 = new WebPageMutation("PolynomialMutation", "DoubleSolution", 4, parameterLists);
			mutationRepository.save(mu18);

			// ---------------------------------SimpleRandomMutation----------------------------------

			Parameter par44 = new Parameter("double", "mutationProbability");
			parameterRepository.save(par44);
			parameterLists.clear();
			parameterLists.add(par44);
			WebPageMutation mu19 = new WebPageMutation("SimpleRandomMutation", "DoubleSolution", 1, parameterLists);
			mutationRepository.save(mu19);

			Parameter par45 = new Parameter("double", "mutationProbability");
			Parameter par46 = new Parameter("RandomGenerator-Double", "mutationRandomGenerator");
			parameterRepository.save(par45);
			parameterRepository.save(par46);
			parameterLists.clear();
			parameterLists.add(par45);
			parameterLists.add(par46);
			WebPageMutation mu20 = new WebPageMutation("SimpleRandomMutation", "DoubleSolution", 2, parameterLists);
			mutationRepository.save(mu20);

			// ---------------------------------UniformMutation----------------------------------

			Parameter par47 = new Parameter("double", "mutationProbability");
			Parameter par48 = new Parameter("double", "perturbation");
			parameterRepository.save(par47);
			parameterRepository.save(par48);
			parameterLists.clear();
			parameterLists.add(par47);
			parameterLists.add(par48);
			WebPageMutation mu21 = new WebPageMutation("UniformMutation", "DoubleSolution", 2, parameterLists);
			mutationRepository.save(mu21);

			Parameter par49 = new Parameter("double", "mutationProbability");
			Parameter par50 = new Parameter("double", "perturbation");
			Parameter par51 = new Parameter("RandomGenerator-Double", "mutationRandomGenerator");
			parameterRepository.save(par49);
			parameterRepository.save(par50);
			parameterRepository.save(par51);
			parameterLists.clear();
			parameterLists.add(par49);
			parameterLists.add(par50);
			parameterLists.add(par51);
			WebPageMutation mu22 = new WebPageMutation("UniformMutation", "DoubleSolution", 3, parameterLists);
			mutationRepository.save(mu22);

			// ---------------------------------IntegerPolynomialMutation----------------------------------

			Parameter par52 = new Parameter("IntegerProblem", "problem");
			Parameter par53 = new Parameter("double", "distributionIndex");
			parameterRepository.save(par52);
			parameterRepository.save(par53);
			parameterLists.clear();
			parameterLists.add(par52);
			parameterLists.add(par53);
			WebPageMutation mu23 = new WebPageMutation("IntegerPolynomialMutation", "IntergerSolution", 2,
					parameterLists);
			mutationRepository.save(mu23);

			Parameter par54 = new Parameter("double", "mutationProbability");
			Parameter par55 = new Parameter("double", "distributionIndex");
			parameterRepository.save(par54);
			parameterRepository.save(par55);
			parameterLists.clear();
			parameterLists.add(par54);
			parameterLists.add(par55);
			WebPageMutation mu24 = new WebPageMutation("IntegerPolynomialMutation", "IntergerSolution", 2,
					parameterLists);
			mutationRepository.save(mu24);

			Parameter par56 = new Parameter("double", "mutationProbability");
			Parameter par57 = new Parameter("double", "distributionIndex");
			Parameter par58 = new Parameter("RepairDoubleSolution", "mutationSolutionRepair");
			parameterRepository.save(par56);
			parameterRepository.save(par57);
			parameterRepository.save(par58);
			parameterLists.clear();
			parameterLists.add(par56);
			parameterLists.add(par57);
			parameterLists.add(par58);
			WebPageMutation mu25 = new WebPageMutation("IntegerPolynomialMutation", "IntergerSolution", 3,
					parameterLists);
			mutationRepository.save(mu25);

			Parameter par59 = new Parameter("double", "mutationProbability");
			Parameter par60 = new Parameter("double", "distributionIndex");
			Parameter par61 = new Parameter("RepairDoubleSolution", "mutationSolutionRepair");
			Parameter par62 = new Parameter("RandomGenerator-Double", "mutationRandomGenerator");
			parameterRepository.save(par59);
			parameterRepository.save(par60);
			parameterRepository.save(par61);
			parameterRepository.save(par62);
			parameterLists.clear();
			parameterLists.add(par59);
			parameterLists.add(par60);
			parameterLists.add(par61);
			parameterLists.add(par62);
			WebPageMutation mu26 = new WebPageMutation("IntegerPolynomialMutation", "IntergerSolution", 4,
					parameterLists);
			mutationRepository.save(mu26);
		}
	}

	private void createCrossoverData() {
		if (crossoverRepository.count() == 0) {
			Parameter par1 = new Parameter("double", "crossoverProbability");

			parameterRepository.save(par1);

			// -------------------BLXAlphaCrossover------------------
			List<Parameter> parameterLists = new LinkedList<Parameter>();
			parameterLists.add(par1);
			WebPageCrossover cr1 = new WebPageCrossover("BLXAlphaCrossover", "DoubleSolution", 1, parameterLists);
			crossoverRepository.save(cr1);

			Parameter par2 = new Parameter("double", "crossoverProbability");
			Parameter par3 = new Parameter("double", "alpha");
			parameterRepository.save(par2);
			parameterRepository.save(par3);
			parameterLists.clear();
			parameterLists.add(par2);
			parameterLists.add(par3);
			WebPageCrossover cr2 = new WebPageCrossover("BLXAlphaCrossover", "DoubleSolution", 2, parameterLists);
			crossoverRepository.save(cr2);

			Parameter par4 = new Parameter("double", "crossoverProbability");
			Parameter par5 = new Parameter("double", "alpha");
			Parameter par6 = new Parameter("RepairDoubleSolution", "crossoverSolutionRepair");
			parameterRepository.save(par4);
			parameterRepository.save(par5);
			parameterRepository.save(par6);
			parameterLists.clear();
			parameterLists.add(par4);
			parameterLists.add(par5);
			parameterLists.add(par6);
			WebPageCrossover cr3 = new WebPageCrossover("BLXAlphaCrossover", "DoubleSolution", 3, parameterLists);
			crossoverRepository.save(cr3);

			Parameter par7 = new Parameter("double", "crossoverProbability");
			Parameter par8 = new Parameter("double", "alpha");
			Parameter par9 = new Parameter("RepairDoubleSolution", "crossoverSolutionRepair");
			Parameter par10 = new Parameter("RandomGenerator-Double", "crossoverRandomGenerator");
			parameterRepository.save(par7);
			parameterRepository.save(par8);
			parameterRepository.save(par9);
			parameterRepository.save(par10);
			parameterLists.clear();
			parameterLists.add(par7);
			parameterLists.add(par8);
			parameterLists.add(par9);
			parameterLists.add(par10);
			WebPageCrossover cr4 = new WebPageCrossover("BLXAlphaCrossover", "DoubleSolution", 4, parameterLists);
			crossoverRepository.save(cr4);

			// -------------------DifferentialEvolutionCrossover------------------

			WebPageCrossover cr5 = new WebPageCrossover("DifferentialEvolutionCrossover", "DoubleSolution", 0);
			crossoverRepository.save(cr5);

			Parameter par11 = new Parameter("double", "crossoverProbability");
			Parameter par12 = new Parameter("double", "f");
			Parameter par13 = new Parameter("String", "variant");
			parameterRepository.save(par11);
			parameterRepository.save(par12);
			parameterRepository.save(par13);

			parameterLists.clear();
			parameterLists.add(par11);
			parameterLists.add(par12);
			parameterLists.add(par13);

			WebPageCrossover cr6 = new WebPageCrossover("DifferentialEvolutionCrossover", "DoubleSolution", 3,
					parameterLists);
			crossoverRepository.save(cr6);

			Parameter par14 = new Parameter("double", "crossoverProbability");
			Parameter par15 = new Parameter("double", "f");
			Parameter par16 = new Parameter("String", "variant");
			Parameter par17 = new Parameter("RandomGenerator-Double", "crossoverRandomGenerator");
			parameterRepository.save(par14);
			parameterRepository.save(par15);
			parameterRepository.save(par16);
			parameterRepository.save(par17);

			parameterLists.clear();
			parameterLists.add(par14);
			parameterLists.add(par15);
			parameterLists.add(par16);
			parameterLists.add(par17);
			WebPageCrossover cr7 = new WebPageCrossover("DifferentialEvolutionCrossover", "DoubleSolution", 4,
					parameterLists);
			crossoverRepository.save(cr7);

			Parameter par18 = new Parameter("double", "crossoverProbability");
			Parameter par19 = new Parameter("double", "f");
			Parameter par20 = new Parameter("String", "variant");
			parameterRepository.save(par18);
			parameterRepository.save(par19);
			parameterRepository.save(par20);
			Parameter par22 = new Parameter("BoundedRandomGenerator-Integer", "jRandomGenerator");
			Parameter par23 = new Parameter("BoundedRandomGenerator-Double", "crRandomGenerator");
			parameterRepository.save(par22);
			parameterRepository.save(par23);

			parameterLists.clear();
			parameterLists.add(par18);
			parameterLists.add(par19);
			parameterLists.add(par20);
			parameterLists.add(par22);
			parameterLists.add(par23);
			WebPageCrossover cr8 = new WebPageCrossover("DifferentialEvolutionCrossover", "DoubleSolution", 6,
					parameterLists);
			crossoverRepository.save(cr8);

			Parameter par24 = new Parameter("double", "crossoverProbability");
			Parameter par25 = new Parameter("double", "f");
			Parameter par26 = new Parameter("double", "k");
			Parameter par27 = new Parameter("String", "variant");
			parameterRepository.save(par24);
			parameterRepository.save(par25);
			parameterRepository.save(par26);
			parameterRepository.save(par27);
			parameterLists.clear();
			parameterLists.add(par24);
			parameterLists.add(par25);
			parameterLists.add(par26);
			parameterLists.add(par27);

			WebPageCrossover cr9 = new WebPageCrossover("DifferentialEvolutionCrossover", "DoubleSolution", 4,
					parameterLists);
			crossoverRepository.save(cr9);

			// -------------------SBXCrossover------------------

			Parameter par28 = new Parameter("double", "crossoverProbability");
			Parameter par29 = new Parameter("double", "distributionIndex");
			parameterRepository.save(par28);
			parameterRepository.save(par29);
			parameterLists.clear();
			parameterLists.add(par28);
			parameterLists.add(par29);
			WebPageCrossover cr10 = new WebPageCrossover("SBXCrossover", "DoubleSolution", 2, parameterLists);
			crossoverRepository.save(cr10);

			Parameter par30 = new Parameter("double", "crossoverProbability");
			Parameter par31 = new Parameter("double", "distributionIndex");
			Parameter par32 = new Parameter("RandomGenerator-Double", "crossoverRandomGenerator");
			parameterRepository.save(par30);
			parameterRepository.save(par31);
			parameterRepository.save(par32);
			parameterLists.clear();
			parameterLists.add(par30);
			parameterLists.add(par31);
			parameterLists.add(par32);
			WebPageCrossover cr11 = new WebPageCrossover("SBXCrossover", "DoubleSolution", 3, parameterLists);
			crossoverRepository.save(cr11);

			Parameter par33 = new Parameter("double", "crossoverProbability");
			Parameter par34 = new Parameter("double", "distributionIndex");
			Parameter par35 = new Parameter("RepairDoubleSolution", "crossoverSolutionRepair");
			parameterRepository.save(par33);
			parameterRepository.save(par34);
			parameterRepository.save(par35);
			parameterLists.clear();
			parameterLists.add(par33);
			parameterLists.add(par34);
			parameterLists.add(par35);
			WebPageCrossover cr12 = new WebPageCrossover("SBXCrossover", "DoubleSolution", 3, parameterLists);
			crossoverRepository.save(cr12);

			Parameter par36 = new Parameter("double", "crossoverProbability");
			Parameter par37 = new Parameter("double", "distributionIndex");
			Parameter par38 = new Parameter("RepairDoubleSolution", "crossoverSolutionRepair");
			Parameter par39 = new Parameter("RandomGenerator-Double", "crossoverRandomGenerator");
			parameterRepository.save(par36);
			parameterRepository.save(par37);
			parameterRepository.save(par38);
			parameterRepository.save(par39);
			parameterLists.clear();
			parameterLists.add(par36);
			parameterLists.add(par37);
			parameterLists.add(par38);
			parameterLists.add(par39);
			WebPageCrossover cr13 = new WebPageCrossover("SBXCrossover", "DoubleSolution", 4, parameterLists);
			crossoverRepository.save(cr13);

			// -------------------HUXCrossover------------------

			Parameter par40 = new Parameter("double", "crossoverProbability");
			parameterRepository.save(par40);
			parameterLists.clear();
			parameterLists.add(par40);
			WebPageCrossover cr14 = new WebPageCrossover("HUXCrossover", "BinarySolution", 1, parameterLists);
			crossoverRepository.save(cr14);

			Parameter par41 = new Parameter("double", "crossoverProbability");
			Parameter par42 = new Parameter("RandomGenerator-Double", "crossoverRandomGenerator");
			parameterRepository.save(par41);
			parameterRepository.save(par42);
			parameterLists.clear();
			parameterLists.add(par41);
			parameterLists.add(par42);
			WebPageCrossover cr15 = new WebPageCrossover("HUXCrossover", "BinarySolution", 2, parameterLists);
			crossoverRepository.save(cr15);

			// -------------------SinglePointCrossover------------------

			Parameter par43 = new Parameter("double", "crossoverProbability");
			parameterRepository.save(par43);
			parameterLists.clear();
			parameterLists.add(par43);
			WebPageCrossover cr16 = new WebPageCrossover("SinglePointCrossover", "BinarySolution", 1, parameterLists);
			crossoverRepository.save(cr16);

			Parameter par44 = new Parameter("double", "crossoverProbability");
			Parameter par45 = new Parameter("RandomGenerator-Double", "crossoverRandomGenerator");
			parameterRepository.save(par44);
			parameterRepository.save(par45);
			parameterLists.clear();
			parameterLists.add(par44);
			parameterLists.add(par45);
			WebPageCrossover cr17 = new WebPageCrossover("SinglePointCrossover", "BinarySolution", 2, parameterLists);
			crossoverRepository.save(cr17);

			Parameter par46 = new Parameter("double", "crossoverProbability");
			Parameter par47 = new Parameter("RandomGenerator-Double", "crossoverRandomGenerator");
			Parameter par48 = new Parameter("BoundedRandomGenerator-Integer", "jRandomGenerator");
			parameterRepository.save(par46);
			parameterRepository.save(par47);
			parameterRepository.save(par48);
			parameterLists.clear();
			parameterLists.add(par46);
			parameterLists.add(par47);
			parameterLists.add(par48);
			WebPageCrossover cr18 = new WebPageCrossover("SinglePointCrossover", "BinarySolution", 3, parameterLists);
			crossoverRepository.save(cr18);

			// -------------------IntegerSBXCrossover------------------

			Parameter par49 = new Parameter("double", "crossoverProbability");
			Parameter par50 = new Parameter("double", "distributionIndex");
			parameterRepository.save(par49);
			parameterRepository.save(par50);
			parameterLists.clear();
			parameterLists.add(par49);
			parameterLists.add(par50);
			WebPageCrossover cr19 = new WebPageCrossover("IntegerSBXCrossover", "IntergerSolution", 2, parameterLists);
			crossoverRepository.save(cr19);

			Parameter par51 = new Parameter("double", "crossoverProbability");
			Parameter par52 = new Parameter("double", "distributionIndex");
			Parameter par53 = new Parameter("RandomGenerator-Double", "crossoverRandomGenerator");
			parameterRepository.save(par51);
			parameterRepository.save(par52);
			parameterRepository.save(par53);
			parameterLists.clear();
			parameterLists.add(par51);
			parameterLists.add(par52);
			parameterLists.add(par53);
			WebPageCrossover cr20 = new WebPageCrossover("IntegerSBXCrossover", "IntergerSolution", 3, parameterLists);
			crossoverRepository.save(cr20);

			// -------------------PMXCrossover------------------

			Parameter par54 = new Parameter("double", "crossoverProbability");
			parameterRepository.save(par54);
			parameterLists.clear();
			parameterLists.add(par54);
			WebPageCrossover cr21 = new WebPageCrossover("PMXCrossover", "PermutationSolution-Integer", 1,
					parameterLists);
			crossoverRepository.save(cr21);

			Parameter par55 = new Parameter("double", "crossoverProbability");
			Parameter par56 = new Parameter("RandomGenerator-Double", "crossoverRandomGenerator");
			parameterRepository.save(par55);
			parameterRepository.save(par56);
			parameterLists.clear();
			parameterLists.add(par55);
			parameterLists.add(par56);
			WebPageCrossover cr22 = new WebPageCrossover("PMXCrossover", "PermutationSolution-Integer", 2,
					parameterLists);
			crossoverRepository.save(cr22);

			Parameter par57 = new Parameter("double", "crossoverProbability");
			Parameter par58 = new Parameter("RandomGenerator-Double", "crossoverRandomGenerator");
			Parameter par59 = new Parameter("BoundedRandomGenerator-Integer", "jRandomGenerator");
			parameterRepository.save(par57);
			parameterRepository.save(par58);
			parameterRepository.save(par59);
			parameterLists.clear();
			parameterLists.add(par57);
			parameterLists.add(par58);
			parameterLists.add(par59);
			WebPageCrossover cr23 = new WebPageCrossover("PMXCrossover", "PermutationSolution-Integer", 3,
					parameterLists);
			crossoverRepository.save(cr23);

			// -------------------TwoPointCrossover------------------

			Parameter par60 = new Parameter("double", "crossoverProbability");
			parameterRepository.save(par60);
			parameterLists.clear();
			parameterLists.add(par60);
			WebPageCrossover cr24 = new WebPageCrossover("TwoPointCrossover", "GENERIC", 2, parameterLists);
			crossoverRepository.save(cr24);

			// -------------------NPointCrossover------------------

			Parameter par61 = new Parameter("double", "crossoverProbability");
			Parameter par62 = new Parameter("int", "numberOfCrossovers");
			parameterRepository.save(par61);
			parameterRepository.save(par62);
			parameterLists.clear();
			parameterLists.add(par61);
			parameterLists.add(par62);

			WebPageCrossover cr25 = new WebPageCrossover("NPointCrossover", "GENERIC", 2, parameterLists);
			crossoverRepository.save(cr25);

			Parameter par63 = new Parameter("int", "numberOfCrossovers");
			parameterRepository.save(par63);
			parameterLists.clear();
			parameterLists.add(par63);

			WebPageCrossover cr26 = new WebPageCrossover("NPointCrossover", "GENERIC", 2, parameterLists);
			crossoverRepository.save(cr26);

			// -------------------NullCrossover------------------

			WebPageCrossover cr27 = new WebPageCrossover("NullCrossover", "GENERIC", 0);
			crossoverRepository.save(cr27);
		}
	}

	private void createProblemsData() {
		if (problemRepository.count() == 0) {

			List<Parameter> parameterLists = new LinkedList<Parameter>();
			// ----------------------------------------Griewank-----------------------------------
			parameterLists.clear();
			Parameter par3 = new Parameter("int", "numberOfVariables");
			parameterRepository.save(par3);

			parameterLists.add(par3);
			String description ="The Griewank function is a function widely used to test the convergence of optimization functions. The Griewank function has many widespread local minima, which are regularly distributed. The function is continuous,  not convex,  unimodal and can be defined on n-dimensional space. This makes it difficult to obtain the minimum.";
			WebPageProblem pro2 = new WebPageProblem("org.uma.jmetal.problem.singleobjective.Griewank",
					"DoubleSolution", 1, true, description, parameterLists);
			problemRepository.save(pro2);

			// ----------------------------------------Rastrigin-----------------------------------
			parameterLists.clear();
			Parameter par4 = new Parameter("int", "numberOfVariables");
			parameterRepository.save(par4);

			parameterLists.add(par4);
			description = "The Rastrigin function is a non-convex function used as a performance test problem for optimization algorithms. It is a typical example of non-linear multimodal function. It was first proposed in 1974 as a 2-dimensional function and has been generalized. Finding the minimum of this function is a fairly difficult problem due to its large search space and its large number of local minima.";
			WebPageProblem pro3 = new WebPageProblem("org.uma.jmetal.problem.singleobjective.Rastrigin",
					"DoubleSolution", 1, true, description, parameterLists);
			problemRepository.save(pro3);

			// ----------------------------------------Rosenbrock-----------------------------------
			parameterLists.clear();
			Parameter par5 = new Parameter("int", "numberOfVariables");
			parameterRepository.save(par5);

			parameterLists.add(par5);
			description = "The Rosenbrock function is a non-convex function, which is used as a performance test problem for optimization algorithms. It is also known as Rosenbrock's valley or Rosenbrock's banana function. The global minimum is inside a long, narrow, parabolic shaped flat valley. To find the valley is trivial. To converge to the global minimum, however, is difficult.";
			WebPageProblem pro4 = new WebPageProblem("org.uma.jmetal.problem.singleobjective.Rosenbrock",
					"DoubleSolution", 1, true, description, parameterLists);
			problemRepository.save(pro4);

			// ----------------------------------------Sphere-----------------------------------
			parameterLists.clear();
			Parameter par6 = new Parameter("int", "numberOfVariables");
			parameterRepository.save(par6);

			parameterLists.add(par6);
			description = "The Sphere function has dimensions local minima except for the global one. It is continuous, convex and unimodal. The problem consist in computing the center of a minimal bounding sphere.";
			WebPageProblem pro5 = new WebPageProblem("org.uma.jmetal.problem.singleobjective.Sphere", "DoubleSolution",
					1, true, description, parameterLists);
			problemRepository.save(pro5);

			// ----------------------------------------TSP-----------------------------------
			Parameter par7 = new Parameter("String", "distanceFile");
			parameterRepository.save(par7);

			parameterLists.clear();
			parameterLists.add(par7);
			description = "The travelling salesman problem (TSP) asks the following question: Given a list of cities and the distances between each pair of cities, what is the shortest possible route that visits each city exactly once and returns to the origin city? It is an NP-hard problem in combinatorial optimization, important in theoretical computer science and operations research.";
			WebPageProblem pro6 = new WebPageProblem("org.uma.jmetal.problem.singleobjective.TSP",
					"PermutationSolution-Integer", 1, true, description, parameterLists);
			problemRepository.save(pro6);

			// ----------------------------------------NIntegerMin-----------------------------------
			Parameter par8 = new Parameter("int", "numberOfVariables");
			parameterRepository.save(par8);
			Parameter par9 = new Parameter("int", "n");
			Parameter par10 = new Parameter("int", "lowerBound");
			Parameter par11 = new Parameter("int", "upperBound");
			parameterRepository.save(par9);
			parameterRepository.save(par10);
			parameterRepository.save(par11);

			parameterLists.clear();
			parameterLists.add(par8);
			parameterLists.add(par9);
			parameterLists.add(par10);
			parameterLists.add(par11);
			description = "Single objective problem for testing integer encoding. Objective: minimizing the distance to value N.";
			WebPageProblem pro7 = new WebPageProblem("org.uma.jmetal.problem.singleobjective.NIntegerMin",
					"IntegerSolution", 1, true, description, parameterLists);
			problemRepository.save(pro7);

			// ----------------------------------------OneMax-----------------------------------
			Parameter par12 = new Parameter("int", "numberOfBits");
			parameterRepository.save(par12);

			parameterLists.clear();
			parameterLists.add(par12);
			description = "The problem consist of maximizing the number of '1's in a binary string.";
			WebPageProblem pro8 = new WebPageProblem("org.uma.jmetal.problem.singleobjective.OneMax", "BinarySolution",
					1, true, description, parameterLists);
			problemRepository.save(pro8);
		}
	}
}
