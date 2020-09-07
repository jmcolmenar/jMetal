package jmetal.apicontroller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TSPUploaderController {

	@RequestMapping("/uploadTSPFile")
	public String uploadTSPFile(HttpServletRequest request) throws ServletException, IOException {
	    Part filePart = request.getPart("TSP"); // Retrieves <input type="file" name="file">
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    File uploads = new File("src/main/resources/TSPFiles");
	    File file = new File(uploads, fileName);

	    if (!file.isFile()) {
	    	try (InputStream input = filePart.getInputStream()) {
	    		Files.copy(input, file.toPath());
	    	}
        } else {
//        	Alert alert = new Alert(AlertType.INFORMATION);
//            alert.setTitle("Upload");
//            alert.setContentText("File already exists.");
//            alert.showAndWait();

        	System.out.println("File already exists.");
        }
	    
	    
	    return getTSPPage();
	}
	
	@RequestMapping("/getTSPPage")
	public String getTSPPage(){
		return "TSPsFilesList";
	}
	
	@RequestMapping("/getTSPs")
	@ResponseBody
	public String getTSPs() {
		File folder = new File("src/main/resources/TSPFiles");
		File[] listOfFiles = folder.listFiles();
		
		JSONArray jsonTSPsName = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		for (int i = 0; i < listOfFiles.length; i++) {
			  if (listOfFiles[i].isFile()) {
			    jsonTSPsName.put(listOfFiles[i].getName());
			  } 
			}
		
		jsonObject.putOpt("TSPsName", jsonTSPsName);
		return jsonObject.toString();
	}
}
