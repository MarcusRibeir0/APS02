package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

import main.Main;

public class PanelMenu{
	private static final Font FONT = new Font("MV Boli", Font.BOLD, 25);		
	static Life life;
	private int aparencia;

	public void menudesenhar (Graphics g,int score,int vida,Missao missao){
		try{
			life = new Life(713 ,7);

		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("Não foi possível carregar a Sprite");
		}

		g.setFont(FONT);
		g.setColor(Color.WHITE);
		g.drawString("Vida: ", 650, 30);
		g.drawString("Score: "+score, 650, 290);

		desenharVida(vida,aparencia,g);

		g.drawString("Jogador: "+Main.player.getNome(), 650, 120);
		
		g.setColor(Color.RED);
		g.setFont(new Font("MV Boli", Font.BOLD, 18));
		g.drawString("•Lixo Vermelho: Plástico", 650, 440);
		
		g.setColor(Color.YELLOW);
		g.setFont(new Font("MV Boli", Font.BOLD, 20));
		g.drawString("•Lixo Amarelo: Metal", 650, 470);
		
		g.setColor(Color.GREEN);
		g.setFont(new Font("MV Boli", Font.BOLD, 20));
		g.drawString("•Lixo Verde: Vidro", 650, 500);

		g.setColor(Color.BLUE);
		g.setFont(new Font("MV Boli", Font.BOLD, 20));
		g.drawString("•Lixo Azul: Papel", 650, 530);
		
		g.setColor(Color.ORANGE);
		g.setFont(new Font("MV Boli", Font.BOLD, 18));
		g.drawString("•Lixo Marrom: Orgânico", 650, 560);
	}

	public void setAparencia(int aparencia) {
		this.aparencia = aparencia;
	}

	public void desenharVida(int vida,int aparencia, Graphics g){
		if (vida==3){
			g.drawImage(life.sprites[aparencia], life.posX, life.posY, null);
			g.drawImage(life.sprites[aparencia], life.posX+30, life.posY, null);
			g.drawImage(life.sprites[aparencia], life.posX+60, life.posY, null);
		}
		if (vida==2){
			g.drawImage(life.sprites[aparencia], life.posX, life.posY, null);
			g.drawImage(life.sprites[aparencia], life.posX+30, life.posY, null);
		}
		if (vida==1){
			g.drawImage(life.sprites[aparencia], life.posX, life.posY, null);
		}
	}

}
