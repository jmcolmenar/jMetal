$.ajax({
	url : 'getTSPs',
	success : function(result) {
		var TSPs = (JSON.parse(result).TSPsName);
		showTSPs(TSPs);
	}
});

function showTSPs(TSPs) {
	
	var ul = document.getElementById("ul");
	for (var i = 0; i < TSPs.length; i++) {
		
		var y = document.createElement("LI");
		y.setAttribute("class", "list-group-item");
		
		var resultText = document.createTextNode(TSPs[i]);
		y.appendChild(resultText);
		ul.appendChild(y);
	}
}
