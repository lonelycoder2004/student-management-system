import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
@SuppressWarnings("deprecation")
public class Update extends JFrame implements ActionListener 
{
    JFrame frame;
    Connection co;
    JPanel inputPanel,buttonPanel;
    JTextField roll,name,depar,m1,m2,m3,grade;
    JButton btnSave, btnExit;
    public Update(String s)
    {
        frame=new JFrame("ADD");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        inputPanel.setBounds(50,50,400,400);
        frame.add(inputPanel);
        c.insets=new Insets(10, 10, 10, 10);
        c.anchor=GridBagConstraints.LINE_START;

        roll = new JTextField(10);
        roll.setText(s);
        roll.setEditable(false);
        name = new JTextField(10);
        depar = new JTextField(10);
        depar.setEditable(false);
        m1 = new JTextField(10);
        m2 = new JTextField(10);
        m3=new JTextField(10);
        grade=new JTextField(10);
        grade.setEditable(false);

        c.gridx=0;c.gridy=0;
        inputPanel.add(new JLabel("ROLL-NO"),c);
        c.gridx=1;c.gridy=0;
        inputPanel.add(roll,c);
        c.gridx=0;c.gridy=1;
        inputPanel.add(new JLabel("NAME"),c);
        c.gridx=1;c.gridy=1;
        inputPanel.add(name,c);
        c.gridx=0;c.gridy=2;
        inputPanel.add(new JLabel("DEPARTMENT"),c);
        c.gridx=1;c.gridy=2;
        inputPanel.add(depar,c);
        c.gridx=0;c.gridy=3;
        inputPanel.add(new JLabel("MARK-1"),c);
        c.gridx=1;c.gridy=3;
        inputPanel.add(m1,c);
        c.gridx=0;c.gridy=4;
        inputPanel.add(new JLabel("MARK-2"),c);
        c.gridx=1;c.gridy=4;
        inputPanel.add(m2,c);
        c.gridx=0;c.gridy=5;
        inputPanel.add(new JLabel("MARK-3"),c);
        c.gridx=1;c.gridy=5;
        inputPanel.add(m3,c);
        c.gridx=0;c.gridy=6;
        inputPanel.add(new JLabel("GRADE"),c);
        c.gridx=1;c.gridy=6;
        inputPanel.add(grade,c);
        
        btnSave = new JButton("Save");
        btnSave.setFocusable(false);
        btnExit = new JButton("Exit");
        btnExit.setFocusable(false);

        buttonPanel = new JPanel();
        buttonPanel.setBounds(100, 450, 300, 50);
        frame.add(buttonPanel);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnExit);

        btnSave.addActionListener(this);
        btnExit.addActionListener(this);

        show();

        frame.setVisible(true);
    }
    public void calgrade()
    {
        int marks1 = Integer.parseInt(m1.getText());
        int marks2 = Integer.parseInt(m2.getText());
        int marks3 = Integer.parseInt(m3.getText());
        
        if(marks1>=40 && marks2>=40 && marks3>=40)
        {
            grade.setText("PASS");
        }
        else
        {
            grade.setText("FAIL");
        }
    }
    public void show()
    {
        co=Connect.getConnect();
        String data=roll.getText();
        int roll=Integer.parseInt(data);
        try
        {
            PreparedStatement po=co.prepareStatement("SELECT NAME,DEPARTMENT,MARK1,MARK2,MARK3,GRADE FROM STUDENT WHERE ROLLNO=?");
            po.setInt(1, roll);
            ResultSet result=po.executeQuery();
            while(result.next())
            {
                String n1=result.getString("NAME");
                String d1=result.getString("DEPARTMENT");
                int mark1=result.getInt("MARK1");
                int mark2=result.getInt("MARK2");
                int mark3=result.getInt("MARK3");
                String g1=result.getString("GRADE");
                name.setText(n1);
                depar.setText(d1);
                m1.setText(String.valueOf(mark1));
                m2.setText(String.valueOf(mark2));
                m3.setText(String.valueOf(mark3));
                grade.setText(g1);
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
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==btnSave)
        {
            co=Connect.getConnect();
            calgrade();
            try
            {
                PreparedStatement po=co.prepareStatement("UPDATE STUDENT SET NAME=?,DEPARTMENT=?,MARK1=?,MARK2=?,MARK3=?,GRADE=? WHERE ROLLNO=?");
                po.setString(1, name.getText());
                po.setString(2,depar.getText());
                po.setInt(3, Integer.parseInt(m1.getText()));
                po.setInt(4, Integer.parseInt(m2.getText()));
                po.setInt(5, Integer.parseInt(m3.getText()));
                po.setString(6,grade.getText());
                po.setInt(7, Integer.parseInt(roll.getText()));
                int row=po.executeUpdate();
                if(row >0)
                {
                    JOptionPane.showMessageDialog(frame, "UPDATED SUCCESSFULLY", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                    show();                
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "UPDATION FAILED", "FAILURE", JOptionPane.INFORMATION_MESSAGE);
                }

            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            
        }
        if(e.getSource()==btnExit)
        {
            new MainFrame();
            frame.dispose();
        }
    }
}
    

