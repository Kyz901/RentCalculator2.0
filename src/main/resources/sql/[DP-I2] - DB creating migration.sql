create table user_roles
( id int unsigned auto_increment primary key,
  name varchar(255) charset utf8
);

create table product_service
( id int unsigned auto_increment primary key,
  product_name varchar (255) charset utf8,
  single_price decimal(12,2) default 0.00 null,
  is_deleted tinyint(1) default 0 not null
);

create table users
( id int unsigned auto_increment primary key,
  login varchar(255) charset utf8,
  password varchar(255) charset utf8,
  role_id int(11) unsigned null,
  first_name varchar(255) charset utf8,
  second_name varchar(255) charset utf8,
  email varchar(255) charset utf8,
  user_id int(11) unsigned null,
  is_deleted tinyint(1) default 0 not null,
  is_active tinyint(1) default 1 not null,
  constraint fk_users_roles
      foreign key (role_id) references rentcalculator.user_roles (id)
          ON UPDATE CASCADE ON DELETE RESTRICT
);
create unique index fk_users_login_idx
    on users (login);

create table payment_master
( id int unsigned auto_increment primary key,
  total_price decimal(12,2) default 0.00 null,
  user_id int(11) unsigned null,
  payment_name varchar(255) charset utf8,
  createdate datetime default CURRENT_TIMESTAMP not null,
  is_deleted tinyint(1) default 0 not null,
  constraint fk_pm_users
      foreign key (user_id) references rentcalculator.users (id)
          ON UPDATE CASCADE ON DELETE RESTRICT
);
create index fk_pm_users_idx
    on payment_master (user_id);

create table payment_price
(
    id int unsigned auto_increment primary key,
    payment_master_id int(11) unsigned null,
    old_meter_readings int unsigned not null,
    new_meter_readings int unsigned not null,
    product_id int(11) unsigned null,
    price decimal(12,2) default 0.00 null,
    createdate datetime default CURRENT_TIMESTAMP not null,
    is_deleted tinyint(1) default 0 not null,
    constraint fk_pp_paymentmaster
        foreign key (payment_master_id) references rentcalculator.payment_master (id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    constraint fk_pp_productservice
        foreign key (product_id) references rentcalculator.product_service (id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);
create index fk_pp_paymentmaster_idx
    on payment_price (payment_master_id);
create index fk_pp_product_idx
    on payment_price (product_id);

insert into user_roles(name) values ('ROLE_ADMIN');
insert into user_roles(name) values ('ROLE_USER');
