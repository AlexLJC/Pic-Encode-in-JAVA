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
    	//savePic("G:\\未命名1.bmp");
    	System.out.println("Congratulation:Deconde successed");
    	
    	return;
    }

	public void savePic(String filePath) {
		try {
			// 创建输出流文件对象
			java.io.FileOutputStream fos = new java.io.FileOutputStream(filePath);
			// 创建原始数据输出流对象
			java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

			// 给文件头的变量赋值
			int bfType = 0x424d; // 位图文件类型（0—1字节）
			int bfSize = 54 + width * height * 3;// bmp文件的大小（2—5字节）
			int bfReserved1 = 0;// 位图文件保留字，必须为0（6-7字节）
			int bfReserved2 = 0;// 位图文件保留字，必须为0（8-9字节）
			int bfOffBits = 54;// 文件头开始到位图实际数据之间的字节的偏移量（10-13字节）

			// 输入数据的时候要注意输入的数据在内存中要占几个字节，
			// 然后再选择相应的写入方法，而不是它自己本身的数据类型
			// 输入文件头数据
			dos.writeShort(bfType); // 输入位图文件类型'BM'
			dos.write(changeByte(bfSize), 0, 4); // 输入位图文件大小
			dos.write(changeByte(bfReserved1), 0, 2);// 输入位图文件保留字
			dos.write(changeByte(bfReserved2), 0, 2);// 输入位图文件保留字
			dos.write(changeByte(bfOffBits), 0, 4);// 输入位图文件偏移量

			// 给信息头的变量赋值
			int biSize = 40;// 信息头所需的字节数（14-17字节）
			int biWidth = width;// 位图的宽（18-21字节）
			int biHeight = height;// 位图的高（22-25字节）
			int biPlanes = 1; // 目标设备的级别，必须是1（26-27字节）
			int biBitcount = 24;// 每个像素所需的位数（28-29字节），必须是1位（双色）、4位（16色）、8位（256色）或者24位（真彩色）之一。
			int biCompression = 0;// 位图压缩类型，必须是0（不压缩）（30-33字节）、1（BI_RLEB压缩类型）或2（BI_RLE4压缩类型）之一。
			int biSizeImage = width * height;// 实际位图图像的大小，即整个实际绘制的图像大小（34-37字节）
			int biXPelsPerMeter = 0;// 位图水平分辨率，每米像素数（38-41字节）这个数是系统默认值
			int biYPelsPerMeter = 0;// 位图垂直分辨率，每米像素数（42-45字节）这个数是系统默认值
			int biClrUsed = 0;// 位图实际使用的颜色表中的颜色数（46-49字节），如果为0的话，说明全部使用了
			int biClrImportant = 0;// 位图显示过程中重要的颜色数(50-53字节)，如果为0的话，说明全部重要

			// 因为java是大端存储，那么也就是说同样会大端输出。
			// 但计算机是按小端读取，如果我们不改变多字节数据的顺序的话，那么机器就不能正常读取。
			// 所以首先调用方法将int数据转变为多个byte数据，并且按小端存储的顺序。
			// 输入信息头数据
			dos.write(changeByte(biSize), 0, 4);// 输入信息头数据的总字节数
			dos.write(changeByte(biWidth), 0, 4);// 输入位图的宽
			dos.write(changeByte(biHeight), 0, 4);// 输入位图的高
			dos.write(changeByte(biPlanes), 0, 2);// 输入位图的目标设备级别
			dos.write(changeByte(biBitcount), 0, 2);// 输入每个像素占据的字节数
			dos.write(changeByte(biCompression), 0, 4);// 输入位图的压缩类型
			dos.write(changeByte(biSizeImage), 0, 4);// 输入位图的实际大小
			dos.write(changeByte(biXPelsPerMeter), 0, 4);// 输入位图的水平分辨率
			dos.write(changeByte(biYPelsPerMeter), 0, 4);// 输入位图的垂直分辨率
			dos.write(changeByte(biClrUsed), 0, 4);// 输入位图使用的总颜色数
			dos.write(changeByte(biClrImportant), 0, 4);// 输入位图使用过程中重要的颜色数

			// 因为是24位图，所以没有颜色表
			// 通过遍历输入位图数据
			// 这里遍历的时候注意，在计算机内存中位图数据是从左到右，从下到上来保存的，
			// 也就是说实际图像的第一行的点在内存是最后一行
			for (int j = height-1; j >=0 ; j--) 
			{
				for (int i=0; i <width; i++) {
					// 这里还需要注意的是，每个像素是有三个RGB颜色分量组成的，
					// 而数据在windows操作系统下是小端存储，对多字节数据有用。
					int red = arry[i][j][0];// 得到位图点的红色分量
					int green = arry[i][j][1];// 得到位图点的绿色分量
					int blue = arry[i][j][2];// 得到位图点的蓝色分量
					byte[] red1 = changeByte(red);
					byte[] green1 = changeByte(green);
					byte[] blue1 = changeByte(blue);
					dos.write(blue1, 0, 1);
					dos.write(green1, 0, 1);
					dos.write(red1, 0, 1);
				}
			}
			// 关闭数据的传输
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

    private void swap(int x,int y,int x2,int y2)//像素点的交换
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
    private void swap2(int x,int y,int n)//rgb的变化
    {
    	switch(n)
    	{
    	   case 0://r与g的交换
    	   {
    		   int c;
    		   c= arry[x][y][0];
    		   arry[x][y][0]=arry[x][y][1];
    		   arry[x][y][1]=c;
    		   break;
    	   }
    	   case 1://r与b的交换
    	   {
    		   int c;
    		   c= arry[x][y][0];
    		   arry[x][y][0]=arry[x][y][2];
    		   arry[x][y][2]=c;
    		   break;
    	   }
    	   case 2://取反
    	   {
    		   int c;
    		   arry[x][y][0]=255-arry[x][y][0];
    		   arry[x][y][1]=255-arry[x][y][1];
    		   arry[x][y][2]=255-arry[x][y][2];
    	   }
    
    	}
    }
    
    
    private void encodeMethod(char t)//加密
    {
    	
    	if(t=='a'||t=='A')//A方法对奇数行进行对折
		{
    		int x=0;
    		while(x<(this.width/2))
    		{
    			for(int y=0;y<height;y++)
    			{
    				swap(x,y,width-1-x,y);
    			}
    			x=x+2;//2是可以变成3的
    		}
			return;
		}
    	if(t=='b'||t=='B')//B方法对奇数列进行对折
		{
    		int y=0;
    		while(y<(this.height/2))
    		{
    			for(int x=0;x<width;x++)
    			{
    				swap(x,y,x,height-1-y);
    				
    			}
    			y=y+2;//2是可以变成3的
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
    			x=x+3;//2是可以变成3的
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
    			y=y+3;//2是可以变成3的
    		}
			return;
			//
		}if(t=='e'||t=='E')//交换像素rgb
		{
			int y=0;
    		while(y<(this.height))
    		{
    			for(int x=0;x<width;x++)
    			{
    				swap2(x,y,0);
    				
    			}
    			y=y+3;//2是可以变成3的
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
    			x=x+3;//2是可以变成3的
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
    			y=y+3;//2是可以变成3的
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
    			x=x+2;//2是可以变成3的
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
    				swap2(x,y,2);//rgb取反
    				
    			}
    			x=x+2;//2是可以变成3的
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
    				swap2(x,y,2);//rgb取反
    				
    			}
    			y=y+3;//2是可以变成3的
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
    			x=x+3;//2是可以变成3的
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
    			y=y+3;//2是可以变成3的
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
    			x=x+3;//2是可以变成3的
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
    			y=y+3;//2是可以变成3的
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
    			x=x-3;//2是可以变成3的
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
    			y=y-3;//2是可以变成3的
    		}
			
			
			return;
			//
		}
    }
}
