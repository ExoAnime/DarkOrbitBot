package gui;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

import javax.swing.JFrame;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.beans.PropertyChangeEvent;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import userControls.GetImage;
import userControls.Timers;
import userControls.textReader;

import javax.swing.event.ChangeEvent;

public class ColectRobot {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ColectRobot window = new ColectRobot();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ColectRobot() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public boolean start=false;
	public int index =0;
	private boolean begin=true;
	
	private void initialize() {
		//colectingTimers cbox= new colectingTimers();
		textReader txt=new textReader();
		
		frame = new JFrame();
		//frame.setUndecorated( true );
		frame.setBackground(Color.BLUE);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 198, 164);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setAlwaysOnTop(true);
		JComboBox comboBox = new JComboBox();
		ArrayList<String> list = new ArrayList<String>();
		File myFile = new File("users.txt");
		if (myFile.exists()) {
			BufferedReader reader;
			try {
				 reader = new BufferedReader(new FileReader(myFile));
				 String text=null;
				while ((text = reader.readLine()) != null) {
				        list.add(text);
				        //System.out.println(text);
				    }
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				if(myFile!=null)
				myFile.createNewFile();
			} catch (IOException e) {}
		}
		comboBox.setModel(new DefaultComboBoxModel<Object>(list.toArray()));;
		comboBox.setEditable(true);
		comboBox.setBounds(83, 11, 100, 20);
		frame.getContentPane().add(comboBox);
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GetImage img=new GetImage();
				
				BufferedImage im = img.screanImage();
				
				String shipName = comboBox.getSelectedItem().toString();
				txt.start(im,shipName);
				if(!list.contains(shipName)){
					list.add(0,shipName);
				}else{
					int ind =list.indexOf(shipName);
					 String temp = list.get(0); // Save value before overwrite.
					   list.set(0, shipName); // First half of swap.
					   list.set(ind, temp);
				}
				comboBox.setModel(new DefaultComboBoxModel<Object>(list.toArray()));;
				try(  PrintWriter out = new PrintWriter( myFile )  ){
					   for(String str:list){
						out.println( str );
					   }
					   out.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//txt.frm=curintPetfuel
				if(txt.userFound&&txt.petFound&&
						txt.shipFound&&txt.mapFound){
				if(begin){
					//txt.readResorces(im,0);
					Timers timer = new Timers(txt,img,shipName);
					
				}
				}
			}
		});
		btnStart.setBounds(33, 42, 89, 23);
		frame.getContentPane().add(btnStart);
		
		
		
		
		JLabel lblShipName = new JLabel("ship name=");
		lblShipName.setForeground(Color.GREEN);
		lblShipName.setBounds(10, 14, 63, 14);
		frame.getContentPane().add(lblShipName);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		Playerinfo Pinfo=new Playerinfo(txt.dispInfo);
		JMenu mnFile = new JMenu("file");
		menuBar.add(mnFile);
		JMenuItem mntmTestPage = new JMenuItem("test page");
		mntmTestPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				Pinfo.setVisible(true);
			}
		});
		
		
		mnFile.add(mntmTestPage);
		
		
	}
}
