package Viborita.Elementos;

public abstract class Sprite implements Draw{
    
	protected int x;
	protected int y;
	protected int ancho;
	protected int altura;
	
	public int getancho() {
		return ancho;
	}

	public void setancho(int ancho) {
		this.ancho = ancho;
	}

	public int getaltura() {
		return altura;
	}

	public void setaltura(int altura) {
		this.altura = altura;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
        
      public boolean choque(Sprite sprite) 
	{
		return !(x + ancho < sprite.getX() || sprite.getX() + sprite.getancho() < x || y + ancho <  sprite.getY() || sprite.getY() + sprite.getaltura() < y);
	}
        
}


