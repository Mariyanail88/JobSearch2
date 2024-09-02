<#import "../layout.ftlh" as layout>
<#import "../modal.ftlh" as modalTemplates>
<@layout.layout>

    <link rel="stylesheet" type="text/css" href="/static/css/profile.css" xmlns="http://www.w3.org/1999/html">

    <#assign title = entityTitle!"Заголовок по умолчанию">
    <#assign name = entityName!"Имя по умолчанию">
    <#assign updated = entityUpdated!false>

    <p>обновлено: <#if updated>да<#else>нет</#if></p>


    <@modalTemplates.modal modalTitle=title modalName=name />

    <div class="profile-container">
        <h1 class="profile-header" id="profile-header"></h1>

        <div class="profile-section">
            <div class="profile-info">
                <img id="profile-avatar"
                     src="/avatars/${userDto.avatar}"
                     alt="User Avatar"
                     class="profile-avatar"
                     onerror="this.onerror=null; this.src='/avatars/<#if userDto.accountType == 'employer'>No_Image_Available.jpg<#else>default_avatar.jpg</#if>';
                             this.onerror=function(){ this.src='/static/images/avatars/<#if userDto.accountType == 'employer'>No_Image_Available.jpg<#else>default_avatar.jpg</#if>'; };">
                <div id="profile-details">
                    <#if userDto.accountType == "employer">
                        <p class="profile-name" id="profile-name">название компании</p><br>
                        <a href="/profile/edit" class="btn btn-primary edit-profile-btn">Редактировать</a>
                    <#else>
                        <p class="profile-name" id="profile-name">${userDto.name}</p>
                        <p class="profile-age" id="profile-age">Возраст: ${userDto.age}</p>
                        <a href="/profile/edit" class="btn btn-primary edit-profile-btn">Редактировать</a>
                    </#if>
                </div>
            </div>

            <div class="profile-actions">
                <div class="create-resume">
                    <#if userDto.accountType == "employer">
                        <form action="/vacancies/create" method="get">
                            <button class="create-btn" id="create-btn">
                                Создать<br>вакансию
                            </button>
                        </form>
                    <#else>
                        <form action="/resumes/create" method="get">
                            <button type="submit" class="create-btn" id="create-btn">
                                Создать<br>резюме
                            </button>
                        </form>
                    </#if>
                </div>
                <#if userDto.accountType == "employer">
                    <form action="/resumes/resumes-responded-to-employer-vacancies" method="get">
                        <button type="submit" class="create-btn" id="create-btn">
                            отклики на ваши вакансии: ${resumesRespondedToEmployerVacancies?size}
                        </button>
                    </form>

                <#else>

                    <form action="/vacancies/vacancies-user-responded" method="get">
                        <button type="submit" class="create-btn" id="create-btn">
                            вы откликнулись на вакансии: ${vacanciesUserResponded?size}
                        </button>
                    </form>
                </#if>
            </div>
        </div>

        <div class="resume-section">
            <h2 id="resume-section-title">
                <#if userDto.accountType == "employer">
                    Вакансии:
                <#else>
                    Резюме:
                </#if>
            </h2>
            <div class="resume-list" id="resume-list">
                <#if userDto.accountType == "employer">
                    <#if userVacancies?size == 0>
                        <p class="no-resumes">Вакансий пока нет</p>
                    <#else>
                        <#list userVacancies as vacancy>
                            <div class="vacancy-or-resume-card">
                                <p class="vacancy-or-resume-title">${vacancy.name}</p>
                                <p class="vacancy-or-resume-title">${vacancy.id}</p>
                                <p class="vacancy-or-resume-date">Дата обновления: ${vacancy.getFormattedUpdatedDate()}</p>
                                <div class="vacancy-or-resume-actions">
                                    <form action="/vacancies/update/${vacancy.id}" method="post" style="display:inline;">
                                        <button type="submit" class="update-vacancy-or-resume-btn">Обновить</button>
                                    </form>
                                    <button class="edit-vacancy-or-resume-btn"
                                            onclick="location.href='/vacancies/edit/${vacancy.id}'">Ред.
                                    </button>
                                </div>
                            </div>
                        </#list>
                    </#if>
                <#else>
                    <#if userResumes?size == 0>
                        <p class="no-resumes">Резюме пока нет</p>
                    <#else>
                        <#list userResumes as resume>
                            <div class="vacancy-or-resume-card">
                                <p class="vacancy-or-resume-title">${resume.name}</p>
                                <p class="vacancy-or-resume-title">${resume.id}</p>
                                <p class="vacancy-or-resume-date">Дата обновления: ${resume.getFormattedUpdatedDate()}</p>
                                <div class="vacancy-or-resume-actions">
                                    <form action="/resumes/update/${resume.id}" method="post" style="display:inline;">
                                        <button type="submit" class="update-vacancy-or-resume-btn">Обновить</button>
                                    </form>
                                    <button class="edit-vacancy-or-resume-btn"
                                            onclick="location.href='/resumes/edit/${resume.id}'">Ред.
                                    </button>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </#if>
            </div>
        </div>
        <div class="home-btn-container">
            <a href="/" class="home-btn">Домой</a>
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            console.log("JavaScript is running");

            const accountType = "${userDto.accountType}";
            console.log("Тип аккаунта:", accountType);

            const profileHeader = document.getElementById("profile-header");
            if (accountType === "employer") {
                profileHeader.textContent = "Личный кабинет работодателя";
            } else {
                profileHeader.textContent = "Личный кабинет соискателя";
            }

            <#assign updated = entityUpdated!false>
            <#assign title = entityTitle!"">
            <#assign name = entityName!"">

            const vacancyUpdated = ${updated?c};
            if (vacancyUpdated) {

                const title = "${title}";
                const name = "${name}";
                const successModalElement = document.getElementById('successModal');


                successModalElement.querySelector('.modal-title').textContent = `${title} обновлено`;
                successModalElement.querySelector('.modal-body').textContent = `${title} ${name} успешно обновлено`;


                const successModal = new bootstrap.Modal(successModalElement);
                successModal.show();


                setTimeout(() => {
                    successModal.hide();
                }, 3000);
            }
        });
    </script>
</@layout.layout>