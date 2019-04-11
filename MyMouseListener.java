package porjecttry;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MyMouseListener implements MouseMotionListener,MouseListener,ActionListener
{
	TrianglePanel panel;
	World world=new World();
	public MyMouseListener(TrianglePanel panel) 
	{
		this.panel = panel;
	}
	public MyMouseListener() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (!panel.isPaused()) 
		{
	        panel.setPaused(true);
	    }
		else
		{
	        panel.setPaused(false);
	    }
	
		
	}
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if (Main.drawMode==true)
		{
			Main.dots.add(new Dot(e.getX(), e.getY()));//добавляем в массив точек-координат треугольника
			Main.world.dots.add(new Dot(e.getX(), e.getY()));
	
			if(Main.world.dots.size()==3)//если количество вершин 3- то добавляется в массив треугольников!
			{
				Main.world.updates11();//добавляется в массив треугольников!
				System.out.println(Main.world.triangles.size());
				Main.world.dots.clear();//обнуляется массив
			}
			if(Main.world.amount_of_triangles==Main.world.triangles.size())//если количество точек совпадает с количеством треугольников
			{
				panel.setPaused3(true);//????????????
			}

			
		}
	}
@Override
public void mousePressed(MouseEvent e) {
}

@Override
public void mouseReleased(MouseEvent e) {
}

@Override
public void mouseEntered(MouseEvent e) {
}

@Override
public void mouseExited(MouseEvent e) {
}

@Override
public void mouseDragged(MouseEvent e) {
}

@Override
public void mouseMoved(MouseEvent e) {
}


}
