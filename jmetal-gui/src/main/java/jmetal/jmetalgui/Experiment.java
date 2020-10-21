package jmetal.jmetalgui;

public class Experiment {
    private String algorithm;
    private String test_problem;
    private int generations;
    private int population_size;
    private String crossover_operator;
    private double crossover_probability;
    private double crossover_distribution_index;
    private String mutation_operator;
    private double mutation_probability;
    private double mutation_distribution_index;
    private String selection_operator;

    public Experiment (String algorithm, String test_problem, int generations, int population_size, String crossover_operator, double crossover_probability,
                       double crossover_distribution_index, String mutation_operator, double mutation_probability, double mutation_distribution_index, String selection_operator) {

        this.algorithm = algorithm;
        this.test_problem = test_problem;
        this.generations = generations;
        this.population_size = population_size;
        this.crossover_operator = crossover_operator;
        this.crossover_probability = crossover_probability;
        this.crossover_distribution_index = crossover_distribution_index;
        this.mutation_operator = mutation_operator;
        this.mutation_probability = mutation_probability;
        this.mutation_distribution_index = mutation_distribution_index;
        this.selection_operator = selection_operator;

    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public String getTestProblem() {
        return this.test_problem;
    }

    public int getGenerations() {
        return this.generations;
    }

    public int getPopulationSize() {
        return this.population_size;
    }

    public String getCrossoverOperator() {
        return this.crossover_operator;
    }

    public double getCrossoverProbability() {
        return this.crossover_probability;
    }

    public double getCrossoverDistributionIndex() {
        return this.crossover_distribution_index;
    }

    public String getMutationOperator() {
        return this.mutation_operator;
    }

    public double getMutationProbability() {
        return this.mutation_probability;
    }

    public double getMutationDistributionIndex() {
        return this.mutation_distribution_index;
    }

    public String getSelectionOperator() {
        return this.selection_operator;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setTestProblem(String test_problem) {
        this.test_problem = test_problem;
    }

    public void setPopulationSize(int population_size) {
        this.population_size = population_size;
    }

    public void setCrossoverOperator(String crossover_operator) {
        this.crossover_operator = crossover_operator;
    }

    public void setCrossoverProbability(int crossover_probability) { this.crossover_probability = crossover_probability; }

    public void setCrossoverDistributionIndex (int crossover_distribution_index){ this.crossover_distribution_index = crossover_distribution_index; }

    public void setMutationOperator(String mutation_operator) {
        this.mutation_operator = mutation_operator;
    }

    public void setMutationProbability(int mutation_probability) {
        this.mutation_probability = mutation_probability;
    }

    public void setMutationDistributionIndex (int mutation_distribution_index) { this.mutation_distribution_index = mutation_distribution_index; }

    public void setSelectionOperator(String selection_operator) {
        this.selection_operator = selection_operator;
    }
}
