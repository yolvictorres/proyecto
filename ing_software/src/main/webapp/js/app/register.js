/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$( document ).ready(function() {
    $.ajax({
            url: "services/buscar/tipos",
            contentType:'application/json ; charset=utf-8',
            dataType : 'json',
            type: 'GET',
            beforeSend: function () {

            },
            success: function (response) {
                console.log(response);
            }, error: function (response) {
                console.log(response);
            }
        });
});

guardar_registro = function(){
    var objectSend = {
        
    };
    $.ajax({
        url: "services/registrar/usuario",
        data: JSON.stringify(objectSend),
        contentType:'application/json ; charset=utf-8',
        dataType : 'json',
        type: 'POST',
        beforeSend: function () {

        },
        success: function (response) {
            console.log(response);
        }, error: function (response) {
            console.log(response);
        }
    });
};
