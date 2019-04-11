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
	static boolean drawMode=false; // отвечает за возможность задавания точек исходных треугольников на экранеы
	static ArrayList <Dot> dots= new ArrayList<Dot>(); //массив точек треугольников, отображаемых на экране
	static  World world = new World();
	public static void main(String[] args) throws InterruptedException, IOException 
    {
        JFrame frame = new JFrame("Проект"); // создаем Frame
       
        TrianglePanel panel = new TrianglePanel(world);
        frame.add(panel);
        panel.setBackground(Color.black);
        frame.setSize(TrianglePanel.WIDTH, TrianglePanel.HEIGHT);
        JButton button = new JButton("Рисование точек треугольника");//при нажатии на кнопку с помощью нажатия мышки на экран можно вводить координаты вершин треугольников, пока на экране не нарисуется ответ
        button.setBackground(Color.black);
        button.setForeground(Color.white);
        JButton button2 = new JButton("Нажмите для окончания ввода точек");//при нажатии на кнопку возможность задавания точек треугольника отключается 
        button2.setBackground(Color.black);
        button2.setForeground(Color.white);
        JButton pauseButton = new JButton("Нарисовать треугольники и широкие лучи");
        JButton helloWorldButton = new JButton("Показать результат");
        pauseButton.setBackground(Color.black);
        pauseButton.setForeground(Color.white);
        helloWorldButton.setBackground(Color.black);
        helloWorldButton.setForeground(Color.white);
        pauseButton.addActionListener(new MyMouseListener(panel));
        panel.addMouseListener(new MyMouseListener(panel) );
        panel.addMouseMotionListener(new MyMouseListener(panel));
        // Вторая кнопка должна выводить сообщение в консоль
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
        	}
        	if(panel.paused3==false)
        	{
        	for (int i =0 ; i<dots.size(); i++)
        	{
        		dots.get(i).draw(panel.getGraphics());//рисование точек на экране
        	}
        	}
        	Thread.sleep(10);
        }
    }
}
