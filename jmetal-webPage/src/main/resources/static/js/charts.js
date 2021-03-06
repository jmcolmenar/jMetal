$.ajax({
	url : 'chartdata',
	success : function(result) {
		
		category = JSON.parse(result).categories;
		serie = JSON.parse(result).series;
		saveCheck = serie[0];
		drawLiveLineChart(category, serie);
	}
})

var updateInterval = 1500;
var category = [];
var serie = [];
var saveCheck;
var numberOfRepetition = 0;

function drawLiveLineChart(category, series) {
	chart = Highcharts.chart('resultsGrafics', {
		chart : {
			type : 'spline',
			marginRight : 10,
			scrollablePlotArea: {
                minWidth: 2000,
                scrollPositionX: 1
            }
		},
		credits : {
			enabled : false
		},

		legend : {
			enabled : false
		},

		title : {
			text : 'Actual iteration'
		},

		xAxis : {
			title : {
				text : 'Generational'
			},
			categories : category
		},

		yAxis : [ {
			title : {
				text : 'Best value'
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		} ],

		tooltip : {
			formatter : function() {
				return '<strong>' + this.x + ': </strong>' + this.y;
			}
		},

		series : [ {
			data : series
		}, {
			events : {
				legendItemClick : function(e) {
					e.preventDefault()
				}
			}
		} ]
	});

}

var update = setInterval(function() {
	updateChart()
}, updateInterval);

function updateChart(count) {
	$.get("/chartdata", function(result) {
		var end = (JSON.parse(result).finish);
		
		var cat = (JSON.parse(result).categories);
		var se = (JSON.parse(result).series);
		var newCat = category.concat(cat);
		var newSe = serie.concat(se);
		if (se.find(checkToSave)) {
			category = newCat;
			serie = newSe;
			saveCheck = se.find(checkToSave);
		} 
		if ((end[end.length-1] == true) && (end.length-1 == numberOfRepetition))  {
			clearInterval(update);
			showFinalResult(end.length);
		}else if(end[numberOfRepetition] == true){
			numberOfRepetition++;
			category = [];
			serie = [];
			showFinalResult(numberOfRepetition);
		}
		drawLiveLineChart(newCat, newSe);
		
	});
}

function checkToSave(num) {
	  return num != saveCheck;
	}

function showFinalResult(id) {
	$.get("/getFinalResult/"+id, function(result) {
		var name = (JSON.parse(result).name);
		var finalResult = (JSON.parse(result).finalResult);
		var finalPopulation = (JSON.parse(result).finalPopulation);
		var resultLI = document.createElement("finalResult");
		var resultText = document.createTextNode(name+"-> {"+finalResult+"}.    ");
		resultLI.appendChild(resultText);
		document.getElementById("finalResult").appendChild(resultLI);
	});
	
	
}

function showAllPopulation(id,div) {
	$.get("/getFinalResult/"+id, function(result) {
		var name = (JSON.parse(result).name);
		var finalResult = (JSON.parse(result).finalResult);
		var finalPopulation = (JSON.parse(result).finalPopulation);
		
		var pilotDiv = document.getElementById(div);
		var h5 = document.createElement("H5");
		var resultText = document.createTextNode("The population for the last generation is: ");
		h5.appendChild(resultText);
		pilotDiv.appendChild(h5);
		var ul = document.createElement("UL");
		var populationLI = document.createElement("LI");
	    var populationText = document.createTextNode(name+"-> "+finalPopulation);
	    populationLI.appendChild(populationText);
	    populationLI.setAttribute("border", "1px solid");
	    ul.appendChild(populationLI);
	    pilotDiv.appendChild(ul);
	});
	
	
}

function drawFullChart() {
	
	$.get("/showAllResultChart", function(result) {
		var cat = (JSON.parse(result).categories);
		var se = (JSON.parse(result).series);
		var names = (JSON.parse(result).names);
		var x;
		var j = 1;
		for(x in cat){
			var div = document.createElement("DIV");
			var newDiv = "allResultsGrafics"+x;
			div.setAttribute("id", newDiv);
			div.setAttribute("text", names[x]);
			document.getElementById("allResultsGrafics").appendChild(div);
			var newCat = cat[x];
			var newSe = se[x];
			showAllPopulation(j,newDiv);
			j++;
			drawChart(newCat, newSe,"Best generational pilot of experiment -> "+names[x],newDiv);
		}
	});
}

function drawChart(category, series,name,div) {
	chart = Highcharts.chart(div, {
		chart : {
			type : 'spline',
			marginRight : 10,
			scrollablePlotArea: {
                minWidth: 2000,
                scrollPositionX: 1
            }
		},
		credits : {
			enabled : false
		},

		legend : {
			enabled : false
		},

		title : {
			text : name
		},

		xAxis : {
			title : {
				text : 'Generational'
			},
			categories : category
		},

		yAxis : [ {
			title : {
				text : 'Best Value'
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		} ],

		tooltip : {
			formatter : function() {
				return '<strong>' + this.x + ': </strong>' + this.y;
			}
		},

		series : [ {
			data : series
		}, {
			events : {
				legendItemClick : function(e) {
					e.preventDefault()
				}
			}
		} ]
	});

}