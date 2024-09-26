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
    <link rel="stylesheet" type="text/css" href="/static/css/company-info.css">
    <div class="container mt-4">
        <h2>${companyInfoTitle}</h2>
        <div class="company-info">
            <p><strong>${companyRepresentative}: </strong> ${userDto.name}</p>
            <p><strong>${companyContactPhone}:</strong> ${userDto.phoneNumber}</p>
            <p><strong>${companyEmail}:</strong> <a href="/company/${userDto.email}">${userDto.email}</a></p>
        </div>

        <h1>${companyActiveVacancies}</h1>
        <#if companyVacancies?size == 0>
            <p>${companyNoVacancies}</p>
        <#else>
            <div class="vacancies-list">
                <#list companyVacancies as vacancy>
                    <div class="vacancy-card">
                        <h4>${vacancy.name}</h4>
                        <p><strong>${vacancyDescription}:</strong> ${vacancy.description}</p>
                        <p><strong>${vacancySalary}:</strong> ${vacancy.salary}</p>
                        <p><strong>${vacancyExperience}:</strong> ${vacancyExperienceFromTo} ${vacancy.expFrom} ${vacancy.expTo}</p>
                        <p><strong>${vacancyCreatedDate}:</strong> ${vacancy.getFormattedCreatedDate()}</p>
                        <p><strong>${vacancyUpdatedDate}:</strong> ${vacancy.getFormattedUpdatedDate()}</p>
                        <div class="vacancy-actions">
                            <a href="/vacancies/${vacancy.id}" class="btn btn-primary">${vacancyDetails}</a>
                        </div>
                    </div>
                </#list>
            </div>
        </#if>
    </div>

    <!-- Добавляем кнопки внизу страницы -->
    <div class="bottom-buttons">
        <a href="/" class="btn">${profileHome}</a>
        <a href="/vacancies" class="btn">${companyAllVacancies}</a>
    </div>
</@layout.layout>