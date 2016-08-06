
insert into model_type (model_type_id, name, enabled) values (100, "Type 1", 1);

insert into model_manifest (model_manifest_id, model_type_id, name) values (100, 100, "Test Model 1.1");

insert into model (model_id, model_manifest_id, major_version, minor_version, enabled) values (100, 100, 1, 3, 1);
