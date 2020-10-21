/*
Documento       : MainController.java / jMetal GUI
Descripción     : Controlador principal de la interfaz.
Entidad         : Universidad Rey Juan Carlos
Autor           : Alejandro Manuel Pazos Boquete
*/

package jmetal.jmetalgui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import java.util.List;

@Controller
public class MainController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/404")
    public String error() {
        return "error";
    }

    @RequestMapping("/run_experiment")
    public String run_experiment() {
        return "run_experiment";
    }

   @RequestMapping(value="/run_experiment/show_results", method=RequestMethod.POST)
    public @ResponseBody void runExperiment(@ModelAttribute Experiment experiment) throws Exception {
        Algorithm algorithm = setAlgorithm(experiment);
        List<DoubleSolution> population = getSolution (algorithm);
        //Send results back to view (in development)
    }

    private Algorithm setAlgorithm(Experiment experiment) throws Exception {
        Problem<DoubleSolution> problem;
        CrossoverOperator<DoubleSolution> crossover;
        MutationOperator<DoubleSolution> mutation;
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
        int populationSize;
        Algorithm<List<DoubleSolution>> algorithm;
        switch(experiment.getAlgorithm()) {
            case "nsgaii":
                    problem = selectProblem(experiment.getTestProblem());
                    crossover = selectCrossoverOperator(experiment.getCrossoverProbability(), experiment.getCrossoverDistributionIndex(), experiment.getCrossoverOperator());
                    mutation = selectMutationOperator(experiment.getMutationProbability(), experiment.getMutationDistributionIndex(), experiment.getMutationOperator());
                    selection = selectSelectionOperator(experiment.getSelectionOperator());
                    populationSize = experiment.getPopulationSize() ;
                    algorithm = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation, populationSize)
                            .setSelectionOperator(selection)
                            .setMaxEvaluations(experiment.getGenerations())
                            .build();
                break;
            default:
                throw new Exception("Error");
        }
        return algorithm;
    }

    private Problem<DoubleSolution> selectProblem(String problem_name) {
        String problemName = "org.uma.jmetal.problem.multiobjective." + problem_name ;
        Problem<DoubleSolution> problem = (DoubleProblem) ProblemUtils.<DoubleSolution>loadProblem(problemName);
        return problem;
    }

    private CrossoverOperator<DoubleSolution> selectCrossoverOperator(double crossover_probability, double crossover_distribution_index, String crossover_operator) throws Exception {
        CrossoverOperator<DoubleSolution> crossover;
        switch(crossover_operator) {
            case "SBXCrossover":
                crossover = new SBXCrossover(crossover_probability, crossover_distribution_index);
                break;
            default:
                throw new Exception("Error");
        }
        return crossover;
    }

    private MutationOperator<DoubleSolution> selectMutationOperator(double mutation_probability, double mutation_distribution_index, String mutation_operator) throws Exception {
        MutationOperator<DoubleSolution> mutation;
        switch(mutation_operator) {
            case "PolynomialMutation":
                mutation = new PolynomialMutation(mutation_probability, mutation_distribution_index);
                break;
            default:
                throw new Exception("Error");
        }
        return mutation;
    }

    private SelectionOperator<List<DoubleSolution>, DoubleSolution>  selectSelectionOperator(String selection_operator) throws Exception {
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
        switch(selection_operator) {
            case "BinaryTournamentSelection":
                selection = new BinaryTournamentSelection<DoubleSolution>(
                        new RankingAndCrowdingDistanceComparator<DoubleSolution>());
                break;
            default:
                throw new Exception("Error");
        }
        return selection;
    }

    private List<DoubleSolution> getSolution (Algorithm<List<DoubleSolution>> algorithm) {
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();
        List<DoubleSolution> population = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();
        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        new SolutionListOutput(population)
                .setSeparator("\t")
                .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
                .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
                .print();
        JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
        JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
        JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");
        return population;
    }

}
