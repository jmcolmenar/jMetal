package jmetal.javaclass;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name="Experiments")
public class Experiment{

	public interface BasicInformation {}
	public interface NameFilmInfo {}
	public interface NameFilmAndType {}
	
	@Id
	@JsonView(BasicInformation.class)
	private String id;
	
	@JsonView(BasicInformation.class)
	private String algorithm;
	@JsonView(BasicInformation.class)
	private String problem;
	@JsonView(BasicInformation.class)
	private String crossover;
	@JsonView(BasicInformation.class)
	private String mutation;
	@JsonView(BasicInformation.class)
	private String operator;
	
	@JsonView(BasicInformation.class)
	private int population;
	@JsonView(BasicInformation.class)
	private int evaluations;
	@JsonView(BasicInformation.class)
	private int specificProblemValue;
	
	@OneToOne
	private User user;
	
	@OneToOne
	private FinalResults finalResult;

	public Experiment() {
	}
	
	public Experiment(String id, String algorithm, String problem, String crossover, String mutation, String operator,
			int population, int evaluations, int specificProblemValue) {
		super();
		this.id = id;
		this.algorithm = algorithm;
		this.problem = problem;
		this.crossover = crossover;
		this.mutation = mutation;
		this.operator = operator;
		this.population = population;
		this.evaluations = evaluations;
		this.specificProblemValue = specificProblemValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getCrossover() {
		return crossover;
	}

	public void setCrossover(String crossover) {
		this.crossover = crossover;
	}

	public String getMutation() {
		return mutation;
	}

	public void setMutation(String mutation) {
		this.mutation = mutation;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public int getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(int evaluations) {
		this.evaluations = evaluations;
	}

	public int getSpecificProblemValue() {
		return specificProblemValue;
	}

	public void setSpecificProblemValue(int specificProblemValue) {
		this.specificProblemValue = specificProblemValue;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public FinalResults getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(FinalResults finalResult) {
		this.finalResult = finalResult;
	}
	
	
	
}
