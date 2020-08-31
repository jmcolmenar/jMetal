package jmetal.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.differentialevolution.DifferentialEvolutionBuilder;
import org.uma.jmetal.algorithm.singleobjective.particleswarmoptimization.StandardPSO2007;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.singleobjective.OneMax;
import org.uma.jmetal.problem.singleobjective.Sphere;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.comparator.ObjectiveComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.MultithreadedSolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

public class test {
	/**
	 * Usage: java
	 * org.uma.jmetal.runner.singleobjective.NonElitistEvolutionStrategyRunner
	 */

	static volatile boolean continuar2;

	public static void procA() throws InterruptedException {
		System.out.println(write());
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				System.out.println("PA1 ");
				System.out.println("PA2 ");
				try {
					procB();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continuar2 = true;
				while (continuar2) {
				}
				System.out.println();
			}
		}).start();
	}

	public static void procB() throws InterruptedException {
		new Thread(() -> {
			System.out.println("PB1 ");
			while (!continuar2) {
			}
			for (int i = 0; i < 100000; i++) {
				System.out.print(i + ",");
			}
			System.out.println("-");
			System.out.println("PB2 ");
			continuar2 = false;
		}).start();
	}

	static private Semaphore continuar;

	public static void procesoA() {

		new Thread(() -> {
			System.out.println(write());
			continuar.release();
			for (int i = 0; i < 5; i++) {
				try {
					Thread.sleep(10);
					continuar.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("PA1 ");
				System.out.println("PA2 ");

				procesoB();
				continuar.release();

				System.out.println();
			}

		}).start();
	}

	public static void procesoB() {
		new Thread(() -> {
			try {
				continuar.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("PB1 ");
			for (int i = 0; i < 10000; i++) {
				System.out.print(i + ",");
			}
			System.out.println("-");
			System.out.println("PB2 ");

		}).start();
		continuar.release();
	}

	public static String write() {
		return "hello";
	}

	public static void main(String[] args) throws InterruptedException {
		continuar = new Semaphore(0);
		continuar2 = false;
		procA();
		
//		procesoA();
//		procesoB();

	}
	/*
	 * public static void main(String[] args) throws Exception {
	 * 
	 * // DoubleProblem problem; // Algorithm<DoubleSolution> algorithm; //
	 * DifferentialEvolutionSelection selection; // DifferentialEvolutionCrossover
	 * crossover; // SolutionListEvaluator<DoubleSolution> evaluator ; // // problem
	 * = new Sphere(20) ; // // evaluator = new
	 * SequentialSolutionListEvaluator<DoubleSolution>() ; // // crossover = new
	 * DifferentialEvolutionCrossover(0.5, 0.5, "rand/1/bin") ; // selection = new
	 * DifferentialEvolutionSelection(); // // algorithm = new
	 * DifferentialEvolutionWebPage(problem, 1000, 100, crossover, // selection,
	 * evaluator); // new AlgorithmRunner.Executor(algorithm).execute() ; //
	 * DoubleSolution solution = algorithm.getResult() ; //
	 * System.out.println(solution); //
	 * System.out.println(solution.getNumberOfVariables()); //
	 * System.out.println(solution.getVariables()); String s = "e8-"; // String[] l
	 * = s.split("-"); int x = Character.getNumericValue(s.charAt(s.length() - 1));
	 * System.out.println(x);
	 * 
	 * }
	 */
}
