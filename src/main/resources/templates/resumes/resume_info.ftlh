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
    <div class="container">
        <h1>${resumeCreateTitle}</h1>
        <form action="/resumes/create" method="post">
            <div class="form-group">
                <label for="name">${resumeName}</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="categoryId">${resumeCategory}</label>
                <select class="form-control" id="categoryId" name="categoryId" required>
                    <#list categories as category>
                        <option value="${category.id}">${category.name}</option>
                    </#list>
                </select>
            </div>
            <div class="form-group">
                <label for="salary">${resumeSalary}</label>
                <input type="number" class="form-control" id="salary" name="salary" required>
            </div>
            <div class="form-group">
                <label for="isActive">${resumeIsActive}</label>
                <select class="form-control" id="isActive" name="isActive" required>
                    <option value="true">${resumeActiveYes}</option>
                    <option value="false">${resumeActiveNo}</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">${resumeCreateButton}</button>
        </form>
    </div>
</@layout.layout>