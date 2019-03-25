package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;
import java.util.Iterator;
import java.util.Map.Entry;

import service.FileService;

public class FileServiceTest {
	// The location where the uploaded file would be saved on Server.
	public static final String downloadDir = "U:/tmp/Upload_Loc/";

	// The location where the downloaded file would be saved on Client.
	public static final String uploadDir = "U:/tmp/Download_Loc/";

	// The location where the Client wish to upload file to Server.
	public static final String sourceDir = "U:/tmp/Source_Loc/";

	FileService fs;
	Hashtable<String, byte[]> fileList;

	@Before
	public void setUp() throws Exception {
		fs = new FileService();
		fileList = FileService.fillTestData(sourceDir);
	}

	@Test
	public void testUploadFile() {
		String key = "Linux_Basic.txt";

		String res = fs.uploadFile(key.substring(0, key.indexOf(".")),
				key.substring(key.indexOf(".") + 1), 
				fileList.get(key));
		assertTrue(Objects.equals("Upload successful!", res));
	}

	@Test
	public void testUploadFiles() {
		String res = fs.uploadFiles(fileList);
		assertTrue(Objects.equals("Upload successful!", res));
	}

	@Test
	public void testDownloadFile() {
		String fileName = "IPv4_vs_IPv6.PNG";
		byte[] fileContent = fs.downloadFile(fileName);
		assertTrue(Arrays.equals(fileContent, fileList.get(fileName)));
	}

	@Test
	public void testDownloadFiles() {
		Vector<String> fileNames = new Vector<String>();
		fileNames.add("Linux_Basic.txt");
		fileNames.add("Process.txt");
		fileNames.add("IPv4_vs_IPv6.PNG");
		Hashtable<String, byte[]> filesToCompare = fs.downloadFiles(fileNames);
		Iterator<Entry<String, byte[]>> it = filesToCompare.entrySet().iterator();
		boolean res = true;
		while (it.hasNext()) {
			Entry<String, byte[]> entry = it.next();
			res &= Arrays.equals(entry.getValue(), fileList.get(entry.getKey()));
		}
		
		assertTrue(res);
	}

}
