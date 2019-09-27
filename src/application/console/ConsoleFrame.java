package application.console;

import java.awt.Color;
import java.awt.Dimension;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

@SuppressWarnings("serial")
public class ConsoleFrame extends JFrame
{
    public JTextComponent textComponent;
    
    public ConsoleFrame()
    {
    	setPreferredSize(new Dimension(500, 700));
    	setTitle("Console - Launcher");
    	setVisible(false);
    	setLocation(100, 100);
    	setDefaultCloseOperation(1);
    	JTextArea ta = new JTextArea();
    	ta.setEditable(false);
    	ta.setBackground(Color.black);
    	ta.setForeground(Color.cyan);
    	ta.setAutoscrolls(true);
    	Console taos = new Console(ta);
    	PrintStream ps = new PrintStream(taos);
    	System.setOut(ps);
    	System.setErr(ps);
    	add(new JScrollPane(ta));
    	pack();
    }
}