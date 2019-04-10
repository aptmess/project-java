package porjecttry;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class FigureInside//массив фигур, нполучаемых при пересечении широкого луча и треугольников
{
	int x []; 
    int y [];
    int n;
    Random rand = new Random();
    float r = rand.nextFloat();
    float t = rand.nextFloat();
    float b = rand.nextFloat();
    Color color =new Color(r,t,b);
    FigureInside(int x[],int y[],int n)
    {
    	this.x=x;
    	this.y=y;
    	this.n=n;
    }
	public void draw4(Graphics g)
	{
    	
		g.setColor(Color.magenta);
	    g.fillPolygon(x,y,n);   
	}	
	
}
