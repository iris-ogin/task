drop table gain;
drop table splash;
drop table end_user;



create table end_user (
	id bigserial,
	room_id varchar(100) not null,
	balance bigint not null default 0,
	primary key(id)
);

create table splash (
	id bigserial,
	token varchar(3) not null unique,
	splasher bigint not null references end_user(id),
	amount bigint not null check ( amount > 0 ),
	gainer_count bigint not null check( gainer_count > 0),
	create_at timestamptz not null default current_timestamp,
	primary key(id)
);

create table gain (
	id bigserial,
	splash_id bigint not null references splash(id),
	gain_amount bigint not null check(gain_amount > 0 ),
	gainer bigint references end_user(id),
	gain_at timestamptz default current_timestamp,
	primary key(id)
);

CREATE UNIQUE INDEX uk_gain_splash_id_gainer ON gain (splash_id, gainer) WHERE gain_at IS not NULL;
splash_id is null && gainer is null or splash_id is not null and gainer is not null



-- trigger when_spalsh
-- trigger when_get

CREATE FUNCTION distribute_money() RETURNS trigger AS $distribute_money$
    declare
    	res INTEGER :=0;
    	count INTEGER;
	BEGIN
        count :=0;
       	loop
       		insert into
       	end loop;
        RETURN NEW;
    END;
$distribute_money$ LANGUAGE plpgsql;

CREATE TRIGGER when_spalsh BEFORE INSERT ON splash
    FOR EACH ROW EXECUTE PROCEDURE distribute_money();