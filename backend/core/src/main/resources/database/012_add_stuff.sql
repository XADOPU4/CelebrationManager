INSERT INTO stuffcategory (name, description)
VALUES
    ('Посуда', 'Категория, вмещающая в себя всю одноразовую посуду: стаканчики, тарелочки, приборы и др.'),
    ('Декор автомобилей', 'Категория, вмещающая в себя различные украшения автомобилей');


INSERT INTO stuff(name, description, price, categoryid)
VALUES
    ('Стаканчик одноразовый',
     'Самый простой',
     1.50,
     (SELECT id FROM stuffcategory as sc WHERE sc.name = 'Посуда')),
    ('Тарелка одноразовая',
     'Самая простая',
     2.50,
     (SELECT id FROM stuffcategory as sc WHERE sc.name = 'Посуда')),
    ('Ленты на автомобиль',
     'Подходят к свадьбе и др.',
     250.0,
     (SELECT id FROM stuffcategory as sc WHERE sc.name = 'Декор автомобилей'));

