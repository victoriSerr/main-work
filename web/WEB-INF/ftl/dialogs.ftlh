<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, appUser-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Dialogs</title>
</head>
<body>
<h1>dialogs</h1>
<#list messages as m>
    <a href="/chat/${m.id}">${m.userFrom.login} - ${m.userTo.login}</a> <br>
</#list>
</body>
</html>