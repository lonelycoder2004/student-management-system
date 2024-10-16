import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;

public class Form extends JFrame implements ActionListener,KeyListener{
    JFrame frame;
    int status=0,status1=0,status2=0;
    Connection co=null;
    JLabel mainl,fname,lname,age,gender,depar,phno,pass;
    JRadioButton male,female;
    Font myl=new Font("Serif", Font.ITALIC, 40);
    JTextField f1,f2,f3,f4,f5,f6;
    JTextField text[]=new JTextField[6];
    JButton save,back;
    ButtonGroup gb;
    JPanel panel,panel2;
    Form()
    {
        frame=new JFrame("SIGN-UP");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setBounds(450,100,400,500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainl=new JLabel("SIGN-UP");
        mainl.setFont(myl);
        mainl.setBounds(110, 20, 250, 40);
        frame.add(mainl);

        panel=new JPanel();
        panel.setLayout(new GridBagLayout());
    
       
        panel.setBounds(30, 80,350 ,300);
        GridBagConstraints c1=new GridBagConstraints();
        c1.insets=new Insets(10, 10, 10, 20);
        c1.anchor = GridBagConstraints.LINE_START;
       
        frame.add(panel);
        
        c1.gridx=0;c1.gridy=0;
        fname=new JLabel("FNAME");
        panel.add(fname,c1);

        c1.gridx=1;c1.gridy=0;
        f1=new JTextField(20);
        panel.add(f1,c1);

        c1.gridx=0;c1.gridy=1;
        lname=new JLabel("LNAME");
        panel.add(lname,c1);

        c1.gridx=1;c1.gridy=1;
        f2=new JTextField(20);
        panel.add(f2,c1);

        c1.gridx=0;c1.gridy=2;
        age=new JLabel("AGE");
        panel.add(age,c1);

        c1.gridx=1;c1.gridy=2;
        f3=new JTextField(20);
        panel.add(f3,c1);

        f3.addKeyListener(this);

        c1.gridx=0;c1.gridy=3;
        depar=new JLabel("DEPARTMENT");
        panel.add(depar,c1);

        c1.gridx=1;c1.gridy=3;
        f4=new JTextField(20);
        panel.add(f4,c1);

        c1.gridx=0;c1.gridy=4;
        phno=new JLabel("PHONE-NO");
        panel.add(phno,c1);

        c1.gridx=1;c1.gridy=4;
        f5=new JTextField(20);
        panel.add(f5,c1);

        f5.addKeyListener(this);

        c1.gridx=0;c1.gridy=5;
        pass=new JLabel("PASSWORD");
        panel.add(pass,c1);

        c1.gridx=1;c1.gridy=5;
        f6=new JTextField(20);
        panel.add(f6,c1);

        c1.gridx=0;c1.gridy=6;
        gender=new JLabel("GENDER");
        panel.add(gender,c1);

        c1.gridx=1;c1.gridy=6;
        male=new JRadioButton("Male");
        male.setFocusable(false);
        female=new JRadioButton("Female");
        female.setFocusable(false);
        gb=new ButtonGroup();
        gb.add(male);
        gb.add(female);
        panel2=new JPanel();
        panel2.setSize(100, 10);
        panel2.add(male);
        panel2.add(female);
        panel.add(panel2,c1);

        back=new JButton("BACK");
        back.setBounds(100, 400, 70, 20);
        frame.add(back);

        back.addActionListener(this);

        save=new JButton("SAVE");
        save.setBounds(200, 400, 70, 20);
        frame.add(save);

        save.addActionListener(this);

        text[0]=f1;
        text[1]=f2;
        text[2]=f3;
        text[3]=f4;
        text[4]=f5;
        text[5]=f6;

        for(int i=0;i<6;i++)
        {
            text[i].addActionListener(this);
            text[i].addKeyListener(this);
        }

        frame.setVisible(true);
    }
    public static void main(String[] args) 
    {
        new Form();    
    }
    public void check()
    {
        for(int i=0;i<6;i++)
        {
            String s[]=new String[6];
            s[i]=text[i].getText();
            
            if(s[i].isEmpty())
            {
               
                status=1;
                break;
            }
            else
            {
                status=0;
            }
        }
        if(male.isSelected()==false && female.isSelected()==false)
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
        String pass1=f6.getText();
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
    @Override
    public void actionPerformed(ActionEvent e) 
    {

        if(e.getSource()==save)
        {
            check();
            if(!f6.getText().isEmpty())
            {
                checkpass();
            }
            if(status==0 && status1==0 && status2==0)
            {
                try
                {
                    co=Connect.getConnect();
                    if(co!=null)
                    {
                        String s1=text[0].getText();
                        String s2=text[1].getText();
                        String s3=text[2].getText();
                        int change=Integer.parseInt(s3);
                        String s4=text[3].getText();
                        String s5=text[4].getText();
                        long number=Long.parseLong(s5);
                        String s6=text[5].getText();
                        PreparedStatement ps1=co.prepareStatement("SELECT * FROM TEACHER WHERE PHONENO1=?");
                        ps1.setLong(1, number);
                        ResultSet resultSet=ps1.executeQuery();
                    if(resultSet.next())
                    {
                        JOptionPane.showMessageDialog(frame, "PHONE-NO ALREADY REGISTERED", "FAILURE", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        PreparedStatement ps=co.prepareStatement("insert into TEACHER (FNAME,LNAME,AGE,DEPARTMENT,PHONENO1,PASSWORD1,GENDER) values(?,?,?,?,?,?,?)");
                        ps.setString(1, s1);
                        ps.setString(2, s2);
                        ps.setInt(3, change);
                        ps.setString(4, s4);
                        ps.setLong(5, number);
                        ps.setString(6, s6);

                        if(male.isSelected())
                        {
                            String one=male.getText(); 
                            ps.setString(7, one);
                        }
                        else
                        {
                            String two=female.getText();
                            ps.setString(7, two);
                        }

                        int row=ps.executeUpdate();
                        if(row>0)
                        {
                            JOptionPane.showMessageDialog(frame, "SIGN-UP SUCCESSFUL", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                            new Login();
                            frame.dispose();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(frame, "SIGN-UP UNSUCCESSFUL", "FAILURE", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                finally
                {
                    if(co!=null)
                    {
                        Connect.connectClose();
                    }
                }
            }
            else
            {
                if(status==1 || status1==1)
                {
                    JOptionPane.showMessageDialog(frame, "FILL ALL REQUIRED FIELDS", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else if(status2==1)
                {
                    JOptionPane.showMessageDialog(frame, "PASSWORD MUST CONTAIN ATLEAST 8 CHARACTERS,UPPERCASE,LOWERCASE,SPECIAL SYMBOLS AND DIGITS", "ERROR", JOptionPane.ERROR_MESSAGE);;
                }
               
            }
        }
        if(e.getSource()==back)
        {
            frame.dispose();
            new Login();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        char c=e.getKeyChar();
        if(e.getSource()==f5 || e.getSource()==f3)
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
            for(int i=0;i<5;i++)
            {
                if(e.getSource()==text[i])
                { 
                    text[i+1].requestFocusInWindow();
                }
                        
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_UP)
        {
            for(int i=1;i<6;i++)
            {
                if(e.getSource()==text[i])
                { 
                    text[i-1].requestFocusInWindow();
                }
                        
            }
        }
        
    }
       
    @Override
    public void keyReleased(KeyEvent e) {
        if(f5.getText().length()>10)
        {
            String text=f5.getText().substring(0,10);
            f5.setText(text);
        }
        if(f3.getText().length()>3)
        {
            String text1=f3.getText().substring(0,3);
            f3.setText(text1);
        }
    }
}
