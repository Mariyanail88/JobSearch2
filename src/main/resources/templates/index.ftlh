<#import "layout.ftlh" as main>
<@main.layout>
    <p class="h4 text-dark">Количество вакансий: ${vacancies?size}</p>
    <#if isAuthenticated>
        <p class="h4 text-dark">Вы вошли как ${username}</p>
    <#else>
        <p class="h4 text-dark">Вы не вошли в систему</p>
    </#if>
    <div class="row d-flex justify-content-between">
        <#list vacancies as vacancy>
            <div class="card col-6 m-2" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">${vacancy.name}</h5>
                    <p class="card-text">${vacancy.description}</p>
                    <p class="card-text">Зарплата: ${vacancy.salary}</p>
                    <p class="card-text">Активно: <#if vacancy.isActive>Да<#else>Нет</#if></p>
                    <a href="/vacancies/${vacancy.id}" class="btn btn-dark">Подробнее</a>
                </div>
            </div>
        </#list>
    </div>
</@main.layout>