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
    <h2>${vacanciesTitle}</h2>
    <div class="row d-flex justify-content-between">
        <#list vacancies as vacancy>
            <div class="card col-6 m-2" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">${vacancy.name}</h5>
                    <p class="card-text">${vacancy.description}</p>
                    <p class="card-text">${vacancySalary}: ${vacancy.salary}</p>
                    <p class="card-text">${vacancyIsActive}: <#if vacancy.isActive>${vacancyActiveYes}<#else>${vacancyActiveNo}</#if></p>
                    <a href="/vacancies/${vacancy.id}" class="btn btn-primary">${vacancyDetails}</a>
                </div>
            </div>
        </#list>
        <div class="row">
            <div class="col text-center">
                <a href="/" class="btn btn-primary w-100">${home}</a>
            </div>
        </div>
    </div>
</@layout.layout>