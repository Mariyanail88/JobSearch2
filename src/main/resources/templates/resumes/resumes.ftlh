<#import "../layout.ftlh" as layout>
<@layout.layout
title=title
home=home
create=create
profile=profile
logout=logout
login=login
register=register
loggedInMessage=loggedInMessage
roleMessage=roleMessage
notLoggedInMessage=notLoggedInMessage
availableActions=availableActions
>
    <h2>Резюме</h2>
    <p class="h4 text-dark">Количество резюме: ${resumes?size}</p>
    <#if isAuthenticated>
        <p class="h4 text-dark">Вы вошли как ${username}</p>
    <#else>
        <p class="h4 text-dark">Вы не вошли в систему</p>
    </#if>
    <div class="row d-flex justify-content-between">
        <#list resumes as resume>
            <div class="col-md-6 col-lg-4 mb-4">
                <div class="card h-100 shadow-sm">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">${resume.name}</h5>
                        <p class="card-text mb-2"><strong>Зарплата:</strong> ${resume.getFormattedCreatedDate()}</p>
                        <a href="/resumes/${resume.id}" class="btn btn-primary mt-auto">Подробнее</a>
                    </div>
                </div>
            </div>
        </#list>
        <!-- Центрирование кнопки "Домой" -->
        <div class="row">
            <div class="col text-center"> <!-- Этот div центрирует кнопку -->
                <a href="/" class="btn btn-primary w-100">Домой</a>
            </div>
        </div>
    </div>
</@layout.layout>