import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
public class PicModel 
{
	int arry[][][];
	int width,height;
	public PicModel()
	{
		
	}
	
	public void build(int x,int y)
	{
		try
		{
			this.arry=new int[x][y][3];
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
	}
	public void set(int x,int y,int r,int g,int b)
	{
		try
		{
			this.arry[x][y][0]=r;
			this.arry[x][y][1]=g;
			this.arry[x][y][2]=b;
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
	}
	public  void ReadPic(String FileName) 
	{ 
		int[] rgb = new int[3];
		File file = new File(FileName); 
		BufferedImage bi=null; 
		try{ 
			bi = ImageIO.read(file); 
			}
		catch(Exception e){ 
			e.printStackTrace(); 
			}
		this.width=bi.getWidth(); 
		this.height=bi.getHeight(); 
		try{ 
			build(width, height);
			}
		catch(Exception e){ 
			e.printStackTrace(); 
			}
		int minx=bi.getMinX(); 
		int miny=bi.getMinY(); 
		//System.out.println("width="+width+",height="+height+"."); 
		//System.out.println("minx="+minx+",miniy="+miny+".");
		for(int i=minx;i<width;i++)
		{ 
			for(int j=miny;j<height;j++)
			{ 
				//System.out.print(bi.getRGB(jw, ih)); 
				int pixel=bi.getRGB(i, j); 
				rgb[0] = (pixel & 0xff0000 ) >> 16 ; 
			    rgb[1] = (pixel & 0xff00 ) >> 8 ; 
			    rgb[2] = (pixel & 0xff ); 
				set(i, j, rgb[0], rgb[1], rgb[2]);
				//System.out.println("i="+i+",j="+j+":("+rgb[0]+","+rgb[1]+","+rgb[2]+")");
			} 
		}
	}
	public void test()
    {
    	System.out.println("r="+arry[0][0][0]+",g="+arry[0][0][1]+".");
    }
    public void encode(String passwdMd5)
    {
    	int i=0;
    	char t;
    	for(i=0;i<passwdMd5.length();i++)
    	{
    		t=passwdMd5.charAt(i);
    		encodeMethod(t);
    	}
    	
    	System.out.println("Congratulation:Enconde successed");
    	return;
    }
    public void decode(String passwdMd5)
    {
    	int i=0;
    	char t;
    	for(i=passwdMd5.length()-1;i>=0;i--)
    	{
    		
    		t=passwdMd5.charAt(i);
    		//System.out.println(t);
    		encodeMethod(t);
    	}
    	//savePic("G:\\δ����1.bmp");
    	System.out.println("Congratulation:Deconde successed");
    	
    	return;
    }

	public void savePic(String filePath) {
		try {
			// ����������ļ�����
			java.io.FileOutputStream fos = new java.io.FileOutputStream(filePath);
			// ����ԭʼ�������������
			java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

			// ���ļ�ͷ�ı�����ֵ
			int bfType = 0x424d; // λͼ�ļ����ͣ�0��1�ֽڣ�
			int bfSize = 54 + width * height * 3;// bmp�ļ��Ĵ�С��2��5�ֽڣ�
			int bfReserved1 = 0;// λͼ�ļ������֣�����Ϊ0��6-7�ֽڣ�
			int bfReserved2 = 0;// λͼ�ļ������֣�����Ϊ0��8-9�ֽڣ�
			int bfOffBits = 54;// �ļ�ͷ��ʼ��λͼʵ������֮����ֽڵ�ƫ������10-13�ֽڣ�

			// �������ݵ�ʱ��Ҫע��������������ڴ���Ҫռ�����ֽڣ�
			// Ȼ����ѡ����Ӧ��д�뷽�������������Լ��������������
			// �����ļ�ͷ����
			dos.writeShort(bfType); // ����λͼ�ļ�����'BM'
			dos.write(changeByte(bfSize), 0, 4); // ����λͼ�ļ���С
			dos.write(changeByte(bfReserved1), 0, 2);// ����λͼ�ļ�������
			dos.write(changeByte(bfReserved2), 0, 2);// ����λͼ�ļ�������
			dos.write(changeByte(bfOffBits), 0, 4);// ����λͼ�ļ�ƫ����

			// ����Ϣͷ�ı�����ֵ
			int biSize = 40;// ��Ϣͷ������ֽ�����14-17�ֽڣ�
			int biWidth = width;// λͼ�Ŀ�18-21�ֽڣ�
			int biHeight = height;// λͼ�ĸߣ�22-25�ֽڣ�
			int biPlanes = 1; // Ŀ���豸�ļ��𣬱�����1��26-27�ֽڣ�
			int biBitcount = 24;// ÿ�����������λ����28-29�ֽڣ���������1λ��˫ɫ����4λ��16ɫ����8λ��256ɫ������24λ�����ɫ��֮һ��
			int biCompression = 0;// λͼѹ�����ͣ�������0����ѹ������30-33�ֽڣ���1��BI_RLEBѹ�����ͣ���2��BI_RLE4ѹ�����ͣ�֮һ��
			int biSizeImage = width * height;// ʵ��λͼͼ��Ĵ�С��������ʵ�ʻ��Ƶ�ͼ���С��34-37�ֽڣ�
			int biXPelsPerMeter = 0;// λͼˮƽ�ֱ��ʣ�ÿ����������38-41�ֽڣ��������ϵͳĬ��ֵ
			int biYPelsPerMeter = 0;// λͼ��ֱ�ֱ��ʣ�ÿ����������42-45�ֽڣ��������ϵͳĬ��ֵ
			int biClrUsed = 0;// λͼʵ��ʹ�õ���ɫ���е���ɫ����46-49�ֽڣ������Ϊ0�Ļ���˵��ȫ��ʹ����
			int biClrImportant = 0;// λͼ��ʾ��������Ҫ����ɫ��(50-53�ֽ�)�����Ϊ0�Ļ���˵��ȫ����Ҫ

			// ��Ϊjava�Ǵ�˴洢����ôҲ����˵ͬ�����������
			// ��������ǰ�С�˶�ȡ��������ǲ��ı���ֽ����ݵ�˳��Ļ�����ô�����Ͳ���������ȡ��
			// �������ȵ��÷�����int����ת��Ϊ���byte���ݣ����Ұ�С�˴洢��˳��
			// ������Ϣͷ����
			dos.write(changeByte(biSize), 0, 4);// ������Ϣͷ���ݵ����ֽ���
			dos.write(changeByte(biWidth), 0, 4);// ����λͼ�Ŀ�
			dos.write(changeByte(biHeight), 0, 4);// ����λͼ�ĸ�
			dos.write(changeByte(biPlanes), 0, 2);// ����λͼ��Ŀ���豸����
			dos.write(changeByte(biBitcount), 0, 2);// ����ÿ������ռ�ݵ��ֽ���
			dos.write(changeByte(biCompression), 0, 4);// ����λͼ��ѹ������
			dos.write(changeByte(biSizeImage), 0, 4);// ����λͼ��ʵ�ʴ�С
			dos.write(changeByte(biXPelsPerMeter), 0, 4);// ����λͼ��ˮƽ�ֱ���
			dos.write(changeByte(biYPelsPerMeter), 0, 4);// ����λͼ�Ĵ�ֱ�ֱ���
			dos.write(changeByte(biClrUsed), 0, 4);// ����λͼʹ�õ�����ɫ��
			dos.write(changeByte(biClrImportant), 0, 4);// ����λͼʹ�ù�������Ҫ����ɫ��

			// ��Ϊ��24λͼ������û����ɫ��
			// ͨ����������λͼ����
			// ���������ʱ��ע�⣬�ڼ�����ڴ���λͼ�����Ǵ����ң����µ���������ģ�
			// Ҳ����˵ʵ��ͼ��ĵ�һ�еĵ����ڴ������һ��
			for (int j = height-1; j >=0 ; j--) 
			{
				for (int i=0; i <width; i++) {
					// ���ﻹ��Ҫע����ǣ�ÿ��������������RGB��ɫ������ɵģ�
					// ��������windows����ϵͳ����С�˴洢���Զ��ֽ��������á�
					int red = arry[i][j][0];// �õ�λͼ��ĺ�ɫ����
					int green = arry[i][j][1];// �õ�λͼ�����ɫ����
					int blue = arry[i][j][2];// �õ�λͼ�����ɫ����
					byte[] red1 = changeByte(red);
					byte[] green1 = changeByte(green);
					byte[] blue1 = changeByte(blue);
					dos.write(blue1, 0, 1);
					dos.write(green1, 0, 1);
					dos.write(red1, 0, 1);
				}
			}
			// �ر����ݵĴ���
			dos.flush();
			dos.close();
			fos.close();
			System.out.println("success!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
    private byte[] changeByte(int data){  
    	       byte b4 = (byte)((data)>>24);  
    	        byte b3 = (byte)(((data)<<8)>>24);  
    	       byte b2= (byte)(((data)<<16)>>24);  
    	       byte b1 = (byte)(((data)<<24)>>24);  
    	        byte[] bytes = {b1,b2,b3,b4};  
            return bytes;  
        }  

    private void swap(int x,int y,int x2,int y2)//���ص�Ľ���
    {
    	int c;
    	c=0;
    	c=arry[x][y][0];
    	arry[x][y][0]=arry[x2][y2][0];
    	arry[x2][y2][0]=c;
    	
    	c=arry[x][y][1];
    	arry[x][y][1]=arry[x2][y2][1];
    	arry[x2][y2][1]=c;
    	
    	c=arry[x][y][2];
    	arry[x][y][2]=arry[x2][y2][2];
    	arry[x2][y2][2]=c;
    	return;
    	
    }
    private void swap2(int x,int y,int n)//rgb�ı仯
    {
    	switch(n)
    	{
    	   case 0://r��g�Ľ���
    	   {
    		   int c;
    		   c= arry[x][y][0];
    		   arry[x][y][0]=arry[x][y][1];
    		   arry[x][y][1]=c;
    		   break;
    	   }
    	   case 1://r��b�Ľ���
    	   {
    		   int c;
    		   c= arry[x][y][0];
    		   arry[x][y][0]=arry[x][y][2];
    		   arry[x][y][2]=c;
    		   break;
    	   }
    	   case 2://ȡ��
    	   {
    		   int c;
    		   arry[x][y][0]=255-arry[x][y][0];
    		   arry[x][y][1]=255-arry[x][y][1];
    		   arry[x][y][2]=255-arry[x][y][2];
    	   }
    
    	}
    }
    
    
    private void encodeMethod(char t)//����
    {
    	
    	if(t=='a'||t=='A')//A�����������н��ж���
		{
    		int x=0;
    		while(x<(this.width/2))
    		{
    			for(int y=0;y<height;y++)
    			{
    				swap(x,y,width-1-x,y);
    			}
    			x=x+2;//2�ǿ��Ա��3��
    		}
			return;
		}
    	if(t=='b'||t=='B')//B�����������н��ж���
		{
    		int y=0;
    		while(y<(this.height/2))
    		{
    			for(int x=0;x<width;x++)
    			{
    				swap(x,y,x,height-1-y);
    				
    			}
    			y=y+2;//2�ǿ��Ա��3��
    		}
			return;
			//
		}
    	if(t=='c'||t=='C')
		{
    		int x=0;
    		while(x<(this.width/2))
    		{
    			for(int y=0;y<height;y++)
    			{
    				swap(x,y,width/2+x,y);
    				
    			}
    			x=x+3;//2�ǿ��Ա��3��
    		}
			return;
			//
		}if(t=='d'||t=='D')
		{
			int y=0;
    		while(y<(this.height/2))
    		{
    			for(int x=0;x<width;x++)
    			{
    				swap(x,y,x,height/2+y);
    				
    			}
    			y=y+3;//2�ǿ��Ա��3��
    		}
			return;
			//
		}if(t=='e'||t=='E')//��������rgb
		{
			int y=0;
    		while(y<(this.height))
    		{
    			for(int x=0;x<width;x++)
    			{
    				swap2(x,y,0);
    				
    			}
    			y=y+3;//2�ǿ��Ա��3��
    		}
			return;
			//
		}if(t=='F'||t=='f')
		{
			int x=0;
    		while(x<(this.width))
    		{
    			for(int y=0;y<height;y++)
    			{
    				swap2(x,y,1);
    				
    			}
    			x=x+3;//2�ǿ��Ա��3��
    		}
			return;
			//
		}if(t=='0')
		{
			int y=0;
    		while(y<(this.height/2))
    		{
    			for(int x=0;x<width;x++)
    			{
    				swap(x,y,x,height/2+y);
    				
    			}
    			y=y+3;//2�ǿ��Ա��3��
    		}
			return;
			//
		}if(t=='1')
		{
			int x=0;
    		while(x<(this.width/2))
    		{
    			for(int y=0;y<height;y++)
    			{
    				swap(x,y,width/2+x,y);
    				
    			}
    			x=x+2;//2�ǿ��Ա��3��
    		}
			return;
			//
		}if(t=='2')
		{
			int x=0;
    		while(x<(this.width/2))
    		{
    			for(int y=0;y<height;y++)
    			{
    				swap2(x,y,2);//rgbȡ��
    				
    			}
    			x=x+2;//2�ǿ��Ա��3��
    		}
			return;
			//
		}if(t=='3')
		{
			int y=0;
    		while(y<(this.height))
    		{
    			for(int x=0;x<width;x++)
    			{
    				swap2(x,y,2);//rgbȡ��
    				
    			}
    			y=y+3;//2�ǿ��Ա��3��
    		}
			
			return;
			//
		}if(t=='4')
		{
			int x=0;
    		while(x<(this.width/3))
    		{
    			for(int y=0;y<height;y++)
    			{
    				swap(x,y,width/3+x,y);
    				
    			}
    			x=x+3;//2�ǿ��Ա��3��
    		}
			return;
			//
		}if(t=='5')
		{
			int y=0;
    		while(y<(this.height/3))
    		{
    			for(int x=0;x<width;x++)
    			{
    				swap(x,y,x,height/3+y);
    				
    			}
    			y=y+3;//2�ǿ��Ա��3��
    		}
			return;
			//
		}if(t=='6')
		{
			int x=this.width/3;
			int x2=0;
    		while(x<(this.width))
    		{
    			for(int y=0;y<height;y++)
    			{
    				swap(x,y,x2,y);
    				
    			}
    			x2++;
    			x=x+3;//2�ǿ��Ա��3��
    		}
			return;
			//
		}if(t=='7')
		{
			int y=this.height/3;
			int y2=0;
    		while(y<(this.height))
    		{
    			for(int x=0;x<width;x++)
    			{
    				swap(x,y,x,y2);
    				
    			}
    			y2++;
    			y=y+3;//2�ǿ��Ա��3��
    		}
			
			return;
			//
		}if(t=='8')
		{
			
			int x2=this.width-1;
			int x=this.width-this.width/3;
    		while(x>=0)
    		{
    			for(int y=0;y<height;y++)
    			{
    				swap(x,y,x2,y);
    				
    			}
    			x2--;
    			x=x-3;//2�ǿ��Ա��3��
    		}
			
			return;
			//
		}if(t=='9')
		{
			
			int y2=this.height-1;
			int y=this.height-this.height/3;
    		while(y>=0)
    		{
    			for(int x=0;x<width;x++)
    			{
    				swap(x,y,x,y2);
    				
    			}
    			y2--;
    			y=y-3;//2�ǿ��Ա��3��
    		}
			
			
			return;
			//
		}
    }
}
