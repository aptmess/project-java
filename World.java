package porjecttry;

import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
public class World //рисование мира треугольников и широких лучей
{	int amount_of_triangles =5; //задается количество треугольников
	int amount_of_wideluch =1; //задается количество широких лучей
	int gran =200; //задается окоемка границы, в пределах данной границы будут генерироваться случайно заданные точки
	int vnutri=0;//переменная-счетчик, отвечающая за подсчет количества точек треугольника, входящих в широкий луч
	Random rand = new Random();// для генерации случайных координат вершин треугольника
	public ArrayList<Triangles> triangles = new ArrayList<>();// массив треугольников
	public ArrayList<WideLuch> wideLuch = new ArrayList<>();//массив широких лучей
	public ArrayList<Triangles> newtriangles = new ArrayList<>();// массив треугольников, прошедщих проверку на кандидата-треугольника, у которого может быть максимальная площадь пересечения с широким лучом
	double koef[] = new double[amount_of_wideluch*2]; //массив коэффициента наклона прямой, четный i-ый элемент-коээфицент наклона широкого луча через две начальные точки, нечетный i-ый элемент- коэффициент прямхы широкого луча, перпендикулярных заданной прямой 
	double pov[] = new double[amount_of_wideluch*2]; //массив коэффициентов pov в уравнении прямой y=koef*x+pov прямых препендикулярных исходной прямой широкого луча
	double no[] = new double[amount_of_wideluch];//массив коэффициентов no уравнения начальной прямой широкого луча
	public ArrayList<PointsVnutri> pointsvnutri = new ArrayList<>();
	public ArrayList<PointsVerh> pointsverh = new ArrayList<>();
	public ArrayList<PointsVniz> pointsvniz = new ArrayList<>();
	public ArrayList<PointsSleva> pointsleva = new ArrayList<>();
	public ArrayList<PointsSlevaVerh> pointslevaverh = new ArrayList<>();
	public ArrayList<PointsSlevaVniz> pointslevavniz = new ArrayList<>();
	public ArrayList<FigureInside> figure_inside = new ArrayList<>();
	double BIGSUMMA=0;
	public ArrayList<SUMMA> bigsum = new ArrayList<>();
	int newx[]=new int[3];
	int newy[]=new int[3];
	int newxx[]=new int[4];
	int newyy[]=new int[4];
	int count=0;
	int t=0;
	int b=1;
	public ArrayList <Dot> dots= new ArrayList<>();
	void drawtriangles(Graphics g)
	{
		if(b==1)
		{
			updates();
			b+=1;
		}
		for (int i = 0; i < triangles.size(); ++i)//рисование исходных треугольников
		{
			triangles.get(i).draw1(g);
		}
		if (wideLuch.size()!=amount_of_wideluch*3)//выполнение фунеции updates1,  умножение на 3, так как в каждом широком луче три элемента - прямая исходная и две перпендикулярные
		{
			updates1(g);
		} 
		for (int i = 0; i < wideLuch.size(); ++i)//рисование широких лучей
		{
			wideLuch.get(i).draw1(g);
		} 
	}
	void drawtriangles2(Graphics g)
	{
		for (int i = 0; i < triangles.size(); ++i)//рисование исходных треугольников
		{
			triangles.get(i).draw1(g);
		}
		if (wideLuch.size()!=amount_of_wideluch*3)//выполнение фунеции updates1,  умножение на 3, так как в каждом широком луче три элемента - прямая исходная и две перпендикулярные
		{
			updates1(g);
		} 
		for (int i = 0; i < wideLuch.size(); ++i)//рисование широких лучей
		{
			wideLuch.get(i).draw1(g);
		} 
	}
	void draw(Graphics g) 
    {
			if (newtriangles.size()!=0)
			{
				for (int i = 0; i < wideLuch.size(); ++i)//рисование широких лучей
				{
					wideLuch.get(i).draw1(g);
				} 
				for (int i = 0; i < newtriangles.size(); ++i)//рисование треугольников, прошедших проверку на возможность быть кандидатом-треугольником с наибольшей площадью пересечения
				{
					//newtriangles.get(i).draw1(g);
				}
			for( int i =0; i< bigsum.size();++i)
			{
				if(bigsum.get(i).sum==BIGSUMMA)
				{
					figure_inside.get(i).draw4(g);
					newtriangles.get(i).draw1(g);
				}
					
			}
			
			}
    }
	void updates()
	{
        for (int i = 0; i < amount_of_triangles; i++)//генерируем массив треугольников 
        {
        	
        	int k[]= {rand.nextInt(TrianglePanel.WIDTH-gran),rand.nextInt(TrianglePanel.WIDTH-gran),rand.nextInt(TrianglePanel.WIDTH-gran)};//3 случайные координаты по x вершин треугольника
        	int g[]= {rand.nextInt(TrianglePanel.HEIGHT-gran),rand.nextInt(TrianglePanel.HEIGHT-gran),rand.nextInt(TrianglePanel.HEIGHT-gran)}; //три случайные коориднаты по y вершин тругольника
            triangles.add(new Triangles(k,g));//добавление нового треугольника в массив треугольников triangles
        }
	}  
	void updates11()
	{
		int k[]= {dots.get(0).getX(),dots.get(1).getX(),dots.get(2).getX()};
		int g[]= {dots.get(0).getY(),dots.get(1).getY(),dots.get(2).getY()};
		triangles.add(new Triangles(k,g));
	}  
    void updates1(Graphics g) //нахождение наибольшой площади пересечения
    {
    	int r=0;
    	int u=0;
    	for (int i = 0; i < amount_of_wideluch; i++)// генерация широких лучей
        {
    		int x1 = rand.nextInt(TrianglePanel.WIDTH-gran);//рандомная координата 4-x точек, через которую пройдет первая прямая: x1,y1,x2,y2
         	int y1 = rand.nextInt(TrianglePanel.HEIGHT-gran);
         	int x2 = rand.nextInt(TrianglePanel.WIDTH-gran);
         	int y2 = rand.nextInt(TrianglePanel.HEIGHT-gran);
         	koef[r] = (double)(y2-y1)/(x2-x1);//добавление на четный элемент массива значения коэффициента наклона данной прямой
     		no[u]=(double)(y2-x2*koef[r]);//добавление на элемент массива значения коэффициента подъема данной начальной прямой
     		koef[r+1] =(double)((-1)/(koef[r]));// ищем коэффициент наклона двух прямых, перпендикулярных начальной, составляющей широкий луч
     		pov[r] = (double)(y1-(koef[r+1])*x1);//добавление на четный элемент массива значения коэффициента подъема перпендикулярной начальной нижней прямой широкого луча
     		pov[r+1] = (double)(y2-(koef[r+1])*x2);//добавление на нечетный элемент массива значения коэффициента подъема перпендикулярной начальной верхней прямой широкого луча
            wideLuch.add(new WideLuch(x1,y1,x2,y2));//добавление в массив широких лучей исходной прямой
            wideLuch.add(new WideLuch(x1,y1,TrianglePanel.WIDTH,(int) (koef[r+1]*TrianglePanel.WIDTH+pov[r])));//перпендикулярной ей нижней
            wideLuch.add(new WideLuch(x2,y2,TrianglePanel.WIDTH,(int) (koef[r+1]*TrianglePanel.WIDTH+pov[r+1])));//перпенидкулярной ей верхней
            r=r+2;
            u=u+1;
         }
    	 for( int b=0; b < amount_of_wideluch; ++b)//проверка на принадлежность точки треугольника определенной области - внутри широго луча, слева от луча, сверху от луча, снизу от луча и.т.д...
    	 {
    		 for( int i=0; i < amount_of_triangles; ++i)
    		 {
    			 for(int j=0; j < 3; ++j)
    			 {
    				 if (pov[2*b]>pov[2*b+1])//если коэффициент подъема нижней прямой больше верхней 
    				 {
    					 if((triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] >= pov[2*b+1]) //если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет > pov[2*b+1]
    							 &&( triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] <= pov[2*b])//если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет < pov[2*b]
    							 &&((triangles.get(i).x[j]-wideLuch.get(b).x1)*(wideLuch.get(b).y2-wideLuch.get(b).y1)-(triangles.get(i).y[j]-wideLuch.get(b).y1)*(wideLuch.get(b).x2-wideLuch.get(b).x1)<0))// проверка на принадлженость лучу внутри
    					 {
    						 pointsvnutri.add(new PointsVnutri(triangles.get(i).x[j],triangles.get(i).y[j]));//добавление в массив точек, лежащих внутри широкого луча
    					 }
    					 if((triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] >= pov[2*b+1]) //если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет > pov[2*b+1]
    							 &&( triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] >= pov[2*b])//если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет > pov[2*b]
    							 &&((triangles.get(i).x[j]-wideLuch.get(b).x1)*(wideLuch.get(b).y2-wideLuch.get(b).y1)-(triangles.get(i).y[j]-wideLuch.get(b).y1)*(wideLuch.get(b).x2-wideLuch.get(b).x1)<0))// проверка на принадлженость лучу именно сверху
    					 {
    						 pointsverh.add(new PointsVerh(triangles.get(i).x[j],triangles.get(i).y[j]));//добавление в массив точек, лежащих сверху широкого луча
    					 }
    					 if((triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] <= pov[2*b+1]) //если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет > pov[2*b+1]
    							 &&( triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] <= pov[2*b])//если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет < pov[2*b]
    							 &&((triangles.get(i).x[j]-wideLuch.get(b).x1)*(wideLuch.get(b).y2-wideLuch.get(b).y1)-(triangles.get(i).y[j]-wideLuch.get(b).y1)*(wideLuch.get(b).x2-wideLuch.get(b).x1)<0))// проверка на принадлженость лучу именно снизу
    					 {
    						 pointsvniz.add(new PointsVniz(triangles.get(i).x[j],triangles.get(i).y[j]));//добавление в массив точек, лежащих снизу широкого луча
    					 }
    					 if((triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] >= pov[2*b+1]) //если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет > pov[2*b+1]
    							 &&( triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] <= pov[2*b])//если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет < pov[2*b]
    							 &&((triangles.get(i).x[j]-wideLuch.get(b).x1)*(wideLuch.get(b).y2-wideLuch.get(b).y1)-(triangles.get(i).y[j]-wideLuch.get(b).y1)*(wideLuch.get(b).x2-wideLuch.get(b).x1)>0))// проверка на принадлженость лучу именно слева
    					 {
    						 pointsleva.add(new PointsSleva(triangles.get(i).x[j],triangles.get(i).y[j]));//добавление в массив точек, лежащих слева от широкого луча
    					 }
    					 if((triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] >= pov[2*b+1]) //если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет > pov[2*b+1]
    							 &&( triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] >= pov[2*b])//если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет < pov[2*b]
    							 &&((triangles.get(i).x[j]-wideLuch.get(b).x1)*(wideLuch.get(b).y2-wideLuch.get(b).y1)-(triangles.get(i).y[j]-wideLuch.get(b).y1)*(wideLuch.get(b).x2-wideLuch.get(b).x1)>0))// проверка на принадлженость лучу именно слева
    					 {
    						 pointslevaverh.add(new PointsSlevaVerh(triangles.get(i).x[j],triangles.get(i).y[j]));//добавление в массив точек, лежащих слева от широкого луча
    					 }
    					 if((triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] <= pov[2*b+1]) //если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет > pov[2*b+1]
    							 &&( triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] <= pov[2*b])//если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет < pov[2*b]
    							 &&((triangles.get(i).x[j]-wideLuch.get(b).x1)*(wideLuch.get(b).y2-wideLuch.get(b).y1)-(triangles.get(i).y[j]-wideLuch.get(b).y1)*(wideLuch.get(b).x2-wideLuch.get(b).x1)>0))// проверка на принадлженость лучу именно слева
    					 {
    						 pointslevavniz.add(new PointsSlevaVniz(triangles.get(i).x[j],triangles.get(i).y[j]));//добавление в массив точек, лежащих слева от широкого луча
    					 }
    				 }
    				 if (pov[2*b]<pov[2*b+1])//если коэффициент подъема нижней прямой меньше верхней 
    				 {
    					 if((triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] >= pov[2*b])
    							 &&(triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] <= pov[2*b+1])
    							 &&((triangles.get(i).x[j]-wideLuch.get(b).x1)*(wideLuch.get(b).y2-wideLuch.get(b).y1)-(triangles.get(i).y[j]-wideLuch.get(b).y1)*(wideLuch.get(b).x2-wideLuch.get(b).x1)>0))
    					 {
    						 pointsvnutri.add(new PointsVnutri(triangles.get(i).x[j],triangles.get(i).y[j]));
    						
    					 }
    					 if((triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] >= pov[2*b])
    							 &&(triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] >= pov[2*b+1])
    							 &&((triangles.get(i).x[j]-wideLuch.get(b).x1)*(wideLuch.get(b).y2-wideLuch.get(b).y1)-(triangles.get(i).y[j]-wideLuch.get(b).y1)*(wideLuch.get(b).x2-wideLuch.get(b).x1)>0))
    					 {
    						 pointsverh.add(new PointsVerh(triangles.get(i).x[j],triangles.get(i).y[j]));
    						
    					 }
    					 if((triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] <= pov[2*b])
    							 &&(triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] <= pov[2*b+1])
    							 &&((triangles.get(i).x[j]-wideLuch.get(b).x1)*(wideLuch.get(b).y2-wideLuch.get(b).y1)-(triangles.get(i).y[j]-wideLuch.get(b).y1)*(wideLuch.get(b).x2-wideLuch.get(b).x1)>0))
    					 {
    						 pointsvniz.add(new PointsVniz(triangles.get(i).x[j],triangles.get(i).y[j]));
    						
    					 }
    					 if((triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] >= pov[2*b])
    							 &&(triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] <= pov[2*b+1])
    							 &&((triangles.get(i).x[j]-wideLuch.get(b).x1)*(wideLuch.get(b).y2-wideLuch.get(b).y1)-(triangles.get(i).y[j]-wideLuch.get(b).y1)*(wideLuch.get(b).x2-wideLuch.get(b).x1)<0))
    					 {
    						 pointsleva.add(new PointsSleva(triangles.get(i).x[j],triangles.get(i).y[j]));
    					 }
    					 if((triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] >= pov[2*b+1]) //если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет > pov[2*b+1]
    							 &&( triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] >= pov[2*b])//если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет < pov[2*b]
    							 &&((triangles.get(i).x[j]-wideLuch.get(b).x1)*(wideLuch.get(b).y2-wideLuch.get(b).y1)-(triangles.get(i).y[j]-wideLuch.get(b).y1)*(wideLuch.get(b).x2-wideLuch.get(b).x1)<0))// проверка на принадлженость лучу именно слева
    					 {
    						 pointslevaverh.add(new PointsSlevaVerh(triangles.get(i).x[j],triangles.get(i).y[j]));//добавление в массив точек, лежащих слева от широкого луча
    					 }
    					 if((triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] <= pov[2*b+1]) //если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет > pov[2*b+1]
    							 &&( triangles.get(i).y[j]-triangles.get(i).x[j]*koef[2*b+1] <= pov[2*b])//если черех точку треугольника провести прямую параллельную широкому лучу и коэффициент подъема будет < pov[2*b]
    							 &&((triangles.get(i).x[j]-wideLuch.get(b).x1)*(wideLuch.get(b).y2-wideLuch.get(b).y1)-(triangles.get(i).y[j]-wideLuch.get(b).y1)*(wideLuch.get(b).x2-wideLuch.get(b).x1)<0))// проверка на принадлженость лучу именно слева
    					 {
    						 pointslevavniz.add(new PointsSlevaVniz(triangles.get(i).x[j],triangles.get(i).y[j]));//добавление в массив точек, лежащих слева от широкого луча
    					 }
    				 }
    			 }
    			 if(pointsvnutri.size()==1&&pointsverh.size()==2)
    			 {
    				 newtriangles.add( new Triangles(triangles.get(i).x,triangles.get(i).y));
    				 newx[0]=pointsvnutri.get(0).x;
    				 newy[0]=pointsvnutri.get(0).y;
    				 double koef1=(double)((pointsverh.get(0).y-pointsvnutri.get(0).y)/(pointsverh.get(0).x-pointsvnutri.get(0).x));
    				 double b1 = (double)((pointsvnutri.get(0).y-pointsvnutri.get(0).x*koef1));
    				 double koef2=(double)((pointsverh.get(1).y-pointsvnutri.get(0).y)/(pointsverh.get(1).x-pointsvnutri.get(0).x));
    				 double b2 = (double)((pointsvnutri.get(0).y-pointsvnutri.get(0).x*koef2));
    				 if(pov[2*b]<pov[2*b+1])
    				 {
    					 double rrr = (b1-pov[2*b])/(koef[2*b+1]-koef1);	
        				 newx[1]=(int) (rrr);
        				 double rrr2 = (b2-pov[2*b])/(koef[2*b+1]-koef2);
        				 newx[2]=(int) (rrr2);
        				 int rrr1=(int)(koef1*rrr+b1);
        				 newy[1]=rrr1;
        				 int rrr12=(int)(koef2*rrr2+b2);
        				 newy[2]=rrr12;
    				 }
    				 if(pov[2*b]>pov[2*b+1])
    				 {
    					 double rrr = (b1-pov[2*b+1])/(koef[2*b+1]-koef1);	
        				 newx[1]=(int) (rrr);
        				 double rrr2 = (b2-pov[2*b+1])/(koef[2*b+1]-koef2);
        				 newx[2]=(int) (rrr2);
        				 int rrr1=(int)(koef1*rrr+b1);
        				 newy[1]=rrr1;
        				 int rrr12=(int)(koef2*rrr2+b2);
        				 newy[2]=rrr12;
    				 }
    				 figure_inside.add(new FigureInside(newx,newy,3));
    				 //figure_inside.get(count).draw4(g);
    				 double first = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[1]-figure_inside.get(count).y[0],2)+Math.pow(figure_inside.get(count).x[1]-figure_inside.get(count).x[0],2));
    				 double second = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[2]-figure_inside.get(count).y[1],2)+Math.pow(figure_inside.get(count).x[2]-figure_inside.get(count).x[1],2));
    				 double third = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[2]-figure_inside.get(count).y[0],2)+Math.pow(figure_inside.get(count).x[2]-figure_inside.get(count).x[0],2));
    				 double p =(double)(first+second+third)/2;
    				 double S = (double)Math.sqrt(p*(p-first)*(p-second)*(p-third));
    				 bigsum.add(new SUMMA(S));
    				 if(S>BIGSUMMA)
    				 {
    					BIGSUMMA=S; 
    					t=count;
    				 }
    				 count+=1;
    				 System.out.println(S);
    				 System.out.println("VNIZ");
     			 }
    			 if((pointsvnutri.size()==1)&&(pointsvniz.size()==2))//случай если одна точка внутри широкого луча и две точки вверху
    			 { 
    				 newtriangles.add(new Triangles(triangles.get(i).x,triangles.get(i).y));//добавление в массив треугольников, которые могут иметь наибольшую площадь пересечения
    				 newx[0]=pointsvnutri.get(0).x;//первый элемент в массиве координат x новой фигуры , образованной при пересечении треугольника и широкого луча
    				 newy[0]=pointsvnutri.get(0).y;//первый элемент в массиве координат y новой фигуры , образованной при пересечении треугольника и широкого луча
    				 double koef1=(double)(pointsvniz.get(0).y-pointsvnutri.get(0).y)/(pointsvniz.get(0).x-pointsvnutri.get(0).x);//вычисление коэффициентов двух прямых через две точки
    				 double b1 = (double)(pointsvnutri.get(0).y-pointsvnutri.get(0).x*koef1);
    				 double koef2=(double)(pointsvniz.get(1).y-pointsvnutri.get(0).y)/(pointsvniz.get(1).x-pointsvnutri.get(0).x);
    				 double b2 = (double)(pointsvnutri.get(0).y-pointsvnutri.get(0).x*koef2);
    				 if(pov[2*b]>pov[2*b+1])//нахождение точек пересечения со сторонами широкго луча и добавление данных координат в массив newx
    				 {
    					 double rrr = (b1-pov[2*b+1])/(koef[2*b+1]-koef1);	
        				 newx[1]=(int) (rrr);
        				 double rrr2 = (b2-pov[2*b+1])/(koef[2*b+1]-koef2);
        				 newx[2]=(int) (rrr2);
        				 int rrr1=(int)(koef1*rrr+b1);
        				 newy[1]=rrr1;
        				 int rrr12=(int)(koef2*rrr2+b2);
        				 newy[2]=rrr12;
    				 }
    				 if(pov[2*b]<pov[2*b+1])
    				 {
    					 double rrr = (b1-pov[2*b])/(koef[2*b+1]-koef1);	
        				 newx[1]=(int) (rrr);
        				 double rrr2 = (b2-pov[2*b])/(koef[2*b+1]-koef2);
        				 newx[2]=(int) (rrr2);
        				 int rrr1=(int)(koef1*rrr+b1);
        				 newy[1]=rrr1;
        				 int rrr12=(int)(koef2*rrr2+b2);
        				 newy[2]=rrr12;
    				 }
    				 figure_inside.add(new FigureInside(newx,newy,3));//добавление фигуры, являющейся пересечением широкого луча и треугольника
    				 //figure_inside.get(count).draw4(g);
    				 double first = (double)Math.sqrt(Math.pow(newy[1]-newy[0],2)+Math.pow(newx[1]-newx[0],2));
    				 double second = (double)Math.sqrt(Math.pow(newy[2]-newy[1],2)+Math.pow(newx[2]-newx[1],2));
    				 double third = (double)Math.sqrt(Math.pow(newy[2]-newy[0],2)+Math.pow(newx[2]-newx[0],2));
    				 double p =(double)(first+second+third)/2;//полупериметр
    				 double S = (double)Math.sqrt(p*(p-first)*(p-second)*(p-third));//герон
    				 bigsum.add(new SUMMA(S));//добавление данной суммы в массив сумм, чтобы потом нарисовать только нужную наибольшую площадь
    				 if(S>BIGSUMMA)//если сумма вычисленная > суммы, которая была до вычилсения, то запоминаем номер и присваиваем сумме значение максимальной
    				 {
    					BIGSUMMA=S; 
    					t=count;
    				 }
    				 count+=1;
    				 System.out.println(S);
    				 System.out.println("VERH");
    			 }
    			 if(pointsvnutri.size()==2&&pointsleva.size()==1)
    			 {
    				 newtriangles.add( new Triangles(triangles.get(i).x,triangles.get(i).y));
    				 newxx[0]=pointsvnutri.get(0).x;
    				 newyy[0]=pointsvnutri.get(0).y;
    				 newxx[1]=pointsvnutri.get(1).x;
    				 newyy[1]=pointsvnutri.get(1).y;
    				 double koef1=(double)((pointsvnutri.get(0).y-pointsleva.get(0).y)/(pointsvnutri.get(0).x-pointsleva.get(0).x));
    				 double b1 = (double)((pointsvnutri.get(1).y-(pointsvnutri.get(1).x)*(koef1)));
    				 double koef2=(double)((pointsvnutri.get(1).y-pointsleva.get(0).y)/(pointsvnutri.get(1).x-pointsleva.get(0).x));
    				 double b2 = (double)((pointsvnutri.get(0).y-(pointsvnutri.get(0).x)*(koef2)));
    				 double rrr = (double)(b1-no[b])/(koef[2*b]-koef1);	
        			 newxx[2]=(int) (rrr); 
        		     double rrr2 = (double)(b2-no[b])/(koef[2*b]-koef2);
        		     newxx[3]=(int) (rrr2);
        		     double rrr1=(double)(koef1*rrr+b1);
        		     newyy[2]=(int) (rrr1);
        		     double rrr12=(double)(koef2*rrr2+b2);
        			 newyy[3]=(int) (rrr12);
    				 
    				 int n = 4;
    				 figure_inside.add(new FigureInside(newxx,newyy,n));
    				 //figure_inside.get(count).draw4(g);
    				 double first1 = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[1]-figure_inside.get(count).y[0],2)+Math.pow(figure_inside.get(count).x[1]-figure_inside.get(count).x[0],2));
    				 double second1 = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[2]-figure_inside.get(count).y[1],2)+Math.pow(figure_inside.get(count).x[2]-figure_inside.get(count).x[1],2));
    				 double third1 = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[2]-figure_inside.get(count).y[0],2)+Math.pow(figure_inside.get(count).x[2]-figure_inside.get(count).x[0],2));
    				 double p1 =(double)(first1+second1+third1)/2;
    				 double S1 = (double)Math.sqrt(p1*(p1-first1)*(p1-second1)*(p1-third1));
    				 double first2 = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[3]-figure_inside.get(count).y[2],2)+Math.pow(figure_inside.get(count).x[3]-figure_inside.get(count).x[2],2));
    				 double second2 = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[3]-figure_inside.get(count).y[0],2)+Math.pow(figure_inside.get(count).x[3]-figure_inside.get(count).x[0],2));
    				 double third2 = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[2]-figure_inside.get(count).y[0],2)+Math.pow(figure_inside.get(count).x[2]-figure_inside.get(count).x[0],2));
    				 double p2 =(double)(first2+second2+third2)/2;
    				 double S2 = (double)Math.sqrt(p2*(p2-first2)*(p2-second2)*(p2-third2));
    				 double S=S1+S2;
    				 bigsum.add(new SUMMA(S));
    				 if(S>BIGSUMMA)
    				 {
    					 BIGSUMMA=S; 
    					 t=count;
    				 }
    				 count+=1;
    				 System.out.println(S);
    				 System.out.println("SLEVAODNA");
     			 }
    			 if( (pointsvnutri.size()==1) && (pointsleva.size()==2) )//дальше идет то же самое рассмотрение случаев - 1 внутри  - две слева и.т.д..
    			 {
    				 newtriangles.add(new Triangles(triangles.get(i).x,triangles.get(i).y));
    				 newx[0]=pointsvnutri.get(0).x;
    				 newy[0]=pointsvnutri.get(0).y;
    				 double koef1=(double)(pointsleva.get(0).y-pointsvnutri.get(0).y)/(pointsleva.get(0).x-pointsvnutri.get(0).x);
    				 double b1 = (double)(pointsvnutri.get(0).y-pointsvnutri.get(0).x*koef1);
    				 double koef2=(double)(pointsleva.get(1).y-pointsvnutri.get(0).y)/(pointsleva.get(1).x-pointsvnutri.get(0).x);
    				 double b2 = (double)(pointsvnutri.get(0).y-pointsvnutri.get(0).x*koef2);
    				 double rrr = (b1-no[b])/(koef[2*b]-koef1);	
    				 newx[1]=(int) (rrr);
    				 double rrr2 = (b2-no[b])/(koef[2*b]-koef2);
    				 newx[2]=(int) (rrr2);
    				 int rrr1=(int)(koef1*rrr+b1);
    				 newy[1]=rrr1;
    				 int rrr12=(int)(koef2*rrr2+b2);
    				 newy[2]=rrr12;
    				 figure_inside.add(new FigureInside(newx,newy,3));
    				 System.out.println(newx[1]);
    				 System.out.println(figure_inside.get(count).x[1]);
    				 //figure_inside.get(count).draw4(g);
    				 double first = (double)Math.sqrt(Math.pow(newy[1]-newy[0],2)+Math.pow(newx[1]-newx[0],2));
    				 double second = (double)Math.sqrt(Math.pow(newy[2]-newy[1],2)+Math.pow(newx[2]-newx[1],2));
    				 double third = (double)Math.sqrt(Math.pow(newy[2]-newy[0],2)+Math.pow(newx[2]-newx[0],2));
    				 double p =(double)(first+second+third)/2;
    				 double S = (double)Math.sqrt(p*(p-first)*(p-second)*(p-third));
    				 bigsum.add(new SUMMA(S));
    				 if(S>BIGSUMMA)
    				 {
    					BIGSUMMA=S; 
    					t=count;
    				 }
    				 count+=1; 
    				 System.out.println(S);
    				 System.out.println("SLEVADVE");
    			 }
    			 if(pointsvnutri.size()==3)
    			 {
    				 newtriangles.add( new Triangles(triangles.get(i).x,triangles.get(i).y));
    				 figure_inside.add(new FigureInside(triangles.get(i).x,triangles.get(i).y,3));
    				 //figure_inside.get(count).draw4(g);
    				 double first = (double)Math.sqrt(Math.pow(triangles.get(i).y[1]-triangles.get(i).y[0],2)+Math.pow(triangles.get(i).x[1]-triangles.get(i).x[0],2));
    				 double second = (double)Math.sqrt(Math.pow(triangles.get(i).y[2]-triangles.get(i).y[1],2)+Math.pow(triangles.get(i).x[2]-triangles.get(i).x[1],2));
    				 double third = (double)Math.sqrt(Math.pow(triangles.get(i).y[2]-triangles.get(i).y[0],2)+Math.pow(triangles.get(i).x[2]-triangles.get(i).x[0],2));
    				 double p =(double)(first+second+third)/2;
    				 double S = (double)Math.sqrt(p*(p-first)*(p-second)*(p-third));
    				 bigsum.add(new SUMMA(S));
    				 if(S>BIGSUMMA)
    				 {
    					BIGSUMMA=S; 
    					t=count;
    				 }
    				 count+=1;
    				 System.out.println(S);
    				 System.out.println("VNUTRI");
    			 }
    			 if(pointsvnutri.size()==2&&pointsverh.size()==1)
    			 {
    				 newtriangles.add( new Triangles(triangles.get(i).x,triangles.get(i).y));
    				 newxx[0]=pointsvnutri.get(0).x;
    				 newyy[0]=pointsvnutri.get(0).y;
    				 newxx[1]=pointsvnutri.get(1).x;
    				 newyy[1]=pointsvnutri.get(1).y;
    				 double koef1=(double)((-pointsverh.get(0).y+pointsvnutri.get(0).y)/(-pointsverh.get(0).x+pointsvnutri.get(0).x));
    				 double b1 = (double)((pointsvnutri.get(0).y-pointsvnutri.get(0).x*koef1));
    				 double koef2=(double)((-pointsverh.get(0).y+pointsvnutri.get(1).y)/(-pointsverh.get(0).x+pointsvnutri.get(1).x));
    				 double b2 = (double)((pointsvnutri.get(1).y-pointsvnutri.get(1).x*koef2));
    				 if(pov[2*b]<pov[2*b+1])
    				 {
    					 double rrr = (b1-pov[2*b+1])/(koef[2*b+1]-koef1);	
        				 newxx[3]=(int) (rrr);
        				 double rrr2 = (b2-pov[2*b+1])/(koef[2*b+1]-koef2);
        				 newxx[2]=(int) (rrr2);
        				 double rrr1=(double)(koef1*rrr+b1);
        				 newyy[3]=(int) (rrr1);
        				 double rrr12=(double)(koef2*rrr2+b2);
        				 newyy[2]=(int) (rrr12);
    				 }
    				 if(pov[2*b]>pov[2*b+1])
    				 {
    					 double rrr = (b1-pov[2*b])/(koef[2*b+1]-koef1);	
        				 newxx[3]=(int) (rrr);
        				 double rrr2 = (b2-pov[2*b])/(koef[2*b+1]-koef2);
        				 newxx[2]=(int) (rrr2);
        				 int rrr1=(int)(koef1*rrr+b1);
        				 newyy[3]=rrr1;
        				 int rrr12=(int)(koef2*rrr2+b2);
        				 newyy[2]=rrr12;
    				 }
    				 int n = 4;
    				 figure_inside.add(new FigureInside(newxx,newyy,n));
    				 //figure_inside.get(count).draw4(g);
    				 double first1 = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[1]-figure_inside.get(count).y[0],2)+Math.pow(figure_inside.get(count).x[1]-figure_inside.get(count).x[0],2));
    				 double second1 = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[2]-figure_inside.get(count).y[1],2)+Math.pow(figure_inside.get(count).x[2]-figure_inside.get(count).x[1],2));
    				 double third1 = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[2]-figure_inside.get(count).y[0],2)+Math.pow(figure_inside.get(count).x[2]-figure_inside.get(count).x[0],2));
    				 double p1 =(double)(first1+second1+third1)/2;
    				 double S1 = (double)Math.sqrt(p1*(p1-first1)*(p1-second1)*(p1-third1));
    				 double first2 = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[3]-figure_inside.get(count).y[2],2)+Math.pow(figure_inside.get(count).x[3]-figure_inside.get(count).x[2],2));
    				 double second2 = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[3]-figure_inside.get(count).y[0],2)+Math.pow(figure_inside.get(count).x[3]-figure_inside.get(count).x[0],2));
    				 double third2 = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[2]-figure_inside.get(count).y[0],2)+Math.pow(figure_inside.get(count).x[2]-figure_inside.get(count).x[0],2));
    				 double p2 =(double)(first2+second2+third2)/2;
    				 double S2 = (double)Math.sqrt(p2*(p2-first2)*(p2-second2)*(p2-third2));
    				 double S=S1+S2;
    				 bigsum.add(new SUMMA(S));
    				 if(S>BIGSUMMA)
    				 {
    					 BIGSUMMA=S; 
    					 t=count;
    				 }
    				 count+=1;
    				 System.out.println(S);
    				 System.out.println("VNIZODNA");
     			 }
    			 if(pointsvnutri.size()==2&&pointsvniz.size()==1)
    			 {
    				 newtriangles.add( new Triangles(triangles.get(i).x,triangles.get(i).y));
    				 newxx[1]=pointsvnutri.get(0).x;
    				 newyy[1]=pointsvnutri.get(0).y;
    				 newxx[0]=pointsvnutri.get(1).x;
    				 newyy[0]=pointsvnutri.get(1).y;
    				 double koef1=(double)((pointsvniz.get(0).y-pointsvnutri.get(0).y)/(pointsvniz.get(0).x-pointsvnutri.get(0).x));
    				 double b1 = (double)((pointsvniz.get(0).y-pointsvniz.get(0).x*koef1));
    				 double koef2=(double)((pointsvniz.get(0).y-pointsvnutri.get(1).y)/(pointsvniz.get(0).x-pointsvnutri.get(1).x));
    				 double b2 = (double)((pointsvnutri.get(0).y-pointsvnutri.get(0).x*koef2));
    				 if(pov[2*b]>pov[2*b+1])
    				 {
    					 double rrr = (b1-pov[2*b+1])/(koef[2*b+1]-koef1);	
        				 newxx[2]=(int)(rrr);
        				 double rrr2 = (b2-pov[2*b+1])/(koef[2*b+1]-koef2);
        				 newxx[3]=(int)(rrr2);
        				 double rrr1=(double)(koef1*rrr+b1);
        				 newyy[2]=(int)rrr1;
        				 double rrr12=(double)(koef2*rrr2+b2);
        				 newyy[3]=(int)rrr12;
    				 }
    				 if(pov[2*b]<pov[2*b+1])
    				 {
    					 double rrr = (b1-pov[2*b])/(koef[2*b+1]-koef1);	
        				 newxx[3]=(int) (rrr);
        				 double rrr2 = (b2-pov[2*b])/(koef[2*b+1]-koef2);
        				 newxx[2]=(int) (rrr2);
        				 int rrr1=(int)(koef1*rrr+b1);
        				 newyy[3]=rrr1;
        				 int rrr12=(int)(koef2*rrr2+b2);
        				 newyy[2]=rrr12;
    				 }
    				 int n = 4;
    				 figure_inside.add(new FigureInside(newxx,newyy,n));
    				 //figure_inside.get(count).draw4(g);
    				 double first = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[1]-figure_inside.get(count).y[0],2)+Math.pow(figure_inside.get(count).x[1]-figure_inside.get(count).x[0],2));
    				 double second = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[2]-figure_inside.get(count).y[1],2)+Math.pow(figure_inside.get(count).x[2]-figure_inside.get(count).x[1],2));
    				 double third = (double)Math.sqrt(Math.pow(figure_inside.get(count).y[2]-figure_inside.get(count).y[0],2)+Math.pow(figure_inside.get(count).x[2]-figure_inside.get(count).x[0],2));
    				 double p =(double)(first+second+third)/2;
    				 double S = (double)Math.sqrt(p*(p-first)*(p-second)*(p-third));
    				 bigsum.add(new SUMMA(S));
    				 if(S>BIGSUMMA)
    				 {
    					BIGSUMMA=S; 
    					t=count;
    				 }
    				 count+=1;
    				 System.out.println(S);
     			 }
    			 pointsvnutri.clear();//обнуляются массивы точек
    			 pointsvniz.clear();
    			 pointsverh.clear();
    			 pointsleva.clear();
    		 }
    		 System.out.println(BIGSUMMA);//выводится наибольшая сумма
    		 for(int i=0; i<bigsum.size();++i)
    		 {
    			 System.out.println(bigsum.get(i).sum);
    		 }
    	 }
    }
}
