package jmetal.javarepository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jmetal.javaclass.WebPageExperiment;


public interface ExperimentRepository extends JpaRepository<WebPageExperiment, Long> {

	WebPageExperiment findExperimentById(String id);
	
	@Query(value = "SELECT COUNT(id)  FROM experiments where id like ?1%", nativeQuery = true)
	Integer countExperimentsById(String id);
	
	@Query(value = "SELECT id  FROM experiments where id like ?1%", nativeQuery = true)
	List<String> getExperimentId(String id);
	

	@Query(value = "SELECT *  FROM experiments where id like ?1%", nativeQuery = true)
	List<WebPageExperiment> getAllExperimentById(String id);
}
