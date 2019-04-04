package client;

import service.IFileService;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
//import javax.swing.RowFilter.Entry;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.DefaultListModel;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.util.ClientFactory;

public class Client extends JFrame {
	
	// set in setupConnection()
//	XmlRpcClient client = null;

	private static final long serialVersionUID = 20190327;
	
	// Container 2nd to our JFrame.
	JPanel contentPane = null;
	
	// List of files that we could choose for download operation.
	JList<String> fileNameList = null;
	
	// Container of fileNameList.
	JPanel listPanel = null;
	
	// Container of buttons.
	JPanel buttonPanel = null;
	
	JButton uploadFilesButton = null,
			downloadFilesButton = null;
	
	// TODO (Quan): add some kind of file chosen instead of sysout.
	JLabel feedbacklabel = null;
	
	// Select files to use for upload operation.
	JFileChooser fileChooser = null;

	// Directory where our downloaded files would be stored.
	String downloadDir = "U:/tmp/Download_Loc/";
	
	// Default dir that is shown in FileChooser (Normally should be "C:/temp/")
	String defaultDir = "U:/tmp/Source_Loc/";
	
	// Dynamic proxy where we will call our file service operations.
	IFileService fileService = null;

	public Client() {
		setupConnection();
		
		// Do the file name list panel
		this.listPanel = new JPanel(new GridLayout(1, 1));
		refreshFileNameList();
		this.fileNameList.setSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.fileNameList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
//                	feedbacklabel.setText(fileNameList.getSelectedValue());
                  }
			}
		});
		
		JScrollPane fileNameListScroller = new JScrollPane(this.fileNameList,
						ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// Create a JPanel to include our list
		this.listPanel.setBorder(BorderFactory.createTitledBorder("List"));
		this.listPanel.add(fileNameListScroller);
		
		// Do the button panel.
		this.buttonPanel = new JPanel();
		this.uploadFilesButton = new JButton("Upload files");
		this.uploadFilesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {	
				uploadFiles();
			}
		});
		buttonPanel.add(uploadFilesButton);

		downloadFilesButton = new JButton("Download files");
		downloadFilesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				downloadFiles();
			}
		});
		buttonPanel.add(downloadFilesButton);
		
		// Do the layout (buttonPanel + listPanel)
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.add(listPanel);
		splitPane.add(buttonPanel);

		contentPane = new JPanel();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		this.setContentPane(contentPane);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("File handling client");
//		this.setSize(600, 300);
		this.pack();
		this.setVisible(true);
	}
	
	private void refreshFileNameList() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		String fileNames = fileService.getFileList();
		String[] fileNamesArray = fileNames.split("\\r?\\n");
		for (int i = 0; i < fileNamesArray.length; ++i) {
			model.addElement(fileNamesArray[i]);
		}
		if (this.fileNameList != null) {
			this.fileNameList.removeAll();			
		}
		this.fileNameList = new JList<String>(model);
		this.listPanel.removeAll();
		this.listPanel.add(this.fileNameList);
		this.listPanel.updateUI();
	}
	
	private void uploadFiles() {
		this.fileChooser = new JFileChooser(defaultDir);
		this.fileChooser.setVisible(true);
		this.fileChooser.setMultiSelectionEnabled(true);
		this.fileChooser.setDialogTitle("Choose files to upload");
		
		// If OK btn is pressed
		if (this.fileChooser.showDialog(null, "OK") == JFileChooser.APPROVE_OPTION) {
			File[] files = this.fileChooser.getSelectedFiles();
			
			// If no file is chosen
			if (files.length == 0) {
				JOptionPane.showMessageDialog(null, "Choose files first!", "Error", JOptionPane.ERROR_MESSAGE, null);
				return;
			}
		
			Hashtable<String, byte[]> fileList = new Hashtable<String, byte[]>();
			for (File f : files) {
				try {
					String fullFileName = f.getName();
					byte[] fileContent = Files.readAllBytes(f.toPath());
					fileList.put(fullFileName, fileContent);
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE, null);
				} catch (IOException e) {							
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE, null);
				}
			}
			
			String feedback = fileService.uploadFiles(fileList);
			
			refreshFileNameList();
			JOptionPane.showMessageDialog(null, feedback, "Info",
					JOptionPane.INFORMATION_MESSAGE, null);
			
			
		}
	}
	
	private void downloadFiles() {
		List<String> filesChosen = fileNameList.getSelectedValuesList();
		
		// If no file is selected
		if (filesChosen.size() == 0) {
			JOptionPane.showMessageDialog(null, "Choose files first!", "Error",
					JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		
		// convert the files to Vector so that we could pass it to fileService.downloadFiles().
		Vector<String> fileNames = new Vector<String>(filesChosen.size());
		Iterator<String> listIt = filesChosen.iterator();
		while (listIt.hasNext()) {
			fileNames.add(listIt.next());
		}
		
		// Checking purpose.
		Iterator<String> vectorIt = fileNames.iterator();
		while (vectorIt.hasNext()) {
			System.out.println(vectorIt.next());
		}
		
		// Download files through fileService.downloadFiles() & try to write them to our downloadDir.
		// Hashtable with key = file name & value = byte array of that file.
		Hashtable<String, byte[]> fileList = fileService.downloadFiles(fileNames);
		Iterator<Entry<String, byte[]>> fileListIt = fileList.entrySet().iterator();
		while (fileListIt.hasNext()) {
			Entry<String, byte[]> entry = fileListIt.next();
			File downloadedFile = new File(downloadDir + entry.getKey());
			try {
				Files.write(downloadedFile.toPath(), entry.getValue());
			} catch (IOException e) {
				e.getStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage(), "Exception",
						JOptionPane.ERROR_MESSAGE, null);
				return;
			}
		}
		
		JOptionPane.showMessageDialog(null, "Download successful!", "Info",
				JOptionPane.INFORMATION_MESSAGE, null);
	}
	
	void setupConnection() {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL("http://app3.cc.puv.fi/Assignment3_Server_xmlrpc/file_service"));
//			config.setServerURL(new URL("http://localhost:9081/Assignment3_Server_xmlrpc/file_service"));
			config.setEnabledForExtensions(true);
			config.setEnabledForExceptions(true);
			
			config.setBasicUserName("Lizzie");
			config.setBasicPassword("L1000");
			
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			ClientFactory factory = new ClientFactory(client);
			fileService = (IFileService) factory.newInstance(IFileService.class);
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE, null);
//			e.printStackTrace();
		} 
	}

	public static void main(String[] args) {
		new Client();
	}
}