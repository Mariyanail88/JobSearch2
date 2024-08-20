<#import "../layout.ftlh" as main>
<@main.layout>
    <#if isAuthenticated>
        <p class="h4 text-dark">You logged in as ${username}</p>
    <#else>
        <p class="h4 text-dark">You are not logged in</p>
    </#if>
    <div class="container">
        <div class="card mb-3">
            <div class="card-body">
                <h1 class="card-title">${vacancy.name}</h1>

                <p class="card-text"><strong>Vacancy ID:</strong> ${vacancy.id}</p>
                <p class="card-text"><strong>Description:</strong> ${vacancy.description}</p>
                <p class="card-text"><strong>Category:</strong> ${vacancy.categoryId}</p>
                <p class="card-text"><strong>Salary:</strong> ${vacancy.salary}</p>
                <p class="card-text">
                    <strong>Work Experience:</strong> From ${vacancy.expFrom} to ${vacancy.expTo} years</p>
                <p class="card-text"><strong>Active:</strong> ${vacancy.isActive?string('Yes', 'No')}</p>
                <p class="card-text"><strong>Author ID:</strong> ${vacancy.authorId}</p>
                <p class="card-text"><strong>Created Date:</strong> ${vacancy.getFormattedCreatedDate()}</p>
                <p class="card-text"><strong>Updated Time:</strong> ${vacancy.getFormattedUpdatedDate()}</p>
            </div>
        </div>
        <!-- Centering the Home button -->
        <div class="row">
            <div class="col text-center"> <!-- This div centers the button -->
                <a href="/" class="btn btn-primary">Home</a>
            </div>
        </div>
    </div>
</@main.layout>