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

-- Вставка пользователей
INSERT INTO users (name, age, email, password, phone_number, avatar, account_type)
VALUES ('John Doe', 30, 'john.doe@example.com', '123456', '123-456-7890', 'avatar1.png', 'applicant');

INSERT INTO users (name, age, email, password, phone_number, avatar, account_type)
VALUES ('Jane Doe', 25, 'jane.doe@example.com', 'qwerty', '098-765-4321', 'avatar2.png', 'employer');

-- Вставка категорий
INSERT INTO categories (name, parent_id)
VALUES ('IT', NULL);

INSERT INTO categories (name, parent_id)
VALUES ('Software Development', 1);

-- Вставка резюме
INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time)
VALUES (1, 'Software Engineer', 2, 60000, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time)
VALUES (1, 'Data Scientist', 2, 70000, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Вставка вакансий
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES ('Backend Developer', 'Develop and maintain backend services', 2, 80000, 2, 5, TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES ('Frontend Developer', 'Develop and maintain frontend applications', 2, 75000, 1, 3, TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Вставка типов контактов
INSERT INTO contact_types (type)
VALUES ('Email');

INSERT INTO contact_types (type)
VALUES ('Phone');

-- Вставка контактной информации
INSERT INTO contact_info (type_id, resume_id, "value")
VALUES (1, 1, 'john.doe@example.com');

INSERT INTO contact_info (type_id, resume_id, "value")
VALUES (2, 1, '123-456-7890');

-- Вставка об образовании
INSERT INTO education_info (institution, program, start_date, end_date, degree, resume_id)
VALUES ('MIT', 'Computer Science', '2015-09-01 00:00:00', '2019-06-01 00:00:00', 'Bachelor', 1);

INSERT INTO education_info (institution, program, start_date, end_date, degree, resume_id)
VALUES ('Stanford', 'Data Science', '2016-09-01 00:00:00', '2020-06-01 00:00:00', 'Master', 1);

-- Вставка откликнувшихся соискателей
INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation)
VALUES (1, 1, TRUE);

INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation)
VALUES (2, 2, TRUE);

-- Вставка сообщений
INSERT INTO messages (responded_applicant_id, content, timestamp)
VALUES (1, 'Thank you for applying!', CURRENT_TIMESTAMP);

INSERT INTO messages (responded_applicant_id, content, timestamp)
VALUES (2, 'We will review your application soon.', CURRENT_TIMESTAMP);

-- Вставка  опыта
INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
VALUES (1, 3, 'Google', 'Software Engineer', 'Developed scalable backend services');

INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
VALUES (1, 2, 'Facebook', 'Data Scientist', 'Analyzed large datasets');