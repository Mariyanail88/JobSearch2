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
        <h1>${vacancyEditTitle}</h1>
        <form action="/vacancies/edit/${vacancy.id}" method="post">
            <div class="form-group">
                <label for="name">${vacancyEditName}</label>
                <input type="text" class="form-control" id="name" name="name" value="${vacancy.name}" required>
            </div>
            <div class="form-group">
                <label for="description">${vacancyEditDescription}</label>
                <textarea class="form-control" id="description" name="description" required>${vacancy.description}</textarea>
            </div>
            <div class="form-group">
                <label for="categoryId">${vacancyEditCategoryId}</label>
                <input type="number" class="form-control" id="categoryId" name="categoryId" value="${vacancy.categoryId}" required>
            </div>
            <div class="form-group">
                <label for="salary">${vacancyEditSalary}</label>
                <input type="number" class="form-control" id="salary" name="salary" value="${vacancy.salary}" required>
            </div>
            <div class="form-group">
                <label for="expFrom">${vacancyEditExpFrom}</label>
                <input type="number" class="form-control" id="expFrom" name="expFrom" value="${vacancy.expFrom}" required>
            </div>
            <div class="form-group">
                <label for="expTo">${vacancyEditExpTo}</label>
                <input type="number" class="form-control" id="expTo" name="expTo" value="${vacancy.expTo}" required>
            </div>
            <div class="form-group">
                <label for="isActive">${vacancyEditIsActive}</label>
                <select class="form-control" id="isActive" name="isActive" required>
                    <option value="true" <#if vacancy.isActive?? && vacancy.isActive>selected</#if>>${vacancyEditActiveYes}</option>
                    <option value="false" <#if vacancy.isActive?? && !vacancy.isActive>selected</#if>>${vacancyEditActiveNo}</option>
                </select>
            </div>
            <div class="form-group">
                <label for="authorId">${vacancyEditAuthorId}</label>
                <input type="number" class="form-control" id="authorId" name="authorId" value="${vacancy.authorId}" required>
            </div>
            <button type="submit" class="btn btn-primary">${vacancyEditSubmit}</button>
        </form>
    </div>
</@layout.layout>