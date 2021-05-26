package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Creditos extends JFrame implements ActionListener{
		
	private static final long serialVersionUID = 1L;
	private Image imagemIcone;
	private Image fundo;
	private ImageIcon referencia;
	private JLabel label;
	private JButton voltar;
	
	public Creditos(){
		
			fundo = new ImageIcon("src/res/FUNDO.png").getImage();
		
			referencia = new ImageIcon("src/res/Icone.png");
			imagemIcone = referencia.getImage();
			
			repaint();
			
			add(label = new JLabel());
			voltar = new JButton();
			label.add(voltar);
				voltar.addActionListener(this);
			
			voltar.setBounds(640, 430, 144, 40);
			voltar.setIcon(new ImageIcon("src/res/QuitBar.png"));
			voltar.setBorder(null);
			voltar.setContentAreaFilled(false);
			
			setUndecorated(true);
			setTitle("Lixeirinho");
			setSize(800,500);
			setIconImage(imagemIcone);
			setVisible(true);
			setLocationRelativeTo(null);
			setResizable(false);
	}
	
	public void paint(Graphics g){
		
		g.drawImage(fundo, 0, -5, this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
		
	}
}
