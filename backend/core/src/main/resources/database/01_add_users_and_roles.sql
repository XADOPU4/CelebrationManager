INSERT INTO role(name)
VALUES ('CLIENT'),
       ('ORGANIZER'),
       ('EXECUTOR'),
       ('ADMIN');


INSERT INTO "user"(code, login, phonenumber, roleid, password, email)
VALUES ('sys', 'system', '+7-999-999-99-99', (SELECT id FROM role WHERE name='ADMIN'), '1', 'admin@celebration.ru'),
       ('xadopu4', 'xadopu4', '+7-999-221-10-32', (SELECT id FROM role WHERE name='CLIENT'), '1', 'danya.khadorich@yandex.ru'),
       ('Shelepushka', 'Shelepushka', '+7-999-221-10-94', (SELECT id FROM role WHERE name='ORGANIZER'), '1', 'misa3kiruka@gmail.com');

INSERT INTO userinfo(userid, name, contactphonenumber, description, country, region, city, street, house, dateofbirth,
                     status)
VALUES ((SELECT id FROM "user" as u WHERE u.login = 'xadopu4'),
        'Хадорич Даниил Дмитриевич',
        '+7-999-221-10-32',
        'Топ кун',
        'Russia',
        'Bryanskaya oblast',
        'Noviy Sviet',
        'Mirnaya st',
        '1A',
        '2001-11-22',
        'Chilling...'),
       ((SELECT id FROM "user" u WHERE u.login = 'Shelepushka'),
        'Шелепина Ольга Дмитриевна',
        '+7-999-221-10-94',
        'Топ тян',
        'Russia',
        'Bryanskaya oblast',
        'Noviy Sviet',
        'Mirnaya st',
        '1A',
        '2001-11-22',
        'Chilling...');