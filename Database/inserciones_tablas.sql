-- Inserciones en la tabla  Usuario
INSERT INTO Usuario (idUsuario, nombre, email, contraseña)
VALUES (1, 'admin', 'admin', 'admin'),
       (2, 'Caro', 'caro@labo.com', 'password123'),
       (3, 'Gonzalo', 'gonzalo@labo.com', 'password123');

-- Inserciones en la tabla  Articulo
INSERT INTO Articulo (idArticulo, nombre, codigo, descripcion)
VALUES (1, 'Acetato De Sodio Trihidrato', 'JSX010', 'Descripción del artículo 1'),
       (2, 'Ácido Cítrico Sc 50%', 'ACX040', 'Descripción del artículo 2'),
       (3, 'Ácido Láctico Tamponado', 'ALT010', 'Descripción del artículo 3'),
       (4, 'Adama BR', 'ENZ010', 'Descripción del artículo 4'),
       (5, 'Acetato De Sodio Trihidrato Seco', 'JSS010', 'Descripción del artículo 5'),
       (6, 'XA-1600', 'XIN010', 'Descripción del artículo 6'),
       (7, 'Alfa Amilasa 5000', 'ENZ020', 'Descripción del artículo 7'),
       (8, 'Benzoato De Sodio Granulado', 'BNS010', 'Descripción del artículo 8'),
       (9, 'Citrato De Potasio Monohidrato', 'CIK010', 'Descripción del artículo 9'),
       (10, 'Citrato De Sodio', 'CIS020', 'Descripción del artículo 10');

-- Inserciones en la tabla  Atributo
INSERT INTO Atributo (idAtributo, nombre)
VALUES (1, 'pH'),
       (2, 'Densidad'),
       (3, 'Solubilidad'),
       (4, 'Título'),
       (5, 'Pureza'),
       (6, 'Conductividad'),
       (7, 'Viscosidad'),
       (8, 'Coloración'),
       (9, 'Humedad'),
       (10, 'Absorción UV');


-- Inserciones en la tabla  Ingreso
INSERT INTO Ingreso (idIngreso, proveedor, tipo, fecha, idUsuario, idArticulo)
VALUES (1, 'QUIMTIA', 'MP', '2022-07-26', 1, 1),
       (2, 'SINO PHOS', 'MP', '2022-07-25', 2, 2),
       (3, 'LATIN CHEMICAL', 'MP', '2022-07-25', 3, 3),
       (4, 'NEOPHOS', 'MP', '2022-07-20', 1, 4),
       (5, 'INDAQUIM', 'MP', '2022-07-20', 2, 5),
       (6, 'CEAMSA', 'MP', '2022-07-14', 3, 6),
       (7, 'PLASTICOS DEL PLATA', 'MP', '2022-07-14', 1, 7),
       (8, 'DOW', 'MP', '2022-07-14', 2, 8),
       (9, 'Rame', 'MP', '2022-07-14', 3, 9),
       (10, 'LODRA', 'MP', '2022-07-14', 1, 10);


-- Inserciones en la tabla  Especificacion
INSERT INTO Especificacion (idEspecificacion, idArticulo, nombre)
VALUES (1, 1, 'Especificación Acetato de Sodio Trihidrato'),
       (2, 2, 'Especificación Ácido Cítrico Sc 50%'),
       (3, 3, 'Especificación Ácido Láctico Tamponado'),
       (4, 4, 'Especificación Adama BR'),
       (5, 5, 'Especificación Acetato de Sodio Trihidrato Seco'),
       (6, 6, 'Especificación XA-1600'),
       (7, 7, 'Especificación Alfa Amilasa 5000'),
       (8, 8, 'Especificación Benzoato de Sodio Granulado'),
       (9, 9, 'Especificación Citrato de Potasio Monohidrato'),
       (10, 10, 'Especificación Citrato de Sodio');


-- Inserciones en la tabla  EspecificacionAtributo
INSERT INTO EspecificacionAtributo (idAtributo, idEspecificacion, valorMin, valorMax, unidadMedida)
VALUES
-- Especificación 1 (Acetato de Sodio Trihidrato)
(1, 1, 7.0, 8.0, 'pH'),
(2, 1, 0.9, 1.1, 'g/mL'),
(3, 1, 90, 100, '%'),
-- Especificación 2 (Ácido Cítrico Sc 50%)
(1, 2, 2.0, 4.0, 'pH'),
(4, 2, 90, 100, '%'),
(5, 2, 98, 100, '%'),
-- Especificación 3 (Ácido Láctico Tamponado)
(1, 3, 3.5, 4.5, 'pH'),
(6, 3, 10, 20, 'mS/cm'),
(7, 3, 100, 500, 'cP'),
-- Especificación 4 (Adama BR)
(8, 4, 0, 50, 'Absorbancia'),
(5, 4, 95, 100, '%'),
(3, 4, 90, 100, '%'),
-- Especificación 5 (Acetato de Sodio Trihidrato Seco)
(1, 5, 6.5, 7.5, 'pH'),
(2, 5, 0.8, 1.0, 'g/mL'),
(9, 5, 0, 5, '%'),
-- Especificación 6 (XA-1600)
(1, 6, 5.0, 6.0, 'pH'),
(2, 6, 0.9, 1.1, 'g/mL'),
(3, 6, 90, 95, '%'),
-- Especificación 7 (Alfa Amilasa 5000)
(1, 7, 6.0, 7.0, 'pH'),
(2, 7, 0.95, 1.05, 'g/mL'),
(4, 7, 88, 100, '%'),
-- Especificación 8 (Benzoato de Sodio Granulado)
(1, 8, 7.0, 8.0, 'pH'),
(3, 8, 90, 100, '%'),
(5, 8, 98, 100, '%'),
-- Especificación 9 (Citrato de Potasio Monohidrato)
(1, 9, 3.0, 4.0, 'pH'),
(2, 9, 0.9, 1.1, 'g/mL'),
(5, 9, 96, 100, '%'),
-- Especificación 10 (Citrato de Sodio Primario)
(1, 10, 2.5, 3.5, 'pH'),
(2, 10, 0.85, 1.05, 'g/mL'),
(5, 10, 94, 100, '%');


-- Inserciones en la tabla  CalifLoteAtributo
-- Ingreso 1
INSERT INTO CalifLoteAtributo (idIngreso, numMuestra, idAtributo, idEspecificacion, valor, comentario)
VALUES (1, 1, 1, 1, 7.5, 'Dentro de especificación'),
       (1, 1, 2, 1, 1.0, 'Cumple con la densidad'),
       (1, 2, 1, 1, 7.8, 'Dentro de especificación'),
       (1, 2, 3, 1, 95, 'Dentro de especificación'),
-- Ingreso 2
       (2, 1, 1, 2, 3.5, 'pH dentro del rango'),
       (2, 1, 4, 2, 98, 'Título cumple'),
       (2, 2, 1, 2, 3.9, 'pH en el límite superior'),
       (2, 2, 5, 2, 99, 'Pureza aceptable'),
-- Ingreso 3
       (3, 1, 1, 3, 4.0, 'Cumple pH'),
       (3, 1, 6, 3, 15, 'Conductividad dentro del rango'),
       (3, 2, 1, 3, 3.7, 'Cumple pH'),
       (3, 2, 7, 3, 300, 'Viscosidad aceptable'),
-- Ingreso 4
       (4, 1, 8, 4, 25, 'Absorbancia dentro de lo esperado'),
       (4, 1, 5, 4, 96, 'Pureza aceptable'),
       (4, 2, 8, 4, 30, 'Absorbancia ligeramente alta'),
       (4, 2, 3, 4, 98, 'Solubilidad dentro de lo esperado'),
-- Ingreso 5
       (5, 1, 1, 5, 7.0, 'Dentro de especificación de pH'),
       (5, 1, 2, 5, 0.9, 'Densidad correcta'),
       (5, 2, 1, 5, 7.2, 'Dentro de especificación de pH'),
       (5, 2, 9, 5, 2, 'Humedad aceptable'),
-- Ingreso 6
       (6, 1, 1, 6, 5.5, 'pH dentro del rango'),
       (6, 1, 2, 6, 1.0, 'Densidad correcta'),
       (6, 2, 1, 6, 5.8, 'pH dentro de lo esperado'),
       (6, 2, 3, 6, 92, 'Cumple con los parámetros'),
-- Ingreso 7
       (7, 1, 1, 7, 6.7, 'pH correcto'),
       (7, 1, 2, 7, 1.02, 'Densidad aceptable'),
       (7, 2, 1, 7, 6.5, 'pH cumple con lo esperado'),
       (7, 2, 4, 7, 90, 'Título en el rango permitido'),
-- Ingreso 8
       (8, 1, 1, 8, 7.2, 'pH dentro del rango'),
       (8, 1, 3, 8, 98, 'Pureza cumple'),
       (8, 2, 1, 8, 7.4, 'pH aceptable'),
       (8, 2, 5, 8, 100, 'Pureza máxima alcanzada'),
-- Ingreso 9
       (9, 1, 1, 9, 3.5, 'Dentro de especificación'),
       (9, 1, 2, 9, 0.98, 'Cumple con la densidad'),
       (9, 2, 1, 9, 3.8, 'Dentro de especificación'),
       (9, 2, 5, 9, 99, 'Pureza dentro del rango'),
-- Ingreso 10
       (10, 1, 1, 10, 2.9, 'Dentro de especificación'),
       (10, 1, 2, 10, 0.90, 'Densidad aceptable'),
       (10, 2, 1, 10, 3.0, 'Cumple pH'),
       (10, 2, 5, 10, 98, 'Pureza esperada');


-- Inserciones en la tabla  CalificacionLote
INSERT INTO CalificacionLote (idIngreso, numMuestra, idEspecificacion, estado, fecha)
VALUES
-- Ingreso 1
(1, 1, 1, 'Aprobado', '2022-07-27'),
(1, 2, 1, 'Aprobado', '2022-07-27'),
-- Ingreso 2
(2, 1, 2, 'Aprobado', '2022-07-26'),
(2, 2, 2, 'Aprobado', '2022-07-26'),
-- Ingreso 3
(3, 1, 3, 'Aprobado', '2022-07-25'),
(3, 2, 3, 'Aprobado', '2022-07-25'),
-- Ingreso 4
(4, 1, 4, 'Aprobado', '2022-07-24'),
(4, 2, 4, 'Aprobado', '2022-07-24'),
-- Ingreso 5
(5, 1, 5, 'Aprobado', '2022-07-23'),
(5, 2, 5, 'Aprobado', '2022-07-23'),
-- Ingreso 6
(6, 1, 6, 'Aprobado', '2022-07-22'),
(6, 2, 6, 'Aprobado', '2022-07-22'),
-- Ingreso 7
(7, 1, 7, 'Aprobado', '2022-07-21'),
(7, 2, 7, 'Aprobado', '2022-07-21'),
-- Ingreso 8
(8, 1, 8, 'Aprobado', '2022-07-20'),
(8, 2, 8, 'Aprobado', '2022-07-20'),
-- Ingreso 9
(9, 1, 9, 'Aprobado', '2022-07-19'),
(9, 2, 9, 'Aprobado', '2022-07-19'),
-- Ingreso 10
(10, 1, 10, 'Aprobado', '2022-07-18'),
(10, 2, 10, 'Aprobado', '2022-07-18');

-- Inserciones en la tabla  CalifFinalAtributo
INSERT INTO CalifFinalAtributo (idIngreso, idEspecificacion, idAtributo, valor, comentario)
VALUES
-- Ingreso 1
(1, 1, 1, 7.5, 'pH Aceptable'),
(1, 1, 2, 1.0, 'Densidad Aceptable'),
-- Ingreso 2
(2, 2, 1, 3.5, 'pH Cumple'),
(2, 2, 5, 99, 'Pureza Aceptable'),
-- Ingreso 3
(3, 3, 1, 4.0, 'Cumple'),
(3, 3, 6, 15, 'Conductividad Correcta'),
-- Ingreso 4
(4, 4, 8, 25, 'Absorbancia en rango'),
(4, 4, 5, 96, 'Pureza aceptable'),
-- Ingreso 5
(5, 5, 1, 7.0, 'pH dentro del rango'),
(5, 5, 9, 2.0, 'Humedad aceptable'),
-- Ingreso 6
(6, 6, 1, 5.5, 'pH Correcto'),
(6, 6, 2, 1.0, 'Densidad Correcta'),
-- Ingreso 7
(7, 7, 1, 6.7, 'pH Cumple'),
(7, 7, 4, 90, 'Título correcto'),
-- Ingreso 8
(8, 8, 1, 7.2, 'pH Aceptable'),
(8, 8, 5, 100, 'Pureza máxima'),
-- Ingreso 9
(9, 9, 1, 3.5, 'pH Cumple'),
(9, 9, 5, 99, 'Pureza correcta'),
-- Ingreso 10
(10, 10, 1, 2.9, 'pH dentro del rango'),
(10, 10, 5, 98, 'Pureza aceptable');


-- Inserciones en la tabla  CalificacionFinal
INSERT INTO CalificacionFinal (idIngreso, idEspecificacion, fecha, estado)
VALUES
    (1, 1, '2022-07-27', 'Aprobado'),
    (2, 2, '2022-07-26', 'Aprobado'),
    (3, 3, '2022-07-25', 'Aprobado'),
    (4, 4, '2022-07-24', 'Aprobado'),
    (5, 5, '2022-07-23', 'Aprobado'),
    (6, 6, '2022-07-22', 'Aprobado'),
    (7, 7, '2022-07-21', 'Aprobado'),
    (8, 8, '2022-07-20', 'Aprobado'),
    (9, 9, '2022-07-19', 'Aprobado'),
    (10, 10, '2022-07-18', 'Aprobado');


