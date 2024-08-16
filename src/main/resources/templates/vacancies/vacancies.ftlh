<#import "../layout.ftlh" as main>
<@main.layout>
    <h2>Vacancies</h2>
    <div class="row d-flex justify-content-between">
        <#list vacancies as vacancy>
            <div class="card col-6 m-2" style="width: 18rem;">
                <img src="/static/images/No_Image_Available.jpg" class="card-img-top" alt="No image available">
                <div class="card-body">
                    <h5 class="card-title">${vacancy.name}</h5>
                    <p class="card-text">${vacancy.description}</p>
                    <p class="card-text">Salary: ${vacancy.salary}</p>
                    <p class="card-text">Active: <#if vacancy.isActive>Yes<#else>No</#if></p>
                    <a href="#" class="btn btn-primary">More Info</a>
                </div>
            </div>
        </#list>
    </div>
</@main.layout>