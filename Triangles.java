package porjecttry;
import java.awt.*;
import java.util.Random;
public class Triangles//�����, ���������� �� ���������� � �������������
{
    int x []; 
    int y [];
    Random rand = new Random();
    float r = rand.nextFloat();
    float t = rand.nextFloat();
    float b = rand.nextFloat();
    Color color =new Color(r,t,b);
    Triangles(int x[], int y[])
    {
     this.x=x;
     this.y=y;
    }
    public void draw1(Graphics g)
    {
    	r = rand.nextFloat();//���������� ��� ��������� ���������� r,g,b, �� ������� �������������� ����� 
    	t = rand.nextFloat();
    	b = rand.nextFloat();
        g.setColor(color);//�������� ����� ������
        g.drawPolygon(x,y, 3);
    }
}