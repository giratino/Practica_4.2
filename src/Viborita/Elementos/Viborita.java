package Viborita.Elementos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;

public class Viborita extends Sprite
{
	public static  int LEFT = 0;
	public static  int RIGHT = 1;
	public static  int UP = 2;
	public static  int DOWN = 3;
	private LinkedList<Posicion> cuerpo = new LinkedList<>();
	private int direccion; //  IZQ: 0 | DER: 1 | ARRIBA: 2 | ABAJO: 3
	
	public Viborita(int x, int y, int ancho, int altura, int direccion)
	{
		Posicion p = new Posicion(x,y);
		cuerpo.add(p);
		
		this.ancho = ancho;
		this.altura = altura;
		this.direccion = direccion;
	}
	
	public void setdireccion(int direccion)
	{
		this.direccion = direccion;
	}
	
	public int getdireccion()
	{
		return direccion;
	}
	
        @Override
        public int getancho() {
		return ancho;
	}

        @Override
	public void setancho(int ancho) {
		this.ancho = ancho;
	}

        @Override
	public int getaltura() {
		return altura;
	}

        @Override
	public void setaltura(int altura) {
		this.altura = altura;
	}
        
        @Override
	public boolean choque(Sprite sprite) {
		Iterator<Posicion> it = cuerpo.iterator();
		int i = 0;
		while(it.hasNext())
		{
			Posicion posicion = it.next();
			if (posicion.getX() + ancho <= sprite.getX() || sprite.getX() + sprite.getancho() <= posicion.getX() || posicion.getY() + ancho <= sprite.getY()|| sprite.getY() + sprite.getaltura() <= posicion.getY())
				i++;
		}

		return i != cuerpo.size();
	}
	
	public boolean suicidio() {
		Iterator<Posicion> it = cuerpo.iterator();
		int i = 0;
		while(it.hasNext())
		{
			Iterator<Posicion> it2 = cuerpo.iterator();
			Posicion posicion = it.next();
			
			while(it2.hasNext())	
			{
				Posicion posicion2 = it2.next();
				
				if(posicion.getX() != posicion2.getX() || posicion.getY() != posicion2.getY())
				{
					if (posicion.getX() + ancho <= posicion2.getX() || posicion2.getX() + ancho <= posicion.getX()
							|| posicion.getY() + ancho <= posicion2.getY()
							|| posicion2.getY() + altura <= posicion.getY())
						i++;
				}
			}
		}

		if((((cuerpo.size() * cuerpo.size()) - cuerpo.size())) == i)
			return false;
		
		return true;
	}
	
	@Override
	public void paint(Graphics2D g2) {
		Iterator<Posicion> it = cuerpo.iterator();
		while(it.hasNext())
		{
			Posicion posicion = it.next();
			g2.setColor(Color.black);
			g2.fillRect(posicion.getX(), posicion.getY(), ancho, altura);
		}
	}
	
	public void crecer()
	{
		Posicion p =  new Posicion();
		Posicion last = cuerpo.getLast();

		if(direccion == Viborita.LEFT)
		{
			p.setLocation(last.getX() + ancho, last.getY());
		}
		else if(direccion == Viborita.RIGHT)
		{
			p.setLocation(last.getX() - ancho, last.getY());
		}
		else if(direccion == Viborita.UP)
		{
			p.setLocation(last.getX(), last.getY() + altura);
		}
		else if(direccion == Viborita.DOWN)
		{
			p.setLocation(last.getX(), last.getY() - altura);
		}
				
		cuerpo.addLast(p);		
	}
	
	public void mov()
	{	
		if(cuerpo.size() > 0)
		{
			Posicion posicion = cuerpo.get(cuerpo.size() - 1);
			
			for(int i = cuerpo.size() - 2; i >= 0; i--)
			{
				Posicion posicion2 = cuerpo.get(i);

				posicion.setLocation(posicion2.getX(), posicion2.getY());
				
				posicion = posicion2;
			}
		}
		
		Posicion posicion = cuerpo.get(0);
		
		if (direccion == Viborita.LEFT)
		{
			posicion.setLocation(posicion.getX() - ancho, posicion.getY());
		}
		else if (direccion == Viborita.RIGHT)
		{
			posicion.setLocation(posicion.getX() + ancho, posicion.getY());
		}
		else if (direccion == Viborita.UP)
		{
			posicion.setLocation(posicion.getX(), posicion.getY() - ancho);
		}
		else if (direccion == Viborita.DOWN)
		{
			posicion.setLocation(posicion.getX(), posicion.getY() + ancho);
		}
	}
	
	
	public LinkedList<Posicion> getcuerpo() {
		return cuerpo;
	}

	public void setcuerpo(LinkedList<Posicion> cuerpo) {
		this.cuerpo = cuerpo;
	}
}
