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
    <head>
        <style>
            .card-title {
                font-weight: bold;
                text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
            }
            .card-text {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .card-text strong {
                font-weight: bold;
                text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
                flex: 1;
            }
            .card-text .value {
                margin-left: 10px;
                font-size: 1.1em;
                flex: 2;
                text-align: left;
            }
            .highlight {
                animation: highlight 1s ease-in-out;
            }
            @keyframes highlight {
                from { background-color: yellow; }
                to { background-color: transparent; }
            }
        </style>
        <script>
            document.addEventListener("DOMContentLoaded", function() {
                const values = document.querySelectorAll('.value');
                values.forEach(value => {
                    value.classList.add('highlight');
                });
            });
        </script>
    </head>
    <body>
    <div class="container">
        <div class="card mb-3">
            <div class="card-body">
                <h1 class="card-title">${vacancy.name}</h1>

                <p class="card-text"><strong>${vacancyDescription}:</strong> <span class="value">${vacancy.description}</span></p>
                <p class="card-text"><strong>${vacancyCategory}:</strong> <span class="value">${category.name}</span></p>
                <p class="card-text"><strong>${vacancySalary}:</strong> <span class="value">${vacancy.salary}</span></p>
                <p class="card-text"><strong>${vacancyExperience}:</strong> <span class="value">${vacancyExperienceFromTo} ${vacancy.expFrom} ${vacancy.expTo} лет</span></p>
                <p class="card-text"><strong>${vacancyIsActive}:</strong> <span class="value">${vacancy.isActive?string(vacancyActiveYes, vacancyActiveNo)}</span></p>
                <p class="card-text"><strong>${companyEmail}:</strong> <span class="value"><a href="/company/${user.email}">${user.email}</a></span></p>
                <p class="card-text"><strong>${vacancyCreatedDate}:</strong> <span class="value">${vacancy.getFormattedCreatedDate()}</span></p>
                <p class="card-text"><strong>${vacancyUpdatedDate}:</strong> <span class="value">${vacancy.getFormattedUpdatedDate()}</span></p>
            </div>
        </div>
        <div class="row">
            <div class="col text-center">
                <a href="/" class="btn btn-primary">${home}</a>
            </div>
        </div>
    </div>
    </body>
</@layout.layout>