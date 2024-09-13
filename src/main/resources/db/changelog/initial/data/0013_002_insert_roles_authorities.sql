INSERT INTO roles_authorities (role_id, authority_id)
-- Assign Authorities to ADMIN
VALUES ((SELECT id FROM roles WHERE role_name = 'ADMIN'), (SELECT id FROM authorities WHERE authority_name = 'CREATE')),
       ((SELECT id FROM roles WHERE role_name = 'ADMIN'), (SELECT id FROM authorities WHERE authority_name = 'EDIT')),
       ((SELECT id FROM roles WHERE role_name = 'ADMIN'), (SELECT id FROM authorities WHERE authority_name = 'READ')),
       ((SELECT id FROM roles WHERE role_name = 'ADMIN'), (SELECT id FROM authorities WHERE authority_name = 'UPDATE')),
       ((SELECT id FROM roles WHERE role_name = 'ADMIN'), (SELECT id FROM authorities WHERE authority_name = 'DELETE')),
       ((SELECT id FROM roles WHERE role_name = 'ADMIN'), (SELECT id FROM authorities WHERE authority_name = 'APPROVE')),
       ((SELECT id FROM roles WHERE role_name = 'ADMIN'), (SELECT id FROM authorities WHERE authority_name = 'REJECT')),

-- Assign Authorities to DOCTOR
       ((SELECT id FROM roles WHERE role_name = 'EMPLOYER'), (SELECT id FROM authorities WHERE authority_name = 'CREATE')),
       ((SELECT id FROM roles WHERE role_name = 'EMPLOYER'), (SELECT id FROM authorities WHERE authority_name = 'EDIT')),
       ((SELECT id FROM roles WHERE role_name = 'EMPLOYER'), (SELECT id FROM authorities WHERE authority_name = 'READ')),
       ((SELECT id FROM roles WHERE role_name = 'EMPLOYER'), (SELECT id FROM authorities WHERE authority_name = 'UPDATE')),

-- Assign Authorities to PATIENT
       ((SELECT id FROM roles WHERE role_name = 'APPLICANT'), (SELECT id FROM authorities WHERE authority_name = 'CREATE')),
       ((SELECT id FROM roles WHERE role_name = 'APPLICANT'), (SELECT id FROM authorities WHERE authority_name = 'EDIT')),
       ((SELECT id FROM roles WHERE role_name = 'APPLICANT'), (SELECT id FROM authorities WHERE authority_name = 'READ')),
       ((SELECT id FROM roles WHERE role_name = 'APPLICANT'), (SELECT id FROM authorities WHERE authority_name = 'UPDATE')),

-- Assign Authorities to AUTHORIZED_USER
       ((SELECT id FROM roles WHERE role_name = 'AUTHORIZED_USER'), (SELECT id FROM authorities WHERE authority_name = 'READ')),
       ((SELECT id FROM roles WHERE role_name = 'AUTHORIZED_USER'), (SELECT id FROM authorities WHERE authority_name = 'UPDATE')),

-- Assign Authorities to GUEST
       ((SELECT id FROM roles WHERE role_name = 'GUEST'), (SELECT id FROM authorities WHERE authority_name = 'READ'));