package net.aegistudio.pe.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DisassemblyView extends JPanel {
	private static final long serialVersionUID = 1L;
	protected final byte[] data;
	protected final JTextField command;
	protected final JTextArea textArea;
	
	public DisassemblyView(byte[] data) {
		super.setLayout(new BorderLayout());
		this.data = data;
		
		JPanel controlPanel = new JPanel();
		controlPanel.add(new JLabel("Command"));
		controlPanel.add(command = new JTextField("ndisasm"));
		command.setPreferredSize(new Dimension(100, 30));
		
		ButtonGroup group = new ButtonGroup();
		JRadioButton pipe = new JRadioButton("Pipe");
		controlPanel.add(pipe);
		group.add(pipe);
		
		JRadioButton file = new JRadioButton("File");
		controlPanel.add(file);
		file.setSelected(true);
		group.add(file);
	
		JButton disasm = new JButton("Disassemble");
		controlPanel.add(disasm);
		
		super.add(controlPanel, BorderLayout.NORTH);
		super.add(new JScrollPane(textArea = new JTextArea()), 
				BorderLayout.CENTER);
		
		disasm.addActionListener(a -> {
			new Thread(() -> {
				try {
					textArea.setText("");
					
					File output = File.createTempFile("peviewer_", ".img");
					output.deleteOnExit();
					String fileName = output.getAbsolutePath();
					
					File input = File.createTempFile("peviewer_", ".disasm");
					input.deleteOnExit();
					
					OutputStream outputStream = new FileOutputStream(output);
					writeOutData(outputStream);
					outputStream.close();
						
					ProcessBuilder builder = new ProcessBuilder();
					List<String> cmdList = new ArrayList<>();
					cmdList.add(command.getText());
					if(file.isSelected())
						cmdList.add(fileName);
					else builder.redirectInput(output);
					
					Process process = builder.command(cmdList)
						.redirectError(input)
						.redirectOutput(input).start();

					if(process.isAlive())
						process.waitFor();
					
					disassemblyPipe(new FileInputStream(input));
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}).start();
		});
	}
	
	public void writeOutData(OutputStream data) throws IOException {
		data.write(this.data);
	}
	
	public void disassemblyPipe(InputStream inputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		for(String input = reader.readLine(); input != null; 
				input = reader.readLine()) {
			textArea.append(input);
			textArea.append("\n");
		}
	}
}
