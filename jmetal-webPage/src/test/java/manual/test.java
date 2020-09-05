package manual;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.coralreefsoptimization.CoralReefsOptimizationBuilder;
import org.uma.jmetal.algorithm.singleobjective.differentialevolution.DifferentialEvolutionBuilder;
import org.uma.jmetal.algorithm.singleobjective.evolutionstrategy.CovarianceMatrixAdaptationEvolutionStrategy;
import org.uma.jmetal.algorithm.singleobjective.particleswarmoptimization.StandardPSO2007;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.crossover.NPointCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.NullMutation;
import org.uma.jmetal.operator.impl.selection.BestSolutionSelection;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.problem.singleobjective.NIntegerMin;
import org.uma.jmetal.problem.singleobjective.OneMax;
import org.uma.jmetal.problem.singleobjective.Sphere;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.comparator.ObjectiveComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.MultithreadedSolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import jmetal.algorithms.CoralReefsOptimizationWebPage;
import jmetal.algorithms.CovarianceMatrixAdaptationEvolutionStrategyWebPage;
import jmetal.algorithms.CovarianceMatrixAdaptationEvolutionStrategyWebPage.Builder;
import jmetal.algorithms.GenerationalGeneticAlgorithmWebFormet;

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

		Algorithm<IntegerSolution> algorithm;
		IntegerProblem problem = new NIntegerMin(32, 8, 1, 50);

		CrossoverOperator<IntegerSolution> crossoverOperator = new NPointCrossover(1);
		MutationOperator<IntegerSolution> mutationOperator = new NullMutation();
		SelectionOperator<List<IntegerSolution>, IntegerSolution> selectionOperator = new BestSolutionSelection<IntegerSolution>(new ObjectiveComparator<>(problem.getNumberOfObjectives()));

		algorithm = new GenerationalGeneticAlgorithmWebFormet<IntegerSolution>(null, null, problem, 2500, 100, crossoverOperator, mutationOperator, selectionOperator, new SequentialSolutionListEvaluator<IntegerSolution>());

		
		new AlgorithmRunner.Executor(algorithm).execute();

//		DoubleSolution solution = algorithm.getResult();
		IntegerSolution population = algorithm.getResult();
		System.out.println("--------------------------");
		System.out.println(population);
		System.out.println(population.getNumberOfVariables());
		System.out.println(population.getVariables());
		System.out.println(population.getObjective(0));

	}

}
