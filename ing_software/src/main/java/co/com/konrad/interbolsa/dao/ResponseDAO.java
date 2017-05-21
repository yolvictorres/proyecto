/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.dao;

/**
 *
 * @author ADGS
 */
public class ResponseDAO {
    
    private Integer codigo;
    private String mensaje;
    private Object data;

    public ResponseDAO() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseDAO{" + "codigo=" + codigo + ", mensaje=" + mensaje + ", data=" + data + '}';
    }
    
}
