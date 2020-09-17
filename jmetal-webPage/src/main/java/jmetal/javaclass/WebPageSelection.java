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
@Table(name="Selections")
public class WebPageSelection {

	public interface BasicInformation {}	
	@Id
	@JsonView(BasicInformation.class)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonView(BasicInformation.class)
	private String selectionName;
	
	@JsonView(BasicInformation.class)
	private String solutionType;
	
	@JsonView(BasicInformation.class)
	private String listType;
	
	@JsonView(BasicInformation.class)
	private int numberOfParameter;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Parameter> parameters = new LinkedList<>();

	
	public WebPageSelection () {
		super();
	}

	public WebPageSelection (String selectionName, String solutionType, String listType, int numberOfParameter) {
		super();
		this.selectionName = selectionName;
		this.solutionType = solutionType;
		this.listType = listType;
		this.numberOfParameter = numberOfParameter;
	}
	
	public WebPageSelection (String selectionName, String solutionType, String listType, int numberOfParameter, List<Parameter> parameters) {
		super();
		this.selectionName = selectionName;
		this.solutionType = solutionType;
		this.listType = listType;
		this.numberOfParameter = numberOfParameter;
		this.parameters = parameters;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSelectionName() {
		return selectionName;
	}


	public void setSelectionName(String selectionName) {
		this.selectionName = selectionName;
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
	
	@Override
	public String toString() {	
		return (String.valueOf(getId())+"-"+getSelectionName());
	}
}
