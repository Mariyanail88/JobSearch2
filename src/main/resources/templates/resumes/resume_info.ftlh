<#import "../layout.ftlh" as main>
<@main.layout>
    <#if isAuthenticated>
        <p class="h4 text-dark">You logged in as ${username}</p>
    <#else>
        <p class="h4 text-dark">You are not logged in</p>
    </#if>
    <body>
    <div class="container">
        <div class="card mb-3">
            <div class="card-body">
                <h1 class="card-title">${resume.name}</h1>
                <p class="card-text"><strong>vacancy ID:</strong> ${resume.id}</p>
                <p class="card-text"><strong>Description:</strong> ${resume.applicantId}</p>
                <p class="card-text"><strong>Category:</strong> ${resume.categoryId}</p>
                <p class="card-text"><strong>Salary:</strong> ${resume.salary}</p>
                <p class="card-text"><strong>Active:</strong> ${resume.isActive?string('Yes', 'No')}</p>
                <p class="card-text"><strong>Created Date:</strong> ${resume.getFormattedCreatedDate()}</p>
                <p class="card-text"><strong>Updated Time:</strong> ${resume.getFormattedUpdatedDate()}</p>
            </div>
        </div>
        <!-- Centering the Home button -->
        <div class="row">
            <div class="col text-center"> <!-- This div centers the button -->
                <a href="/" class="btn btn-primary">Home</a>
            </div>
        </div>
    </div>
    </body>
</@main.layout>