/**
 * @author Aric Gutierrez
 * @version 1.0
 * Esta funcion se encarda de la validacion del formulario de empresa
 * @param {Empresa} empresa Estos son los campos requeridos para crear la empresa
 **/
validarVaciosEmpresa = function (empresa){
    if(empresa.empresaNit === ''){
        return false;
    }if(empresa.empresaNombre === ''){
        return false;
    }if(empresa.empresaSociedad === ''){
        return false;
    }if(empresa.empresaTelefono === ''){
        return false;
    }if(empresa.empresaDireccion === ''){
        return false;
    }
    return true;
};
/**
 * @author Aric Gutierrez
 * @version 1.0
 * Esta funcion se encarda de la validacion de las claves asociadas al usuario 
 * al momento de su creación
 * @param {String} clave_1 Esta es la primera clave que se le solicita al usuario
 * @param {String} clave_2 Esta es la segunda clave que se le solicita al usuario y con la cual se validará su similitud
 **/
validarClaves = function(clave_1 , clave_2){
    var vsExprReg = /[A-Za-z0-9_]/;
    if(clave_1 !== '' && clave_2 !== ''){
        if(clave_1 === clave_2){
            if(vsExprReg.test(clave_1)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }else{
        return false;
    }
};
/**
 * @author Aric Gutierrez
 * @version 1.0
 * Esta funcion se encarda de la validacion del formulario de usuario
 * @param {Usuario} usuario Estos son los campos requeridos para crear el usuairo administrador
 **/
validarVaciosUsuario = function(usuario){
    if(usuario.usuarioNombre === ''){
        return false;
    }if(usuario.usuarioCedula === ''){
        return false;
    }if(usuario.usuarioCorreo === ''){
        return false;
    }if(usuario.usuarioTelefono === ''){
        return false;
    }if(usuario.usuarioApellido === ''){
        return false;
    }
    return true;
};