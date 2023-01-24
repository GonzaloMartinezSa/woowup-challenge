-- Ejercicio SQL:

-- Escribir una consulta SQL que traiga todos los clientes que han comprado en total más de 100,000$ 
-- en los últimos 12 meses usando las siguientes tablas: 

-- Clientes: ID, Nombre, Apellido
-- Ventas: Fecha, Sucursal, Numero_factura, Importe, Id_cliente

select ID, Nombre, Apellido from Cliente
join Ventas on ID = Id_cliente
where Fecha between date_sub(now(), interval 1 year) and date(now()) 
group by ID, Nombre, Apellido
having sum(Importe) > 100000

-- Hecho basado en MySQL