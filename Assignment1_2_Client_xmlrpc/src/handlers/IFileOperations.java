package handlers;

public interface IFileOperations {
	public String getFileList();
	
	public String readFromFile(String fileName);
	
	public String writeToFile(String fileName, String inputStr);
	
	public String getFilePath();
}
