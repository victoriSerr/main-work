<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>


    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <meta name="viewport"
          content="width=device-width, appUser-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <title>Document</title>
    <script>
        function sendFile() {
            // данные для отправки
            let formData = new FormData();
            // забрал файл из input
            let files = ($('#fileInfo'))[0]['files'];
            // добавляю файл в formData
            [].forEach.call(files, function (fileInfo, i, files) {
                formData.append("file", fileInfo);
            });
            // var token = $("meta[name='_csrf']").attr("content");
            // var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/files",
                data: formData,
                processData: false,
                contentType: false,
                // beforeSend: function (request) {
                //     return request.setRequestHeader(header, token);
                // }
            })
                .done(function (response) {
                    alert(response)
                })
                .fail(function () {
                    alert('Error')
                });
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <input type="fileInfo" id="fileInfo" name="fileInfo" placeholder="Имя файла..."/>

    <button onclick="sendFile()">
        Загрузить файл
    </button>
    <input type="hidden" id="file_hidden">
<#--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">-->
    <div class="filename"></div>
</div>
</body>
</html>