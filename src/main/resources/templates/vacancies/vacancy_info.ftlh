<#import "../layout.ftlh" as main>
<@main.layout>
    <#if isAuthenticated>
        <p class="h4 text-dark">You logged in as ${username}</p>
    <#else>
        <p class="h4 text-dark">You are not logged in</p>
    </#if>
    <div class="container">
        <#if vacancy??>
            <div class="card mb-3">
                <div class="card-body">
                    <h1 class="card-title">${vacancy.name?if_exists}</h1>

                    <p class="card-text"><strong>Vacancy ID:</strong> ${vacancy.id?if_exists}</p>
                    <p class="card-text"><strong>Description:</strong> ${vacancy.description?if_exists}</p>
                    <p class="card-text"><strong>Category:</strong> ${vacancy.categoryId?if_exists}</p>
                    <p class="card-text"><strong>Salary:</strong> ${vacancy.salary?if_exists}</p>
                    <p class="card-text">
                        <strong>Work Experience:</strong> From ${vacancy.expFrom?if_exists} to ${vacancy.expTo?if_exists} years</p>
                    <p class="card-text"><strong>Active:</strong> ${vacancy.isActive?string('Yes', 'No')}</p>
                    <p class="card-text"><strong>Author ID:</strong> ${vacancy.authorId?if_exists}</p>
                    <p class="card-text"><strong>Created Date:</strong> ${vacancy.createdDate?if_exists}</p>
                    <p class="card-text"><strong>Updated Time:</strong> ${vacancy.updateTime?if_exists}</p>
                </div>
            </div>
        <#else>
            <p class="h4 text-dark">Vacancy not found.</p>
        </#if>
        <!-- Centering the Home button -->
        <div class="row">
            <div class="col text-center"> <!-- This div centers the button -->
                <a href="/" class="btn btn-primary">Home</a>
            </div>
        </div>
    </div>
</@main.layout>