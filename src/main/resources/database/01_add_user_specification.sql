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
    id                      bigint primary key generated always as identity,
    userInfoSpecificationId bigint references userinfospecification (id),
    eventId                 bigint references Event (id),
    busyDate                date not null,
    hours                   decimal default 1.0,
    price                   money,
    calenderStatus          text not null,
    description             text
);