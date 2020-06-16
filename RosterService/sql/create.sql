/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  juneau
 * Created: Apr 4, 2020
 */

CREATE SEQUENCE ROSTER_S AS INTEGER START WITH 1 INCREMENT BY 1;

insert into roster values(
next value for roster_s,
'2020-04-01',
'JOSH',
'JUNEAU',
'FORWARD');

insert into roster values(
next value for roster_s,
'2020-03-01',
'DUKE',
'JAVA',
'GOALIE');

insert into roster values(
next value for roster_s,
'2020-03-01',
'CARL',
'QUINN',
'CAPTAIN');