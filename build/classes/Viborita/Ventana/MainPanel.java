package Viborita.Ventana;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JPanel;
import Viborita.Utils.Utils;
import Viborita.Elementos.Bloques;
import Viborita.Elementos.Comida;
import Viborita.Elementos.Posicion;
import Viborita.Elementos.Viborita;
import Viborita.Elementos.Sprite;
import javax.sound.sampled.Clip;

public class MainPanel extends JPanel implements Runnable
{
	private static final long serialVersionUID = 1L;
	private final BufferedImage buffer;
	private Thread th;
	private Viborita viborita;
	private Comida food;
	private LinkedList<Bloques> blocks;
	private boolean gameOver;
	private int iteration = 0;
	private final Frame frame;
	private int blocksNumber = 20;
	private int speed = 5;
	private int squareSize = 10; // 5, 10 o 20
	private boolean titulo = true;
	private final Image tituloViboritaImage;
	private final Clip crashSound;
	private final Clip growSound;
	private boolean directionChanged;
        private final Clip play = Utils.getSound("inicio.wav");
        

        
	public MainPanel(Frame frame)
	{
                
		this.frame = frame;
		setSize(new Dimension(500, 400));
		setPreferredSize(new Dimension(500, 400));
		buffer = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);

		String key;
		KeyStroke keyStroke;
		AbstractAction izqAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(viborita.getdireccion() == Viborita.UP || viborita.getdireccion() == Viborita.DOWN)
				{
					viborita.setdireccion(Viborita.LEFT);	
					viborita.mov();
					directionChanged = true;
				}			
			}};
		 key = "LEFT";
		 keyStroke = KeyStroke.getKeyStroke(key);
		 this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
		 this.getActionMap().put(key, izqAction);

		AbstractAction derAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(viborita.getdireccion() == Viborita.UP || viborita.getdireccion() == Viborita.DOWN)
				{
					viborita.setdireccion(Viborita.RIGHT);
					viborita.mov();
					directionChanged = true;
				}		
			}};
		 key = "RIGHT";
		 keyStroke = KeyStroke.getKeyStroke(key);
		 this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
		 this.getActionMap().put(key, derAction);
		 
		AbstractAction arribaAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(viborita.getdireccion() == Viborita.LEFT || viborita.getdireccion() == Viborita.RIGHT)
				{
					viborita.setdireccion(Viborita.UP);
					viborita.mov();
					directionChanged = true;
				}			
			}};
		 key = "UP";
		 keyStroke = KeyStroke.getKeyStroke(key);
		 this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
		 this.getActionMap().put(key, arribaAction);
				 
		AbstractAction abaAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(viborita.getdireccion() == Viborita.LEFT || viborita.getdireccion() == Viborita.RIGHT)
				{
					viborita.setdireccion(Viborita.DOWN);
					viborita.mov();
					directionChanged = true;
				}	
			}};
		 key = "DOWN";
		 keyStroke = KeyStroke.getKeyStroke(key);
		 this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
		 this.getActionMap().put(key, abaAction);
		 
		AbstractAction enterAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(titulo)
				{
                                      
					titulo = false;
					init();
				}
				else if(gameOver)
                                {
                                
				init();
                                }
			}};
		 key = "ENTER";
		 keyStroke = KeyStroke.getKeyStroke(key);
		 this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
		 this.getActionMap().put(key, enterAction);
                 
		
		tituloViboritaImage = Utils.getImage("snake.gif");
		crashSound = Utils.getSound("gameover.wav");
		growSound = Utils.getSound("coin.wav");
           
		repaint();
	}

	public void init()
	{
                
                 
                 Utils.playSound(play);
		gameOver = false;
		blocks = new LinkedList<>();
		
		for(int i = 1; i <= blocksNumber; i++)
		{
			while(true)
			{
                             
				Bloques block = new Bloques();
				block.setX(Utils.random(0, (getWidth() - 1) / squareSize) * squareSize);
				block.setY(Utils.random(0, (getHeight() - 1) / squareSize) * squareSize);
				block.setancho(squareSize);
				block.setaltura(squareSize);
				
				if(choqueBloques(block))
					continue;
				
				blocks.add(block);
				break;
			}
		}		
		
		while(true)
		{
			boolean viboritaOK = true;
			
			viborita = new Viborita(Utils.random(0, (getWidth() - 1) / squareSize) * squareSize, 
					Utils.random(0, (getHeight() - 1) / squareSize) * squareSize, squareSize, squareSize,
					Utils.random(0, 3));

			Iterator<Bloques> it = blocks.iterator();
			while(it.hasNext())
			{
				Bloques block = it.next();
				if(viborita.choque(block))
					viboritaOK = false;
			}
			
			if(viboritaOK)
				break;
		}
			
		while(true)
		{					
			food = new Comida();
			food.setancho(squareSize);
			food.setaltura(squareSize);
			food.setX(Utils.random(0, (getWidth() - 1) / squareSize) * squareSize);
			food.setY(Utils.random(0, (getHeight() - 1) / squareSize) * squareSize);

			if(!choqueBloques(food))
				break;
		}
		
		frame.getControlsPanel().getScoreLabel().setText(String.format("%03d", viborita.getcuerpo().size() - 1));
		frame.getControlsPanel().disableControls();
		
		th = new Thread(this);
		th.start();
	}
	

        @Override
	public void paint(Graphics g)
	{	
		Graphics2D g2 = (Graphics2D) buffer.createGraphics();
		
		if(titulo)
		{
			// Paint background
			g2.setColor(Color.red);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			g2.drawImage(tituloViboritaImage, 0, 0, this.getWidth(), this.getHeight(), this);
			g2.setColor(Color.white);
			g2.setFont(new Font("Verdana", Font.BOLD, 80));
			g2.drawString("Snake", 180, 270);
			g2.setFont(new Font("Verdana", Font.BOLD, 20));
			g2.drawString("OLMOS Y CARDENAS", 210, 300);
			g2.setFont(new Font("Verdana", Font.BOLD, 30));
			g2.drawString("Presiona enter", 200, 350);	
                        
		}
		else
		{
			// Paint background
			g2.setColor(Color.white);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			// Paint blocks
			Iterator<Bloques> it = blocks.iterator();
			while(it.hasNext())
			{
				Bloques block = it.next();
				block.paint(g2);
			}
			
			// pinta comida
			if(iteration % 5 == 0)
				food.paint(g2);
			
			// Pinta vibo
			viborita.paint(g2);
			
			// Pinta game over
			if(gameOver)
			{
				g2.setColor(new Color(200,0,255));
				g2.setFont(new Font("Verdana", Font.BOLD, 70));
				g2.drawString("GAME OVER", 16, this.getHeight() / 2);	
				g2.setFont(new Font("Verdana", Font.BOLD, 30));
				g2.drawString("Presiona enter", 120, this.getHeight() / 2 + 50);			
			}
		}
		g.drawImage(buffer, 0, 0, this);
	}
	
	@Override
        
	public void run()
	{		
            
		while(!gameOver)
		{
			iteration++;
			if(iteration == 10)
				iteration = 0;
			
			if(!directionChanged)
				viborita.mov();
			else
				directionChanged = false;

			Posicion point = viborita.getcuerpo().get(0);
			if(point.getX() < 0 || point.getX() + viborita.getancho() > this.getWidth() || point.getY() < 0 || point.getY() + viborita.getaltura() > this.getHeight())
			{
				Utils.playSound(crashSound);
				gameOver = true;
			}
			
			Iterator<Bloques> it = blocks.iterator();
			while(it.hasNext())
			{
				Bloques block = it.next();
				if(viborita.choque(block))
				{
					Utils.playSound(crashSound);
					gameOver = true;
				}
			}
			
			if(viborita.choque(food))
			{ 
				Utils.playSound(growSound);
				viborita.crecer();
				frame.getControlsPanel().getScoreLabel().setText(String.format("%03d", viborita.getcuerpo().size() - 1));
				
				while(true)
				{					
					food = new Comida();
					food.setancho(squareSize);
					food.setaltura(squareSize);
					food.setX(Utils.random(0, (getWidth() - 1) / squareSize) * squareSize);
					food.setY(Utils.random(0, (getHeight() - 1) / squareSize) * squareSize);
	
					if(!choqueBloques(food))
						break;
				}
			}
			else if(viborita.suicidio())
			{
				Utils.playSound(crashSound);
				gameOver = true;
			}
			
			try {
				Thread.sleep(400 / speed);
			} catch (InterruptedException e) {
			}
			
			repaint();
		}
		
		frame.getControlsPanel().enableControls();
	}

	private boolean choqueBloques(Sprite sprite)
	{
		Iterator<Bloques> it = blocks.iterator();
		while(it.hasNext())
		{
			Bloques block = it.next();
			if(sprite.choque(block))
				return true;
		}
		
		return false;
	}
	
	public boolean istitulo() {
		return titulo;
	}

	public void settitulo(boolean titulo) {
		this.titulo = titulo;
	}
	
	public int getBlocksNumber() {
		return blocksNumber;
	}

	public void setBlocksNumber(int blocksNumber) {
		this.blocksNumber = blocksNumber;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSquareSize() {
		return squareSize;
	}

	public void setSquareSize(int squareSize) {
		this.squareSize = squareSize;
	}

    
}