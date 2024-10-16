import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteStudentWindow extends JFrame implements ActionListener {
    JFrame frame;
    Connection connection;
    JTextField txtRollNo;
    JButton btnDelete,btnExit;
    JPanel panel;

    public DeleteStudentWindow() {
        frame = new JFrame("DELETE");
        frame.setLayout(null);
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);


        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBounds(20,20 ,250 ,100 );
        GridBagConstraints c1 = new GridBagConstraints();
        c1.insets = new Insets(10, 10, 10, 20);
        c1.anchor = GridBagConstraints.LINE_START;
        frame.add(panel);

        c1.gridx = 0;
        c1.gridy = 0;
        panel.add(new JLabel("Roll No:"), c1);
        txtRollNo = new JTextField(10);
        c1.gridx = 1;
        c1.gridy = 0;
        panel.add(txtRollNo, c1);

        c1.gridx = 0;
        c1.gridy = 1;
        btnDelete = new JButton("Delete");
        panel.add(btnDelete,c1);

        c1.gridx = 1;
        c1.gridy = 1;
        btnExit = new JButton("Exit");
    
        panel.add(btnExit,c1);


        btnDelete.addActionListener(this);
        btnExit.addActionListener(this);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DeleteStudentWindow();
            }
        });
    }

    public void actionPerformed(ActionEvent e) 
    {
        connection=Connect.getConnect();
        if (e.getSource() == btnDelete) 
        {
            String rollNo = txtRollNo.getText();

            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM student WHERE rollno = ?");
                statement.setString(1, rollNo);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "Student with roll number " + rollNo + " deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "No student found with roll number " + rollNo + ".", "Error", JOptionPane.ERROR_MESSAGE);
                }
                } 
                catch (SQLException ex) 
                {
                    ex.printStackTrace();
                }
                finally
                {
                    Connect.connectClose();
                }
        }
        if(e.getSource()==btnExit)
        {
            new MainFrame();
            frame.dispose();
        }
    }    
}

