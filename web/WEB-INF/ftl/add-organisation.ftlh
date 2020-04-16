
<html>
<head>
    <title>add</title>
</head>
<body>

<form method="post" action="/organisations/add">
    <input type="text" name="name" value="name">

    <input type="tel" name="phone" value="phone number">

    <input type="text" name="addr" value="address">

    <textarea name="description"></textarea>
<#--    <input hidden="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">-->
    <br>
    <input type="submit" value="save">
</form>

<div></div>

</body>
</html>