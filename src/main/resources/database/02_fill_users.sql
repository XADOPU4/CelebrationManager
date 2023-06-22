INSERT INTO role(name)
VALUES ('ADMIN'),
       ('CLIENT'),
       ('ORGANIZER'),
       ('EXECUTOR');

INSERT INTO "user"(code, login, phonenumber, roleid, password, email)
VALUES ('XXXX1', 'xadopu4', '+79998887766', 1, '1', 'danya.khadorich@yandex.ru'),
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
                     lat,
                     lon,
                     dateofbirth,
                     status)
VALUES ('1', 'Даниил Хадорич', '+79992211032',
        'Администратор сервиса Celebration Manager',
        'Россия', 'Брянская область',
        'Новый Свет', 'Мирная', '1А',
        53.375768,34.228354,
        now(), 'Где я - там бан!'),
       ('2', 'Клиент Сервиса', '+79998887734',
        'Клиент сервиса, которому нужны качественно подготовленные мероприятия',
        'Россия', 'Брянская область',
        'Новый Свет', 'Мирная', '1А',
        53.375768,34.228354,
        now(), 'Хочу праздник!'),
       ('3', 'Организатор Мероприятий', '+79998887755',
        'Профессиональный организатор мероприятий любой сложности и масштаба! Хотите свадьбу? - Легко! Корпоратив? - не проблема. День рождения любимого ребенка? - в лучшем виде!',
        'Россия', 'Брянская область',
        'Брянск', 'проспект Ленина', '77',
        53.252081, 34.376123,
        now(), 'Организую всё!'),
       ('4', 'Ващило Иван', '+79532791877',
        'Исполнять - моя профессия! Амбассадор кайфа...',
        'Россия', 'Брянская область',
        'Брянск', 'Богдана Хмельницкого', '41',
        53.207006, 34.430148,
        now(), 'Исполняю желания...');


INSERT INTO specification(name, roleid, description)
VALUES ('Свадьба',
        (SELECT id FROM role WHERE role.name = 'ORGANIZER'),
        'Занимается организацией свадебных мероприятий под ключ'),
       ('Детский день рождения',
        (SELECT id FROM role WHERE role.name = 'ORGANIZER'),
        'Занимается организацией детских дней рождения'),
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



