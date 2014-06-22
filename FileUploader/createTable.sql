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
	
create table USER_FILE_MAPPINGS (
	id int not null auto_increment primary key,
	userId int,
	foreign key(userId) references USERS(userId)	
);

create table FILE_EXPENSE_ENTRIES (
	expenseEntryId int not null auto_increment primary key,
	fileId int not null,
	expenseDate DATE not null,
	category varchar(100) not null,
	employeeName varchar (50) not null,
	employeeAddress varchar (200) not null,
	expenseDescription varchar(50) not null,
	preTaxAmount NUMERIC(10,2) not null,
	taxName varchar(50) not null,
	taxAmount NUMERIC(7,2) not null,
	foreign key(fileId) references USER_FILE_MAPPINGS(id)
);