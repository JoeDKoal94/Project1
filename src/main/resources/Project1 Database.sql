-- "TRMS".employee definition

-- Drop table

-- DROP TABLE "TRMS".employee;

CREATE TABLE "TRMS".employee (
	employee_id serial NOT NULL DEFAULT nextval('"TRMS".employee_employee_id_seq'::regclass),
	firstname varchar(255) NULL,
	lastname varchar(255) NULL,
	phonenumber bpchar(10) NULL,
	address varchar(255) NULL,
	available_amount numeric(10,2) NULL,
	authority "TRMS".job NULL,
	city varchar NULL,
	state varchar NULL,
	postal_code varchar NULL,
	email_add varchar NULL,
	username varchar NULL,
	"password" varchar NULL,
	CONSTRAINT employee_pkey PRIMARY KEY (employee_id)
);


-- "TRMS".grading definition

-- Drop table

-- DROP TABLE "TRMS".grading;

CREATE TABLE "TRMS".grading (
	grading_format varchar(255) NOT NULL,
	passing_grade varchar(255) NULL,
	required_presentation bool NULL,
	employee_grade_cutoff varchar(255) NULL,
	CONSTRAINT grading_pk PRIMARY KEY (grading_format)
);


-- "TRMS".form definition

-- Drop table

-- DROP TABLE "TRMS".form;

CREATE TABLE "TRMS".form (
	form_number serial NOT NULL DEFAULT nextval('"TRMS".form_form_number_seq'::regclass),
	employee_id int4 NULL,
	event_date date NULL,
	event_time time NULL,
	event_location varchar(255) NULL,
	description text NULL,
	grading_format varchar(255) NULL,
	type_of_event varchar(255) NULL,
	attachments bytea NULL,
	projected_reimbursement numeric(10,2) NULL,
	approval_status bool NULL,
	is_urgent bool NULL,
	costs numeric(10,2) NOT NULL,
	time_posted time(0) NULL,
	CONSTRAINT form_pkey PRIMARY KEY (form_number),
	CONSTRAINT form_fk FOREIGN KEY (employee_id) REFERENCES "TRMS".employee(employee_id) ON DELETE CASCADE,
	CONSTRAINT form_fk_1 FOREIGN KEY (grading_format) REFERENCES "TRMS".grading(grading_format) ON DELETE CASCADE
);

-- Table Triggers

-- DROP TRIGGER create_form ON "TRMS".form;

create trigger create_form after
insert
    on
    "TRMS".form for each row execute function "TRMS".create_approval_form();
-- DROP TRIGGER update_funds ON "TRMS".form;

create trigger update_funds after
insert
    on
    "TRMS".form for each row execute function update_employee_funds();
-- DROP TRIGGER refund_on_delete ON "TRMS".form;

create trigger refund_on_delete before
delete
    on
    "TRMS".form for each row
    when ((old.approval_status is not true)) execute function "TRMS".refund_employee_funds();
-- DROP TRIGGER refund_on_denial ON "TRMS".form;

create trigger refund_on_denial after
update
    of approval_status on
    "TRMS".form for each row
    when ((new.approval_status is false)) execute function "TRMS".refund_employee_denial();


-- "TRMS".approval_form definition

-- Drop table

-- DROP TABLE "TRMS".approval_form;

CREATE TABLE "TRMS".approval_form (
	approval_id serial NOT NULL DEFAULT nextval('"TRMS".approval_form_approval_id_seq'::regclass),
	form_number int4 NULL,
	ds_approval bool NULL,
	dh_approval bool NULL,
	benco_approval bool NULL,
	approval_status varchar NULL,
	is_urgent bool NULL,
	amount_exceeded bool NULL,
	reason_to_exceed varchar NULL,
	consent_to_exceed bool NULL,
	amount_awarded numeric NULL,
	CONSTRAINT approval_form_pkey PRIMARY KEY (approval_id),
	CONSTRAINT approval_form_fk FOREIGN KEY (form_number) REFERENCES "TRMS".form(form_number) ON DELETE CASCADE
);

-- Table Triggers

-- DROP TRIGGER start_review ON "TRMS".approval_form;

create trigger start_review after
insert
    on
    "TRMS".approval_form for each row execute function "TRMS".insert_into_ds();
-- DROP TRIGGER exceeded_award_pass ON "TRMS".approval_form;

create trigger exceeded_award_pass after
update
    of consent_to_exceed on
    "TRMS".approval_form for each row
    when ((new.consent_to_exceed is true)) execute function "TRMS".exceeded_amount_approved();
-- DROP TRIGGER exceeded_award_declined ON "TRMS".approval_form;

create trigger exceeded_award_declined after
update
    of consent_to_exceed on
    "TRMS".approval_form for each row
    when ((new.consent_to_exceed is false)) execute function "TRMS".exceeded_amount_approved();


-- "TRMS".benco_waitlist definition

-- Drop table

-- DROP TABLE "TRMS".benco_waitlist;

CREATE TABLE "TRMS".benco_waitlist (
	priority_number serial NOT NULL DEFAULT nextval('"TRMS".benco_waitlist_priority_number_seq'::regclass),
	approval_id int4 NULL,
	ds_approval bool NULL,
	dh_approval bool NULL,
	reason_for_denial text NULL,
	add_info_req bool NULL,
	add_info_target "TRMS".job NULL,
	amount_awarded numeric(10,2) NULL,
	amount_exceeded bool NULL,
	reason_to_exceed text NULL,
	benco_approval bool NULL,
	is_urgent bool NULL,
	notify_me bool NULL,
	CONSTRAINT benco_waitlist_pkey PRIMARY KEY (priority_number),
	CONSTRAINT benco_waitlist_fk FOREIGN KEY (approval_id) REFERENCES "TRMS".approval_form(approval_id) ON DELETE CASCADE
);

-- Table Triggers

-- DROP TRIGGER add_info_benco ON "TRMS".benco_waitlist;

create trigger add_info_benco after
update
    of add_info_req on
    "TRMS".benco_waitlist for each row
    when ((new.add_info_req is true)) execute function "TRMS".notify_info();
-- DROP TRIGGER benco_approve ON "TRMS".benco_waitlist;

create trigger benco_approve after
update
    of benco_approval on
    "TRMS".benco_waitlist for each row
    when ((new.benco_approval is true)) execute function "TRMS".pass_approvalbenco();
-- DROP TRIGGER benco_decline ON "TRMS".benco_waitlist;

create trigger benco_decline after
update
    of benco_approval on
    "TRMS".benco_waitlist for each row
    when (((new.benco_approval is false)
    and (new.add_info_req is false)
    and (new.amount_exceeded is false))) execute function "TRMS".decline_approvalbenco();
-- DROP TRIGGER exceeded_award ON "TRMS".benco_waitlist;

create trigger exceeded_award after
update
    of amount_exceeded on
    "TRMS".benco_waitlist for each row
    when ((new.amount_exceeded is true)) execute function "TRMS".exceeded_amount();


-- "TRMS".dh_waitlist definition

-- Drop table

-- DROP TABLE "TRMS".dh_waitlist;

CREATE TABLE "TRMS".dh_waitlist (
	priority_number serial NOT NULL DEFAULT nextval('"TRMS".dh_waitlist_priority_number_seq'::regclass),
	approval_id int4 NULL,
	ds_approval bool NULL,
	dh_approval bool NULL,
	reason_for_denial text NULL,
	add_info_req bool NULL,
	add_info_target "TRMS".job NULL,
	isurgent bool NULL,
	CONSTRAINT dh_waitlist_pkey PRIMARY KEY (priority_number),
	CONSTRAINT dh_waitlist_fk FOREIGN KEY (approval_id) REFERENCES "TRMS".approval_form(approval_id) ON DELETE CASCADE
);

-- Table Triggers

-- DROP TRIGGER dh_approve ON "TRMS".dh_waitlist;

create trigger dh_approve after
update
    of dh_approval on
    "TRMS".dh_waitlist for each row execute function "TRMS".pass_approvaldh();
-- DROP TRIGGER add_info_dh ON "TRMS".dh_waitlist;

create trigger add_info_dh after
update
    of add_info_req on
    "TRMS".dh_waitlist for each row
    when ((new.add_info_req is true)) execute function "TRMS".notify_info_dh();


-- "TRMS".ds_waitlist definition

-- Drop table

-- DROP TABLE "TRMS".ds_waitlist;

CREATE TABLE "TRMS".ds_waitlist (
	priority_number serial NOT NULL DEFAULT nextval('"TRMS".ds_waitlist_priority_number_seq'::regclass),
	approval_id int4 NULL,
	ds_approval bool NULL,
	reason_for_denial text NULL,
	add_info_req bool NULL,
	is_urgent bool NULL,
	add_info_target varchar NULL,
	CONSTRAINT ds_waitlist_pkey PRIMARY KEY (priority_number),
	CONSTRAINT ds_waitlist_fk FOREIGN KEY (approval_id) REFERENCES "TRMS".approval_form(approval_id) ON DELETE CASCADE
);

-- Table Triggers

-- DROP TRIGGER ds_approve ON "TRMS".ds_waitlist;

create trigger ds_approve after
update
    of ds_approval on
    "TRMS".ds_waitlist for each row execute function "TRMS".pass_approvalds();
-- DROP TRIGGER add_info_ds ON "TRMS".ds_waitlist;

create trigger add_info_ds after
update
    of add_info_req on
    "TRMS".ds_waitlist for each row
    when ((new.add_info_req is true)) execute function "TRMS".notify_info();


-- "TRMS".grade_presentation_upload definition

-- Drop table

-- DROP TABLE "TRMS".grade_presentation_upload;

CREATE TABLE "TRMS".grade_presentation_upload (
	approval_id int4 NULL,
	grade varchar NULL,
	passed bool NULL,
	amountawarded bool NULL,
	CONSTRAINT grade_presentation_upload_fk FOREIGN KEY (approval_id) REFERENCES "TRMS".approval_form(approval_id)
);

CREATE OR REPLACE FUNCTION "TRMS".create_approval_form()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin 
	insert into "TRMS".approval_form (form_number, approval_status, is_urgent) values(new.form_number, 'Pending', new.is_urgent);
	return new;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".decline_approvalbenco()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin 

	update "TRMS".approval_form set benco_approval = false, approval_status = 'Denied' where approval_id = new.approval_id;

	
return new;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".exceeded_amount()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin

	update "TRMS".approval_form set amount_exceeded = new.amount_exceeded, reason_to_exceed = new.reason_to_exceed, approval_status = 'Amount awarded exceeded the projected amount, please review and consent to continue.',
	amount_awarded = new.amount_awarded where approval_id  = new.approval_id;
	return new;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".exceeded_amount_approved()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin

	update "TRMS".approval_form set approval_status = 'Granted - Amount awarded have been Modified' where approval_id  = new.approval_id;
	update "TRMS".benco_waitlist set benco_approval = true where approval_id = new.approval_id;
	update "TRMS".form set projected_reimbursement = new.amount_awarded where form_number = new.form_number;

	return new;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".exceeded_amount_decline()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin

	update "TRMS".approval_form set approval_status = 'Declined - Notified Benefits Coordinator' where approval_id  = new.approval_id;
	update "TRMS".benco_waitlist set notify_me = true where approval_id = new.approval_id;
	

	return new;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".insert_into_ds()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin 
	insert into "TRMS".ds_waitlist (approval_id, is_urgent) values(new.approval_id, new.is_urgent);
	return new;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".notify_info()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin
	
	update "TRMS".approval_form set approval_status = 'Required Additional Information, Contact DirectSupervisor' where approval_id = new.approval_id;

	return new;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".notify_info_benco()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin 
	if("TRMS".benco_waitlist.add_info_req == true) then
	update "TRMS".approval_form set approval_status = 'Required Additional Information, Contact Benefits Coordinator - ' || benco_waitlist.add_info_target  where approval_id = new.approval_id;
	end if;
	return new;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".notify_info_dh()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin
	
	update "TRMS".approval_form set approval_status = 'Required Additional Information, Contact Department Head - ' || new.add_info_target  where approval_id = new.approval_id;

	return new;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".pass_approvalbenco()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin 

	update "TRMS".approval_form set benco_approval = true, approval_status = 'Granted' where approval_id = new.approval_id;
	update "TRMS".form set approval_status = true where form_number = (select form_number from "TRMS".approval_form where approval_id = new.approval_id);

	
return new;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".pass_approvaldh()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin 
	if(new.dh_approval = true) then
	insert into "TRMS".benco_waitlist (approval_id, ds_approval, dh_approval) values(new.approval_id, true, true);
	update "TRMS".approval_form set dh_approval = true where approval_id = new.approval_id;
	end if;
	if(new.dh_approval is false and new.add_info_req is false) then
	update "TRMS".approval_form set dh_approval = false, approval_status = 'Denied' where approval_id = new.approval_id;
	end if;
return new;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".pass_approvalds()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin 
	if(new.ds_approval = true) then
	insert into "TRMS".dh_waitlist (approval_id, ds_approval) values(new.approval_id, true);
	update "TRMS".approval_form set ds_approval = true where approval_id = new.approval_id;
	end if;
	if(new.ds_approval is false and new.add_info_req is false) then
	update "TRMS".approval_form set ds_approval = false, approval_status = 'Denied' where approval_id = new.approval_id;
	end if;
return new;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".refund_employee_denial()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin

	update "TRMS".employee set available_amount = (available_amount + new.projected_reimbursement) where employee_id = new.employee_id;

	return new;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".refund_employee_funds()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin
	
	update "TRMS".employee set available_amount = (available_amount + old.projected_reimbursement) where employee_id = old.employee_id;

	return old;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".update_employee_funds()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin 
	update "TRMS".employee set available_amount = (available_amount - new.projected_reimbursement) where employee_id = new.employee_id;
	return new;
end;
$function$
;

CREATE OR REPLACE FUNCTION "TRMS".update_projected_amount()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin 
	if((select type_of_event from "TRMS".form where "TRMS".form.employee_id = new.employee_id) == 'Seminars') then
	update "TRMS".form set projected_reimbursement = (new.costs *0.60) where "TRMS".form.employee_id = new.employee_id;
	end if;
	return new;
	
return new;
end; 
$function$
;
