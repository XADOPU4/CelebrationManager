INSERT INTO "user"(code, login, phonenumber, roleid, password, email)
VALUES ('XXXX5', 'executor1', '+79998887701', 4, '1', 'a@yandex.ru'),
       ('XXXX6', 'executor2', '+79998888802', 4, '1', 'b@yandex.ru'),
       ('XXXX7', 'organizer2', '+79998887703', 3, '1', 'c@yandex.ru'),
       ('XXXX8', 'executor3', '+79998888804', 4, '1', 'ex@yandex.ru');

INSERT INTO specification(name, roleid, description)
VALUES ('Аниматор',
        (SELECT id FROM role WHERE role.name = 'EXECUTOR'),
        'Занимается организацией конкурсов для детей'),
       ('Ассистент аниматора',
        (SELECT id FROM role WHERE role.name = 'EXECUTOR'),
        'Занимается помощью основному аниматору'),
       ('Водитель',
        (SELECT id FROM role WHERE role.name = 'EXECUTOR'),
        'Занимается перевозкой пассажиров'),
       ('Повар',
        (SELECT id FROM role WHERE role.name = 'EXECUTOR'),
        'Занимается приготовлением пищи'),
       ('Официант',
        (SELECT id FROM role WHERE role.name = 'EXECUTOR'),
        'Занимается подачей пищи к столу'),
       ('Аквагримёр',
        (SELECT id FROM role WHERE role.name = 'EXECUTOR'),
        'Занимается нанесением аквагрима'),
       ('DJ',
        (SELECT id FROM role WHERE role.name = 'EXECUTOR'),
        'Занимается звукорежиссёрскими заботами')
;

INSERT INTO userinfo(userid,
                     name,
                     contactphonenumber,
                     description,
                     country,
                     region,
                     city,
                     street,
                     house,
                     dateofbirth,
                     status)
VALUES ('5', 'Иванов Александр', '+79998887701',
        'Мастер на все руки...', 'Russia', 'Bryanskaya oblast',
        'Noviy Svet', 'Mirnaya', '1A', now(), 'Мастер!'),
       ('6', 'Петров Сергей', '+79998887702',
        'Отличный мастер на все руки', 'Russia', 'Bryanskaya oblast',
        'Bryansk', 'Lenina', '777', now(), 'Мастер x2!'),
       ('7', 'Остин Джеймс', '+79998887703',
        'Звукорежиссёр без особых навыков', 'Russia', 'Bryanskaya oblast',
        'Noviy Svet', 'Mirnaya', '777', now(), 'Живу музыкой'),
       ('8', 'Дятлов Денис', '+79998887704',
        'Похуже Остина', 'Russia', 'Bryanskaya oblast',
        'Noviy Svet', 'Mirnaya', '777', now(), 'Всем добра')
;

INSERT INTO userinfospecification(userinfoid, specid)
VALUES
    (5, 5),
    (5, 6),
    (5, 7),
    (5, 9),
    (5, 11),
    (5, 10),
    (6, 5),
    (6, 6),
    (6, 7),
    (6, 9),
    (6, 10),
    (6, 11),
    (7, 6),
    (7, 8),
    (7, 9),
    (7, 11),
    (8, 5),
    (8, 6),
    (8, 9),
    (8, 11)
;