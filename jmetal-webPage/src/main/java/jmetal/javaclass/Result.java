package jmetal.javaclass;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name="Results")
public class Result {
	public interface BasicInformation {}
	
	@JsonView(BasicInformation.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonView(BasicInformation.class)
	@ManyToOne
	private Experiment experiment;
	
	@JsonView(BasicInformation.class)
	private double value;

	public Result() {
	}
	
	public Result(double value, Experiment experiment) {
		super();
		this.experiment = experiment;
		this.value = value;
	}

	public Result(double value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Experiment getExperiment() {
		return experiment;
	}

	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value * (-1);
	}
	
}
