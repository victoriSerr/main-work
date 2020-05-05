<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, appUser-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Animals</title>
</head>
<body>
<#list animals as o>
    <h1>ID - ${o.id}</h1>
    <h1>Name - ${o.name}</h1>
    <a href="/organisations/${o.organisation.id}"> <h1>Organisation - ${o.organisation.name}</h1> </a>
</#list>
</body>
</html>