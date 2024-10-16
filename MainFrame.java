import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements ActionListener 
{
    JButton btnAdd, btnUpdate, btnView, btnDelete;
    JPanel buttonPanel;
    JFrame frame;
    Font my=new Font("Serif",Font.BOLD,25);
    public MainFrame() 
    {
        frame=new JFrame("Student Management System");
        frame.setSize(600, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        buttonPanel = new JPanel();
        buttonPanel.setBounds(50, 50, 500, 500);

        buttonPanel.setLayout(new GridLayout(2,2,20,20));

        btnAdd = new JButton("<html>Click here to<br>Add Student</html>");
        btnAdd.setFont(my);
        btnUpdate = new JButton("<html>Click here to<br>Update Student</html>");
        btnUpdate.setFont(my);
        btnView = new JButton("<html>Click here to<br>View<br> Student Detials</html>");
        btnView.setFont(my);
        btnDelete = new JButton("<html>Click here to<br>Delete Student</html>");
        btnDelete.setFont(my);
        btnAdd.addActionListener(this);
        btnAdd.setFocusable(false);
        btnUpdate.addActionListener(this);
        btnUpdate.setFocusable(false);
        btnView.addActionListener(this);
        btnView.setFocusable(false);
        btnDelete.addActionListener(this);
        btnDelete.setFocusable(false);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnView);
        buttonPanel.add(btnDelete);

       
        frame.add(buttonPanel);

        frame.setVisible(true);
    
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            new AddStudentWindow();
        } else if (e.getSource() == btnUpdate) {
            new BeforeUpdate();
        } else if (e.getSource() == btnView) {
            new View();
        } else if (e.getSource() == btnDelete) {
            new DeleteStudentWindow();
        }
        frame.dispose();
    }

    public static void main(String[] args)
    {
       
        new MainFrame();
    }
}