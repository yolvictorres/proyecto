/** 
 * @author jleguizamon
 * @version 1.0
 * Funcion para la creación de acciones de una empresa
 * Esta funcion es llamada desde el HTML para la validación y creacion de la empresa
 * **/
guardar_acciones = function(){
    var objectSend = {
            "accionCantidad" : $("#accion_cantidad").val(),
            "accionCantidad": $("#accion_condicion").val(),
            "accionvenderA": $("#accion_vender_A").val(),
        };

        $.ajax({
            url: "../services/acciones/create",
            data: JSON.stringify(objectSend),
            contentType:'application/json ; charset=utf-8',
            dataType : 'json',
            type: 'POST',
            beforeSend: function () {

            },
            success: function (response) {
                console.log(response.data);
                if(response.codigo === 200){
                    empresa = response.data;
                    console.log(response.data);
                    //$('#register_login').show();
                    //$('#register_empresa').hide();
                }else{
                    //$('#error_div').show();
                    //$('#error_sms').html(response.mensaje);
                }
            }, error: function (response) {
                console.log(response);
                /*$('#error_div').show();
                $('#error_sms').html("Se produjo un error desconocido.");*/
            }
        });
};