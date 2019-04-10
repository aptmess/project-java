package porjecttry;
import javax.swing.*;
import porjecttry.World;
import java.awt.*;
import java.io.IOException;
public class TrianglePanel extends JPanel 
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	World world;
	boolean paused;
	boolean paused2;
	boolean paused3;
	public static final int WIDTH = 1280;//размеры окна
	public static final int HEIGHT = 720;
    public boolean isPaused() {
        return paused;
    }
    public boolean isPaused2() {
        return paused2;
    }
    public boolean isPaused3() {
        return paused3;
    }
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    public void setPaused2(boolean paused2) {
        this.paused2 = paused2;
    }
    public void setPaused3(boolean paused3) {
        this.paused3 = paused3;
    }
    public TrianglePanel(World world)//создание мира треугольники - широкие лучи
    {
    	this.world=world;
    	this.paused = false;
    	this.paused2 = false;
    	this.paused3 = false;
    }
    @Override
    protected void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	if(this.paused==true)
    	{
    		world.drawtriangles(g);//рисование мира
    	}
    	if(this.paused2==true)
    	{
    		world.draw(g);//рисование мира
    	}
    	if(this.paused3==true)
    	{
    		world.drawtriangles2(g);//рисование мира
    	}
    }
}
