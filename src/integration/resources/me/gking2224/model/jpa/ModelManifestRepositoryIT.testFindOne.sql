
insert into model_type (model_type_id, name, enabled) values (100, "Default Type", 1);

insert into model_manifest (model_manifest_id, model_type_id, name) values (100, 100, "Test Model");

insert into model (model_id, model_manifest_id, major_version, minor_version, enabled) values (100, 100, 1, 0, 1);

insert into model (model_id, model_manifest_id, major_version, minor_version, enabled) values (101, 100, 1, 1, 1);

insert into model (model_id, model_manifest_id, major_version, minor_version, enabled) values (102, 100, 2, 0, 1);

insert into model_nature (model_id, nature) values (100, "me.gking2224.modelserver.DefaultNature");
