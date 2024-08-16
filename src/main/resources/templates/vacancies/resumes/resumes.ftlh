<#import "../layout.ftlh" as main>
<@main.layout>
    <h2>Resumes</h2>
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
                        <strong>Created Date:</strong> ${resume.createdDate?string("yyyy-MM-dd")}<br>
                        <strong>Updated Date:</strong> ${resume.updateTime?string("yyyy-MM-dd")}
                    </p>
                    <a href="#" class="btn btn-primary">More Info</a>
                </div>
            </div>
        </#list>
    </div>
</@main.layout>