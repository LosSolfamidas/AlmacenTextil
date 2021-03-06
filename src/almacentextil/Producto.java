package almacentextil;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

final class Producto extends JFrame {

    private final JTabbedPane tabbedPane;
    private JPanel pestaña01, pestaña02, pestaña03;
    private JTable tablaBD;
    private JTextField txt_ID;
    private JComboBox cmbox_ID, cmbox_ID2;
    private JScrollPane scroll;
    private int ultimoID;

    //Para poder conectarse a la base de datos
    private final conexionDB meConecto = new conexionDB();

    public Producto() {

        setTitle(" -- Producto -- ");
        setSize(450, 400);
        setBackground(Color.gray);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);
        
        // Create the tab pages
        crearPestaña01();
        crearPestaña02();
        crearPestaña03();

        // Create a tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Crear", pestaña01);
        tabbedPane.addTab("Modificar", pestaña02);
        tabbedPane.addTab("Mostrar", pestaña03);
        topPanel.add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    //--------------------------------------------------------------------------
    //Primmera Pestaña
    public void crearPestaña01() {
        pestaña01 = new JPanel();
        pestaña01.setLayout(null);

        JLabel lbl_ID = new JLabel("ID: ");
        lbl_ID.setBounds(10, 15, 50, 25);
        pestaña01.add(lbl_ID);

        txt_ID = new JTextField();
        txt_ID.setBounds(80, 15, 100, 25);
        txt_ID.setEditable(false);
        txt_ID.setText(Integer.toString(ultimoID));
        pestaña01.add(txt_ID);

        JLabel lbl_Nombre = new JLabel("Nombre:");
        lbl_Nombre.setBounds(10, 60, 150, 25);
        pestaña01.add(lbl_Nombre);

        JTextField txt_Nombre = new JTextField();
        txt_Nombre.setBounds(80, 60, 300, 25);
        txt_Nombre.addKeyListener(new KeyAdapter() {    //Solo caracteres Alfabeticos
            @Override
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (Character.isDigit(ch)) {
                    e.consume();
                }
            }
        });
        pestaña01.add(txt_Nombre);

        JLabel lbl_Descrip = new JLabel("Descripcion:");
        lbl_Descrip.setBounds(10, 105, 100, 25);
        pestaña01.add(lbl_Descrip);

        JTextField txt_Descrip = new JTextField();
        txt_Descrip.setBounds(80, 105, 300, 25);
        pestaña01.add(txt_Descrip);

        JLabel lbl_PrecioU = new JLabel("Precio U.:");
        lbl_PrecioU.setBounds(10, 145, 80, 25);
        pestaña01.add(lbl_PrecioU);

        JTextField txt_PrecioU = new JTextField();
        txt_PrecioU.setBounds(80, 145, 100, 25);
        txt_PrecioU.addKeyListener(new KeyAdapter() {    //Solo caracteres numericos 
            @Override
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b') && (caracter != '.') && (caracter == ',')) {
                    e.consume();
                }
            }
        });
        pestaña01.add(txt_PrecioU);

        JButton btn_Insertar = new JButton("Insertar");
        btn_Insertar.setBounds(220, 290, 80, 25);
        pestaña01.add(btn_Insertar);

        JButton btn_Limpiar = new JButton("Limpiar");
        btn_Limpiar.setBounds(320, 290, 80, 25);
        pestaña01.add(btn_Limpiar);

        btn_Limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    txt_Nombre.setText("");
                    txt_Descrip.setText("");
                    txt_PrecioU.setText("");

                } catch (Exception err) {
                    System.out.println("Error: " + err);
                }
            }
        });

        btn_Insertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                boolean insertado = false;
                
                String ID = txt_ID.getText();
                String Nombre = txt_Nombre.getText();
                String Descrip = txt_Descrip.getText();
                String Precio = txt_PrecioU.getText();

                Connection miConexion = (Connection) meConecto.ConectarMysql();

                try (Statement st = miConexion.createStatement()) {

                    //Para ejecutar la consulta
                    String query = "INSERT INTO `almacentextil`.`producto` (`Id_producto`, `Nombre`, `Descrip`, `Precio_uni`) VALUES ('" + ID + "', '" + Nombre + "', '" + Descrip + "', '" + Precio + "')";
                    Statement s = miConexion.createStatement();
                    st.executeUpdate(query);
                    insertado = true;

                    miConexion.close();

                    if (insertado == true) {
                        JOptionPane.showMessageDialog(null, "Insertado Con Exito!", "Guardado", JOptionPane.INFORMATION_MESSAGE);
                        llenarJtable();
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Se ha producido un Error", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    insertado = false;
                }
            }
        });
    }

    //--------------------------------------------------------------------------
    //Segunda Pestaña
    public void crearPestaña02() {
        pestaña02 = new JPanel();
        pestaña02.setLayout(null);

        JLabel lbl_ID = new JLabel("ID: ");
        lbl_ID.setBounds(10, 15, 50, 25);
        pestaña02.add(lbl_ID);

        cmbox_ID2 = new JComboBox();
        cmbox_ID2.setBounds(80, 15, 100, 25);
        pestaña02.add(cmbox_ID2);

        JLabel lbl_Nombre = new JLabel("Nombre:");
        lbl_Nombre.setBounds(10, 60, 150, 25);
        pestaña02.add(lbl_Nombre);

        JTextField txt_Nombre = new JTextField();
        txt_Nombre.setBounds(80, 60, 300, 25);
        txt_Nombre.addKeyListener(new KeyAdapter() {    //Solo caracteres Alfabeticos
            @Override
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (Character.isDigit(ch)) {
                    e.consume();
                }
            }
        });
        pestaña02.add(txt_Nombre);

        JLabel lbl_Descrip = new JLabel("Descripcion:");
        lbl_Descrip.setBounds(10, 105, 100, 25);
        pestaña02.add(lbl_Descrip);

        JTextField txt_Descrip = new JTextField();
        txt_Descrip.setBounds(80, 105, 300, 25);
        pestaña02.add(txt_Descrip);

        JLabel lbl_PrecioU = new JLabel("Precio U.:");
        lbl_PrecioU.setBounds(10, 145, 80, 25);
        pestaña02.add(lbl_PrecioU);

        JTextField txt_PrecioU = new JTextField();
        txt_PrecioU.setBounds(80, 145, 100, 25);
        txt_PrecioU.addKeyListener(new KeyAdapter() {    //Solo caracteres numericos 
            @Override
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b') && (caracter != '.') && (caracter == ',')) {
                    e.consume();
                }
            }
        });
        pestaña02.add(txt_PrecioU);

        JButton btn_Eliminar = new JButton("Eliminar");
        btn_Eliminar.setBounds(30, 290, 80, 25);
        btn_Eliminar.setBackground(Color.red);
        btn_Eliminar.setForeground(Color.white);
        pestaña02.add(btn_Eliminar);

        JButton btn_Modificar = new JButton("Modificar");
        btn_Modificar.setBounds(220, 290, 80, 25);
        pestaña02.add(btn_Modificar);

        JButton btn_Buscar = new JButton("Buscar");
        btn_Buscar.setBounds(250, 15, 100, 25);
        pestaña02.add(btn_Buscar);

        JButton btn_Limpiar = new JButton("Limpiar");
        btn_Limpiar.setBounds(320, 290, 80, 25);
        pestaña02.add(btn_Limpiar);

        btn_Limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    cmbox_ID2.setSelectedIndex(0);
                    txt_Nombre.setText("");
                    txt_Descrip.setText("");
                    txt_PrecioU.setText("");

                } catch (Exception err) {
                    System.out.println("Error: " + err);
                }
            }
        });

        btn_Buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                int buscarA = (Integer) cmbox_ID2.getSelectedItem();

                Connection miConexion = (Connection) meConecto.ConectarMysql();

                try (Statement st = miConexion.createStatement()) {

                    //Para ejecutar la consulta
                    String query = "SELECT * FROM `producto` WHERE `Id_producto` = " + buscarA + "";
                    Statement s = miConexion.createStatement();

                    //Almacenamos en un ResultSet
                    ResultSet rs = s.executeQuery(query);

                    //Obteniendo la informacion de las columnas que estan siendo consultadas
                    ResultSetMetaData rsMd = rs.getMetaData();

                    //Creando las filas para el JTable
                    while (rs.next()) {

                        txt_Nombre.setText(rs.getString("Nombre"));
                        txt_Descrip.setText(rs.getString("Descrip"));
                        txt_PrecioU.setText(rs.getString("Precio_uni"));

                    }
                    rs.close();
                    miConexion.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btn_Eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                boolean eliminado = false;
                int eliminarA = (Integer) cmbox_ID2.getSelectedItem();

                if (txt_Nombre.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Seleccione un Producto", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int Borrar = JOptionPane.showConfirmDialog(null, "Desea Eliminar : " + txt_Nombre.getText());

                    if (JOptionPane.OK_OPTION == Borrar) {
                        Connection miConexion = (Connection) meConecto.ConectarMysql();

                        try (Statement st = miConexion.createStatement()) {

                            //Para ejecutar la consulta
                            String query = "DELETE FROM `almacentextil`.`producto` WHERE `producto`.`Id_producto` =" + eliminarA + "";

                            Statement s = miConexion.createStatement();
                            st.executeUpdate(query);
                            eliminado = true;

                            miConexion.close();

                            if (eliminado == true) {
                                JOptionPane.showMessageDialog(null, "Eliminado Con Exito!", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
                                llenarJtable();
                            }

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Se ha producido un Error", "Error", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                            eliminado = false;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Eliminacion Cancelada", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
        );

        btn_Modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                boolean modificado = false;

                int modificarA = (Integer) cmbox_ID2.getSelectedItem();

                if (txt_Nombre.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Seleccione un Producto", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int Modificar = JOptionPane.showConfirmDialog(null, "Desea Modificar: " + txt_Nombre.getText());

                    if (JOptionPane.OK_OPTION == Modificar) {

                        String Nombre = txt_Nombre.getText();
                        String Descrip = txt_Descrip.getText();
                        float PrecioU = Float.parseFloat(txt_PrecioU.getText());

                        Connection miConexion = (Connection) meConecto.ConectarMysql();

                        try (Statement st = miConexion.createStatement()) {

                            //Para ejecutar la consulta
                            String query = "UPDATE `almacentextil`.`producto` SET `Nombre` = '" + Nombre + "', `Descrip` = '" + Descrip + "', `Precio_uni` = '" + PrecioU + "' WHERE `producto`.`Id_producto` = '" + modificarA + "';";

                            Statement s = miConexion.createStatement();
                            st.executeUpdate(query);
                            modificado = true;

                            miConexion.close();

                            if (modificado == true) {
                                JOptionPane.showMessageDialog(null, "Modificado Con Exito!", "Guardado", JOptionPane.INFORMATION_MESSAGE);
                                llenarJtable();
                            }

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Se ha producido un Error", "Error", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                            modificado = false;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Modificacion Cancelada", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }

    //--------------------------------------------------------------------------
    //Tercera Pestaña
    public void crearPestaña03() {
        pestaña03 = new JPanel();
        pestaña03.setLayout(null);

        cmbox_ID = new JComboBox();
        cmbox_ID.setBounds(15, 5, 100, 20);
        pestaña03.add(cmbox_ID);

        JButton btn_Buscar = new JButton("Buscar");
        btn_Buscar.setBounds(334, 5, 90, 20);
        pestaña03.add(btn_Buscar);

        tablaBD = new JTable();
        scroll = new JScrollPane(tablaBD);
        scroll.setBounds(5, 35, 420, 250);
        pestaña03.add(scroll);

        llenarJtable();

        btn_Buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                int buscarA = (Integer) cmbox_ID.getSelectedItem();

                Connection miConexion = (Connection) meConecto.ConectarMysql();

                try (Statement st = miConexion.createStatement()) {

                    //Para establecer el modelo al JTable
                    DefaultTableModel modelo = new DefaultTableModel();
                    tablaBD.setModel(modelo);

                    //Para ejecutar la consulta
                    String query = "SELECT * FROM `producto` WHERE `Id_producto` = " + buscarA + "";
                    Statement s = miConexion.createStatement();

                    //Almacenamos en un ResultSet
                    ResultSet rs = s.executeQuery(query);

                    //Obteniendo la informacion de las columnas que estan siendo consultadas
                    ResultSetMetaData rsMd = rs.getMetaData();

                    //La cantidad de columnas que tiene la consulta
                    int cantidadColumnas = rsMd.getColumnCount();

                    //Establecer como cabezeras el nombre de las colimnas
                    for (int i = 1; i <= cantidadColumnas; i++) {
                        modelo.addColumn(rsMd.getColumnLabel(i));
                    }
                    //Creando las filas para el JTable
                    while (rs.next()) {
                        Object[] fila = new Object[cantidadColumnas];
                        for (int i = 0; i < cantidadColumnas; i++) {
                            fila[i] = rs.getObject(i + 1);
                        }
                        modelo.addRow(fila);
                    }
                    rs.close();
                    miConexion.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public void llenarJtable() {

        cmbox_ID.removeAllItems();
        cmbox_ID2.removeAllItems();

        Connection miConexion = (Connection) meConecto.ConectarMysql();

        try (Statement st = miConexion.createStatement()) {

            //Para establecer el modelo al JTable
            DefaultTableModel modelo = new DefaultTableModel();
            tablaBD.setModel(modelo);

            //Nuestra sentencia SQL
            String sentencia = "SELECT * FROM `producto`";
            Statement s = miConexion.createStatement();

            //Almacenamos en un ResultSet
            ResultSet rs = s.executeQuery(sentencia);

            //Obteniendo la informacion de las columnas que estan siendo consultadas
            ResultSetMetaData rsMd = rs.getMetaData();

            //La cantidad de columnas que tiene la consulta
            int cantidadColumnas = rsMd.getColumnCount();

            //Establecer como cabezeras el nombre de las colimnas
            for (int i = 1; i <= cantidadColumnas; i++) {
                modelo.addColumn(rsMd.getColumnLabel(i));
            }
            //Creando las filas para el JTable
            while (rs.next()) {

                Object[] fila = new Object[cantidadColumnas];
                cmbox_ID.addItem(rs.getInt("Id_producto"));
                cmbox_ID2.addItem(rs.getInt("Id_producto"));
                ultimoID = rs.getInt("Id_producto");

                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }
            rs.close();
            miConexion.close();
        } catch (SQLException e) {
            System.err.println("Se ha producido un Error! ");
            System.err.println(e.getMessage());
        }
        
        ultimoID = ultimoID+1;
        txt_ID.setText(Integer.toString(ultimoID));
    }
}
