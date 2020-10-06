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

import jmetal.javaclass.WebPageProblem.BasicInformation;

/**
 * 
 * @author Harender
 *
 */
@Entity
@Table(name="Algorithms")
public class WebPageAlgorithm {

	public interface BasicInformation {}	
	@Id
	@JsonView(BasicInformation.class)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonView(BasicInformation.class)
	private String algorithmName;
	
	@JsonView(BasicInformation.class)
	private String solutionType;
	
	@JsonView(BasicInformation.class)
	private int numberOfParameter;
	
	@Lob
	@Column(columnDefinition="TEXT")
	private String description;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Parameter> parameters = new LinkedList<>();
	

	public WebPageAlgorithm() {
		super();
	}


	public WebPageAlgorithm(String algorithmName, String solutionType, int numberOfParameter, String descrption) {
		super();
		this.algorithmName = algorithmName;
		this.solutionType = solutionType;
		this.numberOfParameter = numberOfParameter;
		this.description = descrption;
	}


	public WebPageAlgorithm(String algorithmName, String solutionType, int numberOfParameter, List<Parameter> parameters, String descrption) {
		super();
		this.algorithmName = algorithmName;
		this.solutionType = solutionType;
		this.numberOfParameter = numberOfParameter;
		this.parameters = parameters;
		this.description = descrption;
	}
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAlgorithmName() {
		return algorithmName;
	}


	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
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
		return (String.valueOf(getId())+"-"+getAlgorithmName());
	}
	
}
