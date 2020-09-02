$.ajax({
	success : function(result) {
		getExperiments();
	}
});

function getExperiments() {
	$.get("/getExperimentList", function(result) {
		var experiments = JSON.parse(result);
		
		var select = document.getElementById("experimentList");
		var i = 1;
		for (key in experiments) {
			select.options[select.options.length] =  new Option("Experiment= "+key+" -> ["+experiments[key]+"]", key);
			i++;
		}
		select.setAttribute("size",i);
	});
}

function show(id) {
	$.get("/showExperiment/"+id, function() {
		
	})
}
	
function play(id) {
	$.post("/playExperiment/"+id, function() {
		
	})
}