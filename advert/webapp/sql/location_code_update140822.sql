create table t_location_code_bak
as select * from t_location_code;

update t_location_code t set t.LOCATIONCODE='21111' where t.LOCATIONCODE='152060000000';
update t_location_code t set t.LOCATIONCODE='21222' where t.LOCATIONCODE='152070000000';
update t_location_code t set t.LOCATIONCODE='21333' where t.LOCATIONCODE='152080000000';

update t_location_code t set t.LOCATIONCODE='152080000000' where t.LOCATIONCODE='21111';
update t_location_code t set t.LOCATIONCODE='152060000000' where t.LOCATIONCODE='21222';
update t_location_code t set t.LOCATIONCODE='152070000000' where t.LOCATIONCODE='21333';

update t_location_code t set t.PARENTLOCATION='31111' where t.PARENTLOCATION='152060000000';
update t_location_code t set t.PARENTLOCATION='31222' where t.PARENTLOCATION='152070000000';
update t_location_code t set t.PARENTLOCATION='31333' where t.PARENTLOCATION='152080000000';

update t_location_code t set t.PARENTLOCATION='152080000000' where t.PARENTLOCATION='31111';
update t_location_code t set t.PARENTLOCATION='152060000000' where t.PARENTLOCATION='31222';
update t_location_code t set t.PARENTLOCATION='152070000000' where t.PARENTLOCATION='31333';