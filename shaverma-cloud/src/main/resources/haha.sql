create table if not exists Shaverma_Order(
    id identity,
    delivery_Name varchar(50) not null,
    delivery_Street varchar(50) not null,
    delivery_City varchar(50) not null,
    delivery_State varchar(2) not null,
    delivery_Zip varchar(10) not null,
    cc_number varchar(16) not null,
    cc_expiration varchar(5) not null,
    cc_cvv varchar(3) not null,
    placed_at timestamp not null
);

create table if not  exists Shaverma(
    id identity,
    name varchar(50) not null,
    shaverma_order bigint not null,
    shaverma_order_key bigint not null,
    created_at timestamp not null   
);

create table if not exists Ingredient_Ref(
    ingredient varchar(4) not null,
    shaverma bigint not null,
    shave_key bigint not null
);

create table if not exists Ingredient(
    id varchar(4) PRIMARY KEY ,
    name varchar(25) not null,
    type varchar(25) not null
);

alter table Shaverma
    add foreign key (shaverma_order) references Shaverma_Order(id);
alter table Ingredient_Ref
    add foreign key (ingredient) references Ingredient(id);