/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.logica;

import co.com.konrad.interbolsa.constantes.Constantes;
import co.com.konrad.interbolsa.dao.EmpresaDAO;
import co.com.konrad.interbolsa.dao.ResponseDAO;
import co.com.konrad.interbolsa.dao.UsuarioDAO;
import co.com.konrad.interbolsa.dto.EmpresaDAOJpaController;
import co.com.konrad.interbolsa.dto.UsuarioDAOJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author ADGS
 */
public class Registro {
 
    @EJB
    private EmpresaDAOJpaController empresaController;
    @EJB
    private UsuarioDAOJpaController usuarioController;
    private ResponseDAO respuesta;
    
    public ResponseDAO registrar (UsuarioDAO usuario , EmpresaDAO empresa){
        this.respuesta = new ResponseDAO();
        this.respuesta.setCodigo(Constantes.C_EXITOSO);
        this.respuesta.setMensaje(Constantes.M_EXITOSO);
        try {
            this.empresaController.create(empresa);
            usuario.setEmpresaEmpresaId(empresa);
            this.usuarioController.create(usuario);
        } catch (Exception ex) {
            this.respuesta.setCodigo(Constantes.C_ERROR);
            this.respuesta.setMensaje(Constantes.M_ERROR);
        }
        return this.respuesta;
    }
}
