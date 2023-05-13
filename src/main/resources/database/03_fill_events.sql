INSERT INTO eventtype (name)
VALUES ('Свадьба'),
       ('Корпоратив'),
       ('Детский день рождения'),
       ('Юбилей 50+');

INSERT INTO event(
                  name,
                  eventtypeid,
                  description,
                  actdate,
                  enddate,
                  status)
VALUES (
        'Свадьба такс',
        (SELECT id FROM eventtype WHERE eventtype.name = 'Свадьба'),
        'Очень дорогая свадьба!!!',
        '31.12.2023',
        null,
        'CREATED'),
       (
        'День рождения Споти',
        (SELECT id FROM eventtype WHERE eventtype.name = 'Детский день рождения'),
        'День рождения маленького пса, нужно всё в ажуре!!!',
        '14.08.2023',
        null,
        'CREATED');

INSERT INTO usertoevent(userid, eventid, status, description)
VALUES ((SELECT id FROM "user" WHERE "user".id = 2),
        (SELECT id FROM event WHERE name = 'Свадьба такс'),
        'APPROVED',
        'Клиент, заказавший свадьбу такс!!'),
       ((SELECT id FROM "user" WHERE "user".id = 3),
        (SELECT id FROM event WHERE name = 'Свадьба такс'),
        'APPROVED',
        'Организатор свадьбы такс!!'),
       ((SELECT id FROM "user" WHERE "user".id = 4),
        (SELECT id FROM event WHERE name = 'Свадьба такс'),
        'INVITED',
        'Исполнитель, которого пригласили исполнять!!');

