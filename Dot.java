package porjecttry;
import java.awt.*;

public class Dot//точки на экране 
{
	private int x;
	private int y;
	Color color =new Color(x);
	Dot(){}
	Dot(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	public int getY()
	{
		return y;
	}
	public int getX() 
	{
		return x;
	}
	public void draw(Graphics graphics)
	{
		graphics.setColor(Color.white);
		graphics.fillOval(x,y,3,3);
	}
}
