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
    <style>
        .form-label {
            font-weight: bold; /* Сделать текст жирным */
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1); /* Добавить тень к тексту */
        }
    </style>

    <div class="container">
        <h1>${resumeEditTitle!''}</h1>
        <form action="/resumes/edit/${resume.id}" method="post">
            <div class="form-group">
                <label for="name" class="form-label">${resumeName!''}</label>
                <input type="text" class="form-control" id="name" name="name" value="${resume.name!''}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("name")>
                    <span class="error-message">${bindingResult.getFieldError("name").defaultMessage}</span>
                </#if>
            </div>
            <div class="form-group">
                <label for="applicantId" class="form-label">${resumeApplicantId!''}</label>
                <input type="number" class="form-control" id="applicantId" name="applicantId" value="${resume.applicantId!''}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("applicantId")>
                    <span class="error-message">${bindingResult.getFieldError("applicantId").defaultMessage}</span>
                </#if>
            </div>
            <div class="form-group">
                <label for="categoryId" class="form-label">${resumeCategory!''}</label>
                <select class="form-control" id="categoryId" name="categoryId" required>
                    <#list categories as category>
                        <option value="${category.id}" <#if resume.categoryId == category.id>selected</#if>>${category.name}</option>
                    </#list>
                </select>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("categoryId")>
                    <span class="error-message">${bindingResult.getFieldError("categoryId").defaultMessage}</span>
                </#if>
            </div>
            <div class="form-group">
                <label for="salary" class="form-label">${resumeSalary!''}</label>
                <input type="number" class="form-control" id="salary" name="salary" value="${resume.salary!''}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("salary")>
                    <span class="error-message">${bindingResult.getFieldError("salary").defaultMessage}</span>
                </#if>
            </div>
            <div class="form-group">
                <label for="isActive" class="form-label">${resumeIsActive!''}</label>
                <select class="form-control" id="isActive" name="isActive" required>
                    <option value="true" <#if resume.isActive?? && resume.isActive>selected</#if>>${resumeActiveYes!''}</option>
                    <option value="false" <#if resume.isActive?? && !resume.isActive>selected</#if>>${resumeActiveNo!''}</option>
                </select>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("isActive")>
                    <span class="error-message">${bindingResult.getFieldError("isActive").defaultMessage}</span>
                </#if>
            </div>
            <button type="submit" class="btn btn-primary">${resumeSaveButton!''}</button>
        </form>
    </div>
</@layout.layout>