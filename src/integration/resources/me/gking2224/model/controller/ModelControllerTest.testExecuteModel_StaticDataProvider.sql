
insert into model_type (model_type_id, name, enabled) values (100, "Model Type", 1);

insert into model_manifest (model_manifest_id, model_type_id, name) values (200, 100, 'Test Model');

insert into model (model_id, model_manifest_id, major_version, minor_version, script, enabled)
values (300, 200, 1, 0, '
  def rate = xferrates.getRate(\'USD\', \'GBP\')
  setOutput "testExecuteModel_result",  {
    (input.testExecuteModel_a + input.testExecuteModel_b) * rate
  }
  output.testExecuteModel_usd_gbp_rate=rate
', 1);

insert into model_nature (model_id, nature) values (300, 'groovy.shell2');
insert into model_nature (model_id, nature) values (300, 'staticdata.xferrates');


insert into model_variable (model_variable_id, name, type) values (400, 'testExecuteModel_a', 'java.lang.Integer');

insert into model_variable (model_variable_id, name, type) values (401, 'testExecuteModel_b', 'java.lang.Integer');

insert into model_variable (model_variable_id, name, type) values (402, 'testExecuteModel_result', 'java.lang.Integer');

insert into model_variable (model_variable_id, name, type) values (403, 'testExecuteModel_usd_gbp_rate', 'java.lang.Integer');

insert into model_assigned_variable (model_id, model_variable_id, direction, mandatory)
values (300, 400, 'i', 1);

insert into model_assigned_variable (model_id, model_variable_id, direction, mandatory)
values (300, 401, 'i', 1);

insert into model_assigned_variable (model_id, model_variable_id, direction, mandatory)
values (300, 402, 'o', 1);

insert into model_assigned_variable (model_id, model_variable_id, direction, mandatory)
values (300, 403, 'o', 1);
