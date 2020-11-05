import java.awt.EventQueue;
import java.awt.FileDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.deploy.uitoolkit.impl.fx.ui.FXUIFactory;

import javafx.stage.DirectoryChooser;


public class Main {

	private static JFrame frame;

	/**
	 * Initialize the contents of the frame.
	 */
	public static void main(String[] args) throws IOException {
		frame = new JFrame();
		
		FileDialog fd = new FileDialog(frame);
		fd.setVisible(true);
		
		String path = fd.getDirectory();
		String fullPath = fd.getDirectory() + fd.getFile();
		
		File file = new File(fullPath);
		
		if (!file.exists()) {
			file.createNewFile();
		}
		
		String fileName = fd.getFile().replace(".json", "_RESULTS");
		
		JSONParser parser = new JSONParser();
		
		if (path != null && fullPath.contains(".json")) {
			try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_16)){
				JSONObject test = new JSONObject();
	
				JSONObject mainObject = (JSONObject) parser.parse(reader);
				JSONObject sessionResult = (JSONObject) mainObject.get("sessionResult");
				JSONArray leaderBoardLines = (JSONArray) sessionResult.get("leaderBoardLines");
				
				File outputFile = new File(path + fileName + ".txt");
				
				FileWriter fileWriter = new FileWriter(outputFile);
				
				for (int i = 0; i <= leaderBoardLines.size() - 1; i++) {
					
					JSONObject carObject = new JSONObject();
					JSONArray array = new JSONArray();
	
					test = (JSONObject) leaderBoardLines.get(i);
					carObject = (JSONObject) test.get("car");
					array = (JSONArray) carObject.get("drivers");
					
					JSONArray outArray = new JSONArray();
					outArray.add(array.get(0));
					
					try {
						fileWriter.write(i+1 + ". " + outArray.toString() + "\n\n");
						fileWriter.flush();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		
		}
		System.exit(0);
	}

}
