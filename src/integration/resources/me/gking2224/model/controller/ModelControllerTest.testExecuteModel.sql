
insert into model_type (model_type_id, name, enabled) values (100, "Model Type", 1);

insert into model_manifest (model_manifest_id, model_type_id, name) values (200, 100, 'Test Model');

insert into model (model_id, model_manifest_id, major_version, minor_version, script, enabled)
values (300, 200, 1, 0, 'def executeModel() {
  out.write(input.toString())
  output.testExecuteModel_result = input.testExecuteModel_a + input.testExecuteModel_b
}', 1);

insert into model_nature (model_id, nature) values (300, 'me.gking2224.model.execution.groovy.shell');


insert into model_variable (model_variable_id, name, type) values (400, 'testExecuteModel_a', 'java.lang.Integer');

insert into model_variable (model_variable_id, name, type) values (401, 'testExecuteModel_b', 'java.lang.Integer');

insert into model_variable (model_variable_id, name, type) values (402, 'testExecuteModel_result', 'java.lang.Integer');

insert into model_variable (model_variable_id, name, type) values (403, 'testExecuteModel_optionalout', 'java.lang.Integer');

insert into model_variable (model_variable_id, name, type) values (404, 'testExecuteModel_optionalin', 'java.lang.Integer');

insert into model_assigned_variable (model_id, model_variable_id, direction, mandatory)
values (300, 400, 'i', 1);

insert into model_assigned_variable (model_id, model_variable_id, direction, mandatory)
values (300, 401, 'i', 1);

insert into model_assigned_variable (model_id, model_variable_id, direction, mandatory)
values (300, 402, 'o', 1);

insert into model_assigned_variable (model_id, model_variable_id, direction, mandatory)
values (300, 403, 'o', 0);

insert into model_assigned_variable (model_id, model_variable_id, direction, mandatory)
values (300, 404, 'i', 0);
