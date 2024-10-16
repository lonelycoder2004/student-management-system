import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;

public class Reset extends JFrame implements ActionListener,KeyListener
{
    JFrame frame;
    JLabel mainl,label1,label2;
    Connection co=null;
    int status2=0,status1=0;
    JTextField field1,field2;
    JPanel panel;
    JButton button,button1;
    Font myl=new Font("Serif", Font.ITALIC, 30);

    Reset()
    {
        frame=new JFrame("RESET PASSWORD");
        frame.setBounds(450,200,400, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        mainl=new JLabel("RESET PASSWORD");
        mainl.setFont(myl);
        mainl.setBounds(70, 20, 250, 30);
        frame.add(mainl);

        panel=new JPanel(new GridBagLayout());
        GridBagConstraints c1=new GridBagConstraints();
        panel.setBounds(30,70, 350, 100);
        frame.add(panel);

        c1.insets=new Insets(10,10,10,20);
        c1.anchor=GridBagConstraints.LINE_START;

        c1.gridx=0;c1.gridy=0;
        label1=new JLabel("PHONE-NO");
        panel.add(label1,c1);

        c1.gridx=1;c1.gridy=0;
        field1=new JTextField(20);
        panel.add(field1,c1);

        field1.addKeyListener(this);


        c1.gridx=0;c1.gridy=1;
        label2=new JLabel("NEW-PASSWORD");
        panel.add(label2,c1);

        c1.gridx=1;c1.gridy=1;
        field2=new JTextField(20);
        field2.addKeyListener(this);
        panel.add(field2,c1);

        button1=new JButton("BACK");
        button1.setFocusable(false);
        button1.setBounds(100, 200, 80, 23);
        frame.add(button1);

        button1.addActionListener(this);

        button=new JButton("RESET");
        button.setFocusable(false);
        button.setBounds(200, 200, 80, 23);
        frame.add(button);

        button.addActionListener(this);

        frame.setVisible(true);
    }
    public void textcheck()
    {
        String s=field1.getText();
        String s2=new String(field2.getText());
        if(s.isEmpty() || s2.isEmpty())
        {
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
        String pass1=field2.getText();
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
        }
    }
    public static void main(String[] args) {
        new Reset();
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==button)
        {
            textcheck();
            checkpass();
            if(status2==0 && status1==0)
            {
            co=Connect.getConnect();
            if(co!=null)
            {
                    try
                    {
                        String phone=field1.getText();
                        String pass=field2.getText();
                        long phoneno=Long.parseLong(phone);
                        PreparedStatement po=co.prepareStatement("UPDATE TEACHER SET PASSWORD1=? WHERE PHONENO1=?");
                        
                        po.setString(1, pass);
                        po.setLong(2, phoneno);

                        int row=po.executeUpdate();
                        if(row>0)
                        {
                            JOptionPane.showMessageDialog(frame, "PASSWORD UPDATED", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                            new Login();
                            frame.dispose();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(frame, "NUMBER NOT AVAILIABLE", "FAILURE", JOptionPane.INFORMATION_MESSAGE);
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
            else
            {
                
                if(status1==1)
                {
                    JOptionPane.showMessageDialog(frame, "FILL PASSWORD AND PHONE-NO", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

                else if(status2==1)
                {
                    JOptionPane.showMessageDialog(frame, "PASSWORD MUST CONTAIN ATLEAST 8 CHARACTERS,UPPERCASE,LOWERCASE,SPECIAL SYMBOLS AND DIGITS", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        }
        if(e.getSource()==button1)
        {
            frame.dispose();
            new Login();
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
        if(e.getKeyCode()==KeyEvent.VK_UP)
        {
            field1.requestFocusInWindow();
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN)
        {
            field2.requestFocusInWindow();
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