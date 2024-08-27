<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container mt-4">
        <h2>Регистрация</h2>
        <#if errorMessage??>
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </#if>
        <#if successMessage??>
            <div class="toast align-items-center text-bg-success border-0" role="alert" aria-live="assertive"
                 aria-atomic="true" id="successToast">
                <div class="d-flex">
                    <div class="toast-body">
                        ${successMessage}
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"
                            aria-label="Закрыть"></button>
                </div>
            </div>
        </#if>

        <!-- Отображение списка ошибок в стилизованном блоке с заголовком -->
        <#if bindingResult?has_content>
            <div class="alert alert-danger" role="alert">
                <h4 class="alert-heading">Пожалуйста, исправьте следующие ошибки:</h4>
                <hr>
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>Название поля</th>
                        <th>Описание ошибки</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list bindingResult.fieldErrors as fieldError>
                        <tr>
                            <td><strong>${fieldError.field}</strong></td>
                            <td>${fieldError.defaultMessage}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </#if>

        <form action="/auth/register" method="post" enctype="multipart/form-data">
            <#if _csrf??>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>
            <div class="mb-3">
                <label for="name" class="form-label">Имя</label>
                <input type="text" class="form-control" id="name" name="name" value="${userDto.name!'Sid W'}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("name")>
                    <span class="error-message">${bindingResult.getFieldError("name").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="age" class="form-label">Возраст</label>
                <input type="number" class="form-control" id="age" name="age" value="${userDto.age!'42'}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("age")>
                    <span class="error-message">${bindingResult.getFieldError("age").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Электронная почта</label>
                <input type="email" class="form-control" id="email" name="email"
                       value="${userDto.email!'sid.w1@example.com'}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("email")>
                    <span class="error-message">${bindingResult.getFieldError("email").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Пароль</label>
                <input type="password" class="form-control" id="password" name="password"
                       value="${userDto.password!'123'}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("password")>
                    <span class="error-message">${bindingResult.getFieldError("password").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="phoneNumber" class="form-label">Номер телефона</label>
                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                       value="${userDto.phoneNumber!'+9961234783'}" required>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("phoneNumber")>
                    <span class="error-message">${bindingResult.getFieldError("phoneNumber").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="accountType" class="form-label">Тип аккаунта</label>
                <select class="form-control" id="accountType" name="accountType" required>
                    <option value="applicant"
                            <#if userDto.accountType?? && userDto.accountType == "applicant">selected</#if>>Соискатель
                    </option>
                    <option value="employer"
                            <#if userDto.accountType?? && userDto.accountType == "employer">selected</#if>>Работодатель
                    </option>
                </select>
                <#if bindingResult?has_content && bindingResult.hasFieldErrors("accountType")>
                    <span class="error-message">${bindingResult.getFieldError("accountType").defaultMessage}</span>
                </#if>
            </div>
            <div class="mb-3">
                <label for="avatar" class="form-label">Аватар (необязательно)</label>
                <input type="file" class="form-control" id="avatar" name="avatar">
            </div>
            <button type="submit" class="btn btn-primary w-100">Зарегистрироваться</button>
        </form>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const successToast = document.getElementById('successToast');
            if (successToast) {
                const toast = new bootstrap.Toast(successToast);
                toast.show();

                setTimeout(() => {
                    window.location.href = '/auth/profile';
                }, 3000);
            }
        });
    </script>
</@main.layout>