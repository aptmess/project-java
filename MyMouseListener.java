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
			Main.dots.add(new Dot(e.getX(), e.getY()));//��������� � ������ �����-��������� ������������
			world.dots.add(new Dot(e.getX(), e.getY()));
	
			if(world.dots.size()==3)//���� ���������� ������ 3- �� ����������� � ������ �������������!
			{
				world.updates11();//����������� � ������ �������������!
				System.out.println(world.triangles.size());
				world.dots.clear();//���������� ������
			}
			if(world.amount_of_triangles==world.triangles.size())//���� ���������� ����� ��������� � ����������� �������������
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
