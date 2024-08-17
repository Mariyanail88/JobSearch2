<#import "layout.ftlh" as main>
<@main.layout>
    <p class="h4 text-dark">Number of vacancies: ${vacancies?size}</p>
    <#if isAuthenticated>
        <p class="h4 text-dark">You logged in as ${username}</p>
    <#else>
        <p class="h4 text-dark">You are not logged in</p>
    </#if>
    <div class="row d-flex justify-content-between">
        <#list vacancies as vacancy>
            <div class="card col-6 m-2" style="width: 18rem;">
                <img src="/static/default_avatar(1).png" class="card-img-top" alt="No image available">
                <div class="card-body">
                    <h5 class="card-title">${vacancy.name}</h5>
                    <p class="card-text">${vacancy.description}</p>
                    <p class="card-text">Salary: ${vacancy.salary}</p>
                    <p class="card-text">Active: <#if vacancy.isActive>Yes<#else>No</#if></p>
                    <a href="#" class="btn btn-dark">details</a>
                </div>
            </div>
        </#list>
    </div>
</@main.layout>