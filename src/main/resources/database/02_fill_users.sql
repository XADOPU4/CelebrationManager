INSERT INTO role(name)
VALUES ('ADMIN'),
       ('CLIENT'),
       ('ORGANIZER'),
       ('EXECUTOR');

INSERT INTO "user"(code, login, phonenumber, roleid, password, email)
VALUES ('XXXX1', 'xadopu4', '+79998887766', 1, 'pASSw0rd', 'danya.khadorich@yandex.ru'),
       ('XXXX2', 'client', '+79998888889', 2, '1', 'client@yandex.ru'),
       ('XXXX3', 'shelepushka', '+79998887755', 3, '1', 'shelepushka@yandex.ru'),
       ('XXXX4', 'executor', '+79998888888', 4, '1', 'executor@yandex.ru');

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
VALUES ('1', 'Daniil Xadopu4', '+79998887766',
        'Best guy', 'Russia', 'Bryanskaya oblast',
        'Noviy Svet', 'Mirnaya', '1A', now(), 'Chilling...'),
       ('2', 'Клиент Сервиса', '+79998887734',
        'Best guy', 'Russia', 'Bryanskaya oblast',
        'Noviy Svet', 'Mirnaya', '1A', now(), 'Хочу праздник!'),
       ('3', 'Olga Shelepina', '+79998887755',
        'Best girl', 'Russia', 'Bryanskaya oblast',
        'Bryansk', 'Lenina', '777', now(), 'Dachshund adorer'),
       ('4', 'Executor Ivanov', '+79998887711',
        'Best executor', 'Russia', 'Bryanskaya oblast',
        'Noviy Svet', 'Mirnaya', '777', now(), 'Executing...');


INSERT INTO specification(name, roleid, description)
VALUES ('Свадебный организатор',
        (SELECT id FROM role WHERE role.name = 'ORGANIZER'),
        'Занимается организацией свадебных мероприятий под ключ'),
       ('Организатор корпоративов',
        (SELECT id FROM role WHERE role.name = 'ORGANIZER'),
        'Занимается организацией корпоративов'),
       ('Тамада',
        (SELECT id FROM role WHERE role.name = 'EXECUTOR'),
        'Занимается организацией свадебных конкурсов'),
       ('Шеф-повар',
        (SELECT id FROM role WHERE role.name = 'EXECUTOR'),
        'Занимается приготовлением блюд для банкетов');

-- Всем ролям все их специализации выдать для теста
INSERT INTO userinfospecification (userinfoid, specid)
SELECT ui.id, s.id
FROM userinfo ui
         JOIN "user" u on u.id = ui.userid
         JOIN specification s on s.roleid = u.roleid;



