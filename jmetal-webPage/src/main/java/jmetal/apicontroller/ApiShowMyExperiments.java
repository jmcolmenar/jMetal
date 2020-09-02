package jmetal.apicontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jmetal.MainPageController;
import jmetal.javaclass.Parameter;
import jmetal.javaclass.WebPageAlgorithm;
import jmetal.javaclass.WebPageCrossover;
import jmetal.javaclass.WebPageExperiment;
import jmetal.javaclass.WebPageMutation;
import jmetal.javaclass.WebPageProblem;
import jmetal.javaclass.WebPageSelection;
import jmetal.javarepository.AlgorithmRepository;
import jmetal.javarepository.CrossoverRepository;
import jmetal.javarepository.ExperimentRepository;
import jmetal.javarepository.FinalResultsRepository;
import jmetal.javarepository.MutationRepository;
import jmetal.javarepository.ParameterRepository;
import jmetal.javarepository.ProblemRepository;
import jmetal.javarepository.ResultRepository;
import jmetal.javarepository.SelectionRepository;

/**
 * 
 * @author Harender
 *
 */

@RestController
public class ApiShowMyExperiments {

	@Autowired
	private ExperimentRepository experimentRepository; 
	@Autowired
	private ResultRepository resultRepository; 
	@Autowired
	private FinalResultsRepository finalresultsRepository; 
	@Autowired
	private ProblemRepository problemRepository;
	@Autowired
	private AlgorithmRepository algorithmRepository;
	@Autowired
	private CrossoverRepository crossoverRepository;
	@Autowired
	private MutationRepository mutationRepository;
	@Autowired
	private SelectionRepository selectionRepository;
	@Autowired
	private ParameterRepository parameterRepository;
	
	@RequestMapping("/getExperimentList")
	@ResponseBody
	public String getExperimentList() {
		List<WebPageExperiment> experimentList = experimentRepository.findAll();
		WebPageProblem problem;
		WebPageAlgorithm algorithm;
		WebPageCrossover crossover;
		WebPageSelection selection;
		WebPageMutation mutation;
		
		Map<String, String> mapToPass = new HashedMap<>();
		
		for (WebPageExperiment webPageExperiment : experimentList) {
			problem = webPageExperiment.getProblem();
			algorithm = webPageExperiment.getAlgorithm();
			crossover = webPageExperiment.getCrossover();
			selection = webPageExperiment.getSelectionOperator();
			mutation = webPageExperiment.getMutation();
			
			String[] id = webPageExperiment.getId().split("-");
			String value = "Problem="+problem.getProblemName()+",	Algorithm="+algorithm.getAlgorithmName();
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
		
}
