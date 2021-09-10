create table product_service
( id int unsigned auto_increment primary key,
  product_name varchar (255) charset utf8,
  single_price decimal(12,2) default 0.00 null,
  is_deleted tinyint(1) default 0 null
);

create table users
( id int unsigned auto_increment primary key,
  first_name varchar(255) charset utf8,
  second_name varchar(255) charset utf8,
  email varchar(255) charset utf8,
  login varchar(255) charset utf8,
  password varchar(255) charset utf8,
  is_deleted tinyint(1) default 0 null
);

create table payment_master
( id int unsigned auto_increment primary key,
  total_price decimal(12,2) default 0.00 null,
  user_id int(11) unsigned null,
  payment_name varchar(255) charset utf8,
  createdate datetime default CURRENT_TIMESTAMP not null,
  is_deleted tinyint(1) default 0 null,
  constraint fk_pm_users
    foreign key (user_id) references rentcalculator.users (id)
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
  is_deleted tinyint(1) default 0 null,
  constraint fk_pp_paymentmaster
    foreign key (payment_master_id) references rentcalculator.payment_master (id),
  constraint fk_pp_productservice
    foreign key (product_id) references rentcalculator.product_service (id)
);
create index fk_pp_paymentmaster_idx
    on payment_price (payment_master_id);
create index fk_pp_product_idx
    on payment_price (product_id);