package jmetal.javaclass;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 
 * @author Harender
 *
 */

@Entity
@Table(name="Results")
public class Result {
	public interface BasicInformation {}
	
	@JsonView(BasicInformation.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonView(BasicInformation.class)
	@ManyToOne(cascade = CascadeType.REMOVE)
	private WebPageExperiment experiment;
	
	@JsonView(BasicInformation.class)
	private double value;

	public Result() {
	}
	
	public Result(double value, WebPageExperiment experiment) {
		super();
		this.experiment = experiment;
		this.value = value;
	}

	public Result(double value) {
		this.value = value;
	}

	public WebPageExperiment getExperiment() {
		return experiment;
	}

	public void setExperiment(WebPageExperiment experiment) {
		this.experiment = experiment;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value * (-1);
	}
	
}
