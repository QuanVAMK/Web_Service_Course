package server;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



//import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.XmlRpcServletServer;

import service.FileService;

public class FileServletServer extends HttpServlet {
	private static final long serialVersionUID = 20190325;
	
	// This path should end up in: WEB-INF/classes/properties/FileServiceMapping.properties.
	private static final String fileServiceMappingFilePath = "properties" + File.separator + "FileServiceMapping.properties";
	private XmlRpcServletServer server = null;
	
	private final String uploadDirName = "upload_folder" + File.separator;
	private String uploadDirPath;
	File uploadDir = null;

	public void init() {
		
		uploadDirPath = this.getServletContext().getRealPath(uploadDirName);
		uploadDir = (File) this.getServletContext().getAttribute(ServletContext.TEMPDIR);
		File uploadDirObj = new File(uploadDirPath);
		if (!uploadDirObj.exists()) {
			uploadDirObj.mkdirs();
		}
		
		try {
			FileService.setUploadDir(uploadDirPath);
			System.out.println(uploadDir.getCanonicalPath());
			this.server = new XmlRpcServletServer();
			PropertyHandlerMapping mapping = new PropertyHandlerMapping();

			// Direct way
//			mapping.addHandler("fileService", service.FileService.class);
//			mapping.addHandler("service.IFileService", service.FileService.class);

			// Load the mapping file.
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			mapping.load(cl, fileServiceMappingFilePath);
			
			this.server.setHandlerMapping(mapping);
			XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) this.server.getConfig();
			serverConfig.setEnabledForExceptions(true);
			serverConfig.setEnabledForExtensions(true);
			this.server.setConfig(serverConfig);

		} catch (Exception e) { // could be XmlRpcException or IOException from mapping.load() method 
			e.getMessage();
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(uploadDir.getCanonicalPath());
		req.getRequestDispatcher("WEB-INF/file_service.html").forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.server.execute(req, resp);
	}

}
