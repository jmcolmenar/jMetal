package jmetal.javaclass;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonView;


@Entity
@Table(name="FinalResults")
public class FinalResults {
	public interface BasicInformation {}
	
	@JsonView(BasicInformation.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonView(BasicInformation.class)
	@ManyToOne
	private Experiment experiment;
	
	@JsonView(BasicInformation.class)
	private double resultValue;
	
	@JsonView(BasicInformation.class)
	private String lastPopulation;

	public FinalResults() {
	}
	
	public FinalResults(Experiment experiment, double resultValue, String lastPopulation) {
		super();
		this.experiment = experiment;
		this.resultValue = resultValue;
		this.lastPopulation = lastPopulation;
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

	public double getResultValue() {
		return resultValue;
	}

	public void setResultValue(double resultValue) {
		this.resultValue = resultValue;
	}

	public String getLastPopulation() {
		return lastPopulation;
	}

	public void setLastPopulation(String lastPopulation) {
		this.lastPopulation = lastPopulation;
	}
	
}
