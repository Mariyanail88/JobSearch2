<#import "../layout.ftlh" as main>
<@main.layout>
    <h2>Register</h2>
    <form action="/auth/register" method="post">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" class="form-control" id="username" name="username" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <div class="mb-3">
            <label for="accountType" class="form-label">Account Type</label>
            <select class="form-select" id="accountType" name="accountType" required>
                <option value="jobseeker">Job Seeker</option>
                <option value="employer">Employer</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Register</button>
    </form>
</@main.layout>