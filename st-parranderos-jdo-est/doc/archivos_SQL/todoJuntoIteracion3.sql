@'/Users/santiagotriana/Documents/Universidad/Sistemas transaccionales/Iteración 3/VacuAndesIteracion3/VacuandesIteración3/st-parranderos-jdo-est/doc/archivos_SQL/borrar_tablas_iteracion3.sql'
@'/Users/santiagotriana/Documents/Universidad/Sistemas transaccionales/Iteración 3/VacuAndesIteracion3/VacuandesIteración3/st-parranderos-jdo-est/doc/archivos_SQL/Creacion_tablas_iteracion3.sql'
@'/Users/santiagotriana/Documents/Universidad/Sistemas transaccionales/Iteración 3/VacuAndesIteracion3/VacuandesIteración3/st-parranderos-jdo-est/doc/archivos_SQL/llenado_de_BD_iteracion3.sql'

SELECT * 
FROM PUNTO_VACUNACION punto INNER JOIN OFICINA_REGIONAL_EPS oficina ON punto.oficina_regional_eps = oficina.id_oficina
WHERE oficina.region = 'Andina' and habilitado = 1

--Trianix, el error quedó arreglado, era que en el insert no se había agregado el paramtro de habilitado en insert punto vacunacion