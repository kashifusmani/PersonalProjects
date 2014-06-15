create table USERS (
	userId int not null auto_increment primary key,
	email  varchar(40) not null,
	firstname varchar(40) ,
	lastname varchar(40) ,	
	registrationType varchar(20) not null
	);
	
create table AUTHENTICATION (
	userId int ,
	pwdhash varchar(100) not null,
	primary key (userId),
	foreign key (userId) references USERS(userId)
	);
	
create table CONTACTS (
	contactId int not null auto_increment primary key,
	userId int,
	firstname varchar(40) not null,
	lastname varchar(40) not null,
	phone long not null,
	foreign key(userId) references USERS(userId)	
);