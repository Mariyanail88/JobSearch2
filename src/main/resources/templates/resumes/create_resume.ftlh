<#import "../layout.ftlh" as layout>
<@layout.layout>
    <div class="container">
        <h1>Создать резюме</h1>
        <form action="/resumes/create" method="post">
            <div class="form-group">
                <label for="name">Название</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="applicantId">ID Соискателя</label>
                <input type="number" class="form-control" id="applicantId" name="applicantId" required>
            </div>
            <div class="form-group">
                <label for="categoryId">Категория</label>
                <input type="number" class="form-control" id="categoryId" name="categoryId" required>
            </div>
            <div class="form-group">
                <label for="salary">Зарплата</label>
                <input type="number" class="form-control" id="salary" name="salary" required>
            </div>
            <div class="form-group">
                <label for="isActive">Активно</label>
                <select class="form-control" id="isActive" name="isActive" required>
                    <option value="true">Да</option>
                    <option value="false">Нет</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Создать</button>
        </form>
    </div>
</@layout.layout>