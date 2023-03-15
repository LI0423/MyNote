
CREATE TABLE `user_ad_risk_config` (
   `id` bigint NOT NULL AUTO_INCREMENT ,
   app varchar(255),
   type int,
   model varchar(255),
   token varchar(255),
   source varchar(20),
   min_value bigint,
   max_value bigint,
   ratio double,
   create_time datetime DEFAULT NULL,
   create_by varchar(100),
   last_modify_time datetime DEFAULT NULL,
   last_modify_by varchar(100),
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE INDEX index_app_token ON user_ad_risk_config (app,type,model,token);



insert into user_ad_risk_config(app,type,source,min_value,max_value,ratio) values('com.simulation.sheep',0,'csj',5,100,0.3);
insert into user_ad_risk_config(app,type,source,min_value,max_value,ratio) values('com.simulation.sheep',0,'ks',5,100,0.3);
insert into user_ad_risk_config(app,type,source,min_value,max_value,ratio) values('com.simulation.sheep',0,'gdt',5,100,0.3);
insert into user_ad_risk_config(app,type,source,min_value,max_value,ratio) values('com.simulation.sheep',0,'sig',5,100,0.3);

insert into user_ad_risk_config(app,type,model,source,min_value,max_value,ratio) values('com.simulation.sheep',2,'M2010J19SC','sig',5,50,0.3);
insert into user_ad_risk_config(app,type,model,source,min_value,max_value,ratio) values('com.simulation.sheep',2,'M2010J19SC','csj',5,50,0.3);
insert into user_ad_risk_config(app,type,model,source,min_value,max_value,ratio) values('com.simulation.sheep',2,'M2010J19SC','ks',5,50,0.3);
insert into user_ad_risk_config(app,type,model,source,min_value,max_value,ratio) values('com.simulation.sheep',2,'M2010J19SC','gdt',5,50,0.3);

insert into user_ad_risk_config(app,type,model,source,min_value,max_value,ratio) values('com.simulation.sheep',2,'M2006C3LC','sig',5,50,0.3);
insert into user_ad_risk_config(app,type,model,source,min_value,max_value,ratio) values('com.simulation.sheep',2,'M2006C3LC','csj',5,50,0.3);
insert into user_ad_risk_config(app,type,model,source,min_value,max_value,ratio) values('com.simulation.sheep',2,'M2006C3LC','ks',5,50,0.3);
insert into user_ad_risk_config(app,type,model,source,min_value,max_value,ratio) values('com.simulation.sheep',2,'M2006C3LC','gdt',5,50,0.3);



alter table user_ad_risk_config add column warn_ctr double;
alter table user_ad_risk_config add column max_ctr double;
alter table user_ad_risk_config add column min_click int;


update user_ad_risk_config set warn_ctr=0.2;
update user_ad_risk_config set max_ctr=0.3;
update user_ad_risk_config set min_click=10;
