package jmetal.apicontroller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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

	String TSPLocation = "";

	@RequestMapping("/uploadTSPFile")
	public String uploadTSPFile(HttpServletRequest request) throws ServletException, IOException {
		if (TSPLocation.equals("")) {
			setTSPLocation(getLocation());
		}
		Part filePart = request.getPart("TSP"); // Retrieves <input type="file" name="file">
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
		File uploads = new File(TSPLocation);
		File file = new File(uploads, fileName);

		if (!file.isFile()) {
			try (InputStream input = filePart.getInputStream()) {
				Files.copy(input, file.toPath());
			}
		} else {
			// Alert alert = new Alert(AlertType.INFORMATION);
			// alert.setTitle("Upload");
			// alert.setContentText("File already exists.");
			// alert.showAndWait();

			System.out.println("File already exists.");
		}

		return getTSPPage();
	}

	@RequestMapping("/getTSPPage")
	public String getTSPPage() {
		return "TSPsFilesList";
	}

	@RequestMapping("/getTSPs")
	@ResponseBody
	public String getTSPs() {
		if (TSPLocation.equals("")) {
			setTSPLocation(getLocation());
		}
		File folder = new File(TSPLocation);
		File[] listOfFiles = folder.listFiles();

		JSONArray jsonTSPsName = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					jsonTSPsName.put(listOfFiles[i].getName());
				}
			}
		}

		jsonObject.putOpt("TSPsName", jsonTSPsName);
		return jsonObject.toString();
	}

	private String getLocation() {
		String path = "";

		Path FILES_FOLDER = Paths.get(System. getProperty("user.dir"), "TSPFiles");
		try {
			if (!Files.exists(FILES_FOLDER)) {
				Files.createDirectories(FILES_FOLDER);
			}
			path = FILES_FOLDER.toString();
		} catch (Exception e) {
			System.out.println("Error path");
			e.printStackTrace();
		}

		return path;
	}

	public String getTSPLocation() {
		return TSPLocation;
	}

	public void setTSPLocation(String tSPLocation) {
		TSPLocation = tSPLocation;
	}

}
