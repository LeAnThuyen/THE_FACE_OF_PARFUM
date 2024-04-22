$(document).ready(function () {
    setTimeout(() => {
        var total = document.getElementById("finalAmount");
        $("#finalAmount").text(Number(total.innerText));
    }, 100);

});

function IncreaseQuantity(proId, quantity, price) {

    $.ajax({
        type: 'POST',
        url: `IncreaseQuantity/${proId}/${quantity}`,
        contentType: 'text/plain',
        crossDomain: false,
        async: true,
        success: function (response) {
            var quantityVal = document.getElementById(proId);
            var total = document.getElementById("finalAmount");
            quantityVal.value = Number(quantityVal.value) + 1;
            $("#finalAmount").text(Number(total.innerText) + Number(price));
        }
    });


}

function OnBlurQuantity(proId) {

    var quantityVal = document.getElementById(proId).value;
    if (quantityVal != "") {

        $.ajax({
            type: 'POST',
            url: `OnKeyUpQuantity/${proId}/${quantityVal}`,
            contentType: 'text/plain',
            crossDomain: false,
            async: true,
            success: function (response) {

                if (response != '0') {
                    $("#finalAmount").text(Number(response));
                }

            }

        });

    }
}

function DecreaseQuantity(proId, quantity, price) {

    $.ajax({
        type: 'POST',
        url: `DecreaseQuantity/${proId}/${quantity}`,
        contentType: 'text/plain',
        crossDomain: false,
        async: true,
        success: function (response) {
            var quantityVal = document.getElementById(proId);
            var total = document.getElementById("finalAmount");
            quantityVal.value = Number(quantityVal.value) - 1;
            $("#finalAmount").text(Number(total.innerText) - Number(price));


        }
    });

}

function Payment() {
    var fullNameVal = document.getElementById("FullName").value;
    var phoneNumberVal = document.getElementById("PhoneNumber").value;
    var addressVal = document.getElementById("Address").value;
    var codeVal = document.getElementById("Code").value;
    var shippingDateVal = document.getElementById("ShippingDate").value;
    var noteVal = document.getElementById("Note").value;
    var storedDataSend = {
        "fullName": fullNameVal,
        "phoneNumber": phoneNumberVal,
        "address": addressVal,
        "code": codeVal,
        "shippingDate": shippingDateVal,
        "note": noteVal
    }

    $.ajax({
        type: 'POST',
        url: `Payment`,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(storedDataSend),
        crossDomain: false,
        async: true,
        success: function (response) {

            console.log(response)

        }
    });
}


