INSERT INTO eventtype (name)
VALUES ('Свадьба'),
       ('Детский день рождения');

INSERT INTO event(name,
                  eventtypeid,
                  description,
                  actdate,
                  enddate,
                  status)
VALUES ('Свадьба жениха и невесты',
        (SELECT id FROM eventtype WHERE eventtype.name = 'Свадьба'),
        'Очень дорогая свадьба!!!',
        '2023-12-31',
        null,
        'CREATED'),
       ('День рождения 26 июня',
        (SELECT id FROM eventtype WHERE eventtype.name = 'Детский день рождения'),
        'День рождения замечательного человека!!!',
        '2023-06-26',
        null,
        'CREATED'),
       ('День рождения ребёнка',
        (SELECT id FROM eventtype WHERE eventtype.name = 'Детский день рождения'),
        'День рождения маленького ребёнка, нужно всё в ажуре!!!',
        '2023-12-31',
        null,
        'CREATED');

INSERT INTO usertoevent(userid, eventid, status, description)
VALUES ((SELECT id FROM "user" WHERE "user".id = 3),
        (SELECT id FROM event WHERE name = 'Свадьба жениха и невесты'),
        'APPROVED',
        'Организатор свадьбы!!'),
       ((SELECT id FROM "user" WHERE "user".id = 3),
        (SELECT id FROM event WHERE name = 'День рождения 26 июня'),
        'INVITED',
        'Организатор дня рождения!'),
       ((SELECT id FROM "user" WHERE "user".id = 4),
        (SELECT id FROM event WHERE name = 'Свадьба жениха и невесты'),
        'INVITED',
        'Исполнитель, которого пригласили исполнять!!');

