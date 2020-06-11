INSERT INTO EMPLOYEE(id, name, pesel, surname, role, supervisor_id) VALUES (2, 'bartek', '91022404123', 'nowak', 'CEO',null);
INSERT INTO EMPLOYEE(id, name, pesel, surname, role, supervisor_id) VALUES (1, 'jan', '91022404635', 'kowalski', 'DIRECTOR',2);

INSERT INTO ADDRESS(id, flat_nr, house_nr, postal_code, street, address_type) VALUES (1, 49, 7, '50-317', 'Prusa', 'ADDRESS_OF_RESIDENCE');
INSERT INTO ADDRESS(id, flat_nr, house_nr, postal_code, street, address_type) VALUES (2, 11, 32, '51-300', 'Rynek', 'ADDRESS_OF_PERMANENT_RESIDENCE');

INSERT INTO ADDRESS_EMPLOYEE(address_id, employee_id) VALUES (1,1);
INSERT INTO ADDRESS_EMPLOYEE(address_id, employee_id) VALUES (1,2);
INSERT INTO ADDRESS_EMPLOYEE(address_id, employee_id) VALUES (2,1);