<#import "../layout.ftlh" as layout>
<@layout.layout>
    <div class="container">
        <h1>Редактировать резюме</h1>
        <form action="/resumes/edit/${resume.id}" method="post">
            <div class="form-group">
                <label for="name">Название</label>
                <input type="text" class="form-control" id="name" name="name" value="${resume.name}" required>
            </div>
            <div class="form-group">
                <label for="applicantId">ID Соискателя</label>
                <input type="number" class="form-control" id="applicantId" name="applicantId" value="${resume.applicantId}" required>
            </div>
            <div class="form-group">
                <label for="categoryId">Категория</label>
                <input type="number" class="form-control" id="categoryId" name="categoryId" value="${resume.categoryId}" required>
            </div>
            <div class="form-group">
                <label for="salary">Зарплата</label>
                <input type="number" class="form-control" id="salary" name="salary" value="${resume.salary}" required>
            </div>
            <div class="form-group">
                <label for="isActive">Активно</label>
                <select class="form-control" id="isActive" name="isActive" required>
                    <option value="true" <#if resume.isActive?? && resume.isActive>selected</#if>>Да</option>
                    <option value="false" <#if resume.isActive?? && !resume.isActive>selected</#if>>Нет</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Сохранить</button>
        </form>
    </div>
</@layout.layout>