import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class BeforeUpdate extends JFrame implements ActionListener{
    JFrame frame;
    JTextField txtRollNo;
    JButton btnOK,btnback;
    JPanel panel;
    Connection co;
    public BeforeUpdate()
    {
        frame = new JFrame("UPDATE");
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
        btnback = new JButton("BACK");
        btnback.setFocusable(false);
        panel.add(btnback,c1);

        btnback.addActionListener(this);

        c1.gridx = 1;
        c1.gridy = 1;
        btnOK = new JButton("OK");
        btnOK.setFocusable(false);
        panel.add(btnOK,c1);


        btnOK.addActionListener(this);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        co=Connect.getConnect();
        if(e.getSource()==btnOK)
        {
            String data=txtRollNo.getText();
            int roll=Integer.parseInt(data);
            try
            {
                PreparedStatement s=co.prepareStatement("SELECT * FROM STUDENT WHERE ROLLNO=?");
                s.setInt(1,roll);
                ResultSet row=s.executeQuery();
                if(row.next())
                {
                    frame.dispose();
                    new Update(data);
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "ROLL-NO NOT AVAILIABLE", "FAILED", JOptionPane.INFORMATION_MESSAGE);
                }

            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            
        }
        if(e.getSource()==btnback)
        {
            frame.dispose();
            new MainFrame();
        }
    }

    public static void main(String[] args) {
        new BeforeUpdate();
    }
    
}
