package Viborita.Elementos;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bloques extends Sprite
{
	@Override
	public void paint(Graphics2D g2) {
		g2.setColor(Color.yellow);
		g2.fillRect(x, y, ancho, altura);
	}
}
