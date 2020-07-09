package jmetal;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jmetal.apicontroller.ExperiementCreateController;
import jmetal.javaclass.Experiment;
import jmetal.javaclass.Result;
import jmetal.javaropository.ExperimentRepository;
import jmetal.javaropository.ResultRepository;
import weka.core.converters.JSONSaver;

@Controller
public class MainPageController {
	
	@Autowired
	private ExperimentRepository experimentRepository; 
	@Autowired
	private ResultRepository resultRepository; 
	private Experiment experiment;
	private static volatile String result;
	private static volatile boolean finished, pushed;
	private int count;
	
	@RequestMapping("/")
	public String mainPage(Model model) {
		return "index";
	}
	
	@RequestMapping("/chart")
	public String chart(Model model) {
		return "chart";
	}
	
	@RequestMapping("/404")
	public String errorPage(Model model) {
		return "404";
	}
	
	@RequestMapping("/experimentPage")
	public String experimentPage(Model model) {
		return "experimentPage";
	}
	
	@RequestMapping("/experimentResultPage")
	public void experimentResultPage(Model model, @RequestParam String algorithm, @RequestParam String problem, 
			@RequestParam String crossover, @RequestParam double crossoverValue, @RequestParam String mutation,  @RequestParam String selectionOperator,
			@RequestParam int maxPopulation, @RequestParam int maxEvaluations, @RequestParam int specificProblemValue) throws InterruptedException {
		
		model.addAttribute("selectedAlgorithm", algorithm);
		model.addAttribute("selectedProblem", problem);
		model.addAttribute("selectedCrossover", crossover);
		model.addAttribute("selectedCrossoverValue", crossoverValue);
		model.addAttribute("selectedMutation", mutation);
		model.addAttribute("selectedSelectionOperator", selectionOperator);
		model.addAttribute("selectedMaxPopulation", maxPopulation);
		model.addAttribute("selectedMaxEvaluations", maxEvaluations);
		model.addAttribute("selectedNumberOfBits", specificProblemValue);
		count = 0;
		
		String id = "experiment"+experimentRepository.count();
		 Experiment exp = new Experiment(id, algorithm, problem, crossover, mutation, selectionOperator,
				maxPopulation, maxEvaluations, specificProblemValue);
		experimentRepository.save(exp);
		setExpeeriment(exp);
		
		ExperiementCreateController experiementCreateController = 
				new ExperiementCreateController(exp,experimentRepository,resultRepository,algorithm, problem, crossover,crossoverValue, mutation, selectionOperator,
						maxEvaluations, maxPopulation, specificProblemValue);
		finished = false;
		pushed = true;
//		result = experiementCreateController.createExperimentAndRun();
//		processShowResultPage();
		processRunExperiment(experiementCreateController);
		processShowResultPage();

		
	}
	
	public String processShowResultPage() {
		return "experimentResultPage";
	} 
	
	public static void processRunExperiment(ExperiementCreateController experiementCreateController) throws InterruptedException {
		new Thread(() -> {
			result = experiementCreateController.createExperimentAndRun();
			finished = true;
		}).start();
	}

	@RequestMapping("/chartdata")
	@ResponseBody
	public String getDataFromDB(){
		Experiment exp = getExperiment();
		List<Result> dataList = resultRepository.findResultsByExperimentId(exp.getId());
		
		JSONArray jsonArrayId = new JSONArray();
		JSONArray jsonArrayValue = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		int x;
		for(x = count; x < dataList.size(); x++) {
			jsonArrayId.put(x+1);
			jsonArrayValue.put(dataList.get(x).getValue());
		};
		count = dataList.size();
		if(finished) 
			jsonObject.putOpt("finish", true);
		else
			jsonObject.putOpt("finish", false);
		jsonObject.putOpt("categories", jsonArrayId);
		jsonObject.putOpt("series", jsonArrayValue);
		return jsonObject.toString();
	}
	
	@RequestMapping("/showAllResultChart")
	@ResponseBody
	public String showFullResultCharts(){
		List<Result> dataList = resultRepository.findResultsByExperimentId(experiment.getId());
		
		JSONArray jsonArrayId = new JSONArray();
		JSONArray jsonArrayValue = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		for(int x = 0; x < dataList.size(); x++) {
			jsonArrayId.put(x+1);
			jsonArrayValue.put(dataList.get(x).getValue());
		};
		
		jsonObject.putOpt("categories", jsonArrayId);
		jsonObject.putOpt("series", jsonArrayValue);
		return jsonObject.toString();
	}
	
	public Experiment getExperiment() {
		return experiment;
	}

	public void setExpeeriment(Experiment exp) {
		this.experiment = exp;
	}
}
