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
    <h2>Вакансии</h2>
    <div class="row d-flex justify-content-between">
        <#list vacancies as vacancy>
            <div class="card col-6 m-2" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">${vacancy.name}</h5>
                    <p class="card-text">${vacancy.description}</p>
                    <p class="card-text">Зарплата: ${vacancy.salary}</p>
                    <p class="card-text">Активно: <#if vacancy.isActive>Да<#else>Нет</#if></p>
                    <a href="/vacancies/${vacancy.id}" class="btn btn-primary">Подробнее</a>
                </div>
            </div>
        </#list>
        <div class="row">
            <div class="col text-center">
                <a href="/" class="btn btn-primary w-100">Домой</a>
            </div>
        </div>
    </div>
</@layout.layout>