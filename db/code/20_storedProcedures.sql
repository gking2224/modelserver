drop procedure if exists ${task.databaseName}.create_user;

DELIMITER ::
CREATE PROCEDURE `create_user` (in i_name varchar(255),
in i_email varchar(255))
BEGIN

    insert into user (name, email) values (i_name, i_email);
END::

DELIMITER ;


drop procedure if exists ${task.databaseName}.find_user_by_name;

DELIMITER ::
CREATE PROCEDURE `find_user_by_name` (in i_name varchar(255))
BEGIN
  select user_id, name, email from user where name=i_name;
END::

DELIMITER ;


drop procedure if exists ${task.databaseName}.get_user_payments;

DELIMITER ::
CREATE PROCEDURE `get_user_payments` (in i_user_id int(8))
BEGIN
    
    select payment_id, user_id, trx_type, pmt_amount, pmt_currency from payments
    where user_id=i_user_id;
    
    select user_id, name, email from user where user_id=i_user_id;
    
END::

DELIMITER ;

drop procedure if exists ${task.databaseName}.get_customer_by_id;

DELIMITER ::
CREATE PROCEDURE `get_customer_by_id` (in i_customer_id int(8))
BEGIN
    
    select customer_id, first_name, surname, country_code, registration_date from customer
    where customer_id=i_customer_id;
    
END::

DELIMITER ;



drop procedure if exists ${task.databaseName}.get_next_version;
DELIMITER ::
CREATE PROCEDURE `get_next_version` (
  in i_model_type varchar(20)
    , in i_model_name varchar(20)
    , in i_inc_type bit
)
BEGIN
    DECLARE l_max_major int(2);
    DECLARE l_next_major int(2);
    DECLARE l_next_minor int(2);
    
  select max(m.major_version)
  from model m
  left outer join model_manifest mm on mm.model_manifest_id=m.model_manifest_id
  left outer join model_type mt on mm.model_type_id=mt.model_type_id
    where mt.name=i_model_type
    and mm.name=i_model_name
    into l_max_major;

  IF (select l_max_major IS NULL)
    then select 0 into l_max_major;
  END IF;
    
  IF i_inc_type=1
    then
    select l_max_major + 1, 0 into l_next_major, l_next_minor;
  ELSE
    select max(m.minor_version)+1, l_max_major
    from model m
    left outer join model_manifest mm on mm.model_manifest_id=m.model_manifest_id
    left outer join model_type mt on mm.model_type_id=mt.model_type_id
    where mt.name=i_model_type
    and mm.name=i_model_name
    into l_next_minor, l_next_major;
  END IF;

  IF (select l_next_minor IS NULL)
    then select 1 into l_next_minor;
  END IF;
  
  select l_next_major as o_major_version, l_next_minor as o_minor_version;
END::

DELIMITER ;

