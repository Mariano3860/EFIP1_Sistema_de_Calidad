-- 1. Consulta para obtener ingresos con el nombre de los artículos:
SELECT Ingreso.idIngreso, Ingreso.proveedor, Ingreso.tipo, Ingreso.fecha,
       Articulo.nombre AS nombreArticulo, Usuario.nombre AS nombreUsuario
FROM Ingreso
         JOIN Articulo ON Ingreso.idArticulo = Articulo.idArticulo
         JOIN Usuario ON Ingreso.idUsuario = Usuario.idUsuario;

-- 2. Consulta para obtener las especificaciones de los artículos:
SELECT Especificacion.idEspecificacion, Especificacion.nombre AS nombreEspecificacion,
       Articulo.nombre AS nombreArticulo, EspecificacionAtributo.idAtributo,
       Atributo.nombre AS nombreAtributo, EspecificacionAtributo.valorMin,
       EspecificacionAtributo.valorMax, EspecificacionAtributo.unidadMedida
FROM Especificacion
         JOIN Articulo ON Especificacion.idArticulo = Articulo.idArticulo
         JOIN EspecificacionAtributo ON Especificacion.idEspecificacion = EspecificacionAtributo.idEspecificacion
         JOIN Atributo ON EspecificacionAtributo.idAtributo = Atributo.idAtributo;

-- 3. Consulta para obtener las calificaciones por lote:
SELECT CalifLoteAtributo.idIngreso, CalifLoteAtributo.numMuestra, Articulo.nombre AS nombreArticulo,
       Atributo.nombre AS nombreAtributo, CalifLoteAtributo.valor, CalifLoteAtributo.comentario
FROM CalifLoteAtributo
         JOIN Ingreso ON CalifLoteAtributo.idIngreso = Ingreso.idIngreso
         JOIN Articulo ON Ingreso.idArticulo = Articulo.idArticulo
         JOIN Atributo ON CalifLoteAtributo.idAtributo = Atributo.idAtributo;

-- 4. Consulta para obtener la calificación final por ingreso:
SELECT CalificacionFinal.idIngreso, Articulo.nombre AS nombreArticulo,
       CalificacionFinal.fecha, CalificacionFinal.estado
FROM CalificacionFinal
         JOIN Ingreso ON CalificacionFinal.idIngreso = Ingreso.idIngreso
         JOIN Articulo ON Ingreso.idArticulo = Articulo.idArticulo;