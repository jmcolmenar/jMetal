package jmetal.javaclass;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name="Experiments")
public class WebPageExperiment{

	public interface BasicInformation {}
	
	@Id
	@JsonView(BasicInformation.class)
	private String id;
	
	@OneToOne()
	private WebPageAlgorithm algorithm;
	@OneToOne()
	private WebPageProblem problem;
	@OneToOne()
	private WebPageCrossover crossover;
	@OneToOne()
	private WebPageMutation mutation;
	@OneToOne()
	private WebPageSelection selectionOperator;
	
	@OneToOne(cascade=CascadeType.REMOVE)
	private FinalResults finalResult;
	
	private int numberOfRepetition;

	public WebPageExperiment() {
	}
	
	public WebPageExperiment(String id,WebPageAlgorithm algorithm, WebPageProblem problem, WebPageCrossover crossover, WebPageMutation mutation,
			WebPageSelection selectionOperator, int numberOfRepetition) {
		super();
		this.id = id;
		this.algorithm = algorithm;
		this.problem = problem;
		this.crossover = crossover;
		this.mutation = mutation;
		this.selectionOperator = selectionOperator;
		this.numberOfRepetition = numberOfRepetition;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public WebPageAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(WebPageAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public WebPageProblem getProblem() {
		return problem;
	}

	public void setProblem(WebPageProblem problem) {
		this.problem = problem;
	}

	public WebPageCrossover getCrossover() {
		return crossover;
	}

	public void setCrossover(WebPageCrossover crossover) {
		this.crossover = crossover;
	}

	public WebPageMutation getMutation() {
		return mutation;
	}

	public void setMutation(WebPageMutation mutation) {
		this.mutation = mutation;
	}

	public WebPageSelection getSelectionOperator() {
		return selectionOperator;
	}

	public void setSelectionOperator(WebPageSelection selectionOperator) {
		this.selectionOperator = selectionOperator;
	}

	public FinalResults getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(FinalResults finalResult) {
		this.finalResult = finalResult;
	}

	public int getNumberOfRepetition() {
		return numberOfRepetition;
	}

	public void setNumberOfRepetition(int numberOfRepetition) {
		this.numberOfRepetition = numberOfRepetition;
	}
	
	
	
}
