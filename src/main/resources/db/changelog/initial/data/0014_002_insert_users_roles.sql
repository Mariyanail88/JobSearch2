INSERT INTO user_roles (user_id, role_id)
VALUES
    (1, (SELECT id FROM roles WHERE role_name = 'ADMIN')),

    (2, (SELECT id FROM roles WHERE role_name = 'AUTHORIZED_USER')),

    (3, (SELECT id FROM roles WHERE role_name = 'GUEST')),
    (4, (SELECT id FROM roles WHERE role_name = 'GUEST')),

    (5, (SELECT id FROM roles WHERE role_name = 'EMPLOYER')),
    (6, (SELECT id FROM roles WHERE role_name = 'EMPLOYER')),

    (7, (SELECT id FROM roles WHERE role_name = 'APPLICANT')),
    (8, (SELECT id FROM roles WHERE role_name = 'APPLICANT')),
    (9, (SELECT id FROM roles WHERE role_name = 'APPLICANT')),
    (10, (SELECT id FROM roles WHERE role_name = 'APPLICANT')),
    (11, (SELECT id FROM roles WHERE role_name = 'APPLICANT')),
    (12, (SELECT id FROM roles WHERE role_name = 'APPLICANT')),
    (13, (SELECT id FROM roles WHERE role_name = 'APPLICANT')),
    (14, (SELECT id FROM roles WHERE role_name = 'APPLICANT')),
    (15, (SELECT id FROM roles WHERE role_name = 'APPLICANT')),
    (16, (SELECT id FROM roles WHERE role_name = 'APPLICANT')),
    (17, (SELECT id FROM roles WHERE role_name = 'APPLICANT')),
    (18, (SELECT id FROM roles WHERE role_name = 'APPLICANT')),
    (19, (SELECT id FROM roles WHERE role_name = 'APPLICANT')),
    (20, (SELECT id FROM roles WHERE role_name = 'APPLICANT'));