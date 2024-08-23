<#import "../layout.ftlh" as layout>
<@layout.layout>
    <div class="container">
        <h1>Создать работодателя</h1>
        <form action="/employers" method="post">
            <div class="form-group">
                <label for="name">Название компании</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="contactPerson">Контактное лицо</label>
                <input type="text" class="form-control" id="contactPerson" name="contactPerson" required>
            </div>
            <div class="form-group">
                <label for="email">Электронная почта</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="phone">Телефон</label>
                <input type="tel" class="form-control" id="phone" name="phone" required>
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