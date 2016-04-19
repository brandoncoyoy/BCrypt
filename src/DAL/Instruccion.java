/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;
import java.sql.*;
import javax.swing.JOptionPane;
import java.util.*;

/**
 *
 * @author brandon
 */
public class Instruccion {
    Connection conexion = null;
    
    public Instruccion()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conexion =DriverManager.getConnection("jdbc:mysql://localhost:3306/BCrypt","root","7NN388NX"); 
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }   
    }
    private String AñadirCampos(String cadenainicial,ArrayList<String> campos)
        {
            String resultado = cadenainicial;
            resultado = resultado.trim();
            for(String campo:campos)
            {
                resultado += " " + campo + ",";
            }  
            return resultado.substring(0,resultado.lastIndexOf(","));
        }
    public ResultSet Sql(String sql)
    {
        try
        {
            Statement comando = conexion.createStatement();
            return comando.executeQuery(sql);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
    public Boolean Insert(String tabla,ArrayList<String> campos, ArrayList<String> valores)
    {
        try
        {
            Statement comando = conexion.createStatement();
            String sql = "insert into " + tabla + "(";
            sql = AñadirCampos(sql, campos);
            sql += ") values(";
            sql = AñadirCampos(sql, valores);
            sql += ")";
            comando.executeUpdate(sql);
            return true;
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
    }
    private ArrayList<String> UnirCampoValor(ArrayList<String> campos, ArrayList<String> valores)
    {
        ArrayList<String> resultado = new ArrayList<String>();
        for (int i = 0; i < campos.size(); i++)
        {
            resultado.add(campos.get(i)+"="+valores.get(i));
        }
        return resultado;
    }
    public Boolean Update(String tabla,ArrayList<String> campos, ArrayList<String> valores,String condicion)
    {
        try
        {
            Statement comando = conexion.createStatement();
            String sql = "update " + tabla + " set";
            sql = AñadirCampos(sql, UnirCampoValor(campos, valores));
            if (condicion != "")
                sql += " where " + condicion;
            comando.executeUpdate(sql);
            return true;
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
    }
    public Boolean Delete(String tabla,String condicion)
    {
        try
        {
            Statement comando = conexion.createStatement();
            String sql = "delete from " + tabla;
            if (condicion != "")
                sql += " where " + condicion;
            comando.executeUpdate(sql);
            return true;
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
    public ResultSet Select(String tabla, ArrayList<String> campos, String condicion,String orden)
    {
        try
        {
            Statement comando = conexion.createStatement();
            String sql = "select";
            sql = AñadirCampos(sql, campos);
            sql += " from " + tabla;
            if (condicion != "")
                sql += " where " + condicion;
            if (orden != "")
                sql += " order by " + orden;
            return comando.executeQuery(sql);

        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }  
}

