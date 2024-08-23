<#import "../layout.ftlh" as layout>
<@layout.layout>
    <div class="container">
        <h1>Редактировать вакансию</h1>
        <form action="/vacancies/edit/${vacancy.id}" method="post">
            <div class="form-group">
                <label for="name">Название</label>
                <input type="text" class="form-control" id="name" name="name" value="${vacancy.name}" required>
            </div>
            <div class="form-group">
                <label for="description">Описание</label>
                <textarea class="form-control" id="description" name="description" required>${vacancy.description}</textarea>
            </div>
            <div class="form-group">
                <label for="categoryId">Категория</label>
                <input type="number" class="form-control" id="categoryId" name="categoryId" value="${vacancy.categoryId}" required>
            </div>
            <div class="form-group">
                <label for="salary">Зарплата</label>
                <input type="number" class="form-control" id="salary" name="salary" value="${vacancy.salary}" required>
            </div>
            <div class="form-group">
                <label for="expFrom">Опыт от</label>
                <input type="number" class="form-control" id="expFrom" name="expFrom" value="${vacancy.expFrom}" required>
            </div>
            <div class="form-group">
                <label for="expTo">Опыт до</label>
                <input type="number" class="form-control" id="expTo" name="expTo" value="${vacancy.expTo}" required>
            </div>
            <div class="form-group">
                <label for="isActive">Активно</label>
                <select class="form-control" id="isActive" name="isActive" required>
                    <option value="true" <#if vacancy.isActive?? && vacancy.isActive>selected</#if>>Да</option>
                    <option value="false" <#if vacancy.isActive?? && !vacancy.isActive>selected</#if>>Нет</option>
                </select>
            </div>
            <div class="form-group">
                <label for="authorId">ID Автора</label>
                <input type="number" class="form-control" id="authorId" name="authorId" value="${vacancy.authorId}" required>
            </div>
            <button type="submit" class="btn btn-primary">Сохранить</button>
        </form>
    </div>
</@layout.layout>