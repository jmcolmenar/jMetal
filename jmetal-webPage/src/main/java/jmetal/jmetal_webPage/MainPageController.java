package jmetal.jmetal_webPage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import controllers.ExperiementCreateController;

@Controller
public class MainPageController {
	
	@RequestMapping("/")
	public String mainPage(Model model) {
		return "index";
	}
	
	@RequestMapping("/experimentPage")
	public String experimentPage(Model model) {
		return "experimentPage";
	}
	
	@RequestMapping("/experimentResultPage")
	public String experimentResultPage(Model model, @RequestParam String algorithm, @RequestParam String problem, 
			@RequestParam String crossover, @RequestParam String mutation,  @RequestParam String selectionOperator,
			@RequestParam int maxPopulation, @RequestParam int maxEvaluations, @RequestParam int specificProblemValue) {
		
		model.addAttribute("selectedAlgorithm", algorithm);
		model.addAttribute("selectedProblem", problem);
		model.addAttribute("selectedCrossover", crossover);
		model.addAttribute("selectedMutation", mutation);
		model.addAttribute("selectedSelectionOperator", selectionOperator);
		model.addAttribute("selectedMaxPopulation", maxPopulation);
		model.addAttribute("selectedMaxEvaluations", maxEvaluations);
		model.addAttribute("selectedNumberOfBits", specificProblemValue);
		
		ExperiementCreateController experiementCreateController = 
				new ExperiementCreateController(algorithm, problem, crossover, mutation, selectionOperator,
						maxEvaluations, maxPopulation, specificProblemValue);
		
		String result = experiementCreateController.createExperimentAndRun();
		
		String[] arrayResult = result.split(" ");
		int iterator = arrayResult.length-1;
		
		while (!arrayResult[iterator].startsWith("-")) {
			iterator--;
		}
		
		model.addAttribute("result",specificProblemValue + " -> " + (arrayResult[iterator]));
		return "experimentResultPage";
	}
}
