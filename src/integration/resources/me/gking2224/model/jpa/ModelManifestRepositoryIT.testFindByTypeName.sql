
insert into model_type (model_type_id, name, enabled) values (100, "Type 1", 1);
insert into model_type (model_type_id, name, enabled) values (101, "Type 2", 1);

insert into model_manifest (model_manifest_id, model_type_id, name) values (100, 100, "Test Model 1.1");

insert into model_manifest (model_manifest_id, model_type_id, name) values (101, 100, "Test Model 1.2");

insert into model_manifest (model_manifest_id, model_type_id, name) values (102, 101, "Test Model 2.1");
