-- Создание таблиц
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

CREATE TABLE IF NOT EXISTS categories (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(100),
                                          parent_id INT
);

CREATE TABLE IF NOT EXISTS resumes (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       applicant_id INT,
                                       name VARCHAR(100),
                                       category_id INT,
                                       salary DOUBLE,
                                       is_active BOOLEAN,
                                       created_date TIMESTAMP,
                                       update_time TIMESTAMP,
                                       FOREIGN KEY (applicant_id) REFERENCES users(id),
                                       FOREIGN KEY (category_id) REFERENCES categories(id)
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
                                         update_time TIMESTAMP,
                                         FOREIGN KEY (author_id) REFERENCES users(id),
                                         FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS contact_types (
                                             id INT AUTO_INCREMENT PRIMARY KEY,
                                             type VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS contact_info (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            type_id INT,
                                            resume_id INT,
                                            "value" VARCHAR(255),
                                            FOREIGN KEY (type_id) REFERENCES contact_types(id),
                                            FOREIGN KEY (resume_id) REFERENCES resumes(id)
);

CREATE TABLE IF NOT EXISTS education_info (
                                              id INT AUTO_INCREMENT PRIMARY KEY,
                                              institution VARCHAR(100),
                                              program VARCHAR(100),
                                              start_date TIMESTAMP,
                                              end_date TIMESTAMP,
                                              degree VARCHAR(100),
                                              resume_id INT,
                                              FOREIGN KEY (resume_id) REFERENCES resumes(id)
);

CREATE TABLE IF NOT EXISTS responded_applicants (
                                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                                    resume_id INT,
                                                    vacancy_id INT,
                                                    confirmation BOOLEAN,
                                                    FOREIGN KEY (resume_id) REFERENCES resumes(id),
                                                    FOREIGN KEY (vacancy_id) REFERENCES vacancies(id)
);

CREATE TABLE IF NOT EXISTS messages (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        responded_applicant_id INT,
                                        content TEXT,
                                        timestamp TIMESTAMP,
                                        FOREIGN KEY (responded_applicant_id) REFERENCES responded_applicants(id)
);

CREATE TABLE IF NOT EXISTS work_experience_info (
                                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                                    resume_id INT,
                                                    years INT,
                                                    company_name VARCHAR(100),
                                                    position VARCHAR(100),
                                                    responsibilities TEXT,
                                                    FOREIGN KEY (resume_id) REFERENCES resumes(id)
);

-- Вставка пользователей, если их нет
INSERT INTO users (name, age, email, password, phone_number, avatar, account_type)
SELECT 'John Doe', 30, 'john.doe@example.com', '123456', '123-456-7890', 'avatar1.png', 'applicant'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'john.doe@example.com');

INSERT INTO users (name, age, email, password, phone_number, avatar, account_type)
SELECT 'Jane Doe', 25, 'jane.doe@example.com', 'qwerty', '098-765-4321', 'avatar2.png', 'employer'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'jane.doe@example.com');

-- Вставка категорий, если их нет
INSERT INTO categories (name, parent_id)
SELECT 'IT', NULL
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'IT');

INSERT INTO categories (name, parent_id)
SELECT 'Software Development', 1
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Software Development');

-- Вставка резюме, если их нет
INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time)
SELECT 1, 'Software Engineer', 2, 60000, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM resumes WHERE name = 'Software Engineer' AND applicant_id = 1);

INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time)
SELECT 1, 'Data Scientist', 2, 70000, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM resumes WHERE name = 'Data Scientist' AND applicant_id = 1);

-- Вставка вакансий, если их нет
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
SELECT 'Backend Developer', 'Develop and maintain backend services', 2, 80000, 2, 5, TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM vacancies WHERE name = 'Backend Developer' AND author_id = 2);

INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
SELECT 'Frontend Developer', 'Develop and maintain frontend applications', 2, 75000, 1, 3, TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM vacancies WHERE name = 'Frontend Developer' AND author_id = 2);

-- Вставка типов контактов, если их нет
INSERT INTO contact_types (type)
SELECT 'Email'
WHERE NOT EXISTS (SELECT 1 FROM contact_types WHERE type = 'Email');

INSERT INTO contact_types (type)
SELECT 'Phone'
WHERE NOT EXISTS (SELECT 1 FROM contact_types WHERE type = 'Phone');

-- Вставка контактной информации, если её нет
INSERT INTO contact_info (type_id, resume_id, "value")
SELECT 1, 1, 'john.doe@example.com'
WHERE NOT EXISTS (SELECT 1 FROM contact_info WHERE resume_id = 1 AND type_id = 1);

INSERT INTO contact_info (type_id, resume_id, "value")
SELECT 2, 1, '123-456-7890'
WHERE NOT EXISTS (SELECT 1 FROM contact_info WHERE resume_id = 1 AND type_id = 2);

-- Вставка информации об образовании, если её нет
INSERT INTO education_info (institution, program, start_date, end_date, degree, resume_id)
SELECT 'MIT', 'Computer Science', '2015-09-01 00:00:00', '2019-06-01 00:00:00', 'Bachelor', 1
WHERE NOT EXISTS (SELECT 1 FROM education_info WHERE resume_id = 1 AND institution = 'MIT');

INSERT INTO education_info (institution, program, start_date, end_date, degree, resume_id)
SELECT 'Stanford', 'Data Science', '2016-09-01 00:00:00', '2020-06-01 00:00:00', 'Master', 1
WHERE NOT EXISTS (SELECT 1 FROM education_info WHERE resume_id = 1 AND institution = 'Stanford');

-- Вставка откликнувшихся соискателей, если их нет
INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation)
SELECT 1, 1, TRUE
WHERE NOT EXISTS (SELECT 1 FROM responded_applicants WHERE resume_id = 1 AND vacancy_id = 1);

INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation)
SELECT 2, 2, TRUE
WHERE NOT EXISTS (SELECT 1 FROM responded_applicants WHERE resume_id = 2 AND vacancy_id = 2);

-- Вставка сообщений, если их нет
INSERT INTO messages (responded_applicant_id, content, timestamp)
SELECT 1, 'Thank you for applying!', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM messages WHERE responded_applicant_id = 1);

INSERT INTO messages (responded_applicant_id, content, timestamp)
SELECT 2, 'We will review your application soon.', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM messages WHERE responded_applicant_id = 2);

-- Вставка информации о рабочем опыте, если её нет
INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
SELECT 1, 3, 'Google', 'Software Engineer', 'Developed scalable backend services'
WHERE NOT EXISTS (SELECT 1 FROM work_experience_info WHERE resume_id = 1 AND company_name = 'Google');

INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
SELECT 1, 2, 'Facebook', 'Data Scientist', 'Analyzed large datasets'
WHERE NOT EXISTS (SELECT 1 FROM work_experience_info WHERE resume_id = 1 AND company_name = 'Facebook');