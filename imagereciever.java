package virtualclass;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class imagereciever extends JPanel 
{
    static BufferedImage image;
    static Socket soc;
    static InputStream is;
    imagereciever()throws Exception
    {
//        InetAddress inetAddress=   InetAddress.getLocalHost();
//            System.out.println("ip address is");
//            System.out.println(                inetAddress.getHostAddress());
//           
        soc=new Socket("MAURYA",1234);
        
        is=soc.getInputStream();
        JFrame frame=new JFrame();
        frame.setSize(500,500);
        frame.setContentPane(this);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        while(true)
        {
            try
            {
                ObjectInputStream ois=new ObjectInputStream(is);
                int size=Integer.parseInt(ois.readObject().toString());
                ByteArrayOutputStream baos=new ByteArrayOutputStream(size);
                int sizeread=0,bytesin=0;
                byte[] buffer=new byte[1024];
                while(sizeread<size)
                {
                    bytesin=is.read(buffer);
                    sizeread+=bytesin;
                    baos.write(buffer,0,bytesin);
                }
                baos.close();
                ByteArrayInputStream bais=new ByteArrayInputStream(baos.toByteArray());
                image=ImageIO.read(bais);
                this.repaint();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                
                System.out.println("error");
                //System.exit(1);
            }
        }
    }
    public static void main(String aerg[])throws Exception
    {
        new imagereciever();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(image,0,0,null);
    }
}