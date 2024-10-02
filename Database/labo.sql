-- Tabla Usuario
CREATE TABLE Usuario (
                         idUsuario INT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(255) NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         contraseña VARCHAR(255) NOT NULL
);

-- Tabla Articulo
CREATE TABLE Articulo (
                          idArticulo INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(255),
                          codigo VARCHAR(50),
                          descripcion VARCHAR(255)
);

-- Tabla Atributo
CREATE TABLE Atributo (
                          idAtributo INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(255)
);

-- Tabla Ingreso
CREATE TABLE Ingreso (
                         idIngreso INT AUTO_INCREMENT PRIMARY KEY,
                         proveedor VARCHAR(255),
                         tipo VARCHAR(255),
                         fecha DATE,
                         idUsuario INT,
                         idArticulo INT,
                         FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario),
                         FOREIGN KEY (idArticulo) REFERENCES Articulo(idArticulo)
);

-- Tabla Especificacion
CREATE TABLE Especificacion (
                                idEspecificacion INT AUTO_INCREMENT PRIMARY KEY,
                                idArticulo INT,
                                nombre VARCHAR(255),
                                FOREIGN KEY (idArticulo) REFERENCES Articulo(idArticulo)
);

-- Tabla EspecificacionAtributo
CREATE TABLE EspecificacionAtributo (
                                        idAtributo INT,
                                        idEspecificacion INT,
                                        valorMin DECIMAL(10, 2),
                                        valorMax DECIMAL(10, 2),
                                        unidadMedida VARCHAR(50),
                                        PRIMARY KEY (idAtributo, idEspecificacion),
                                        FOREIGN KEY (idAtributo) REFERENCES Atributo(idAtributo),
                                        FOREIGN KEY (idEspecificacion) REFERENCES Especificacion(idEspecificacion)
);

-- Tabla CalificacionLote
CREATE TABLE CalificacionLote (
                                  idIngreso INT,
                                  numMuestra INT,
                                  idEspecificacion INT,
                                  estado VARCHAR(50),
                                  fecha DATE,
                                  PRIMARY KEY (idIngreso, numMuestra, idEspecificacion),
                                  FOREIGN KEY (idIngreso) REFERENCES Ingreso(idIngreso),
                                  FOREIGN KEY (idIngreso, numMuestra, idEspecificacion) REFERENCES CalifLoteAtributo(idIngreso, numMuestra, idEspecificacion)
);

-- Tabla CalifLoteAtributo
CREATE TABLE CalifLoteAtributo (
                                   idIngreso INT,
                                   numMuestra INT,
                                   idAtributo INT,
                                   idEspecificacion INT,
                                   valor DECIMAL(10, 2),
                                   comentario VARCHAR(255),
                                   PRIMARY KEY (idIngreso, numMuestra, idAtributo, idEspecificacion),
                                   FOREIGN KEY (idAtributo, idEspecificacion) REFERENCES EspecificacionAtributo(idAtributo, idEspecificacion)
);

-- Tabla CalificacionFinal
CREATE TABLE CalificacionFinal (
                                   idIngreso INT,
                                   idEspecificacion INT,
                                   fecha DATE,
                                   estado VARCHAR(50),
                                   PRIMARY KEY (idIngreso, idEspecificacion),
                                   FOREIGN KEY (idIngreso) REFERENCES Ingreso(idIngreso),
                                   FOREIGN KEY (idIngreso, idEspecificacion) REFERENCES CalifFinalAtributo(idIngreso, idEspecificacion)
);

-- Tabla CalifFinalAtributo
CREATE TABLE CalifFinalAtributo (
                                    idIngreso INT,
                                    idEspecificacion INT,
                                    idAtributo INT,
                                    valor DECIMAL(10, 2),
                                    comentario VARCHAR(255),
                                    PRIMARY KEY (idIngreso, idEspecificacion, idAtributo),
                                    FOREIGN KEY (idEspecificacion, idAtributo) REFERENCES EspecificacionAtributo(idEspecificacion, idAtributo)
);
