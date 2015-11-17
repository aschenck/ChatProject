package GUI;


import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.*;

import user.Client;
import user.ClientListenerThread;
import user.ClientSendThread;

public class GUI extends JFrame {
	public static BorderLayoutPanel b = new BorderLayoutPanel();
	
	public static void main(String args[]) {
		
		JFrame frame = new GUI();
		frame.setSize(600, 400);
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Chat application");	
		frame.setContentPane(b);
		frame.setVisible(true);
	}
}



