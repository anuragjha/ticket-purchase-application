show tables;

_____________________________________________________________
create table if not exists events(
	eventID int not null auto_increment,
	eventName varchar(30) not null,
	userID int not null,
	numTickets int not null default 0,
	avail int not null default 0,
	purchased int not null default 0,
	
	primary key (eventID)
);


create table if not exists users(
	userID int not null auto_increment,
	userName varchar(30) not null,
	
	primary key (userID)

);


create table if not exists transaction(
	transactionID int not null auto_increment,
	eventID int not null,
	userID int not null,
	numTickets int not null,
	numTransfer int not null default 0,
	targetUserID int not null default 0,
	
	
	primary key (transactionID)
);


create table if not exists tickets(
	ticketID int not null auto_increment,
	eventID int not null,
	userID int not null,
	numTickets int not null,
	
	primary key (ticketID)
);

_____________________________________________________________
insert into users(username) values("Anurag");
insert into users(username) values("Manali");
insert into users(username) values("Kunal");
insert into users(username) values("Alper");
insert into users(username) values("Maruti");
insert into users(username) values("Anjali");

select * from users;

_____________________________________________________________
insert into events(eventName, userID, numTickets, avail, purchased)
	values("Alcatraz", 3, 50, 40, 0);
insert into events(eventName, userID, numTickets, avail, purchased)
	values("Twin Peaks", 3, 30, 30, 0);
insert into events(eventName, userID, numTickets, avail, purchased)
	values("Golden Gate Bridge", 2, 70, 65, 0);
insert into events(eventName, userID, numTickets, avail, purchased)
	values("Pier 39", 4, 40, 40, 0);


select * from events;

_____________________________________________________________







