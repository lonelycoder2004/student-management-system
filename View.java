import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class View extends JFrame implements ActionListener{
    private Connection connection;
    private JTextArea textArea;
    private JTextField field1;
    private JButton button1,exit;
    private JLabel label1;

    public View() {
        setTitle("VIEW");
        setSize(800, 500);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        scrollPane.setBounds(0, 100, 800, 300);
        add(scrollPane);

        label1=new JLabel("ROLL-NO:");
        label1.setBounds(230, 40, 90,20);
        add(label1);

        field1=new JTextField();
        field1.setBounds(300, 40,90,20);
        add(field1);

        button1=new JButton("SEARCH");
        button1.setBounds(400,40,90,20);
        button1.setFocusable(false);
        button1.addActionListener(this);
        add(button1);

        exit=new JButton("EXIT");
        exit.setBounds(350,420,90,20);
        exit.setFocusable(false);
        exit.addActionListener(this);
        add(exit);

        try
        {
            connection = Connect.getConnect();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM STUDENT");
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData metaData=resultSet.getMetaData();
            int columnnumber=metaData.getColumnCount();
            for(int i=1;i<=columnnumber;i++)
            {
                String columString=metaData.getColumnName(i).toUpperCase();
                textArea.append(columString);
                textArea.append("\t\t");
            }
            textArea.append("\n");
            textArea.append("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            textArea.append("\n");
            while (resultSet.next()) 
            {
                StringBuilder rowData = new StringBuilder();
                for (int i = 1; i <= columnnumber; i++) 
                {
                    rowData.append(resultSet.getString(i)).append("\t\t");
                }
                rowData.append("\n");
                
                textArea.append(rowData.toString());

                textArea.append("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                textArea.append("\n");
            }
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            Connect.connectClose();
        }

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
       if(e.getSource()==button1)
       {
        if(field1.getText().isEmpty())
        {
            textArea.setText("");
            try
        {
            connection = Connect.getConnect();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM STUDENT");
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData metaData=resultSet.getMetaData();
            int columnnumber=metaData.getColumnCount();
            for(int i=1;i<=columnnumber;i++)
            {
                String columString=metaData.getColumnName(i).toUpperCase();
                textArea.append(columString);
                textArea.append("\t\t");
            }
            textArea.append("\n");
            textArea.append("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            textArea.append("\n");
            while (resultSet.next()) 
            {
                StringBuilder rowData = new StringBuilder();
                for (int i = 1; i <= columnnumber; i++) 
                {
                    rowData.append(resultSet.getString(i)).append("\t\t");
                }
                rowData.append("\n");
                
                textArea.append(rowData.toString());

                textArea.append("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                textArea.append("\n");
            }
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            Connect.connectClose();
        }
        }
        else
        {
        textArea.setText("");
        String roll=field1.getText();
        int rollNo=Integer.parseInt(roll);
        try {
            connection = Connect.getConnect();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM STUDENT WHERE ROLLNO=?");
            ps.setInt(1, rollNo);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next())
            {
              
                ResultSetMetaData metaData=resultSet.getMetaData();
                int columnnumber=metaData.getColumnCount();
                for(int i=1;i<=columnnumber;i++)
                {   
                    String columString=metaData.getColumnName(i).toUpperCase();
                    textArea.append(columString);
                    textArea.append("\t\t");
                }
                textArea.append("\n");
                textArea.append("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                textArea.append("\n");
                StringBuilder rowData = new StringBuilder();
                for (int i = 1; i <= columnnumber; i++) 
                {
                    rowData.append(resultSet.getString(i)).append("\t\t");
                }
                rowData.append("\n");
                textArea.append(rowData.toString());
                
            }
            else
            {
                textArea.setText("ROLLNO NOT FOUND");
            }
        } 
        catch (SQLException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            Connect.connectClose();
        }
        }
       }
       if(e.getSource()==exit)
       {
            dispose();
            new MainFrame();
       }
    }
    public static void main(String[] args) {
        new View();
    }
}
