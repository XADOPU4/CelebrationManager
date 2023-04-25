create table Role
(
    id   bigint primary key generated always as identity,
    name text unique not null
);


create table "user"
(
    id          bigint primary key generated always as identity,
    code        text unique not null,
    login       text unique not null,
    phoneNumber text unique,
    roleId      bigint references role (id),
    password    text        not null,
    email       text unique not null,
    isActive    bool default true
);

create table UserInfo
(
    id                 bigint primary key generated always as identity,
    userId             bigint references "user" (id),
    name               text not null,
    contactPhoneNumber text unique,
    description        text,
    country            text,
    region             text,
    city               text,
    street             text,
    house              text,
    dateOfBirth        date,
    rating             decimal default 0.0,
    status             text
-- status for user means its description in profile
);

create table EventType
(
    id   bigint primary key generated always as identity,
    name text unique not null
);

create table Event
(
    id           bigint primary key generated always as identity,
    code         text unique not null,
    name         text        not null,
    eventTypeId  bigint references eventType (id),
    description  text,
    creationDate date default now(),
    actDate      date,
    endDate      date,
    status       text        not null
-- status for event means whether it is completed or declined or active
);

create table UserToEvent
(
    id          bigint primary key generated always as identity,
    userId      bigint references "user" (id),
    eventId     bigint references Event (id),
    status      text not null,
    description text
);

create table Calendar
(
    id             bigint primary key generated always as identity,
    userId         bigint references "user" (id),
    eventId        bigint references Event (id),
    busyDate       date not null,
    price          money,
    calenderStatus text not null,
    description    text
);

create table Task
(
    id           bigint primary key generated always as identity,
    authorId     bigint references "user" (id),
    assigneeId   bigint references "user" (id),
    eventId      bigint references Event (id),
    title        text,
    text         text,
    price        money   default 0.0,
    priority     decimal default 5.0,
    status       text not null,
    creationDate date    default now(),
    deadlineDate date,
    completeDate date
);

create table Note
(
    id           bigint primary key generated always as identity,
    userId       bigint references "user" (id),
    title        text,
    text         text,
    fileName     text,
    priority     decimal default 5.0,
    type         text not null,
    tag          text,
    status       text not null,
--     status for note means whether it is active or not
    creationDate date    default now()
);

create table StuffCategory
(
    id          bigint primary key generated always as identity,
    name        text not null,
    description text
);

create table Stuff
(
    id          bigint primary key generated always as identity,
    name        text not null,
    description text,
    price       money,
    categoryId  bigint references StuffCategory (id),
    fileName    text
);

create table StuffFact
(
    id         bigint primary key generated always as identity,
    stuffId    bigint references Stuff (id),
    eventId    bigint references Event (id),
    userId     bigint references "user" (id),
    quantity   decimal default 1,
    usageDate  date,
    totalPrice money,
    fileName   text
);

create table Comment
(
    id           bigint primary key generated always as identity,
    authorId     bigint references "user" (id),
    targetUserId bigint references "user" (id),
    text         text,
    rating       decimal default 5.0,
    status       text not null,
--     status for comment is approved/banned/pending
    creationDate date    default now()
);