package jmetal.javaropository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jmetal.javaclass.Experiment;
import jmetal.javaclass.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {

	List<Result> findResultsByExperimentId(String experiment);

//	@Modifying
//	@Transactional
//	@Query(value = "INSERT INTO results(value,experiment_id) value(result", nativeQuery = true)
//	void insert(Result result);
}
