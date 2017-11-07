package com.ezgo.tools.formatter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Hybrid
{
	
	private void drawFrame() {
		initializeReqData();
		
		myActionListener = new MyActionListener();
		myMouseListener = new MyMouseListener();

		lblSearchKey = new JLabel("Search Key:");
		lblSearchKey.setBounds(10, 10, 200, 25);

		txtSearchKey = new JTextField(defaultSearchKey);
		txtSearchKey.setBounds(90, 10, 370, 21);
		//txtSearchKey.setColumns(10);
		
		lblLogFile = new JLabel("Log/.ppk File:");
		lblLogFile.setBounds(10, 40, 200, 25);
		
		txtLogFile = new JTextField(defaultInputFile);
		txtLogFile.setBounds(90, 40, 280, 21);
		txtLogFile.addMouseListener(myMouseListener);
		
		btnBrowse = new JButton(actionBrowse);
		btnBrowse.setBounds(370, 40, 87, 21);
		btnBrowse.addActionListener(myActionListener);
		
		btnPick = new JButton(actionPick);
		btnPick.setBounds(10, 70, 80, 25);
		btnPick.addActionListener(myActionListener);
		
		checkDefault = new JCheckBox("Use Default File");
		checkDefault.setBounds(200, 70, 115, 25);
		checkDefault.setSelected(defaultUseDefaultFile);
		
		checkUseTextArea = new JCheckBox("Use Text Input");
		checkUseTextArea.setBounds(95, 70, 108, 25);
		checkUseTextArea.setSelected(defaultUseTextInput);
		
		btnRemoveTab = new JButton(actionRemoveTab);
		btnRemoveTab.setBounds(315, 70, 145, 25);
		btnRemoveTab.addActionListener(myActionListener);
		
		btnRemoveDuplicates = new JButton(actionRemoveDuplicates);
		btnRemoveDuplicates.setBounds(10, 100, 145, 25);
		btnRemoveDuplicates.addActionListener(myActionListener);
		
		btnListDuplicates = new JButton(actionListDuplicates);
		btnListDuplicates.setBounds(155, 100, 120, 25);
		btnListDuplicates.addActionListener(myActionListener);
		
		btnFormat = new JButton(actionFormat);
		btnFormat.setBounds(275, 100, 80, 25);
		btnFormat.addActionListener(myActionListener);
		
		btnOpenLog = new JButton(actionOpenLog);
		btnOpenLog.setBounds(355, 100, 105, 25);
		btnOpenLog.addActionListener(myActionListener);
		
		btnAnalyzeUrl = new JButton(actionAnalyzeUrl);
		btnAnalyzeUrl.setBounds(10, 130, 110, 25);
		btnAnalyzeUrl.addActionListener(myActionListener);
		
		btnListJira = new JButton(actionListJira);
		btnListJira.setBounds(125, 130, 85, 25);
		btnListJira.addActionListener(myActionListener);
		
		btnListRelease = new JButton(actionListRelease);
		btnListRelease.setBounds(215, 130, 115, 25);
		btnListRelease.addActionListener(myActionListener);
		
		btnAnalyzeTime = new JButton(actionAnalyzeTime);
		btnAnalyzeTime.setBounds(340, 130, 110, 25);
		btnAnalyzeTime.addActionListener(myActionListener);
		
		lblOutput = new JLabel();
		lblOutput.setBounds(10, 160, 400, 25);
		
		txtArea = new JTextArea();
		txtArea.setAutoscrolls(true);
		txtArea.setLineWrap(true);
		txtArea.setWrapStyleWord(true);
		
		scroller = new JScrollPane(txtArea); //Text Area added through the scroll pane
		scroller.setBounds(10, 180, 450, 350);
		
		lblHelper = new JLabel("Help ?");
		lblHelper.setBounds(430, 530, 100, 25);
		lblHelper.addMouseListener(myMouseListener);

		frame = new JFrame("Satz - Hybrid");
		frame.setBounds(100, 100, 475, 590);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(lblSearchKey);
		frame.getContentPane().add(txtSearchKey);
		frame.getContentPane().add(lblLogFile);
		frame.getContentPane().add(txtLogFile);
		frame.getContentPane().add(btnBrowse);
		frame.getContentPane().add(btnPick);
		frame.getContentPane().add(checkUseTextArea);
		frame.getContentPane().add(checkDefault);
		frame.getContentPane().add(btnRemoveTab);
		frame.getContentPane().add(btnRemoveDuplicates);
		frame.getContentPane().add(btnListDuplicates);
		frame.getContentPane().add(btnFormat);
		frame.getContentPane().add(btnOpenLog);
		frame.getContentPane().add(btnAnalyzeUrl);
		frame.getContentPane().add(btnListJira);
		frame.getContentPane().add(btnListRelease);
		frame.getContentPane().add(btnAnalyzeTime);
		frame.getContentPane().add(lblOutput);
		frame.getContentPane().add(scroller);
		frame.getContentPane().add(lblHelper);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private void initializeReqData() {
		try {
			InputStream iStream = getClass().getResourceAsStream("/properties/hybrid.properties");
			myProperties = new Properties();
			myProperties.load(iStream);
			iStream.close();
			
			defaultUseTextInput = Boolean.parseBoolean(getProperty("default.useTextInput"));
			defaultUseDefaultFile = Boolean.parseBoolean(getProperty("default.useDefaultFile"));
			
			defaultInputFile = getProperty("default.inputFile");
			logFolderPath = getProperty("path.logFolderPath");
			
			errMsgSearchKeyInvalid = getProperty("msg.searchKeyInvalid");
			errMsgInputFormatterInvalid = getProperty("msg.inputFormatterInvalid");
			errMsgFileNotExist = getProperty("msg.fileNotExist");
			msgNoMatchFound = getProperty("msg.noMatchFound");
			
			actionPick = getProperty("action.pick");
			actionBrowse = getProperty("action.browse");
			actionOpenLog = getProperty("action.openLog");
			actionFormat = getProperty("action.format");
			actionRemoveTab = getProperty("action.removeTab");
			actionRemoveDuplicates = getProperty("action.removeDuplicates");
			actionListDuplicates = getProperty("action.listDuplicates");
			actionAnalyzeUrl = getProperty("action.analyzeUrl");
			actionListJira = getProperty("action.listJira");
			actionListRelease = getProperty("action.listRelease");
			actionAnalyzeTime = getProperty("action.analyzeTime");
			
			defaultSearchKey = getProperty("default.searchKey");
			logLevel = getProperty("log.level");
			
			jiraStatusAll = new ArrayList<>();
			jiraStatusAll.add("open");
			jiraStatusAll.add("in progress");
			jiraStatusAll.add("progress blocked");
			jiraStatusAll.add("resolved");
			jiraStatusAll.add("closed");
			
			changesHeadingsAll = new ArrayList<>();
			changesHeadingsAll.add("Functional Changes");
			changesHeadingsAll.add("Technical Changes");
			changesHeadingsAll.add("Infrastructure Changes");
			changesHeadingsAll.add("Function Changes");
			
			Calendar cal = Calendar.getInstance();
			logFilePath = logFolderPath + "\\Hybrid-" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + ".log";
			
		}catch (IOException e) {
			showOutput("Exception Caught", "Check log file at the below path for more details\n" + logFolderPath);
			logData("initializeReqData() | Exception Caught\n" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String event = e.getActionCommand();
			if (null != event) {
				logData("MyActionListener | Button clicked: " + event + "\nUse TextArea for picking value from log: " + checkUseTextArea.isSelected() + " \nUse Default File: " + checkDefault.isSelected());
				if(event.equals(actionBrowse)) {
					openBrowserPopup();
				} else if(event.equals(actionFormat)) {
					formatFilterData();
				} else if(event.equals(actionListDuplicates)) {
					listDuplicatesActionListener();
				} else if(event.equals(actionPick)) {
					pickFromLog();
				} else if(event.equals(actionRemoveDuplicates)) {
					removeDuplicates();
				} else if(event.equals(actionRemoveTab)) {
					removeTabFromTabbedData();
				} else if(event.equals(actionOpenLog)) {
					openLogFolder();
				} else if(event.equals(actionAnalyzeUrl)) {
					analyzeUrl();
				} else if(event.equals(actionListJira)) {
					listReleaseTickets();
				} else if(event.equals(actionListRelease)) {
					listReleaseTickets();
				} else if(event.equals(actionAnalyzeTime)) {
					analyzeTime();
				}
			}
		}
	}
	
	private void openBrowserPopup() {
		if (null != txtLogFile.getText()) {
			selectedLogFile = new File(txtLogFile.getText());
		}
		fileBrowser = new JFileChooser(selectedLogFile);
		fileBrowser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		int rVal = fileBrowser.showOpenDialog(null);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			selectedLogFile = fileBrowser.getSelectedFile();
			txtLogFile.setText(selectedLogFile.toString());
			logData("openBrowserPopup() | Selected file: " + selectedLogFile.toString());
		}
		checkDefault.setSelected(false);
	}
	
	private void pickFromLog() {

		String pickedValue = "";
		String inputFile = null;
		String inputSearchKey = txtSearchKey.getText();
		
		if(null!= selectedLogFile && !selectedLogFile.exists()) {
			throwErrorMessage(errMsgFileNotExist);
			return;
		} else if(inputSearchKey == null || inputSearchKey.length() < 1) {
			throwErrorMessage(errMsgSearchKeyInvalid);
			return;
		}
		if(checkUseTextArea.isSelected()) {
			pickedValue = getDataFromTextArea(inputSearchKey);
		} else {
			if(checkDefault.isSelected()) {
				inputFile = defaultInputFile;
			} else {
				inputFile = txtLogFile.getText();
			}
			logData("pickFromLog() | Chosen File: " + inputFile);
			File f = new File(inputFile);
			if(null == f || !f.exists()) {
				throwErrorMessage(errMsgFileNotExist);
				return;
			}
			if(f.isDirectory()) {
				pickedValue += "Input Folder: " + f.getPath() + "\n";
				for(File fileInsideInputFolder : f.listFiles()) {
					if(!fileInsideInputFolder.isDirectory()) {
						pickedValue += "\nFile: " + fileInsideInputFolder.getPath() + "\n";
						pickedValue += getDataFromFile(fileInsideInputFolder.getPath(), inputSearchKey);
					}
				}
			} else {
				pickedValue = getDataFromFile(inputFile, inputSearchKey);
			}
		}
		
		String label = "List of values for the search key: \"" + inputSearchKey + "\"";
		if(pickedValue!= null && pickedValue.length() > 0) {
			showOutput(label, pickedValue);
		} else {
			showOutput(label, msgNoMatchFound);
		}
	}
	
	private void removeDuplicates() {
		String input = txtArea.getText();
		String lineData = null;
		int duplicateCounter = 0;
		StringBuilder tempBuilder = new StringBuilder();
		
		if(input == null || input.length() < 1) {
			throwErrorMessage(errMsgInputFormatterInvalid);
			return;
		}
		
		StringTokenizer token = new StringTokenizer(input, "\n");
		while(token.hasMoreTokens()) {
			lineData = token.nextToken();
			if(tempBuilder.indexOf(lineData) < 0) {
				tempBuilder.append(lineData + "\n");
			} else {
				duplicateCounter ++;
			}
		}
		showOutput("Total Duplicates removed: " + duplicateCounter, tempBuilder.toString());
	}
	
	private void listDuplicatesActionListener() {
		String input = txtArea.getText();
		String lineData = null;
		duplicateMap = new HashMap<String, Integer>();
		
		if(input == null || input.length() < 1) {
			throwErrorMessage(errMsgInputFormatterInvalid);
			return;
		}
		
		StringTokenizer token = new StringTokenizer(input, "\n");
		while(token.hasMoreTokens()) {
			lineData = token.nextToken();
			if(input.indexOf(lineData) >= 0) {
				addDuplicateCount(lineData);
			}
		}
		showOutput("List of Duplicates", getDuplicateData());
	}
	
	private void formatFilterData() {
		String input = null;
		StringBuilder tempBuilder = new StringBuilder();
		boolean firstValue = true;
		
		tempBuilder.append("(");
		input = txtArea.getText();
		
		if(null == input || input.length() < 1 || input.contains("'")) {
			throwErrorMessage(errMsgInputFormatterInvalid);
			return;
		}
		
		StringTokenizer token = new StringTokenizer(input, "\n");
		while(token.hasMoreTokens()) {
			if(!firstValue) {
				tempBuilder.append(",");
			} else {
				firstValue = false;
			}
			tempBuilder.append("'");
			tempBuilder.append(token.nextToken());
			tempBuilder.append("'");
		}
		tempBuilder.append(")");
		showOutput("Formatted output", tempBuilder.toString());
	}
	
	private void throwErrorMessage(String msg) {
		final JPanel errorPanel = new JPanel();
	    JOptionPane.showMessageDialog(errorPanel, msg, "Error", JOptionPane.ERROR_MESSAGE);
	    logData("Error | " + msg);
	}
	
	private void addDuplicateCount(String key) {
		int duplicateCounter = 1;
		if(duplicateMap.containsKey(key)) {
			duplicateCounter = duplicateMap.get(key);
			duplicateCounter++;
		}
		duplicateMap.put(key, duplicateCounter);
	}
	
	private String getDuplicateData() {
		int duplicateCounter = 0, value = 0;
		StringBuilder tempBuilder = new StringBuilder();
		Set<String> keys = duplicateMap.keySet();
		for(String key: keys) {
			value = duplicateMap.get(key);
			if(value > 1) {
				tempBuilder.append(key);
				tempBuilder.append(": ");
				tempBuilder.append(value);
				tempBuilder.append("\n");
				duplicateCounter+= value;
			}
		}
		tempBuilder.append("Total Duplicates: " + duplicateCounter);
		return tempBuilder.toString();
	}
	
	private String getDataFromFile(String inputFile, String searchKey) {
		String fileLine = null;
		StringBuilder output = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			while ((fileLine = br.readLine()) != null) {
				if(null!=fileLine && fileLine.toLowerCase().contains(searchKey.toLowerCase())) {
					output.append(getValueForKey(fileLine, searchKey) + "\n");
				}
			}
		} catch (IOException e) {
			showOutput("Exception Caught", "Check log file at the below path for more details\n" + logFolderPath);
			logData("getDataFromFile() | Exception Caught\n" + e.getMessage());
			e.printStackTrace();
		}
		return output.toString();
	}
	
	private String getDataFromTextArea(String searchKey) {
		StringBuilder output = new StringBuilder();
		String input = txtArea.getText();
		StringTokenizer tokens = new StringTokenizer(input, "\n");
		
		while(tokens.hasMoreTokens()) {
			input = tokens.nextToken();
			if(null!=input && input.toLowerCase().contains(searchKey.toLowerCase())) {
				output.append(getValueForKey(input, searchKey) + "\n");
			}
		}
		return output.toString();
	}
	
	private String getValueForKey(String data, String key) {
		int keyLoc = 0, spaceLoc =0;
		keyLoc = data.toLowerCase().indexOf(key.toLowerCase());
		keyLoc += key.length();
		data = data.substring(keyLoc).trim();
		
		/**
		 * Extract only the value ignore the remaining
		 * text of the line
		 */
		spaceLoc = data.indexOf(" ");
		if(spaceLoc > 2) {
			data = data.substring(0,spaceLoc);
		}
		return data;
	}
	
	private void openLogFolder() {
		try {
			//Process p = new ProcessBuilder("explorer.exe", "/select," + logFilePath).start();
			Runtime.getRuntime().exec("explorer " + logFolderPath);
		} catch (IOException e) {
			showOutput("Exception Caught", "Check log file at the below path for more details\n" + logFolderPath);
			logData("openLogFolder() | Exception Caught\n" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void createFile(String folder, String file, boolean isDeleteExisting) throws IOException {
		File f = new File(folder);
		if(!f.exists()) {
			f.mkdirs();
		}
		
		f = new File(file);
		if(isDeleteExisting) {
			f.delete();
		}
		if(!f.exists()) {
			f.createNewFile();
		}
	}
	
	private String getProperty(String key) {
		String value = myProperties.getProperty(key);
		while(null != value && value.contains("{") && value.contains("}")) {
			int startLoc = value.indexOf('{');
			int endLoc = value.indexOf('}');
			String tempKey = value.substring(startLoc+1,endLoc);
			value = value.replace("{" + tempKey + "}", getProperty(tempKey));
		}
		return value;
	}
	
	private void logData(String data) {
		try {
			Calendar cal = Calendar.getInstance();
			createFile(logFolderPath, logFilePath, false);
			String currentTime = ("[" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + "] | ");
			data = currentTime + data + "\n";
			writeToFile(logFilePath, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeToFile(String file, String writableData) throws IOException {
		Files.write(Paths.get(file), writableData.getBytes(), StandardOpenOption.APPEND);
	}
	
	private void showOutput(String label, String output) {
		lblOutput.setText(label);
		txtArea.setText(output);
		if(logLevel!=null && logLevel.equals("debug")) {
			logData(label + ": " + output);
		}
	}
	
	private class MyMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			Object source = e.getSource();
			if(source instanceof JTextField) {
				openBrowserPopup();
				logData("mouseClicked() | Component clicked: File Browser");
			} else if(source instanceof JLabel) {
				showHelper();
				logData("mouseClicked() | Component clicked: Helper");
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	
	private void showHelper() {
		try (InputStream helper = getClass().getResourceAsStream("/text/HybridHelper.txt"); BufferedReader br = new BufferedReader(new InputStreamReader(helper))) {
			String fileLine, tempKey;
			StringBuilder output = new StringBuilder();
				while ((fileLine = br.readLine()) != null) {
					if(fileLine.contains("{") && fileLine.contains("}")) {
						int startLoc = fileLine.indexOf('{');
						int endLoc = fileLine.indexOf('}');
						tempKey = fileLine.substring(startLoc+1,endLoc);
						fileLine = fileLine.replace("{" + tempKey + "}", getProperty(tempKey));
					}
					output.append(fileLine + "\n");
				}
			showOutput("Help", output.toString());
			//Runtime.getRuntime().exec("notepad " + helpPath);
		} catch (IOException e) {
			showOutput("Exception Caught", "Check log file at the below path for more details\n" + logFolderPath);
			logData("showHelper() | Exception Caught\n" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void removeTabFromTabbedData() {
		String input = txtArea.getText();
		if(input == null || input.length() < 1) {
			throwErrorMessage(errMsgInputFormatterInvalid);
			return;
		}
		
		StringTokenizer tokens = new StringTokenizer(input, "\t");
		StringBuilder output = new StringBuilder();
		while(tokens.hasMoreTokens()) {
			output.append(tokens.nextToken());
			output.append("\n");
		}
		showOutput("Formatted data after removing tabs:", output.toString());
	}
	
	private void analyzeUrl() {
		String inputUrl = txtArea.getText();
		if(inputUrl == null || inputUrl.length() < 1) {
			throwErrorMessage(errMsgInputFormatterInvalid);
			return;
		}
		logData("analyzeUrl() | Input URL:\n" + inputUrl);
		
		URL url;
		URLConnection urlCnxn;
		try {
			url = new URL(inputUrl);
			urlCnxn = url.openConnection();
			Map<String, List<String>> headerFields = urlCnxn.getHeaderFields();
			StringBuilder output = new StringBuilder();
			for(String key : headerFields.keySet()) {
				output.append(key);
				output.append(" : ");
				output.append(headerFields.get(key));
				output.append("\n");
			}
			showOutput("URL Headers: ", output.toString());
		} catch (IOException e) {
			showOutput("Exception Caught", "Check log file at the below path for more details\n" + logFolderPath);
			logData("analyzeUrl() | Exception Caught\n" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void listReleaseTickets() {
		String input = txtArea.getText();
		if(input == null || input.length() < 1) {
			throwErrorMessage(errMsgInputFormatterInvalid);
			return;
		}
		String lineData = null;
		StringBuilder formattedInput = new StringBuilder();
		
		StringTokenizer tokens = new StringTokenizer(input, "\n");
		while(tokens.hasMoreTokens()) {
			lineData = tokens.nextToken();
			if(isValidReleaseNotes(lineData, false)) {
				formattedInput.append(lineData);
				formattedInput.append("\n");
			}
		}
		
		StringTokenizer formattedInputTokens = new StringTokenizer(formattedInput.toString(), "\n");
		String keyHolder = "Uncategorized Changes";
		Map<String, List<String>> categoryMap = new HashMap<>();
		List<String> valueHolder = null;
		boolean isHeading = false;
		while(formattedInputTokens.hasMoreTokens()) {
			lineData = formattedInputTokens.nextToken();
			for(String heading : changesHeadingsAll) {
				if(lineData.toLowerCase().contains(heading.toLowerCase())) {
					keyHolder = heading;
					isHeading = true;
				}
			}
			if(!isHeading) {
				valueHolder = categoryMap.get(keyHolder);
				if(null == valueHolder) {
					valueHolder = new ArrayList<>();
				}
				valueHolder.add(lineData);
				categoryMap.put(keyHolder, valueHolder);
			}
			isHeading = false;
		}
		
		Set<String> allCategories = categoryMap.keySet(); 
		StringBuilder output = new StringBuilder();
		for(String category : allCategories) {
			output.append(category);
			output.append(": ");
			output.append("\n");
			output.append(getFormattedTicketsFromList(categoryMap.get(category)));
			output.append("\n");
		}
		showOutput("List of JIRA tasks: ", output.toString());
	}
	
	private String getFormattedTicketsFromList(List<String> commitMessages) {
		Map<String, String> committedJiras = getValidCommittedJiras(commitMessages);
		Map<String, String> sprintMapper = getValidSprintData(null);
		Set<String> allTickets = committedJiras.keySet(); 
		StringBuilder output = new StringBuilder();
		for(String jira : allTickets) {
			output.append(jira);
			output.append(": ");
			jira = sprintMapper.get(jira) != null ? sprintMapper.get(jira) : committedJiras.get(jira);
			output.append(jira);
			output.append("\n");
		}
		return output.toString();
	}
	
	private boolean isValidReleaseNotes(String input, boolean isTicket) {
		if(null!=input && (input.toLowerCase().contains("emss-") || input.toLowerCase().contains("emss:"))) {
			return true;
		} else if(!isTicket) {
			for(String heading : changesHeadingsAll) {
				if(input.toLowerCase().contains(heading.toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private Map<String, String> getValidCommittedJiras(List<String> commitMessages) {
		Map<String, String> committedJiras = new TreeMap<>();

		for(String lineData : commitMessages) {
			String key = getJiraTicket(lineData);
			String value = getCommitMessage(lineData, key);
			committedJiras.put(key, value);
		}
		/**
		 * Implemented streams to sort the list but now moved to tree map
		 */
		/*committedJiras = committedJiras.stream().distinct().collect(Collectors.toList());
		Collections.sort(committedJiras);*/
		return committedJiras;
	}
	
	private String getJiraTicket(String lineData) {
		int idxLoc = lineData.toLowerCase().indexOf("emss-");
		if(idxLoc < 0) {
			idxLoc = lineData.toLowerCase().indexOf("emss:");
		}
		if(idxLoc >= 0) {
			lineData = lineData.substring(idxLoc).trim();
			idxLoc = 5;
			
			for(char c: lineData.substring(5).toCharArray()) {
				if(!Character.isDigit(c)) {
					return lineData.substring(0, idxLoc).trim();
				}
				idxLoc++;
			}
		}
		return lineData;
	}
	
	private String getCommitMessage(String lineData, String ticketNumber) {
		int idxLoc = lineData.toLowerCase().indexOf(ticketNumber.toLowerCase()) + ticketNumber.length() + 1;
		if(idxLoc >= 0) {
			lineData = lineData.substring(idxLoc).trim();
			
			for(char c: lineData.toCharArray()) {
				if(c != ' ' && c != ':' && c != '-') {
					idxLoc = lineData.indexOf(c);
					break;
				}
			}
			lineData = lineData.substring(idxLoc).trim();
			for(String status : jiraStatusAll) {
				if(lineData.toLowerCase().contains(status)) {
					idxLoc = lineData.toLowerCase().lastIndexOf(status);
					return lineData.substring(0, idxLoc).trim();
				}
			}
		}
		return lineData;
	}
	
	private Map<String,String> getValidSprintData(String sprintData) {
		Map<String,String> sprintMapper = new HashMap<>();
		if(null != sprintData) {
			String lineData = null, key = null, value = null;
			
			StringTokenizer lineTokens = new StringTokenizer(sprintData, "\n");
			while(lineTokens.hasMoreTokens()) {
				lineData = lineTokens.nextToken();
				if(null!=lineData) {
					StringTokenizer tabTokens = new StringTokenizer(lineData, "\t");
					key = getJiraTicket(tabTokens.nextToken());
					value = tabTokens.nextToken();
					if(!sprintMapper.containsKey(key)) {
						sprintMapper.put(key, value);
					}
				}
			}
		}
		return sprintMapper;
	}
	
	private void analyzeTime() {
		String input = txtArea.getText();
		if(input == null || input.length() < 1) {
			throwErrorMessage(errMsgInputFormatterInvalid);
			return;
		}
		
		String currentLine = null, nextLine = null, prevBookId = null, noOfBooks = null, totalTime = null, failureCounts = null, lastTime = null;
		List<String> allLines = new ArrayList<>();
		List<String> unTouchedBooks = new ArrayList<>();
		Map<String,String> trueMapper = new LinkedHashMap<>();
		Map<String,String> falseMapper = new LinkedHashMap<>();
		Map<String,String> timingMapper = new LinkedHashMap<>();
		
		StringTokenizer token = new StringTokenizer(input, "\n");
		while(token.hasMoreTokens()) {
			allLines.add(token.nextToken());
		}
		
		Iterator<String> iter = allLines.iterator();
		while(iter.hasNext()) {
			currentLine = iter.next();
			if(currentLine.startsWith("satz refresh for book")) {
				if(null != prevBookId) {
					unTouchedBooks.add(prevBookId);
				}
				prevBookId = currentLine.substring(currentLine.indexOf(":")+2);
			} else if(iter.hasNext() && currentLine.startsWith("satz ae")) {
				iter.next();
				nextLine = iter.next();
				nextLine = nextLine.substring(nextLine.indexOf(":")+2);
				if(currentLine.endsWith("false")) {
					falseMapper.put(prevBookId, nextLine);
				} else if(currentLine.endsWith("true")) {
					trueMapper.put(prevBookId, nextLine);
				}
				prevBookId = null;
			}else if(iter.hasNext() && currentLine.startsWith("satz tempCounter")) {
				currentLine = currentLine.substring(currentLine.indexOf(":")+2);
				nextLine = iter.next();
				lastTime = nextLine.substring(nextLine.indexOf(":")+2);
				timingMapper.put(currentLine, lastTime);
				prevBookId = null;
			} else if(currentLine.startsWith("satz PmxRefreshTask")) {
				noOfBooks = currentLine.substring(currentLine.indexOf(":")+2);
			} else if(currentLine.startsWith("satz Total time taken")) {
				totalTime = currentLine.substring(currentLine.indexOf(":")+2);
			} else if(currentLine.startsWith("satz failedCounter")) {
				failureCounts = currentLine.substring(currentLine.indexOf(":")+2);
			}
		}
		
		StringBuilder output = new StringBuilder();
		if(null != noOfBooks) {
			output.append("Total number of books to refresh: " + noOfBooks);
			output.append("\n");
		}
		if(null != totalTime || null != lastTime) {
			long millis = Long.parseLong(totalTime == null ? lastTime : totalTime);
			long minutes = (millis / 1000) / 60;
			long hours = minutes / 60;
			minutes = minutes % 60;
			long seconds = (millis / 1000) % 60;
			output.append("Total time taken: " + hours + "h " + minutes + "m " + seconds + "s (" + millis + " milliseconds)");
			output.append("\n");
		}
		output.append("Number of books processed currently: " + (falseMapper.size() + trueMapper.size() + unTouchedBooks.size()));
		output.append("\n");
		output.append("Number of failures: " + (null==failureCounts ? 0 : failureCounts));
		output.append("\n\n");
		
		output.append("List of books updated without updating editors(" + falseMapper.size() +"):\n");
		for(String key : falseMapper.keySet()) {
			output.append(key);
			output.append(": ");
			output.append(falseMapper.get(key));
			output.append("\n");
		}
		output.append("\n\n");
		
		output.append("List of books updated by updating editors(" + trueMapper.size() +"):\n");
		for(String key : trueMapper.keySet()) {
			output.append(key);
			output.append(": ");
			output.append(trueMapper.get(key));
			output.append("\n");
		}
		output.append("\n\n");
		
		output.append("List of books not updated(" + unTouchedBooks.size() +"):\n");
		for(String book : unTouchedBooks) {
			output.append(book);
			output.append("\n");
		}
		output.append("\n\n");
		
		output.append("Time slots measured at regular interval of 50 books(in milliseconds):\n");
		for(String key : timingMapper.keySet()) {
			output.append(key);
			output.append(": ");
			output.append(timingMapper.get(key));
			output.append("\n");
		}
		showOutput("Refreshed Books: ", output.toString());
	}
	
	public static void main(String[] args) {
		Hybrid hybrid = new Hybrid();
		hybrid.drawFrame();
	}
	
	private JFrame frame;
	private JLabel lblSearchKey, lblLogFile, lblOutput, lblHelper;
	private JTextField txtSearchKey, txtLogFile;
	private JTextArea txtArea;
	private JButton btnPick, btnBrowse, btnFormat, btnRemoveDuplicates, btnListDuplicates, btnRemoveTab, btnOpenLog, btnAnalyzeUrl, btnListJira, btnListRelease, btnAnalyzeTime;
	private JScrollPane scroller;
	private JFileChooser fileBrowser;
	private JCheckBox checkDefault, checkUseTextArea;
	private MyActionListener myActionListener;
	private MyMouseListener myMouseListener;
	
	private String defaultInputFile, logFolderPath, logFilePath;
	private String errMsgSearchKeyInvalid, errMsgInputFormatterInvalid, errMsgFileNotExist;
	private String msgNoMatchFound;
	private String actionPick, actionBrowse, actionOpenLog, actionFormat, actionRemoveTab, actionAnalyzeUrl, actionListJira, actionListRelease, actionAnalyzeTime;
	private String actionRemoveDuplicates, actionListDuplicates;
	private String defaultSearchKey, logLevel;
	private boolean defaultUseTextInput, defaultUseDefaultFile;
	
	private Map<String,Integer> duplicateMap;
	private List<String> jiraStatusAll, changesHeadingsAll;
	private File selectedLogFile;
	private Properties myProperties;
}
