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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.stage.DirectoryChooser;


public class Main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		FileDialog fd = new FileDialog(frame);
		fd.setVisible(true);
		
		String path = fd.getDirectory();
		String fullPath = fd.getDirectory() + fd.getFile();
		
		File file = new File(fullPath);
		frame.dispose();
		
		JSONParser parser = new JSONParser();
		
		try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_16)){
			
			JSONObject mainObject = new JSONObject();
			JSONObject sessionResult = new JSONObject();
			JSONArray leaderBoardLines = new JSONArray();
			JSONObject test = new JSONObject();

			mainObject = (JSONObject) parser.parse(reader);
			sessionResult = (JSONObject) mainObject.get("sessionResult");
			leaderBoardLines = (JSONArray) sessionResult.get("leaderBoardLines");
			
			File outputFile = new File(path + "Output.txt");
			
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
			
			
//			JSONObject testObject = new JSONObject();
//			testObject = (JSONObject) array.get(1);
//			
//			System.out.println(testObject);
//			
//			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}

}
