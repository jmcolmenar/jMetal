package jmetal.apicontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jmetal.javaclass.WebPageAlgorithm;
import jmetal.javaclass.WebPageCrossover;
import jmetal.javaclass.WebPageExperiment;
import jmetal.javaclass.WebPageMutation;
import jmetal.javaclass.WebPageProblem;
import jmetal.javaclass.WebPageSelection;
import jmetal.javarepository.ExperimentRepository;

/**
 * 
 * @author Harender
 *
 */

@RestController
public class ApiShowMyExperiments {

	@Autowired
	private ExperimentRepository experimentRepository; 
	
	List<WebPageExperiment> experimentList = new ArrayList<>();
	
	@RequestMapping("/getExperimentList")
	@ResponseBody
	public String getExperimentList() {
		new Thread(() -> {
			getAllExperiments();
		}).start();
		WebPageProblem problem;
		WebPageAlgorithm algorithm;
		WebPageCrossover crossover;
		WebPageSelection selection;
		WebPageMutation mutation;
		
		Map<String, String> mapToPass = new HashedMap<>();
		long expeListLenght = experimentRepository.count();
		while(expeListLenght > experimentList.size()) {
		}
		System.out.println(expeListLenght);
		for (WebPageExperiment webPageExperiment : experimentList) {
			problem = webPageExperiment.getProblem();
			algorithm = webPageExperiment.getAlgorithm();
			crossover = webPageExperiment.getCrossover();
			selection = webPageExperiment.getSelectionOperator();
			mutation = webPageExperiment.getMutation();
			
			String[] id = webPageExperiment.getId().split("-");
			
			String value ="Number Of Repetition="+webPageExperiment.getNumberOfRepetition()+ ",		Problem="+problem.getProblemName()+",	Algorithm="+algorithm.getAlgorithmName();
			if(crossover != null) {
				value += ",	Crossover="+crossover.getCrossoverName();
			}
			if(mutation != null) {
				value += ",	Mutation="+mutation.getMutationName();
			}
			if(selection != null) {
				value += ",	Selection="+selection.getSelectionName();
			}
			mapToPass.put(id[0],value);
		}
		JSONObject jsonObject = new JSONObject(mapToPass);
		return jsonObject.toString();
	}
	
	private void getAllExperiments(){
		new Thread(() -> {
			setExperimentList(experimentRepository.findAll());
		}).start();
	}

	public void setExperimentList(List<WebPageExperiment> experimentList) {
		this.experimentList = experimentList;
	}

}
