<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script>
        // window.onload = function() {
            // document.getElementById("account").
        function checkAccount() {
            var xhr = new XMLHttpRequest();
            // var xhr = null;
            // if (window.ActiveXObject) {
                // xhr = new ActiveXObject("Microsoft.XMLHttp");
            // } else {
                // xhr = new XMLHttpRequest();
            // }
            var account = document.getElementById("account").value;

            alert(account);
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    document.getElementById("span_account").innerText = xhr.responseText;
                }
            };
            xhr.open("GET", "/webgis/account?account="+account);
            xhr.send();
        }
    </script>
</head>
<body>
    <form>
        <p>
            UserName <input type="text"/>
        </p>
        <p>
            Account <input type="text" id="account" onblur="checkAccount();">
        </p>
        <span id="span_account"></span>

    </form>
    
</body>
</html>