package service;

import java.util.Objects;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileService implements IFileService {
	public static final String uploadDir = "U:/tmp/Upload_Loc/";
	
	// Location to upload the file to the Server (must also include the extension).
	public static final String sourceDir = "U:/tmp/Source_Loc/";
	
	public static final String downloadDir = "U:/tmp/Download_Loc/";
	
	public static void main(String[] args) {
//		try {
//			File fileToUpload = new File(fileToUploadLoc);
//			byte[] fileContent = Files.readAllBytes(fileToUpload.toPath());
//			
//			FileService fs = new FileService();
//			System.out.println(fs.uploadFile("text_file_example", fileToUploadLoc.substring(fileToUploadLoc.indexOf(".") + 1), fileContent));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		FileService fs = new FileService();
		
		System.out.println(fs.getFileList());
		
//		Scanner is = new Scanner(System.in);
//		System.out.println("Enter the file you wish to download: ");
//		String inputStr = is.nextLine();
//		
//		byte[] fileContent = fs.downloadFile(inputStr);
//		File downloadedFile = new File(downloadDir + "ex1.txt");
//		
//		try {
//			downloadedFile.createNewFile();
//			Files.write(downloadedFile.toPath(), fileContent);
//			System.out.println("Download successful!!");
//		} catch (IOException e) {
//			e.getStackTrace();
//		}
		
//		is.close();
		
		Hashtable<String, byte[]> files = new Hashtable<String, byte[]>();
		
	}
	
	/**
	 * Get a list of files from Server directory.
	 * @return A list of files if successful. Else, a message saying that the operation failed.
	 */
	@Override
	public String getFileList() {
		File dir = new File(FileService.uploadDir);
		String[] fileNames = dir.list();
		
		if (fileNames == null) {
			return "No file exists on the Server!";
		}
		
		StringBuilder ret = new StringBuilder("List of files: " + System.getProperty("line.separator"));
		for (String fileName : fileNames) {
			ret.append(fileName + System.getProperty("line.separator"));
		}
		
		return ret.toString();
	}
	
	/**
	 * Upload a file to a dirPath on the Server specified by Client.
	 * @param fileName The name of the file without extension.
	 * @param fileExtension The extension of the file without the dot character.
	 * @param fileContent Bytes of the file you wish to upload.
	 * @return a message informs that the operation was successful, else an exception message.
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/File.html#createTempFile(java.lang.String,%20java.lang.String,%20java.io.File)">
	 * 		createTempFile</a> for possible exceptions.
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/nio/file/Files.html#write(java.nio.file.Path,%20byte[],%20java.nio.file.OpenOption...)">
	 * 		write</a> for possible exceptions.
	 */
	@Override
	public String uploadFile(String fileName, String fileExtension, byte[] fileContent) {
		try {
			// Create a new file & avoid duplicated file names.
			File uploadedFileLoc = new File(FileService.uploadDir);
//			File uploadedFile = File.createTempFile(fileName + "-", "." + fileExtension, uploadedFileLoc);
			File uploadedFile = new File(FileService.uploadDir + fileName + "." + fileExtension);
			
			// write content of uploaded file to
			Files.write(uploadedFile.toPath(), fileContent);
		} catch (Exception e) {
			return e.getMessage();
		} 
		
		return "Upload successful!";
	}
	
	/**
	 * Upload multiple files to a dirPath on the Server specified by Client. 
	 * @param files A hashtable contains file names & bytes of that file. File name must include extension!
	 * @return a message informs that the operation was successful, else an exception message returned from {@link #uploadFile(String, String, byte[])}
	 */
	@Override
	public String uploadFiles(Hashtable<String, byte[]> files) {
		if (files.size() == 0) {
			return "No files to upload!!";
		}
		
		Iterator<Entry<String, byte[]>> it = files.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, byte[]> entry = it.next();
			String fileName = entry.getKey().substring(0, entry.getKey().indexOf("."));
			String fileExtension = entry.getKey().substring(entry.getKey().indexOf(".") + 1);
			String retMsg = uploadFile(fileName, fileExtension, entry.getValue());
			
			// If an exception is thrown from uploadFile()
			if (!Objects.equals(retMsg, "Upload successful!")) {
				return retMsg + " at file: " + entry.getKey();
			}
			
			System.out.println(fileName);
			System.out.println(fileExtension);
		}
		
		return "Upload successful!";
	}
	
	/**
	 * Download a file from Server. See also {@link #getFilelist()}.
	 * @param fileName The name of the file, together with the extension.
	 * @return Bytes of the file requested (the content could be the exception messages!).
	 * @see #getFileList()
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/nio/file/Files.html#readAllBytes(java.nio.file.Path)">readAllBytes</a>
	 * 		for possible exceptions.
	 */
	@Override
	public byte[] downloadFile(String fileName) {
		File fileToDownload = new File(FileService.uploadDir + fileName);
		if (!fileToDownload.exists()) {
			return null;
		}
		
		try {
			byte[] ret = Files.readAllBytes(fileToDownload.toPath());
			return ret;
		} catch (IOException e) {
			return e.getMessage().getBytes();
		}
	}
	
	/**
	 * Download multiple files from Server.
	 * @param fileNames A list containing names of file to download (each file name must have extension)
	 * @return Hashtable with key as the file name & value as bytes of that file (The value could be null or an exception message returned from
	 * {@link #downloadFile(String)}).
	 */
	@Override
	public Hashtable<String, byte[]> downloadFiles(Vector<String> fileNames) {
		Hashtable<String, byte[]> files = new Hashtable<String, byte[]>();
		for (String fileName : fileNames) {
			byte[] fileContent = downloadFile(fileName);
			
			files.put(fileName, fileContent);
		}
		
		return files;
	}
	
	// Fill pre-defined files & their data for testing purposes.
	public static Hashtable<String, byte[]> fillTestData(String sourceDir) {
		File dir = new File(sourceDir);
		File[] files = dir.listFiles();
		Hashtable<String, byte[]> ret = new Hashtable<String, byte[]>();
		for (File file : files) {
			try {
				ret.put(file.getName(), Files.readAllBytes(file.toPath()));	
			} catch (IOException e) {
				ret.put(file.getName(), e.getMessage().getBytes());
			}
			
		}
		
		return ret;
	}
	
}
