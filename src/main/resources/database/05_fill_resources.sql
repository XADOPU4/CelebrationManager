insert into stuffcategory (name, type, description)
values
    ('Одноразовая посуда', 'DISPOSABLE', 'Недорогая одноразовая посуда'),
    ('Декор автомобилей', 'REUSABLE', 'Применяется для украшения автомобилей на свадьбах, фестивалях');

insert into stuff(name, description, price, categoryid, filename)
values
    ('Стаканчики одноразовые', '', 1.50, 1, null),
    ('Тарелочки одноразовые', '', 2.50, 1, null),
    ('Лебеди для автомобиля', '', 2500, 2, null),
    ('Кольца обручальные для автомобиля', '', 3500, 2, null);

