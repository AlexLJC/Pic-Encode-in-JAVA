/*******************************************
 * Version 1.0                             *
 * Alex                                    *
 * 2016-6-12                               *
 *******************************************/
 
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TreeSet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField; 
public class PicEncode implements ActionListener
{
	private int N=7;//NΪ��Կ���ȳ���32��Ӱ������ʱ��͸��Ӷ�
	private PicModel pic=new PicModel();
    private JTextField jt1 = new JTextField(20);
    private JTextField jt2 = new JTextField(20);
    private String stringfile = "";
    public PicEncode() {
        JFrame jf = new JFrame("PicEncode");
        jf.setLayout(new GridLayout(3, 1, 0, 10));
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        jt1.setEditable(false);
        jt1.setHorizontalAlignment(4);
        jt2.setHorizontalAlignment(4);
        JLabel jl1 = new JLabel("   ��ѡ��·����");
        JLabel jl2 = new JLabel("   ���������룺");
        JButton jb1 = new JButton("���");
        jb1.addActionListener(this);
        JButton jb2 = new JButton("����");
        jb2.addActionListener(this);
        JButton jb3 = new JButton("����");
        jb3.addActionListener(this);
        jp1.add(jl1);
        jp1.add(jt1);
        jp2.add(jl2);
        jp2.add(jt2);
        jp3.add(jb1);
        jp3.add(jb2);
        jp3.add(jb3);
        jf.add(jp1);
        jf.add(jp2);
        jf.add(jp3);
        jf.setLocation(200, 300);
        jf.setSize(300, 160);//���ô����СΪ(300, 160)
        jf.pack();// �����С�Զ�����
        jf.setResizable(false);// �����С�������϶��ı�
        jf.setVisible(true);// ����ɼ�,false���ɼ�
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
 
    public void actionPerformed(ActionEvent e) {
        String string = e.getActionCommand();
        if ("���".equals(string)) {
            browse();
            pic.ReadPic(stringfile);
            
            //pic.test();
        } else if ("����".equals(string)) {
            encode();
            pic.savePic(stringfile+"_encode.bmp");
        } else if ("����".equals(string)) {
            decode();
            pic.savePic(stringfile+"_decode.bmp");
        }
    }
 
    /*������� */
    void browse() {
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);
        stringfile = fc.getSelectedFile().getAbsolutePath();
        jt1.setText(stringfile);
    }
 
    /*ͼƬ���� */
    void encode() {
        try {
            String l = jt2.getText();
            String md5=parseStrToMd5L32(l);
            for(int i=0;i<N;i++)
            {
            md5=md5+(parseStrToMd5L32(md5));
            }
            System.out.println("MD5="+md5);
            //���Գ�������ʱ��
            long startTime=System.currentTimeMillis();   //��ȡ��ʼʱ��  
            pic.encode(md5);//����  
            long endTime=System.currentTimeMillis(); //��ȡ����ʱ��  
            //System.out.println("��������ʱ�䣺 "+(endTime-startTime)+"ms"); 
            JOptionPane.showMessageDialog(null, "���ܺ�ʱ"+(endTime-startTime)+"ms", "���ܳɹ�", JOptionPane.NO_OPTION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /** ͼƬ���� */
    void decode() {
    	 try {
    		 String l = jt2.getText();
             String md5=parseStrToMd5L32(l);
             for(int i=0;i<this.N;i++)
             {
            	 md5=md5+(parseStrToMd5L32(md5));
             }
             System.out.println("MD5="+md5);
           //���Գ�������ʱ��
             long startTime=System.currentTimeMillis(); 
             pic.decode(md5);
             long endTime=System.currentTimeMillis(); //��ȡ����ʱ��  
             //System.out.println("��������ʱ�䣺 "+(endTime-startTime)+"ms"); 
             JOptionPane.showMessageDialog(null, "���ܺ�ʱ"+(endTime-startTime)+"ms", "���ܳɹ�", JOptionPane.NO_OPTION);
         } catch (Exception e) {
             e.printStackTrace();
         }
 
    }
 
    public static void main(String[] args) {
        new PicEncode();
    }
    public String parseStrToMd5L32(String str)
    {  
    	String reStr = null; 
    	try
    	{
    		MessageDigest md5 = MessageDigest.getInstance("MD5");  
    		byte[] bytes = md5.digest(str.getBytes());  
    		StringBuffer stringBuffer = new StringBuffer();  
    		for (byte b : bytes)
    		{  
    			int bt = b&0xff;  
    			if (bt < 16)
    			{  
    				stringBuffer.append(0);  
    	        }   
    			stringBuffer.append(Integer.toHexString(bt));  
    	    } 
    		reStr = stringBuffer.toString();  
    	} 
    	catch (NoSuchAlgorithmException e)  {  
    	           e.printStackTrace();  
    	}  
    	return reStr;  
    }
 
}