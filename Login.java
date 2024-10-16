import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener,KeyListener
{
    JFrame frame;
    JLabel lable1,lable2;
    Connection co=null;
    JPanel panel,panel1;
    JTextField field1;
    JPasswordField field2;
    JOptionPane option;
    int status1=0,status2=0,status=0;
    Font f=new Font("Serif",Font.BOLD,35);
    Font myf=new Font("Serif",Font.BOLD,15);
    Font myf1=new Font("Ariel",Font.BOLD,11);
    JButton button1,button2,button3;
    JCheckBox check;
    Login()
    {
        frame=new JFrame("LOGIN PAGE");
        frame.setBounds(450,200,410,350);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lable1=new JLabel("LOGIN");
        lable1.setFont(f);
        lable1.setBounds(140, 20, 130, 48);
        frame.add(lable1);

        panel=new JPanel(new GridBagLayout());
        GridBagConstraints c1=new GridBagConstraints();
        panel.setBounds(20, 80, 350, 80);
        frame.add(panel);

        c1.insets=new Insets(10,20,10,10);
        c1.anchor=GridBagConstraints.LINE_START;

        c1.gridx=0;c1.gridy=0;
        lable1=new JLabel("PHONE-NO");
        lable1.setFont(myf);
        panel.add(lable1,c1);
        c1.gridx=1;c1.gridy=0;
        field1=new JTextField(20);
        field1.setFont(myf1);

        field1.addKeyListener(this);
        field1.addKeyListener(this);

        
        panel.add(field1,c1);

        c1.gridx=0;c1.gridy=1;
        lable2=new JLabel("PASSWORD");
        lable2.setFont(myf);
        panel.add(lable2,c1);
        c1.gridx=1;c1.gridy=1;
        field2=new JPasswordField(20);
        field2.setFont(myf1);
        field2.addKeyListener(this);

        panel.add(field2,c1);

        check=new JCheckBox("Show Password");
        check.setBounds(230, 160, 150, 20);
        check.setFocusable(false);
        frame.add(check);

        check.addActionListener(this);

        panel1=new JPanel(new FlowLayout(0,20,20));
        panel1.setBounds(50, 200, 300, 50);
        frame.add(panel1);

        button1=new JButton("RESET");
        button1.setFocusable(false);
        panel1.add(button1);
        button1.addActionListener(this);

        button2=new JButton("LOGIN");
        button2.setFocusable(false);
        panel1.add(button2);

        button2.addActionListener(this);

        button3=new JButton("SIGN-UP");
        button3.setFocusable(false);
        panel1.add(button3);

        button3.addActionListener(this);

        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new Login();
    }
    public void textcheck()
    {
        String s=field1.getText();
        String s2=new String(field2.getPassword());
        if(s.isEmpty() || s2.isEmpty())
        {
            JOptionPane.showMessageDialog(frame, "FILL PASSWORD AND TEXTFIELD", "ERROR", JOptionPane.ERROR_MESSAGE);
            status1=1;
        }
        else
        {
            status1=0;
        }
    }
    public void checkpass()
    {
        boolean valid=false,valid1=false,valid2=false,valid3=false,valid4=false;
        char pass[]=field2.getPassword();
        String pass1=new String(pass);
        if(pass1.length()>7)
        {
            valid1=true;
        }
        for(int i=0;i<pass1.length();i++)
        {
            char c=pass1.charAt(i);
           
            if(Character.isDigit(c))
            {
                valid=true;
            }
            else if(Character.isUpperCase(c))
            {
                valid2=true;
            }
            else if(Character.isLowerCase(c))
            {
                valid3=true;
            }
            else if(!Character.isLetterOrDigit(c))
            {
                valid4=true;
            }
        }
        if(valid && valid1 && valid2 && valid3 && valid4)
        {
            status2=0;
        }
        else
        {
            status2=1;
            JOptionPane.showMessageDialog(frame, "PASSWORD MUST CONTAIN ATLEAST 8 CHARACTERS,UPPERCASE,LOWERCASE,SPECIAL SYMBOLS AND DIGITS", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==button1)
        {
            new Reset();
            frame.dispose();

        }
        if(e.getSource()==button3)
        {
            new Form();
            frame.dispose();
        }
        if(check.isSelected())
        {
            field2.setEchoChar((char)0);
        }
        else
        {
            field2.setEchoChar('.');
        }
        if(e.getSource()==button2)
        {
            textcheck();
		if(status1==0)
		{	
            		checkpass();
		}
            if(status1==0 && status2==0)
            {
                co=Connect.getConnect();
                if(co!=null)
                {
                    try
                    {
                        String num=field1.getText();
                        char[] pass=field2.getPassword();
                        long number=Long.parseLong(num);
                        String password=new String(pass);
                        PreparedStatement po=co.prepareStatement("SELECT * FROM TEACHER WHERE PHONENO1=? AND PASSWORD1=?");
                        po.setLong(1,number);
                        po.setString(2, password);
                        
                        ResultSet result=po.executeQuery();

                        if(result.next())
                        {
                            JOptionPane.showMessageDialog(frame, "LOGIN SUCCESSFUL", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                            status=1;
                
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(frame, "LOGIN FAILED", "FAILED", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    finally
                    {
                        Connect.connectClose();
                    }
                }
            }
            if(status==1)
            {
                new MainFrame();
                frame.dispose();
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        char c=e.getKeyChar();
        
        if(e.getSource()==field1)
        {
            if(!Character.isDigit(c))
            {
                e.consume();
            }
        }
       
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_DOWN)
        {
            field2.requestFocusInWindow();
        }
        if(e.getKeyCode()==KeyEvent.VK_UP)
        {
            field1.requestFocusInWindow();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(field1.getText().length()>10)
        {
            String text=field1.getText().substring(0,10);
            field1.setText(text);
        }
    }
}
