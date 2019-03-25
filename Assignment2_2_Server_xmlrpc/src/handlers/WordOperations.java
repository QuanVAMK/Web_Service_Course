package handlers;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class WordOperations implements IWordOperations {
	// all the words and their occurrences gathered from all text files available in filePath.
	private Hashtable<String, String> fullWordList;
	
	// List of files in filePath.
	private Hashtable<String, File> fileList;
	
	private String[] fileNames;
	private final String filePath = System.getProperty("user.dir") + File.separator + "logs" + File.separator;
	
	public WordOperations() {
		this.fullWordList = new Hashtable<String, String>();
		this.fileList = new Hashtable<String, File>();
		
		Initialize();
	}
	
	public void fillListOfFiles() {
		File ourDir = new File(this.filePath);
		this.fileNames = ourDir.list();
		File[] fileObjList = ourDir.listFiles();
		for (int i = 0; i < fileNames.length; ++i) {
			this.fileList.put(this.fileNames[i], fileObjList[i]);
		}
	}
	
	public void fillFullWordList() {
		for (String fileName : fileNames) {
			try (BufferedReader reader = new BufferedReader(new FileReader(this.fileList.get(fileName)))) {
				String line = reader.readLine();
				while (line != null) {					
					String[] words = line.split("\\W+");
					for (String word : words) {
						if (this.fullWordList.get(word) == null) {
							this.fullWordList.put(word, "1");
						} else {	// The word appears more than once
							int counter = Integer.parseInt(this.fullWordList.get(word));
							++counter;
							this.fullWordList.put(word, "" + counter);	// Trick to convert int to String.
						}
					}
					line = reader.readLine();
				}
			} catch (IOException e) {
				e.getMessage();
			}
		}
	}
	
	public void Initialize() {
		fillListOfFiles();
		fillFullWordList();
	}
	
	@Override
	public String findOccurrences(String keyword) {
		String occurrences = this.fullWordList.get(keyword);
		if (occurrences == null) {
			return String.format("The word: %s that you requested can't be found!", keyword);
		} else {
			return String.format("The word: %s appeared: %s times", keyword, occurrences);
		}
	}
	
	// Getters
	public Hashtable<String, File> getFileList() {
		return fileList;
	}
	
	public Hashtable<String, String> getFullWordList() {
		return fullWordList;
	}
}
