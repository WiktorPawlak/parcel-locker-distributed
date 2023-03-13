-- Add users
insert into USERS (id, version, firstname, lastname, active, telnumber)
values ('00000000-0000-0000-0000-000000000000', 0, 'Tony', 'Stark', true, '453875343');

insert into USERS (id, version, firstname, lastname, active, telnumber)
values ('00000000-0000-0000-0000-000000000001', 0, 'Eric', 'Evans', true, '562130923');

insert into USERS (id, version, firstname, lastname, active, telnumber)
values ('00000000-0000-0000-0000-000000000002', 0, 'Uncle', 'Bob', true, '909787666');

insert into USERS (id, version, firstname, lastname, active, telnumber)
values ('00000000-0000-0000-0000-000000000003', 0, 'Greg', 'Young', true, '856875123');

--Add locker
insert into LOCKERS (id, version, address, identitynumber)
values ('00000000-0000-0000-0000-000000000000', 1, 'ul. Wojska Polskiego 15', '1');

--Add packages
insert into PACKAGES (dtype, id, version, baseprice, priority, fragile, height, length, weight, width)
values ('List', '00000000-0000-0000-0000-000000000000', 1, 10.5, false, false, 1.1, 1.1, 1.1, 1.1);

insert into PACKAGES (dtype, id, version, baseprice, priority, fragile, height, length, weight, width)
values ('Parcel', '00000000-0000-0000-0000-000000000001', 1, 10.5, true, false, 1.1, 1.1, 1.1, 1.1);

--Add delivery
insert into DELIVERIES (package_type, id, version, allocationstart, allocationstop, isarchived, status,
                        locker_id, pack_id,
                        receiver_id, shipper_id)
values (1, '00000000-0000-0000-0000-000000000000', 1, '2022-11-13 11:58:20', null, false, 1,
        '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000',
        '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000001');

--Add deposit boxes
insert into DEPOSIT_BOXES (id, version, accesscode, isempty, telnumber, delivery_id)
values ('00000000-0000-0000-0000-000000000000', 1, '1234', false, '000000000', '00000000-0000-0000-0000-000000000000');

insert into DEPOSIT_BOXES (id, version, accesscode, isempty, telnumber, delivery_id)
values ('00000000-0000-0000-0000-000000000001', 1, '', true, '', null);

--Add locker_depositbox link
insert into LOCKERS_DEPOSIT_BOXES (lockerentity_id, depositboxes_id)
values ('00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000');

insert into LOCKERS_DEPOSIT_BOXES (lockerentity_id, depositboxes_id)
values ('00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000001');
