CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(45),
                                     age INT,
                                     email VARCHAR(100),
                                     password VARCHAR(45),
                                     phone_number VARCHAR(20),
                                     avatar VARCHAR(255),
                                     account_type VARCHAR(45)
);

CREATE TABLE IF NOT EXISTS resumes (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       applicant_id INT,
                                       name VARCHAR(100),
                                       category_id INT,
                                       salary DOUBLE,
                                       is_active BOOLEAN,
                                       created_date TIMESTAMP,
                                       update_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS vacancies (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         name VARCHAR(100),
                                         description TEXT,
                                         category_id INT,
                                         salary INT,
                                         exp_from INT,
                                         exp_to INT,
                                         is_active BOOLEAN,
                                         author_id INT,
                                         created_date TIMESTAMP,
                                         update_time TIMESTAMP
);
INSERT INTO users (name, age, email, password, phone_number, avatar, account_type)
VALUES
    ('John Doe', 30, 'john.doe@example.com', '123456', '123-456-7890', 'avatar1.png', 'user'),
    ('Jane Doe', 25, 'jane.doe@example.com', 'qwerty', '098-765-4321', 'avatar2.png', 'admin');

INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time)
VALUES
    (1, 'Software Engineer', 1, 60000, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Data Scientist', 2, 70000, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES
    ('Backend Developer', 'Develop and maintain backend services', 1, 80000, 2, 5, TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Frontend Developer', 'Develop and maintain frontend applications', 2, 75000, 1, 3, TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);