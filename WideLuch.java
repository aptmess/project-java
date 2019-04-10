package porjecttry;

import java.awt.Color;
import java.awt.Graphics;
public class WideLuch//метод, отвечающий за информцию о широких лучах
{
	int x1; int y1;
	int x2; int y2;
	Color color = Color.CYAN;
	WideLuch(int x1, int y1,int x2, int y2)
	{
		this.x1=x1; this.y1=y1;
		this.x2=x2; this.y2=y2;
	}
	public void draw1(Graphics g)
	{
		g.setColor(color);
	    g.drawLine(x1,y1, x2,y2);   
	}	
}