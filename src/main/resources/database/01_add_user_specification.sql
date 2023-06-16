create table Specification
(
    id          bigserial primary key,
    name        text not null unique,
    roleId      bigint references role (id),
    eventTypeId bigint references eventtype (id),
    description text,
    isActive    bool default true
);

create table UserInfoSpecification
(
    id         bigserial primary key,
    userInfoId bigint references userinfo (id),
    specId     bigint references Specification (id),
    isActive   bool default true
);

create table Calendar
(
    id              bigint primary key generated always as identity,
    userInfoId      bigint references userinfo (id),
    specificationId bigint references Specification (id),
    eventId         bigint references Event (id),
    busyDate        date not null,
    hours           decimal default 1.0,
    price           decimal,
    calenderStatus  text not null,
    description     text
);