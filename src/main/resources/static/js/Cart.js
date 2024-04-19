function IncreaseQuantity(proId, quantity) {
    var fullNameVal=document.getElementById("FullName").value;
    var phoneNumberVal=document.getElementById("PhoneNumber").value;
    var addressVal=document.getElementById("Address").value;
    var fullAddressVal=document.getElementById("FullAddress").value;
    var codeVal=document.getElementById("Code").value;
    var shippingDateVal=document.getElementById("ShippingDate").value;
    var noteVal=document.getElementById("Note").value;
    var storedDataSend= {"fullName":fullNameVal,"phoneNumber":phoneNumberVal,
        "address":addressVal,"fullAddress":fullAddressVal,"code":codeVal,"shippingDate":shippingDateVal,"note":noteVal}
    console.log(storedDataSend)
    $.ajax({
        type: 'POST',
        url: `IncreaseQuantity/${proId}/${quantity}`,
        contentType: "application/json; charset=utf-8",
        dataType : 'json',
        data:JSON.stringify(storedDataSend),
        crossDomain: false,
        async: true,
        success: function (response) {

                $.ajax({
                    type: 'POST',
                    url: `Cart`,
                    contentType: 'text/plain',

                    crossDomain: false,
                    async: true,
                    success: function (response) {

                        window.location.reload();
                    }
                });


        }
    });


}
function OnKeyUpQuantity(proId) {
    var quantityVal=document.getElementById(proId).value;
    setTimeout(() => {
        $.ajax({
            type: 'POST',
            url: `OnKeyUpQuantity/${proId}/${quantityVal}`,
            contentType: 'text/plain',
            crossDomain: false,
            async: true,
            success: function (response) {

                $.ajax({
                    type: 'POST',
                    url: `Cart`,
                    contentType: 'text/plain',
                    crossDomain: false,
                    async: true,
                    success: function (response) {

                        window.location.reload();
                    }
                });


            }
        });
    }, 500);



}

function DecreaseQuantity(proId, quantity) {

    $.ajax({
        type: 'POST',
        url: `DecreaseQuantity/${proId}/${quantity}`,
        contentType: 'text/plain',
        crossDomain: false,
        async: true,
        success: function (response) {

                $.ajax({
                    type: 'POST',
                    url: `Cart`,
                    contentType: 'text/plain',
                    crossDomain: false,
                    async: true,
                    success: function (response) {
                        window.location.reload();

                    }
                });

        }
    });

}