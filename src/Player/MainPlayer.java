package Player;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import com.sun.prism.paint.Stop;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class MainPlayer extends JFrame {
	
	static BasicPlayer player;
	
	private static String audioFilePath;
	private static String lastOpenPath;
	
	private static JButton stop,open;
	
	public MainPlayer(){
		stop = new JButton("Stop");
		open = new JButton("Open");
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 500);
		add(stop);
		add(open);
		
		GridBagLayout gb = new GridBagLayout();
		setLayout(gb);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
	      gbc.gridx = 0;
	      gbc.gridy = 0;
	      add(stop,gbc);
	      
	      gbc.fill = GridBagConstraints.HORIZONTAL;
	      gbc.gridx = 1;
	      gbc.gridy = 0;
	      add(open,gbc);
		
	}
	
	public static void open() throws FileNotFoundException{
		JFileChooser fileChooser = null;
		if (lastOpenPath != null && !lastOpenPath.equals("")) {
			fileChooser = new JFileChooser(lastOpenPath);
		} else {
			fileChooser = new JFileChooser();
		}
		
		FileFilter mp3Filter = new FileFilter() {
			@Override
			public String getDescription() {
				return "Sound file (*.MP3)";
			}

			@Override
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return true;
				} else {
					return file.getName().toLowerCase().endsWith(".mp3");
				}
			}
		};

		
		fileChooser.setFileFilter(mp3Filter);
		fileChooser.setDialogTitle("Open Audio File");
		fileChooser.setAcceptAllFileFilterUsed(false);

		int userChoice = fileChooser.showOpenDialog(null);
		if (userChoice == JFileChooser.APPROVE_OPTION) {
			audioFilePath = fileChooser.getSelectedFile().getAbsolutePath();
			System.out.println(audioFilePath);
			lastOpenPath = fileChooser.getSelectedFile().getParent();
		}
		else if(userChoice == JFileChooser.CANCEL_OPTION){
			JOptionPane.showMessageDialog(null,"No File Selected, Exit.");
		}
	}
	
	public static void play() throws FileNotFoundException{
		player = new BasicPlayer();
		try {
			player.open(new URL("file:///" + audioFilePath));
		    System.out.println("File Opened");
		    player.play();
		    System.out.println("File Playing");
		} catch (BasicPlayerException e) {
		    e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		
		MainPlayer mp = new MainPlayer();
		
		try {
			open();
		} catch (FileNotFoundException e2) {
			System.out.println("File Not Found.");
		}
		
		//InputStream bufferedIn = new BufferedInputStream();
		
		String a = "C:/Users/steve/Documents/Steve/El Perdon/El Perdon.mp3";
		
		try {
			play();
		} catch (FileNotFoundException e2) {
			System.out.println("File Not Found.");
		}
		
		stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					player.stop();
				} catch (BasicPlayerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		open.addActionListener(new ActionListener(){ 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					open();
				} catch (FileNotFoundException e1) {
					System.out.println("File Not Found.");
				}
				try {
					play();
				} catch (FileNotFoundException e1) {
					System.out.println("File Not Found.");
				}
			}
		});
		
		
	}

}
