package jmetal.javaclass;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 
 * @author Harender
 *
 */

@Entity
@Table(name="Problems")
public class WebPageProblem {

	public interface BasicInformation {}	
	@Id
	@JsonView(BasicInformation.class)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonView(BasicInformation.class)
	private String problemName;
	
	@JsonView(BasicInformation.class)
	private String solutionType;
	
	@JsonView(BasicInformation.class)
	private int numberOfParameter;
	
	@JsonView(BasicInformation.class)
	private boolean initialProblem;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Parameter> parameters = new LinkedList<>();

	
	public WebPageProblem () {
		super();
	}

	public WebPageProblem (String problemName, String solutionType, int numberOfParameter, boolean initialProblem) {
		super();
		this.problemName = problemName;
		this.solutionType = solutionType;
		this.numberOfParameter = numberOfParameter;
		this.initialProblem = initialProblem;
	}

	public WebPageProblem (String problemName, String solutionType, int numberOfParameter, boolean initialProblem,
			List<Parameter> parameters) {
		super();
		this.problemName = problemName;
		this.solutionType = solutionType;
		this.numberOfParameter = numberOfParameter;
		this.initialProblem = initialProblem;
		this.parameters = parameters;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProblemName() {
		return problemName;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	public String getSolutionType() {
		return solutionType;
	}

	public void setSolutionType(String solutionType) {
		this.solutionType = solutionType;
	}

	public int getNumberOfParameter() {
		return numberOfParameter;
	}

	public void setNumberOfParameter(int numberOfParameter) {
		this.numberOfParameter = numberOfParameter;
	}

	public boolean isInitialProblem() {
		return initialProblem;
	}

	public void setInitialProblem(boolean initialProblem) {
		this.initialProblem = initialProblem;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	@Override
	public String toString() {
		String name = getProblemName();
		String[] names = name.split("\\.");
		return (String.valueOf(getId())+"-"+names[names.length-1]+"-"+getSolutionType());
	}
	
}
