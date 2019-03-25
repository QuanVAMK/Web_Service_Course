package service;

import java.util.Hashtable;
import java.util.Vector;

public interface IFileService {
	public String getFileList();
	
	public String uploadFile(String fileName, String fileExtension, byte[] fileContent);
	
	public String uploadFiles(Hashtable<String, byte[]> files);
	
	public byte[] downloadFile(String fileName);
	
	public Hashtable<String, byte[]> downloadFiles(Vector<String> fileNames);
}
