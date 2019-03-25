package handlers;

/*
 * File operations that Server would provide for Client.
 * The folder & files are provided by Server. Client can write/read file but can't create/delete files on Server.
 * The filePath is also provided by the Server.
 */
public interface IFileOperations {
	public String getFileList();
	
	public String readFromFile(String fileName);
	
	public String writeToFile(String fileName, String inputStr);
	
	public String getFilePath();
}
