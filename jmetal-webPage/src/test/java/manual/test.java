package manual;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.AlgorithmRunner;

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

		Problem<PermutationSolution<Integer>> problem;
	    Algorithm<PermutationSolution<Integer>> algorithm;
	    CrossoverOperator<PermutationSolution<Integer>> crossover;
	    MutationOperator<PermutationSolution<Integer>> mutation;
	    SelectionOperator<List<PermutationSolution<Integer>>, PermutationSolution<Integer>> selection;

	    problem = new TSPWebPage("/TSPFiles/test.tsp");

	    crossover = new PMXCrossover(0.9) ;

	    double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
	    mutation = new PermutationSwapMutation<Integer>(mutationProbability) ;

	    selection = new BinaryTournamentSelection<PermutationSolution<Integer>>();

	    algorithm = new GeneticAlgorithmBuilder<>(problem, crossover, mutation)
	            .setPopulationSize(100)
	            .setMaxEvaluations(250000)
	            .setSelectionOperator(selection)
	            .build() ;

	    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
	            .execute() ;

	    PermutationSolution<Integer> solution = algorithm.getResult() ;
	    List<PermutationSolution<Integer>> population = new ArrayList<>(1) ;
	    population.add(solution) ;

	    long computingTime = algorithmRunner.getComputingTime() ;

	    System.out.println(solution);

	}

}
