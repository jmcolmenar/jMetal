package jmetal.javaclass;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 
 * @author Harender
 *
 */

@Entity
@Table(name="Parameters")
public class Parameter {

	public interface BasicInformation {}	
	@Id
	@JsonView(BasicInformation.class)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonView(BasicInformation.class)
	private String parameterType;
	
	@JsonView(BasicInformation.class)
	private String parameterName;

	@JsonView(BasicInformation.class)
	private String parameterValue;
	
	public Parameter() {
		super();
	}

	public Parameter(String parameterType, String parameterName) {
		super();
		this.parameterType = parameterType;
		this.parameterName = parameterName;
	}

	public Parameter(String parameterType, String parameterName, String parameterValue) {
		super();
		this.parameterType = parameterType;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	@Override
	public String toString() {
		return (String.valueOf(getParameterName()+"-"+getParameterType()+"-"+getParameterValue()));
	}
	
}
