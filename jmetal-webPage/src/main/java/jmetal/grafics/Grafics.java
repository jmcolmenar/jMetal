package jmetal.grafics;

public class Grafics {
	private Long name;
	private double points;
	
	public Grafics() {
	}

	public Grafics(Long name, double points) {
		this.name = name;
		this.points = points;
	}

	public Long getName() {
		return name;
	}

	public void setName(Long name) {
		this.name = name;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}
}