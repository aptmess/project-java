package porjecttry;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.io.IOException;
import java.util.ArrayList;
public class Main 
{
	static boolean drawMode=false; // �������� �� ����������� ��������� ����� �������� ������������� �� �������
	static ArrayList <Dot> dots= new ArrayList<Dot>(); //������ ����� �������������, ������������ �� ������
	public static void main(String[] args) throws InterruptedException, IOException 
    {
        JFrame frame = new JFrame("������"); // ������� Frame
        World world = new World();
        TrianglePanel panel = new TrianglePanel(world);
        frame.add(panel);
        panel.setBackground(Color.black);
        frame.setSize(TrianglePanel.WIDTH, TrianglePanel.HEIGHT);
        JButton button = new JButton("��������� ����� ������������");//��� ������� �� ������ � ������� ������� ����� �� ����� ����� ������� ���������� ������ �������������, ���� �� ������ �� ���������� �����
        button.setBackground(Color.black);
        button.setForeground(Color.white);
        JButton button2 = new JButton("������� ��� ��������� ����� �����");//��� ������� �� ������ ����������� ��������� ����� ������������ ����������� 
        button2.setBackground(Color.black);
        button2.setForeground(Color.white);
        JButton pauseButton = new JButton("���������� ������������ � ������� ����");
        JButton helloWorldButton = new JButton("�������� ���������");
        pauseButton.setBackground(Color.black);
        pauseButton.setForeground(Color.white);
        helloWorldButton.setBackground(Color.black);
        helloWorldButton.setForeground(Color.white);
        pauseButton.addActionListener(new MyMouseListener(panel));
        // ������ ������ ������ �������� ��������� � �������
        helloWorldButton.addActionListener(new ActionListener() {
			@Override
        	public void actionPerformed(ActionEvent e) 
        	{
				if (!panel.isPaused2()) 
				{
			        panel.setPaused2(true);
			        }
				else
				{
			        panel.setPaused2(false);
			    }
        	}
        	}
        );
        button.addActionListener(new ActionListener() {
			@Override
        	public void actionPerformed(ActionEvent e) 
        	{
        		drawMode=true;
        	}
        	}
        );
        button2.addActionListener(new ActionListener() {
			@Override
        	public void actionPerformed(ActionEvent e) 
        	{
				//world.draw(panel.getGraphics());
				//System.out.println(world.triangles.size());
				drawMode = false;
        	}
        	}
        );
        JPanel controls =new JPanel();
        controls.setLayout(new GridLayout(1,4));
        controls.add(button);
        controls.add(button2);
        controls.add(pauseButton);
        controls.add(helloWorldButton);
        MyMouseListener mouselistener = new MyMouseListener();
        panel.addMouseListener(mouselistener);
        panel.addMouseMotionListener(mouselistener);
        frame.setLayout(new BorderLayout());
        frame.add(panel,BorderLayout.CENTER);
        frame.add(controls,BorderLayout.NORTH);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable( false );
        while (true) 
        {
        	
        	
        	if(panel.paused==true)
        	{
        		frame.repaint();
        	}
        	
        	if(panel.paused2==true)
        	{
        		frame.repaint();
        		panel.setPaused(false);
        	}
        	if(panel.paused3==true)
        	{
        		frame.repaint();
        		panel.setPaused(false);
        	}
        	for (int i =0 ; i<dots.size(); i++)
        	{
        		dots.get(i).draw(panel.getGraphics());//��������� ����� �� ������
        	}
        	Thread.sleep(10);
        }
    }
}
