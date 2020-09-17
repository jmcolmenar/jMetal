package jmetal.javarepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import jmetal.javaclass.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {

	List<Result> findResultsByExperimentId(String experiment);

}
