package manual;

import java.io.File;
import java.net.URL;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.junit.Test;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.fame.FAME;
import org.uma.jmetal.algorithm.singleobjective.coralreefsoptimization.CoralReefsOptimizationBuilder;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.Operator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.BLXAlphaCrossover;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.mutation.CDGMutation;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.operator.impl.selection.BestSolutionSelection;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.operator.impl.selection.SpatialSpreadDeviationSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.Rosenbrock;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.comparator.ObjectiveComparator;
import org.uma.jmetal.util.comparator.RankingAndSSDComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import jmetal.algorithms.CoralReefsOptimizationWebPage;
import jmetal.algorithms.GenerationalGeneticAlgorithmWebFormet;
import jmetal.algorithms.SteadyStateGeneticAlgorithmWebFormat;
import jmetal.problems.TSPWebPage;

public class test {
	/**
	 * Usage: java
	 * org.uma.jmetal.runner.singleobjective.NonElitistEvolutionStrategyRunner
	 */

	static volatile boolean continuar2;

	// public static void procA() throws InterruptedException {
	// System.out.println(write());
	// new Thread(() -> {
	// for (int i = 0; i < 5; i++) {
	// System.out.println("PA1 ");
	// System.out.println("PA2 ");
	// try {
	// procB();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// continuar2 = true;
	// while (continuar2) {
	// }
	// System.out.println();
	// }
	// }).start();
	// }
	//
	// public static void procB() throws InterruptedException {
	// new Thread(() -> {
	// System.out.println("PB1 ");
	// while (!continuar2) {
	// }
	// for (int i = 0; i < 100000; i++) {
	// System.out.print(i + ",");
	// }
	// System.out.println("-");
	// System.out.println("PB2 ");
	// continuar2 = false;
	// }).start();
	// }
	//
	// static private Semaphore continuar;
	//
	// public static void procesoA() {
	//
	// new Thread(() -> {
	// System.out.println(write());
	// continuar.release();
	// for (int i = 0; i < 5; i++) {
	// try {
	// Thread.sleep(10);
	// continuar.acquire();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// System.out.println("PA1 ");
	// System.out.println("PA2 ");
	//
	// procesoB();
	// continuar.release();
	//
	// System.out.println();
	// }
	//
	// }).start();
	// }
	//
	// public static void procesoB() {
	// new Thread(() -> {
	// try {
	// continuar.acquire();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// System.out.println("PB1 ");
	// for (int i = 0; i < 10000; i++) {
	// System.out.print(i + ",");
	// }
	// System.out.println("-");
	// System.out.println("PB2 ");
	//
	// }).start();
	// continuar.release();
	// }
	//
	// public static String write() {
	// return "hello";
	// }
	//
	// public static void main(String[] args) throws InterruptedException {
	// continuar = new Semaphore(0);
	// continuar2 = false;
	// procA();
	//
	//// procesoA();
	//// procesoB();

	// }

	public static void main(String[] args) throws Exception {

		Problem<DoubleSolution> problem;
	    Algorithm<DoubleSolution> algorithm;
	    CrossoverOperator<DoubleSolution> crossover = new BLXAlphaCrossover(1);
	    MutationOperator<DoubleSolution> mutation;
	    
	    problem = new Rosenbrock(64);

	    mutation = new CDGMutation();
	    SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
	    selection = new SpatialSpreadDeviationSelection<DoubleSolution>(64);
//	    selection = new BestSolutionSelection<DoubleSolution>(new ObjectiveComparator<DoubleSolution>(0));

//	    algorithm = new GeneticAlgorithmBuilder<DoubleSolution>(problem, crossover, mutation)
//	    		.setMaxEvaluations(2500)
//	    		.setPopulationSize(100)
//	    		.setSelectionOperator(selection)
//	    		.setSolutionListEvaluator(new SequentialSolutionListEvaluator<DoubleSolution>())
//	    		.build();
	    
	     algorithm = new SteadyStateGeneticAlgorithmWebFormat<DoubleSolution>(null, null, problem, 2500, 100, crossover, mutation, selection);

	     Algorithm<List<DoubleSolution>> algorithm2;
//	     algorithm2 = new CoralReefsOptimizationWebPage<DoubleSolution>(null, null, problem, 100, new ObjectiveComparator<DoubleSolution>(0), selection, crossover, mutation, 5, 7, 1, 0.2, 0.5, 0.3, 1);
	    algorithm2 = new FAME<>(problem,
	            100,
	            10,
	            2500,
	            selection,
	            new SequentialSolutionListEvaluator<>()) ;
	     
     new AlgorithmRunner.Executor(algorithm2)
	            .execute() ;

     DoubleSolution solution = algorithm.getResult() ;
//     List<DoubleSolution> solution = algorithm2.getResult() ;
	    
	    System.out.println(solution);

	    
	}

}
