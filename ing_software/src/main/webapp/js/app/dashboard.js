$( document ).ready(function() {
    var usuario = sessionStorage.getItem('usuario') || '';
    if(usuario === ''){
        window.location.href = '../index.html';
    }
    usuario = JSON.parse(usuario);
    $("#nombre_usuario").html(usuario.usuarioNombre);
    $("#cargo_usuario").html(usuario.empresaEmpresaId.empresaNombre + ' - ' + usuario.empresaEmpresaId.empresaSociedad);
});

cerrar_sesion = function(){
    sessionStorage.setItem('usuario', '');
    sessionStorage.clear();
    window.location.href = '../index.html';
};

//

acciones = function(){
    
    
    $.ajax({
            url: "create_acccions.html",
            type: 'GET',
            beforeSend: function () {

            },
            success: function (response) {
               $("#content").html(response);
            }, error: function (response) {
                console.log(response);
                /*$('#error_div').show();
                $('#error_sms').html("Se produjo un error desconocido.");*/
            }
        });
};