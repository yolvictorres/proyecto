/**
 * 
 ***/
iniciar_sesion = function(){
  var objectSend = {
        "usuarioNombre" :'',
        "usuarioApellido":'',
        "usuarioCedula": '',
        "usuarioCorreo": $("#usuario_correo").val(),
        "usuarioTelefono":'',
        "usuairoClave": $("#usuario_clave").val(),
        "usuarioTipo":'',
        "usuarioPersona" :'',
        "empresaEmpresaId" : ''
    };  
    if(validarVaciosLogin(objectSend)){
        $.ajax({
                url: "services/usuario/login",
                data: JSON.stringify(objectSend),
                contentType:'application/json ; charset=utf-8',
                dataType : 'json',
                type: 'POST',
                beforeSend: function () {

                },
                success: function (response) {
                    console.log(response);
                    if(response.codigo === 200){
                        var data = response.data;
                        var usuario ={
                                "usuarioId": data[0],
                                "usuarioNombre": data[1],
                                "usuarioApellido": data[2],
                                "usuarioCedula": data[3],
                                "usuarioCorreo": data[4],
                                "usuarioTelefono": data[5],
                                "usuairoClave": data[6],
                                "usuarioTipo": data[7],
                                "usuarioPersona": data[8],
                                "accionesCollection": [],
                                "empresaEmpresaId": {
                                        "empresaId": data[10],
                                        "empresaNit": data[11],
                                        "empresaNombre": data[12],
                                        "empresaSociedad": data[13],
                                        "empresaTelefono": data[14],
                                        "empresaDireccion": data[15]
                                },
                                "compraAccionesCollection": []
                        };
                        sessionStorage.setItem('usuario', JSON.stringify(usuario));
                       window.location.href ='pages/dashboard.html';
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
        $('#error_div').show();
        $('#error_sms').html('Para poder ingresar debes tener usuario y clave');
    }
};