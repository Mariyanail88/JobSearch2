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
        <h1>Редактировать профиль</h1>
        <form action="/profile/edit" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="name">Имя</label>
                <input type="text" class="form-control" id="name" name="name" value="${userDto.name?if_exists}" required>
            </div>
            <div class="form-group">
                <label for="age">Возраст</label>
                <input type="number" class="form-control" id="age" name="age" value="${userDto.age?if_exists}" required>
            </div>
            <div class="form-group">
                <label for="email">Электронная почта</label>
                <input type="email" class="form-control" id="email" name="email" value="${userDto.email?if_exists}" required>
            </div>
            <div class="form-group">
                <label for="password">Пароль</label>
                <input type="password" class="form-control" id="password" name="password" value="${userDto.password?if_exists}" required>
            </div>
            <div class="form-group">
                <label for="phone">Телефон</label>
                <input type="tel" class="form-control" id="phone" name="phone" value="${userDto.phoneNumber?if_exists}" required>
            </div>
            <div class="form-group">
                <label for="avatar">Фото профиля</label>
                <input type="file" class="form-control" id="avatar" name="avatar">
            </div>
            <div class="form-group">
                <label for="accountType">Тип аккаунта</label>
                <select class="form-control" id="accountType" name="accountType" required>
                    <#if userDto.accountType == 'employer'>
                        <option value="employer" selected>Работодатель</option>
                        <option value="applicant">Соискатель</option>
                    <#else>
                        <option value="employer">Работодатель</option>
                        <option value="applicant" selected>Соискатель</option>
                    </#if>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Сохранить</button>
        </form>
    </div>
</@layout.layout>