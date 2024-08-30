<#import "spring.ftl" as spring>
<#macro layout>

    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
                crossorigin="anonymous"></script>

        <link rel="stylesheet" type="text/css" href="/static/css/layout.css">
        <title>Document</title>
    </head>

    <body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-2">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">Job Search</a>

            <#assign known = SPRING_SECURITY_CONTEXT??>
            <#if known>
                <#assign user = SPRING_SECURITY_CONTEXT.authentication.principal>
                <#assign name = user.getUsername()>
                <#assign accountType = user.accountType!"unknown"> <!-- Добавляем проверку наличия переменной accountType -->
                <div class="navbar-text">
                    <span class="navbar-text">you are logged in as ${name}!</span>
                    <#list user.authorities as auth>
                        <span class="navbar-text">your role is ${auth}</span>
                        <#if auth?has_next>
                            /
                        </#if>
                    </#list>
                </div>
                <a href="/vacancies" class="btn btn-outline-info m-2">View Vacancies</a>
                <a href="/resumes" class="btn btn-outline-info m-2">View Resumes</a>
                <a href="/auth/profile" class="btn btn-outline-info ms-2">Profile</a>
                <a href="/logout" class="btn btn-outline-info m-2" id="logout">Logout</a>

            <#-- Добавляем проверку типа аккаунта -->
                <#if accountType == "employer">
                    <a href="/companies" class="btn btn-outline-info m-2">View Companies</a>
                <#elseif accountType == "applicant">
                    <a href="/resumes" class="btn btn-outline-info m-2">View Resumes</a>
                </#if>

            <#else>
                <span class="navbar-text">you are not logged in</span>
                <a href="/auth/login" class="btn btn-outline-info m-2">Login</a>
                <a href="/auth/register" class="btn btn-outline-info m-2">Register</a>
            </#if>
        </div>
    </nav>

    <div class="container">

        <#nested spring>

    </div>

    </body>
    </html>
</#macro>