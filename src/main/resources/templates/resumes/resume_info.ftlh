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
            .card-text strong {
                font-weight: bold;
                text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
            }
            .card-text .value {
                margin-left: 10px;
                font-size: 1.1em;
            }
        </style>
    </head>
    <body>
    <div class="container">
        <div class="card mb-3">
            <div class="card-body">
                <h1 class="card-title">${resume.name}</h1>

                <p class="card-text"><strong>${resumeName}:</strong> <span class="value">${resume.name}</span></p>
                <p class="card-text"><strong>${resumeSalary}:</strong> <span class="value">${resume.salary}</span></p>
                <p class="card-text"><strong>${resumeIsActive}:</strong> <span class="value">${resume.isActive?string(resumeActiveYes, resumeActiveNo)}</span></p>
                <p class="card-text"><strong>${resumeCreatedDate}:</strong> <span class="value">${resume.getFormattedCreatedDate()}</span></p>
                <p class="card-text"><strong>${resumeUpdatedDate}:</strong> <span class="value">${resume.getFormattedUpdatedDate()}</span></p>
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