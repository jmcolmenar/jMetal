package jmetal.javaclass;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
	@ManyToOne(cascade = CascadeType.REMOVE)
	private WebPageExperiment experiment;
	
	@JsonView(BasicInformation.class)
	private double resultValue;
	
	@Lob
	@Column(columnDefinition="MEDIUMTEXT")
	private String lastPopulation;

	public FinalResults() {
	}
	
	public FinalResults(WebPageExperiment experiment, double resultValue, String lastPopulation) {
		super();
		this.experiment = experiment;
		this.resultValue = resultValue;
		this.lastPopulation = lastPopulation;
	}

	public WebPageExperiment getExperiment() {
		return experiment;
	}

	public void setExperiment(WebPageExperiment experiment) {
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
