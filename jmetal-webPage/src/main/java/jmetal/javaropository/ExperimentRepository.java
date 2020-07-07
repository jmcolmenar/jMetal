package jmetal.javaropository;


import org.springframework.data.jpa.repository.JpaRepository;

import jmetal.javaclass.Experiment;


public interface ExperimentRepository extends JpaRepository<Experiment, Long> {

	Experiment findExperimentById(String id);
}
