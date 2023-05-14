create table Specification
(
    id          bigserial primary key,
    name        text not null unique,
    roleId      bigint references role (id),
    description text,
    isActive    bool default true
);

create table UserInfoSpecification(
    id bigserial primary key,
    userInfoId bigint references userinfo(id),
    specId bigint references Specification(id),
    isActive bool default true
);