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
    <body>
    <div class="container">
        <div class="card mb-3">
            <div class="card-body">
                <h1 class="card-title">${resume.name}</h1>
                <p class="card-text"><strong>Автор:</strong> ${userDto.name}</p>
                <p class="card-text"><strong>Категория:</strong> ${categoryDto.name}</p>
                <p class="card-text"><strong>Зарплата:</strong> ${resume.salary}</p>
                <p class="card-text"><strong>Активно:</strong> ${resume.isActive?string('Да', 'Нет')}</p>
                <p class="card-text"><strong>Дата создания:</strong> ${resume.getFormattedCreatedDate()}</p>
                <p class="card-text"><strong>Время обновления:</strong> ${resume.getFormattedUpdatedDate()}</p>
            </div>
        </div>
        <div class="row">
            <div class="col text-center">
                <a href="/" class="btn btn-primary">Домой</a>
            </div>
        </div>
    </div>
    </body>
</@layout.layout>