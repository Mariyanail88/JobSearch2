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
        <h1>${employerCreateTitle}</h1>
        <form action="/employers" method="post">
            <div class="form-group">
                <label for="name">${employerCreateCompanyName}</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="contactPerson">${employerCreateContactPerson}</label>
                <input type="text" class="form-control" id="contactPerson" name="contactPerson" required>
            </div>
            <div class="form-group">
                <label for="email">${employerCreateEmail}</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="phone">${employerCreatePhone}</label>
                <input type="tel" class="form-control" id="phone" name="phone" required>
            </div>
            <div class="form-group">
                <label for="isActive">${employerCreateIsActive}</label>
                <select class="form-control" id="isActive" name="isActive" required>
                    <option value="true">${employerCreateActiveYes}</option>
                    <option value="false">${employerCreateActiveNo}</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">${employerCreateSubmit}</button>
        </form>
    </div>
</@layout.layout>