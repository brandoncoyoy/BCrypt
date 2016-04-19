/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;
import DAL.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.sql.*;
/**
 *
 * @author brandon
 */
public class Operacion {
    Instruccion instruccion;
    public Operacion()
    {
        instruccion = new Instruccion();
    }
    private Boolean ExisteClave(String clave)
    {
        ArrayList<String> campos = new ArrayList<String>();
        campos.add("*");
        ResultSet r = instruccion.Select("Usuarios", campos,"","");
        try
        {
            while(r.next())
            {
                if(BCrypt.checkpw(clave,r.getString("Clave")))
                    return true;
            }
            return false;
        }
        catch(Exception e)
        {
            return true;
        }
    }
    public Boolean AgregarUsuario(String nombre,String clave)
    {
        if(!ExisteClave(clave))
        {
            ArrayList<String> campos = new ArrayList<String>();
            ArrayList<String> valores = new ArrayList<String>();
            campos.add("Codigo_Usuario");
            valores.add("null");
            campos.add("Nombre");
            valores.add("'"+nombre+"'");
            campos.add("Clave");
            valores.add("'"+BCrypt.hashpw(clave, BCrypt.gensalt())+"'");
            if(instruccion.Insert("Usuarios", campos, valores))
                return true;
            else 
                return false;
        }
        else
            return false;
    }
    public String Nombre(String clave)
    {
        ArrayList<String> campos = new ArrayList<String>();
        campos.add("*");
        ResultSet r = instruccion.Select("Usuarios", campos,"","");
        try
        {
            while(r.next())
            {
                if(BCrypt.checkpw(clave,r.getString("Clave")))
                    return r.getString("Nombre");
            }
            return "";
        }
        catch(Exception e)
        {
            return "";
        }
    }
    
}