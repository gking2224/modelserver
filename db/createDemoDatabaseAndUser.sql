delimiter ;

drop database if exists ${task.demoDatabaseName};

create database ${task.demoDatabaseName};

create user '${task.demoDatabaseUser}'@'%'  identified with mysql_native_password;
create user '${task.demoDatabaseUser}'@'localhost'  identified with mysql_native_password;

set password for '${task.demoDatabaseUser}'@'%' = PASSWORD('${task.demoDatabasePassword}');
set password for '${task.demoDatabaseUser}'@'localhost' = PASSWORD('${task.demoDatabasePassword}');

grant ALL on ${task.demoDatabaseName}.* to '${task.demoDatabaseUser}'@'%';
grant ALL on ${task.demoDatabaseName}.* to '${task.demoDatabaseUser}'@'localhost';

flush privileges;
