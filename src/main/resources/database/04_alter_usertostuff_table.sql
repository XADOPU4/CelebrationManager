alter table usertostuff
    add column quantity decimal check ( quantity >= 0.0);