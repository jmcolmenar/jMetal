package jmetal.database;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jmetal.javaclass.Experiment;
import jmetal.javaclass.Result;
import jmetal.javaropository.ExperimentRepository;
import jmetal.javaropository.ResultRepository;


@RestController
public class DatabaseInformationLoader {
	
	@Autowired
	private ExperimentRepository experimentRepository;
	@Autowired
	private ResultRepository resultRepository;

	@PostConstruct
	private void initDatabase() {
		
		Experiment exp = new Experiment("test0", "testAlgorithm", "testProblem", "testCrossover", 
				"testMutation", "testOperator", 0, 0, 0);
		experimentRepository.save(exp);
		
		Result r1 = new Result(4,exp);
		Result r2 = new Result(5,exp);
		Result r3 = new Result(6,exp);
		Result r4 = new Result(7,exp);
		Result r5 = new Result(8,exp);
		
		resultRepository.save(r1);
		resultRepository.save(r2);
		resultRepository.save(r3);
		resultRepository.save(r4);
		resultRepository.save(r5);
		
	}
}
