package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Point;


import main.Main;
import model.Missao;
import model.Vida;
import model.PanelMenu;

import model.Lixeiro;

public class Fase extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final int LARGURA = 625;
	private final int ALTURA = 580;
	private final int LIXO_TAMANHO = 20;
	private final int TAMANHOMATRIZ = 960;
	private int VELOCIDADE = 140;

	private JButton recomecar = new JButton("Recomeçar");
	private JButton sair = new JButton("Sair");

	private Image fundo;

	private int aparencia = 0;
	private int missaoResultado;

	private boolean ativo = true;

	private JFrame j;
	private PanelMenu menuzinho = new PanelMenu();
	private Missao missao;

	private Vida vida2;

	private Color azula = new Color(204);

	private Timer timer;

	private Lixeiro Jogador;

	public Fase() {

		fundo = new ImageIcon("src/res/FaseFundo.png").getImage();

		setFocusable(true);
		setDoubleBuffered(true);

		addKeyListener(new AdaptadorTeclado());

		iniciarGame();

		missao = new Missao(this.ALTURA, this.LARGURA, this.LIXO_TAMANHO);

		Main.opcoes.carregarConfiguracoes(this, missao);

		vida2 = new Vida();
		vida2.start();
		vida2.hide();
	}

	private void iniciarGame() {
		Jogador = new Lixeiro(TAMANHOMATRIZ, 0, 0, "direita");

		Main.player.carregarQuantidade();
		gerarMissaoLocal();
		timer = new Timer(VELOCIDADE, this);
		timer.start();

	}

	/*========Area de desenho========*/

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(azula);

		g.drawImage(fundo, 0, -5, this);

		missaoTela(missao, g);

		doDrawing(g);

		g.setColor(Color.BLACK);
		g.fillRect(640, 0, 260, 600);

		menuzinho.menudesenhar(g, Jogador.getScore(), Jogador.getVida(), missao);
		sprite();

	}

	private void doDrawing(Graphics g) {

		if (ativo) {
			g.drawImage(vida2.getImagem(), vida2.getX(), vida2.getY(), this);
			Jogador.desenharLixeiro(g);

		}
	}

	private void missaoTela(Missao missao, Graphics g) {
		var image = missao.getImagem();

		g.drawImage(image, missao.getX(), missao.getY(), this);

	}

	private void checarMissaoHit() {
		if ((Jogador.getCabeca().x == this.missao.getX())
				&& (Jogador.getCabeca().y == this.missao.getY())) {
			missaoResultado = missao.getResultado();

			if (missao.checarMissao(missaoResultado)) {
				Jogador.setScore(Jogador.getScore() + 1);
				Jogador.addCorpo();
				geradores();
			}
		}
	}

	public void geradores() {
		gerarMissaoLocal();
	}

	private void checarColisao() {
		// Colisão no eixo Y
		if ((Jogador.getCabeca().y >= ALTURA) || (Jogador.getCabeca().y < 0)) {
			resetarPosicao();
		}

		// Colisão no eixo X
		if ((Jogador.getCabeca().x >= LARGURA) || (Jogador.getCabeca().x < 0)) {
			resetarPosicao();
		}

		// Checar colisão com o player
		for(int i = 1; i < Jogador.getTamanho(); i++) {
			Point cabeca = Jogador.getCabeca();
			Point corpo = Jogador.getCorpo(i); 

			if(cabeca.x == corpo.x && cabeca.y == corpo.y) {
				resetarPosicao();
			}
		}

		if (!ativo) {
			timer.stop();
		}
	}

	public void resetarPosicao() {
		Jogador.menosVida();
		checarVida();
		Jogador.recomecar();
	}

	private void checarvida2() {

		if ((Jogador.getCabeca().x == vida2.getX()) && (Jogador.getCabeca().y == vida2.getY())) {
			if (Jogador.getVida() <= 2) {
				Jogador.maisVida();
			}
			vida2.setAtivo(false);
			vida2.hide();
		}

	}

	private void checarVida() {
		if (Jogador.getVida() <= 0) {
			ativo = false;
			missaoResultado = missao.getResultado();
			vida2.hide();
			recomecar();
		}
	}
	// Area de Atualiz�o

	private void recomecar() {

		j = new JFrame();
		j.setTitle("Mais uma vez?");
		j.setSize(220, 65);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setIconImage(new ImageIcon("src/res/Icone.png").getImage());
		j.setLocationRelativeTo(null);
		j.setResizable(false);
		j.add(recomecar);
		j.add(sair);
		j.setLayout(new FlowLayout());
		recomecar.addActionListener(this);
		sair.addActionListener(this);
		j.setVisible(true);
	}

	private void sprite() {
		this.aparencia++;
		if (this.aparencia >= 5) {
			this.aparencia = 0;
		}
		menuzinho.setAparencia(this.aparencia);
	}

	private void gerarMissaoLocal() {

		this.missao = new Missao(this.ALTURA, this.LARGURA, this.LIXO_TAMANHO);

	}
	// Area de eventos

	private class AdaptadorTeclado extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if ((key == KeyEvent.VK_LEFT) && (!Jogador.isDireita())) {
				Jogador.lado("esquerda");
			}

			if ((key == KeyEvent.VK_RIGHT) && (!Jogador.isEsquerda())) {
				Jogador.lado("direita");
			}

			if ((key == KeyEvent.VK_UP) && (!Jogador.isBaixo())) {
				Jogador.lado("cima");
			}

			if ((key == KeyEvent.VK_DOWN) && (!Jogador.isCima())) {
				Jogador.lado("baixo");
			}

			if (key == KeyEvent.VK_P) {
				if (ativo) {
					ativo = false;
				} else {
					ativo = true;
				}
			}

		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == recomecar) {

			Jogador.recomecar();

			Jogador.setScore(0);
			ativo = true;
			Jogador.setTamanhoLixeiro(3);
			j.dispose();
			timer.start();
			Jogador.setVida(3);
		}
		if (e.getSource() == sair) {

			System.exit(0);
		}

		if (ativo) {
			checarVida();
			if (ativo) {
				checarMissaoHit();
				checarvida2();
				checarColisao();
				Jogador.mover();
				repaint();
			}
		}
	}

	public void setVELOCIDADE(int vELOCIDADE) {
		VELOCIDADE = vELOCIDADE;
	}

}
