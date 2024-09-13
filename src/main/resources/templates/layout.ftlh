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
        <!-- Link to the external CSS file -->
        <link rel="stylesheet" type="text/css" href="/static/css/layout.css">
        <!-- Link to the favicon -->
        <link rel="icon" type="image/png" href="/static/images/app_favicon.png">
        <title>JOB SEARCH</title>
    </head>
    <body>

    <#assign known = SPRING_SECURITY_CONTEXT?? >
    <#if known>
        <#assign
        user = SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        >
    </#if>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-2">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">JOB SEARCH</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <#if user?? && user.authorities?seq_contains("ADMIN")>
                        <li class="nav-item">
                            <a class="nav-link" href="/create">Create</a>
                        </li>
                    </#if>
                </ul>
                <div class="d-flex">
                    <#if user??>
                        <div class="text-light m-2 m-sm-0">
                            you logged in with email "${name}"; your role in the system is
                            <#list user.authorities as auth>
                                <#if auth?index == 0>
                                    <span class="text-danger">${auth}</span>
                                <#else>
                                </#if>
                            </#list>
                            ; actions available:
                            <#list user.authorities as auth>
                                <#if auth?index == 0>
                                <#else>
                                    / <span class="text-info">${auth}</span>
                                </#if>
                            </#list>
                        </div>
                        <a href="/profile" class="btn btn-outline-info m-2">Profile</a>
                        <#if _csrf??>
                            <form action="/logout" method="post">
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                       value="${(_csrf.token)!'--no-token--'}"/>
                                <button type="submit" class="btn btn-outline-info m-2" id="logout">Logout</button>
                            </form>
                        <#else>
                            <a href="/logout" class="btn btn-outline-info m-2" id="logout">Logout</a>
                        </#if>
                    <#else>
                        <a href="/auth/login" class="btn btn-outline-info m-2">Login</a>
                        <a href="/auth/register" class="btn btn-outline-info m-2">Register</a>
                    </#if>
                    <div class="btn-group m-2" role="group" aria-label="Language switcher">
                        <a href="?lang=en" class="btn btn-outline-light">EN</a>
                        <a href="?lang=ru" class="btn btn-outline-light">RU</a>
                        <a href="?lang=ky" class="btn btn-outline-light">KY</a>
                    </div>
                </div>
            </div>
        </div>
    </nav>

    <div class="container">
        <#nested spring>
    </div>

    </body>
    </html>

</#macro>