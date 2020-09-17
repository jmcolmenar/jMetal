package jmetal.javaclass;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
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
	
	@Lob
	@Column(columnDefinition="TEXT")
	private String description;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Parameter> parameters = new LinkedList<>();

	
	public WebPageProblem () {
		super();
	}

	public WebPageProblem (String problemName, String solutionType, int numberOfParameter, boolean initialProblem, String descrption) {
		super();
		this.problemName = problemName;
		this.solutionType = solutionType;
		this.numberOfParameter = numberOfParameter;
		this.initialProblem = initialProblem;
		this.description = descrption;
	}

	public WebPageProblem (String problemName, String solutionType, int numberOfParameter, boolean initialProblem, String descrption,
			List<Parameter> parameters) {
		super();
		this.problemName = problemName;
		this.solutionType = solutionType;
		this.numberOfParameter = numberOfParameter;
		this.initialProblem = initialProblem;
		this.parameters = parameters;
		this.description = descrption;
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		String name = getProblemName();
		String[] names = name.split("\\.");
		return (String.valueOf(getId())+"-"+names[names.length-1]+"-"+getSolutionType());
	}
	
}
