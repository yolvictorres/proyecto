var empresa ;
/** 
 * @author Aric Gutierrez
 * @version 1.0
 * Funcion para la creación de la empresa
 * Esta funcion es llamada desde el HTML para la validación y creacion de la empresa
 * **/
guardar_registro = function(){
    var objectSend = {
            "empresaNit" : $("#nit").val(),
            "empresaNombre": $("#nombre").val(),
            "empresaSociedad": $("#sociedad").val(),
            "empresaTelefono": $("#telefono").val(),
            "empresaDireccion": $("#direccion").val()
        };
    if(validarVaciosEmpresa(objectSend)){
        $.ajax({
            url: "services/empresa/create",
            data: JSON.stringify(objectSend),
            contentType:'application/json ; charset=utf-8',
            dataType : 'json',
            type: 'POST',
            beforeSend: function () {

            },
            success: function (response) {
                if(response.codigo === 200){
                    empresa = response.data;
                    $('#register_login').show();
                    $('#register_empresa').hide();
                }else{
                    $('#error_div').show();
                    $('#error_sms').html(response.mensaje);
                }
            }, error: function (response) {
                console.log(response);
                $('#error_div').show();
                $('#error_sms').html("Se produjo un error desconocido.");
            }
        });
    }else{
        $('#error_div').show();
        $('#error_sms').html('Por favor verifica los datos que ingresaste, parece que faltó algo.');
    }
};
/** 
 * @author Aric Gutierrez
 * @version 1.0
 * Funcion para la creación del usuario administrador
 * Esta funcion es llamada desde el HTML para la validación y creacion del usuario administrador de 
 * la empresa creada anteriormente
 * **/
guardar_usuario = function(){
    if(validarClaves($("#usuario_clave").val() , $("#usuario_clave_2").val())){
        var objectSend = {
            "usuarioNombre" : $("#usuario_nombre").val(),
            "usuarioApellido": $("#usuario_apellido").val(),
            "usuarioCedula": $("#usuario_cedula").val(),
            "usuarioCorreo": $("#usuario_correo").val(),
            "usuarioTelefono": $("#usuario_telefono").val(),
            "usuairoClave": $("#usuario_clave").val(),
            "usuarioTipo":"A",
            "usuarioPersona" : "J",
            "empresaEmpresaId" : empresa
        };
        if(validarVaciosUsuario(objectSend)){
            $.ajax({
                url: "services/usuario/create",
                data: JSON.stringify(objectSend),
                contentType:'application/json ; charset=utf-8',
                dataType : 'json',
                type: 'POST',
                beforeSend: function () {

                },
                success: function (response) {
                    console.log(response);
                    if(response.codigo === 200){
                        
                    }else{
                        $('#error_div_user').show();
                        $('#error_sms_user').html(response.mensaje);
                    }
                }, error: function (response) {
                    console.log(response);
                    $('#error_div_user').show();
                    $('#error_sms_user').html("Se produjo un error desconocido.");
                }
            });
        }else{
            $('#error_div_user').show();
            $('#error_sms_user').html('Por favor verifica los datos que ingresaste, parece que faltó algo.');
        }
    }else{
         $('#error_div_user').show();
        $('#error_sms_user').html('Por favor verifica que las claves sean iguales y contengan letras y números, además tengan 8 caracteres');
    }
};