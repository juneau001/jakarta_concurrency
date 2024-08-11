/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  juneau
 * Created: Apr 4, 2020
 * Updated: Apr 20, 2024 (PostgreSQL dialect)
 */

create table roster (
                        id  numeric primary key,
                        enter_date date,
                        first_name varchar(50),
                        last_name varchar(50),
                        position varchar(50),
                        team_id numeric
);

create table team (
                      id numeric primary key,
                      name varchar(100)
);

CREATE SEQUENCE ROSTER_S AS INTEGER START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE TEAM_S AS INTEGER START WITH 1 INCREMENT BY 1;

insert into team values (
nextval('TEAM_S'),
'CODERS'
);

insert into roster values(
nextval('roster_s'),
'2020-04-01',
'JOSH',
'JUNEAU',
'FORWARD');

insert into roster values(
nextval('roster_s'),
'2020-03-01',
'DUKE',
'JAVA',
'GOALIE');

insert into roster values(
nextval('roster_s'),
'2020-03-01',
'CARL',
'QUINN',
'CAPTAIN');