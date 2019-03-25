package handlers;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class FileOperations implements IFileOperations {
	private final String filePath = System.getProperty("user.dir") + File.separator + "logs" + File.separator;
//	private  final String filePath = "logs" + File.separator;
	public String getFileList() {
		File pathName = new File(filePath);
		String[] fileNames = pathName.list();
		StringBuilder strBuilder = new StringBuilder();
		for (String file : fileNames) {
			strBuilder.append(file);
			strBuilder.append("\n");
		}
			
		return strBuilder.toString();
	}

	public String readFromFile(String fileName) {
		File file = new File(this.filePath + fileName);
		if (!file.exists()) { 
			return "The file you requested doesn't exist!!";
		}
		
		if (file.length() == 0) { // This checks both if the file not exist OR the file is empty, in this case we check if file is empty because we already check if file exists for different return String.
			return String.format("The file content of %s is empty!!!", fileName);
		}
		
		try (BufferedReader reader = new BufferedReader(new FileReader(this.filePath + fileName))) {
			StringBuilder strBuilder = new StringBuilder();
			String currentLine = reader.readLine();
			while (currentLine != null) {
				strBuilder.append(currentLine + System.getProperty("line.separator"));
				currentLine = reader.readLine();
			}
			
			return strBuilder.toString();
		} catch (IOException e) {
			return e.getMessage();
		}
	}

//	@SuppressWarnings("unused") 
	public String writeToFile(String fileName, String inputStr) {
		File file = new File(this.filePath + fileName);
		if (!file.exists()) {
			return "The file you requested doesn't exist!!!";
		} 
		
		// Another simpler method is to use BufferedWriter (but can't lock file while writing)
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath + fileName, true))) {
			writer.append(inputStr + System.getProperty("line.separator"));
		} catch (IOException e) {
			return e.getMessage();
		}

		
		// Use try-with-resources for the program to automatically close the stream.
//		try (RandomAccessFile stream = new RandomAccessFile(file, "rw")) {
//			// Lock the file while we are writing.
//			FileChannel fileChannel = stream.getChannel();
//			FileLock lock = null;
//			try {
//				lock = fileChannel.tryLock();
//			} catch (final OverlappingFileLockException e) {
//				e.printStackTrace();
//				return e.getMessage();
//			}
//			
//			// Write to the end of file.
//			stream.seek(stream.length());
//			stream.writeBytes(inputStr + System.getProperty("line.separator"));
//			stream.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return e.getMessage();
//		}			
		

		
		return "Write/Append to file successful!!";
	}
	
	// Getters
	public String getFilePath() {
		return this.filePath;
	}

}
