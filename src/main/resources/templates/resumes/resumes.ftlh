<#import "../layout.ftlh" as main>
<@main.layout>
    <h2>Resumes</h2>
    <p class="h4 text-dark">Number of resume: ${resumes?size}</p>
    <#if isAuthenticated>
        <p class="h4 text-dark">You logged in as ${username}</p>
    <#else>
        <p class="h4 text-dark">You are not logged in</p>
    </#if>
    <div class="row d-flex justify-content-between">
        <#list resumes as resume>
            <div class="card col-6 m-2" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">${resume.name}</h5>
                    <p class="card-text">
                        <strong>Applicant ID:</strong> ${resume.applicantId}<br>
                        <strong>Category ID:</strong> ${resume.categoryId}<br>
                        <strong>Salary:</strong> ${resume.salary}<br>
                        <strong>Active:</strong> <#if resume.isActive>Yes<#else>No</#if><br>
                        <strong>Created Date:</strong> ${resume.createdDate}<br>
                        <strong>Updated Date:</strong> ${resume.updateTime}
                    </p>
                    <a href="#" class="btn btn-dark">More Info</a>
                </div>
            </div>
        </#list>
    </div>
</@main.layout>