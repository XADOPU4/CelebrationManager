INSERT INTO eventtype (name)
VALUES ('День рождения'),
       ('Корпоратив'),
       ('Свадьба');

INSERT INTO event(code, name, eventtypeid, description, actdate, enddate, status)
VALUES ('event_1',
        'Свадьба в горах',
        (SELECT id FROM eventtype as et WHERE et.name = 'Свадьба'),
        'Нужна топ свадьба!',
        '2024-11-22',
        '2024-11-24',
        'CREATED'),

       ('event_2',
        'Корпоратив ГАЗПРОМ',
        (SELECT id FROM eventtype as et WHERE et.name = 'Корпоратив'),
        'Серьезное мероприятие!',
        '2024-09-23',
        '2024-09-24',
        'CREATED');

INSERT INTO usertoevent (userid, eventid, status, description)
VALUES ((SELECT id FROM "user" as u WHERE u.login = 'xadopu4'),
        (SELECT id FROM event as e WHERE e.eventtypeid = (SELECT id FROM eventtype as et WHERE et.name = 'Свадьба' LIMIT 1)),
        'APPROVED',
        'Красоту делает на свадьбе'),
       ((SELECT id FROM "user" as u WHERE u.login = 'Shelepushka'),
        (SELECT id FROM event as e WHERE e.eventtypeid = (SELECT id FROM eventtype as et WHERE et.name = 'Свадьба' LIMIT 1)),
        'APPROVED',
        'Тоже красоту делает на свадьбе'),
       ((SELECT id FROM "user" as u WHERE u.login = 'Shelepushka'),
        (SELECT id FROM event as e WHERE e.eventtypeid = (SELECT id FROM eventtype as et WHERE et.name = 'Корпоратив' LIMIT 1)),
        'APPROVED',
        'Тоже красоту делает на свадьбе');
