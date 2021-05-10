@'/Users/santiagotriana/Documents/Universidad/Sistemas transaccionales/Iteración 3/VacuAndesIteracion3/VacuandesIteración3/st-parranderos-jdo-est/doc/archivos_SQL/borrar_tablas_iteracion3.sql'
@'/Users/santiagotriana/Documents/Universidad/Sistemas transaccionales/Iteración 3/VacuAndesIteracion3/VacuandesIteración3/st-parranderos-jdo-est/doc/archivos_SQL/Creacion_tablas_iteracion3.sql'
--@'/Users/santiagotriana/Documents/Universidad/Sistemas transaccionales/Iteración 3/VacuAndesIteracion3/VacuandesIteración3/st-parranderos-jdo-est/doc/archivos_SQL/llenado_de_BD_iteracion3.sql'
@'/Users/santiagotriana/Documents/Universidad/Sistemas transaccionales/Iteración 3/intento_excel/SLQs/Inicial.sql'
@'/Users/santiagotriana/Documents/Universidad/Sistemas transaccionales/Iteración 3/intento_excel/SLQs/OFICINA_REGIONAL_EPS.sql'
@'/Users/santiagotriana/Documents/Universidad/Sistemas transaccionales/Iteración 3/intento_excel/SLQs/PUNTO_VACUNACION.sql'
@'/Users/santiagotriana/Documents/Universidad/Sistemas transaccionales/Iteración 3/intento_excel/SLQs/VACUNA.sql'
@'/Users/santiagotriana/Documents/Universidad/Sistemas transaccionales/Iteración 3/intento_excel/SLQs/CIUDADANO.sql'
@'/Users/santiagotriana/Documents/Universidad/Sistemas transaccionales/Iteración 3/intento_excel/SLQs/CITA.sql'
@'/Users/santiagotriana/Documents/Universidad/Sistemas transaccionales/Iteración 3/intento_excel/SLQs/LIST_CONDICIONES_CIUDADANO.sql'

UPDATE ciudadano SET PUNTO_VACUNACION= 1, PLAN_DE_VACUNACION= 1, OFICINA_REGIONAL_ASIGNADA = 1 WHERE CEDULA = 1001168160;
UPDATE ciudadano SET PUNTO_VACUNACION= 1, PLAN_DE_VACUNACION= 1, OFICINA_REGIONAL_ASIGNADA = 1 WHERE CEDULA = 1001168161;
UPDATE ciudadano SET PUNTO_VACUNACION= 1, PLAN_DE_VACUNACION= 1, OFICINA_REGIONAL_ASIGNADA = 1 WHERE CEDULA = 1001168162;
UPDATE ciudadano SET PUNTO_VACUNACION= 2, PLAN_DE_VACUNACION= 1, OFICINA_REGIONAL_ASIGNADA = 1 WHERE CEDULA = 1001168163;

INSERT INTO CITA (FECHA, CIUDADANO, PUNTO_VACUNACION, VACUNA, HORA_CITA) VALUES (TO_DATE('2020/06/08', 'yyyy/mm/dd'),1001168160,1,1002,1200);
INSERT INTO CITA (FECHA, CIUDADANO, PUNTO_VACUNACION, VACUNA, HORA_CITA) VALUES (TO_DATE('2021/07/07', 'yyyy/mm/dd'),1001168161,1,1003,1200);
INSERT INTO CITA (FECHA, CIUDADANO, PUNTO_VACUNACION, VACUNA, HORA_CITA) VALUES (TO_DATE('2021/07/07', 'yyyy/mm/dd'),1001168162,1,1004,1600);
INSERT INTO CITA (FECHA, CIUDADANO, PUNTO_VACUNACION, VACUNA, HORA_CITA) VALUES (TO_DATE('2021/07/07', 'yyyy/mm/dd'),1001168437,1,1008,1600);
INSERT INTO CITA (FECHA, CIUDADANO, PUNTO_VACUNACION, VACUNA, HORA_CITA) VALUES (TO_DATE('2021/07/07', 'yyyy/mm/dd'),1001168501,1,1005,1600);
INSERT INTO CITA (FECHA, CIUDADANO, PUNTO_VACUNACION, VACUNA, HORA_CITA) VALUES (TO_DATE('2021/07/08', 'yyyy/mm/dd'),1001169036,1,1006,1200);
INSERT INTO CITA (FECHA, CIUDADANO, PUNTO_VACUNACION, VACUNA, HORA_CITA) VALUES (TO_DATE('2021/07/08', 'yyyy/mm/dd'),1001169115,1,1007,1200);

UPDATE VACUNA SET UTILIZADA = 0 WHERE ID_VACUNA = 1002;
UPDATE VACUNA SET UTILIZADA = 0 WHERE ID_VACUNA = 1003;
UPDATE VACUNA SET UTILIZADA = 0 WHERE ID_VACUNA = 1004;
UPDATE VACUNA SET UTILIZADA = 0 WHERE ID_VACUNA = 1005;
UPDATE VACUNA SET UTILIZADA = 0 WHERE ID_VACUNA = 1006;
UPDATE VACUNA SET UTILIZADA = 0 WHERE ID_VACUNA = 1007;
UPDATE VACUNA SET UTILIZADA = 0 WHERE ID_VACUNA = 1008;
commit;