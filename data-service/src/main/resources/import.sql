INSERT INTO routes (name, mode) VALUES ('1', 'TRAMWAY'), ('3', 'TRAMWAY'), ('4', 'TRAMWAY'), ('7', 'TRAMWAY'), ('9', 'TRAMWAY'), ('X6', 'TRAMWAY');
INSERT INTO routes (name, mode) VALUES ('33', 'TROLLEYBUS'), ('64', 'TROLLEYBUS'), ('201', 'TROLLEYBUS'), ('202', 'TROLLEYBUS'), ('203', 'TROLLEYBUS');
INSERT INTO routes (name, mode) VALUES ('204', 'TROLLEYBUS'), ('205', 'TROLLEYBUS'), ('207', 'TROLLEYBUS'), ('208', 'TROLLEYBUS'), ('209', 'TROLLEYBUS');
INSERT INTO routes (name, mode) VALUES ('210', 'TROLLEYBUS'), ('211', 'TROLLEYBUS'), ('212', 'TROLLEYBUS'), ('20', 'BUS'), ('21', 'BUS'), ('23', 'BUS');
INSERT INTO routes (name, mode) VALUES ('25', 'BUS'), ('26', 'BUS'), ('27', 'BUS'), ('29', 'BUS'), ('30', 'BUS'), ('31', 'BUS'), ('32', 'BUS'), ('35', 'BUS');
INSERT INTO routes (name, mode) VALUES ('36', 'BUS'), ('37', 'BUS'), ('39', 'BUS'), ('41', 'BUS'), ('43', 'BUS'), ('44', 'BUS'), ('50', 'BUS'), ('51', 'BUS');
INSERT INTO routes (name, mode) VALUES ('52', 'BUS'), ('53', 'BUS'), ('56', 'BUS'), ('57', 'BUS'), ('58', 'BUS'), ('59', 'BUS'), ('61', 'BUS'), ('63', 'BUS');
INSERT INTO routes (name, mode) VALUES ('65', 'BUS'), ('66', 'BUS'), ('67', 'BUS'), ('68', 'BUS'), ('69', 'BUS'), ('70', 'BUS'), ('74', 'BUS'), ('75', 'BUS');
INSERT INTO routes (name, mode) VALUES ('77', 'BUS'), ('78', 'BUS'), ('79', 'BUS'), ('80', 'BUS'), ('83', 'BUS'), ('84', 'BUS'), ('87', 'BUS'), ('88', 'BUS');
INSERT INTO routes (name, mode) VALUES ('90', 'BUS'), ('91', 'BUS'), ('92', 'BUS'), ('93', 'BUS'), ('94', 'BUS'), ('95', 'BUS'), ('96', 'BUS'), ('98', 'BUS');
INSERT INTO routes (name, mode) VALUES ('99', 'BUS'), ('123', 'BUS'), ('130', 'BUS'), ('131', 'BUS'), ('133', 'BUS'), ('139', 'BUS'), ('144', 'BUS');
INSERT INTO routes (name, mode) VALUES ('147', 'BUS'), ('151', 'BUS'), ('184', 'BUS'), ('191', 'BUS'), ('192', 'BUS'), ('196', 'BUS'), ('X5', 'BUS');
INSERT INTO routes (name, mode) VALUES ('X72', 'BUS'), ('801', 'BUS'), ('809', 'BUS'), ('N21', 'BUS'), ('N29', 'BUS'), ('N31', 'BUS'), ('N33', 'BUS');
INSERT INTO routes (name, mode) VALUES ('N34', 'BUS'), ('N37', 'BUS'), ('N44', 'BUS'), ('N47', 'BUS'), ('N53', 'BUS'), ('N56', 'BUS'), ('N61', 'BUS');
INSERT INTO routes (name, mode) VALUES ('N70', 'BUS'), ('N72', 'BUS'), ('N74', 'BUS'), ('N80', 'BUS'), ('N91', 'BUS'), ('N93', 'BUS'), ('N95', 'BUS'), ('N99', 'BUS');


INSERT INTO stops (name) VALUES ('Hlavná stanica'), ('Nám. Franza Liszta'), ('Úrad vlády SR'), ('STU'), ('Vysoká');
INSERT INTO stops (name) VALUES ('Tchibo Outlet'), ('Poštová'), ('Martinus'), ('Námestie SNP'), ('Šafárikovo nám.');
INSERT INTO stops (name) VALUES ('Sad J. Kráľa'), ('Divadlo Aréna'), ('Farského'), ('Jungmannova');
INSERT INTO stops (name) VALUES ('Komisárky'), ('Púchovská'), ('Záhumenice, Drevona'), ('Cintorín Rača'), ('Hybešova'), ('Hečkova'), ('Černockého');
INSERT INTO stops (name) VALUES ('Pekná cesta'), ('Malé Krasňany'), ('Vozovňa Krasňany'), ('ŽST Vinohrady'), ('Nám. Biely kríž');
INSERT INTO stops (name) VALUES ('Mladá garda'), ('Riazanská'), ('Pionierska'), ('Ursínyho'), ('Račianske mýto'), ('Blumentál');
INSERT INTO stops (name) VALUES ('Americké nám.'), ('Mariánska'), ('Kamenné nám.'), ('Chatam Sófer'), ('Kapucínska'), ('Krížna'), ('Trnavské mýto');
INSERT INTO stops (name) VALUES ('Odbojárov'), ('Nová doba'), ('Polus City Center'), ('Zátišie'), ('ŽST Nové Mesto'), ('Odborárska'), ('Magnetová');
INSERT INTO stops (name) VALUES ('Jurajov dvor'), ('PPA Controll'), ('Shopping Palace'), ('Zlaté piesky'), ('Saleziáni'), ('Slovanet'), ('Nemocnica Ružinov');
INSERT INTO stops (name) VALUES ('Herlianska'), ('Tomášikova'), ('Súmračná'), ('Chlumeckého'), ('Astronomická'), ('Molecova'), ('Botanická záhrada');
INSERT INTO stops (name) VALUES ('Lafranconi'), ('Kráľovské údolie'), ('Most SNP');

UPDATE stops SET zone=101 WHERE name IN ('Vozovňa Krasňany', 'Malé Krasňany', 'Pekná cesta', 'Komisárky', 'Púchovská', 'Záhumenice, Drevona', 'Cintorín Rača', 'Hybešova', 'Hečkova', 'Černockého', 'PPA Controll', 'Shopping Palace', 'Zlaté piesky');
UPDATE stops SET on_request=true WHERE name IN ('Vozovňa Krasňany', 'Malé Krasňany');

-- Elektricka1 - 1 (1-11 WD)
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 1); --1
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (1, 1, '04:44'), (1, 2, '04:45'), (1, 3, '04:46'), (1, 4, '04:48');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 1); --2
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (2, 1, '04:51'), (2, 2, '04:52'), (2, 3, '04:53'), (2, 4, '04:55');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 1); --3
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (3, 1, '04:59'), (3, 2, '05:00'), (3, 3, '05:01'), (3, 4, '05:03');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 1); --4
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (4, 1, '05:06'), (4, 2, '05:07'), (4, 3, '05:08'), (4, 4, '05:10');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 1); --5
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (5, 1, '05:14'), (5, 2, '05:15'), (5, 3, '05:16'), (5, 4, '05:18');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 1); --6
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (6, 1, '05:21'), (6, 2, '05:22'), (6, 3, '05:23'), (6, 4, '05:25');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 1); --7
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (7, 1, '05:29'), (7, 2, '05:30'), (7, 3, '05:31'), (7, 4, '05:33');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 1); --8
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (8, 1, '05:36'), (8, 2, '05:37'), (8, 3, '05:38'), (8, 4, '05:40');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 1); --9
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (9, 1, '05:44'), (9, 2, '05:45'), (9, 3, '05:46'), (9, 4, '05:48');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 1); --10
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (10, 1, '05:51'), (10, 2, '05:52'), (10, 3, '05:53'), (10, 4, '05:55');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 1); --
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (11, 1, '05:59'), (11, 2, '06:00'), (11, 3, '06:01'), (11, 4, '06:03');

-- Elektricka1 - 1 (1 - 22 WE)
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 1); --12
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (12, 1, '04:44'), (12, 2, '04:45'), (12, 3, '04:46'), (12, 4, '04:48');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 1); --13
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (13, 1, '04:51'), (13, 2, '04:52'), (13, 3, '04:53'), (13, 4, '04:55');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 1); --14
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (14, 1, '04:59'), (14, 2, '05:00'), (14, 3, '05:01'), (14, 4, '05:03');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 1); --15
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (15, 1, '05:06'), (15, 2, '05:07'), (15, 3, '05:08'), (15, 4, '05:10');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 1); --16
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (16, 1, '05:14'), (16, 2, '05:15'), (16, 3, '05:16'), (16, 4, '05:18');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 1); --17
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (17, 1, '05:21'), (17, 2, '05:22'), (17, 3, '05:23'), (17, 4, '05:25');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 1); --18
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (18, 1, '05:29'), (18, 2, '05:30'), (18, 3, '05:31'), (18, 4, '05:33');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 1); --19
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (19, 1, '05:36'), (19, 2, '05:37'), (19, 3, '05:38'), (19, 4, '05:40');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 1); --20
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (20, 1, '05:44'), (20, 2, '05:45'), (20, 3, '05:46'), (20, 4, '05:48');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 1); --21
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (21, 1, '05:51'), (21, 2, '05:52'), (21, 3, '05:53'), (21, 4, '05:55');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 1); --22
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (22, 1, '05:59'), (22, 2, '06:00'), (22, 3, '06:01'), (22, 4, '06:03');

-- Elektricka7 - 4 (23 - 35 WD)
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 4); --23
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (23, 3, '04:41'), (23, 32, '04:44'), (23, 31, '04:46');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 4); --24
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (24, 3, '04:49'), (24, 32, '04:52'), (24, 31, '04:55');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 4); --25
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (25, 3, '04:56'), (25, 32, '04:59'), (25, 31, '05:01');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 4); --26
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (26, 3, '05:04'), (26, 32, '05:07'), (26, 31, '05:09');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 4); --27
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (27, 3, '05:11'), (27, 32, '05:14'), (27, 31, '05:16');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 4); --28
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (28, 3, '05:19'), (28, 32, '05:22'), (28, 31, '05:24');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 4); --29
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (29, 3, '05:26'), (29, 32, '05:29'), (29, 31, '05:31');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 4); --30
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (30, 3, '05:34'), (30, 32, '05:37'), (30, 31, '05:40');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 4); --31
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (31, 3, '05:39'), (31, 32, '05:42'), (31, 31, '05:45');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 4); --32
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (32, 3, '05:44'), (32, 32, '05:47'), (32, 31, '05:50');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 4); --33
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (33, 3, '05:49'), (33, 32, '05:52'), (33, 31, '05:55');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 4); --34
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (34, 3, '05:54'), (34, 32, '05:57'), (34, 31, '06:00');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 4); --35
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (35, 3, '05:59'), (35, 32, '06:02'), (35, 31, '06:05');

-- Elektricka3 - 2 (36 - 54 WD)
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 2); --36
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (36, 32, '05:02'), (36, 33, '05:04'), (36, 34, '05:05');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 2); --37
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (37, 32, '05:11'), (37, 33, '05:13'), (37, 34, '05:14');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 2); --38
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (38, 32, '05:18'), (38, 33, '05:20'), (38, 34, '05:21');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 2); --39
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (39, 32, '05:25'), (39, 33, '05:27'), (39, 34, '05:28');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 2); --40
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (40, 32, '05:32'), (40, 33, '05:34'), (40, 34, '05:35');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 2); --41
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (41, 32, '05:40'), (41, 33, '05:42'), (41, 34, '05:43');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 2); --42
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (42, 32, '05:47'), (42, 33, '05:49'), (42, 34, '05:50');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 2); --43
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (43, 32, '05:55'), (43, 33, '05:57'), (43, 34, '05:58');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 2); --44
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (44, 32, '06:02'), (44, 33, '06:04'), (44, 34, '06:05');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 2); --45
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (45, 32, '06:10'), (45, 33, '06:12'), (45, 34, '06:13');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 2); --46
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (46, 32, '06:15'), (46, 33, '06:17'), (46, 34, '06:18');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 2); --47
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (47, 32, '06:20'), (47, 33, '06:22'), (47, 34, '06:23');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 2); --48
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (48, 32, '06:25'), (48, 33, '06:27'), (48, 34, '06:28');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 2); --49
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (49, 32, '06:30'), (49, 33, '06:32'), (49, 34, '06:34');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 2); --50
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (50, 32, '06:36'), (50, 33, '06:38'), (50, 34, '06:40');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 2); --51
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (51, 32, '06:41'), (51, 33, '06:43'), (51, 34, '06:45');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 2); --52
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (52, 32, '06:46'), (52, 33, '06:48'), (52, 34, '06:50');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'WORKING_DAY', 2); --53
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (53, 32, '06:51'), (53, 33, '06:53'), (53, 34, '06:55');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'WORKING_DAY', 2); --54
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (54, 32, '06:56'), (54, 33, '06:58'), (54, 34, '07:00');

-- Elektricka3 - 2 (55 - 70 WE)
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'FREE_DAY', 2); --55
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (55, 32, '05:02'), (55, 33, '05:04'), (55, 34, '05:05');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 2); --56
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (56, 32, '05:10'), (56, 33, '05:12'), (56, 34, '05:13');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'FREE_DAY', 2); --57
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (57, 32, '05:17'), (57, 33, '05:19'), (57, 34, '05:20');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 2); --58
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (58, 32, '05:25'), (58, 33, '05:27'), (58, 34, '05:28');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'FREE_DAY', 2); --59
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (59, 32, '05:32'), (59, 33, '05:34'), (59, 34, '05:35');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 2); --60
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (60, 32, '05:40'), (60, 33, '05:42'), (60, 34, '05:43');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'FREE_DAY', 2); --61
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (61, 32, '05:47'), (61, 33, '05:49'), (61, 34, '05:50');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 2); --62
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (62, 32, '05:55'), (62, 33, '05:57'), (62, 34, '05:58');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'FREE_DAY', 2); --63
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (63, 32, '06:02'), (63, 33, '06:04'), (63, 34, '06:05');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 2); --64
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (64, 32, '06:10'), (64, 33, '06:12'), (64, 34, '06:13');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'FREE_DAY', 2); --65
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (65, 32, '06:17'), (65, 33, '06:19'), (65, 34, '06:20');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'FREE_DAY', 2); --66
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (66, 32, '06:25'), (66, 33, '06:27'), (66, 34, '06:28');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 2); --67
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (67, 32, '06:32'), (67, 33, '06:34'), (67, 34, '06:35');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'FREE_DAY', 2); --68
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (68, 32, '06:40'), (68, 33, '06:43'), (68, 34, '06:44');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (true, 'FREE_DAY', 2); --69
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (69, 32, '06:47'), (69, 33, '06:50'), (69, 34, '06:51');
INSERT INTO trips (low_floor, day_type, route_id) VALUES (false, 'FREE_DAY', 2); --70
INSERT INTO stop_times (trip_id, stop_id, time) VALUES (70, 32, '06:55'), (70, 33, '06:57'), (70, 34, '06:58');

INSERT INTO holiday (date, type) VALUES ('2019-01-01', 'NATIONAL'),('2019-01-06', 'NATIONAL'),('2019-04-19', 'NATIONAL');
INSERT INTO holiday (date, type) VALUES ('2019-04-22', 'NATIONAL'),('2019-05-01', 'NATIONAL'),('2019-05-08', 'NATIONAL');
INSERT INTO holiday (date, type) VALUES ('2019-05-08', 'NATIONAL'),('2019-07-05', 'NATIONAL'),('2019-08-29', 'NATIONAL');
INSERT INTO holiday (date, type) VALUES ('2019-09-01', 'NATIONAL'),('2019-09-15', 'NATIONAL'),('2019-11-01', 'NATIONAL');
INSERT INTO holiday (date, type) VALUES ('2019-11-17', 'NATIONAL'),('2019-12-24', 'NATIONAL'),('2019-12-25', 'NATIONAL');
INSERT INTO holiday (date, type) VALUES ('2019-12-26', 'NATIONAL'),('2020-01-01', 'NATIONAL'),('2020-01-06', 'NATIONAL');
INSERT INTO holiday (date, type) VALUES ('2020-04-10', 'NATIONAL'),('2020-04-13', 'NATIONAL'),('2020-05-01', 'NATIONAL');
INSERT INTO holiday (date, type) VALUES ('2020-05-08', 'NATIONAL'),('2020-07-05', 'NATIONAL'),('2020-08-29', 'NATIONAL');
INSERT INTO holiday (date, type) VALUES ('2020-09-01', 'NATIONAL'),('2020-09-15', 'NATIONAL'),('2020-11-01', 'NATIONAL');
INSERT INTO holiday (date, type) VALUES ('2020-11-17', 'NATIONAL'),('2020-12-24', 'NATIONAL'),('2020-12-25', 'NATIONAL');
INSERT INTO holiday (date, type) VALUES ('2020-12-26', 'NATIONAL');

INSERT INTO holiday (date, type) VALUES ('2019-10-30', 'SCHOOL'),('2019-10-31', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2019-11-01', 'SCHOOL'),('2019-11-02', 'SCHOOL'),('2019-11-03', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2019-12-21', 'SCHOOL'),('2019-12-22', 'SCHOOL'),('2019-12-23', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2019-12-24', 'SCHOOL'),('2019-12-25', 'SCHOOL'),('2019-12-26', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2019-12-27', 'SCHOOL'),('2019-12-28', 'SCHOOL'),('2019-12-29', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2019-12-30', 'SCHOOL'),('2019-12-31', 'SCHOOL'),('2020-01-01', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-01-02', 'SCHOOL'),('2020-01-03', 'SCHOOL'),('2020-01-04', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-01-05', 'SCHOOL'),('2020-01-06', 'SCHOOL'),('2020-01-07', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-02-03', 'SCHOOL'),('2020-02-15', 'SCHOOL'),('2020-02-16', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-02-17', 'SCHOOL'),('2020-02-18', 'SCHOOL'),('2020-02-19', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-02-20', 'SCHOOL'),('2020-02-21', 'SCHOOL'),('2020-02-22', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-02-23', 'SCHOOL'),('2020-04-09', 'SCHOOL'),('2020-04-10', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-04-11', 'SCHOOL'),('2020-04-12', 'SCHOOL'),('2020-04-13', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-04-14', 'SCHOOL'),('2020-07-01', 'SCHOOL'),('2020-07-02', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-07-03', 'SCHOOL'),('2020-07-04', 'SCHOOL'),('2020-07-05', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-07-06', 'SCHOOL'),('2020-07-07', 'SCHOOL'),('2020-07-08', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-07-09', 'SCHOOL'),('2020-07-10', 'SCHOOL'),('2020-07-11', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-07-12', 'SCHOOL'),('2020-07-13', 'SCHOOL'),('2020-07-14', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-07-15', 'SCHOOL'),('2020-07-16', 'SCHOOL'),('2020-07-17', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-07-18', 'SCHOOL'),('2020-07-19', 'SCHOOL'),('2020-07-20', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-07-21', 'SCHOOL'),('2020-07-22', 'SCHOOL'),('2020-07-23', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-07-24', 'SCHOOL'),('2020-07-25', 'SCHOOL'),('2020-07-26', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-07-27', 'SCHOOL'),('2020-07-28', 'SCHOOL'),('2020-07-29', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-07-30', 'SCHOOL'),('2020-07-31', 'SCHOOL'),('2020-08-01', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-08-02', 'SCHOOL'),('2020-08-03', 'SCHOOL'),('2020-08-04', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-08-05', 'SCHOOL'),('2020-08-06', 'SCHOOL'),('2020-08-07', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-08-08', 'SCHOOL'),('2020-08-09', 'SCHOOL'),('2020-08-10', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-08-11', 'SCHOOL'),('2020-08-12', 'SCHOOL'),('2020-08-13', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-08-14', 'SCHOOL'),('2020-08-15', 'SCHOOL'),('2020-08-16', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-08-17', 'SCHOOL'),('2020-08-18', 'SCHOOL'),('2020-08-19', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-08-20', 'SCHOOL'),('2020-08-21', 'SCHOOL'),('2020-08-22', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-08-23', 'SCHOOL'),('2020-08-24', 'SCHOOL'),('2020-08-25', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-08-26', 'SCHOOL'),('2020-08-27', 'SCHOOL'),('2020-08-28', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-08-29', 'SCHOOL'),('2020-08-30', 'SCHOOL'),('2020-08-31', 'SCHOOL');
INSERT INTO holiday (date, type) VALUES ('2020-09-01', 'SCHOOL');