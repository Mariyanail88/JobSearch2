<#import "spring.ftl" as spring>
<#macro layout title home create profile logout login register
loggedInMessage roleMessage notLoggedInMessage availableActions>

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

        <title>${title}</title> <!-- Dynamically set title -->
        <style>
            .navbar {
                width: 100%; /* Увеличиваем ширину шапки */
                /*max-width: 1200px; !* Ограничиваем максимальную ширину *!*/
                margin: 0 auto; /* Центрируем шапку */
            }
            .navbar-brand {
                font-size: 1.8em; /* Увеличиваем размер шрифта */
                color: #b0b0b0 !important; /* Чуть темнее серый цвет текста */
                text-shadow: 0 0 1px #fff; /* Максимально тонкая обводка белым цветом */
            }
            .text-light {
                color: #b0b0b0 !important; /* Светло-серый цвет текста */
            }
            .btn:hover {
                color: #d3d3d3 !important; /* Белый цвет текста при наведении */
            }
            .user-info {
                display: flex;
                flex-direction: column;
                align-items: flex-start;
                font-size: 0.6em; /* Небольшой размер текста */
                color: #b0b0b0; /* Светло-серый цвет текста */
                margin-left: 20px; /* Отступ слева для позиционирования ближе к бренду */
            }
            .user-info span {
                margin-bottom: 0.2em; /* Небольшой отступ между элементами */
            }
            .language-buttons {
                display: flex;
                flex-direction: column; /* Выстраиваем кнопки по вертикали */
                align-items: flex-end; /* Выравниваем кнопки по правому краю */
            }
            .language-buttons .btn {
                color: #b0b0b0; /* Цвет текста кнопок */
                border-color: #b0b0b0; /* Цвет обводки кнопок */
                border-radius: 0.2rem; /* Менее скругленные углы */
                margin-bottom: 0.5rem; /* Отступ между кнопками */
            }
            .language-buttons .btn:hover {
                color: #d3d3d3 !important; /* Цвет текста при наведении */
                border-color: #d3d3d3 !important; /* Цвет обводки при наведении */
            }
        </style>
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
            <a class="navbar-brand" href="/">${title}</a>
            <#if user??>
                <div class="user-info">
                    <span>${loggedInMessage} "${name}"</span>
                    <span>${roleMessage}</span>
                    <#list user.authorities as auth>
                        <#if auth?index == 0>
                            <span class="text-danger">${auth}</span>
                        <#else>
                        </#if>
                    </#list>
                    <span>${availableActions}</span>
                    <#list user.authorities as auth>
                        <#if auth?index != 0>
                            <span class="text-info">${auth}</span>
                        </#if>
                    </#list>
                </div>
            </#if>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <#if user?? && user.authorities?seq_contains("ADMIN")>
                        <li class="nav-item">
                            <a class="nav-link" href="/create">${create}</a>
                        </li>
                    </#if>
                </ul>
            </div>

            <#if user??>
                <a href="/profile" class="btn btn-outline-info m-2">${profile}</a>
                <#if _csrf??>
                    <form action="/logout" method="post">
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                               value="${(_csrf.token)!'--no-token--'}"/>
                        <button type="submit" class="btn btn-outline-info m-2" id="logout">${logout}</button>
                    </form>
                <#else>
                    <a href="/logout" class="btn btn-outline-info m-2" id="logout">${logout}</a>
                </#if>
            <#else>
                <a href="/auth/login"
                   class="btn btn-outline-info m-2">${login}</a>
                <a href="/auth/register"
                   class="btn btn-outline-info m-2">${register}</a>
            </#if>
            <div class="ms-auto language-buttons">
                <form action="/setLanguage" method="post" class="d-inline">
                    <input type="hidden" name="currentUrl" id="currentUrl" value="">
                    <button type="submit" name="lang" value="en" class="btn btn-outline-light">EN</button>
                    <button type="submit" name="lang" value="ru" class="btn btn-outline-light">RU</button>
                    <button type="submit" name="lang" value="kg" class="btn btn-outline-light">KG</button>
                </form>
            </div>

            <script>
                // Set the current URL in the hidden input field
                document.getElementById('currentUrl').value = window.location.href;
            </script>
        </div>
    </nav>

    <div class="container">

        <#nested spring>

    </div>

    </body>
    </html>
</#macro>