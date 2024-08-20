<#import "../layout.ftlh" as main>
<@main.layout>
<#--    <p class="h4 text-dark">Number of vacancies: ${vacancies?size}</p>-->
<#--    <#if isAuthenticated>-->
<#--        <p class="h4 text-dark">You logged in as ${username}</p>-->
<#--    <#else>-->
<#--        <p class="h4 text-dark">You are not logged in</p>-->
<#--    </#if>-->
    <h2>Vacancies</h2>
    <div class="row d-flex justify-content-between">
        <#list vacancies as vacancy>
            <div class="card col-6 m-2"  style="width: 18rem;">>
                <img src="/static/images/avatars/No_Image_Available.jpg" class="card-img-top" alt="No image available">
                <div class="card-body">
                    <h5 class="card-title">${vacancy.name}</h5>
                    <p class="card-text">${vacancy.description}</p>
                    <p class="card-text">Salary: ${vacancy.salary}</p>
                    <p class="card-text">Active: <#if vacancy.isActive>Yes<#else>No</#if></p>
                    <a href="/vacancies/${vacancy.id}" class="btn btn-primary">More Info</a>
                </div>
            </div>
        </#list>
        <!-- Centering the Home button -->
        <div class="row">
            <div class="col text-center"> <!-- This div centers the button -->
                <a href="/" class="btn btn-primary w-100">Home</a>
            </div>
        </div>
    </div>
</@main.layout>