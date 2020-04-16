<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script>

        function sendMessage(dialogId, text) {
            let body = {
                dialogId: dialogId,
                text: text
            };
            //
            // var token = $("meta[name='_csrf']").attr("content");
            // var header = $("meta[name='_csrf_header']").attr("content");

            $.ajax({
                url: "/messages/" + dialogId,
                method: "POST",
                data: JSON.stringify(body),
                contentType: "application/json",
                dataType: "json",
                complete: function () {
                    if (text === 'Login') {
                        receiveMessage(dialogId)
                    }
                }

            });

        }

        // LONG POLLING
        function receiveMessage(dialogId) {
            $.ajax({
                url: "/messages/" + dialogId,
                method: "GET",
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                    console.log(response);
                    if (response.length === 0) {
                        receiveMessage(dialogId);
                    } else {
                        $('#messages').append('<li>' + response[0]['text'] + '</li>');
                        receiveMessage(dialogId);
                    }
                }
            })
        }

        function f(dialogId) {
            $.ajax({
                url: "/messages/" + dialogId,
                method: "GET",
                dataType: "json",
                contentType: "application/json"
            })
        }

    </script>
</head>
<body onload="sendMessage('${dialogId}', 'Login')">
<#--<body onload="f('${dialogId}')">-->
<#--<body >-->
<h1>Dialog ID - ${dialogId}</h1>

<div>
    <ul id="messages">
        <#list messages as m>
            <li>${m.text}</li>
        </#list>
    </ul>
</div>
<div>
    <input id="message" name="text" placeholder="Ваше сообщение">
    <button onclick="sendMessage('${dialogId}',
            $('#message').val())">Отправить
    </button>
</div>
<div>

</div>
</body>
</html>