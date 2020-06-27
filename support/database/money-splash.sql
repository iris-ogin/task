drop table gain;
drop table splash;
drop table end_user;
drop table room_talker;
drop table room;



create table room (
	id bigserial,
	uuid varchar(3) not null unique,
	primary key(id)
);


create table end_user (
	id bigserial,
	balance bigint not null default 0,
	primary key(id)
);

create table room_talker (
	id bigserial,
	room_id bigint not null,
	end_user_id bigint not null,
	active boolean not null default true,
	primary key(id)
);

CREATE UNIQUE INDEX uk_room_talker ON room_talker (room_id, end_user_id);

create table splash (
	id bigserial,
	token varchar(100) not null unique,
	splasher bigint not null references room_talker(id),
	amount bigint not null check ( amount > 0 ),
	gainer_count bigint not null check( gainer_count > 0),
	create_at timestamptz not null default current_timestamp,
	primary key(id)
);

create table gain (
	id bigserial,
	splash_id bigint not null references splash(id),
	gain_amount bigint not null check(gain_amount > 0 ),
	occupied boolean default false ,
	gainer bigint references room_talker(id),
	gain_at timestamptz default current_timestamp,
	primary key(id)
);

CREATE UNIQUE INDEX uk_gain_splash_id_gainer ON gain (splash_id, gainer) WHERE gain_at IS not NULL;

insert into room (uuid) values('abc');
insert into end_user(balance ) values(10000);
insert into end_user(balance ) values(0);
insert into room_talker(room_id, end_user_id) values(1, 1);
insert into room_talker(room_id, end_user_id) values(1, 2);



CREATE OR REPLACE FUNCTION fn_when_create_splash()
returns trigger
AS $$
DECLARE
BEGIN
    update end_user
    set balance = balance - new.amount
    where id = ( select end_user.id
    	from splash
    	join room_talker on (splash.splasher = room_talker.id and room_talker.active=true)
    	join end_user on ( room_talker.end_user_id = end_user.id)
    	where splash.id = new.id
    );
    return NULL;
END; $$
LANGUAGE 'plpgsql';

create trigger tr_when_create_splash
after insert on splash
for each row
execute procedure fn_when_create_splash();

CREATE OR REPLACE FUNCTION fn_when_gain_splash()
returns trigger
AS $$
DECLARE
BEGIN
    update end_user
    set balance = balance + new.gain_amount
    where id = ( select end_user.id
    	from gain
    	join room_talker on (gain.gainer = room_talker.id and room_talker.active=true)
    	join end_user on ( room_talker.end_user_id = end_user.id)
    	where gain.id = new.id
    );
    return NULL;
END; $$
LANGUAGE 'plpgsql';

create trigger tr_when_gain_splash
after update on gain
for each row
execute procedure fn_when_gain_splash();