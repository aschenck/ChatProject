package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import user.Client;

public class OffMessageBox extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel chatPanel;
	

	//CHATPANEL
	public JTextArea txtConversation;		
	

	public OffMessageBox(String message) 
	{
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		chatPanel = new JPanel();
		
		chatPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		chatPanel.setPreferredSize(new Dimension(600, 400));

		add(chatPanel);//
		
		txtConversation = new JTextArea(15,35);
		JScrollPane scroll = new JScrollPane (txtConversation);
		scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		chatPanel.add(scroll);
		
		//txtConversation.setPreferredSize(new Dimension(400, 225));
		txtConversation.setEditable(false);	
		
		txtConversation.append(message);
	}
	
	public void dropDown()
	{
		txtConversation.setCaretPosition(txtConversation.getDocument().getLength());
	}
	
}

