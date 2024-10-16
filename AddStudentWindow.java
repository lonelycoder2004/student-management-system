import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddStudentWindow extends JFrame implements ActionListener,KeyListener
{
    private JFrame frame;
    private Connection co;
    private JPanel inputPanel,buttonPanel;
    private JTextField roll,name,m1,m2,m3,grade;
    private JComboBox<String> combo;
    private JButton btnSave, btnCancel;
    int status=0;
    public AddStudentWindow() 
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
        name = new JTextField(10);
        m1 = new JTextField(10);
        
        m1.addKeyListener(this);
        
        m2 = new JTextField(10);
        
        m2.addKeyListener(this);
        m3=new JTextField(10);
        
        m3.addKeyListener(this);
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

        String[] choices = { "INFORMATION TECHNOLOGY"};
        combo=new JComboBox<>(choices);

        inputPanel.add(combo,c);
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
        btnCancel = new JButton("Exit");
        btnCancel.setFocusable(false);

        buttonPanel = new JPanel();
        buttonPanel.setBounds(100, 450, 300, 50);
        frame.add(buttonPanel);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        btnSave.addActionListener(this);
        btnCancel.addActionListener(this);

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
    public void check()
    {
        if(roll.getText().isEmpty() || name.getText().isEmpty() || combo.getSelectedItem().toString().isEmpty() || m1.getText().isEmpty() ||m2.getText().isEmpty()||m3.getText().isEmpty())
        {
            status=1;
        }
        else
        {
            status=0;
        }
    }
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==btnCancel)
        {
            frame.dispose();
            new MainFrame();
        }
        
        if(e.getSource()==btnSave)
        {
            check();
        if(status==0)
        {
            co=Connect.getConnect();
            try
            {
            int rn = Integer.parseInt(roll.getText());
            String name1 = name.getText();
            String department =(String) combo.getSelectedItem();
            int marks1 = Integer.parseInt(m1.getText());
            int marks2 = Integer.parseInt(m2.getText());
            int marks3 = Integer.parseInt(m3.getText());
            calgrade();
            String grade1 = grade.getText();
            PreparedStatement s1=co.prepareStatement("SELECT * FROM STUDENT WHERE ROLLNO=?");
            s1.setInt(1, rn);
            ResultSet resultSet=s1.executeQuery();
            if(resultSet.next())
            {
                JOptionPane.showMessageDialog(frame, "ROLL-NO ALREADY EXISTS", "FAILURE", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                PreparedStatement statement = co.prepareStatement("INSERT INTO student(rollno,name,department,mark1,mark2,mark3,grade) VALUES (?, ?, ?, ?, ?, ?, ?)");
                statement.setInt(1, rn);
                statement.setString(2, name1);
                statement.setString(3, department);
                statement.setInt(4, marks1);
                statement.setInt(5, marks2);
                statement.setInt(6, marks3);
                statement.setString(7, grade1);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(frame, "DATA ADDED SUCCESSFULLY", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            } 
        }
            catch (Exception ex) 
            {
                ex.printStackTrace();
            }
        }
        
        else
        {
            JOptionPane.showMessageDialog(frame, "FILL ALL FIELDS", "FAILURE", JOptionPane.INFORMATION_MESSAGE);
        }
    }
        
    }
    public static void main(String[] args) 
    {
        new AddStudentWindow();
    }
    @Override
    public void keyTyped(KeyEvent e) {
        char c=e.getKeyChar();
        if(!Character.isDigit(c))
        {
            e.consume();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
       
    }
    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
