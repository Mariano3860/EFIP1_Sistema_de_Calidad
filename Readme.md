# Sistema de Gestión de Calidad para Laboratorios Químicos

Este proyecto implementa un **Sistema de Gestión de Calidad** para laboratorios químicos que permite gestionar lotes de productos, control de especificaciones y atributos asociados, todo dentro de una interfaz gráfica de usuario desarrollada en **Java Swing**. La aplicación se conecta a una base de datos MySQL a través de JDBC.

## Funcionalidades Principales

- **Gestión de Lotes**: Registro de lotes con información como proveedor, tipo de lote, fecha, artículo y usuario responsable.
- **Especificaciones**: Gestión de especificaciones para artículos, incluyendo atributos como valor mínimo, valor máximo y unidad de medida.
- **Atributos por Especificación**: Posibilidad de añadir, modificar y eliminar atributos asociados a una especificación de un artículo.
- **Autenticación de Usuario**: Sistema de login que asegura que solo usuarios autorizados puedan acceder al sistema.

## Estructura del Proyecto

La estructura del proyecto sigue la arquitectura MVC (Modelo-Vista-Controlador), donde:
- **Modelos**: Representan las entidades principales del sistema, como `Lote`, `Usuario`, `Articulo`, `Especificacion` y `Atributo`.
- **Vistas**: Se utilizan **JFrames** y **JPanels** de **Swing** para la interfaz gráfica del usuario. Ejemplos son `LoginFrame`, `LotePanel` y `EspecificacionPanel`.
- **Controladores**: Gestionan la lógica del negocio y las interacciones con la base de datos. Por ejemplo, `LoteController`, `UsuarioController`, `EspecificacionController`.

Estructura del directorio:

```bash
EFIP1_Sistema_de_Calidad/
├── src/                     # Código fuente del proyecto
│   ├── com/
│   │   ├── labo/            # Paquete principal
│   │   ├── controllers/     # Controladores para la lógica de negocio
│   │   ├── dao/        # Conexiones a la base de datos
│   │   ├── views/          # Interfaces gráficas
│   │   └── models/          # Entidades y modelos de datos
├── resources/               # Recursos como imágenes o archivos de configuración
├── Database/                # Scripts de creación de tablas de la base de datos (labo.sql)
└── README.md                # Archivo README
```

## Requisitos

- **JDK 8** o superior.
- **Apache Maven** para la gestión de dependencias y compilación.
- **MySQL** como base de datos.
- **JDBC MySQL Connector** (automáticamente gestionado por Maven).

## Codificación de Archivos

Este proyecto utiliza caracteres especiales como la ñ y los tildes. Para asegurar el correcto funcionamiento de estos caracteres, es necesario que los archivos del proyecto estén codificados en UTF-8.
La forma de configurar la codificación de archivos a UTF-8 puede variar dependiendo del IDE que estés utilizando. A continuación, se proporcionan instrucciones generales que pueden aplicarse a la mayoría de los IDEs:
1. Busca en la configuración de tu IDE la opción para cambiar la codificación de archivos. Esto puede estar en un menú como `File > Settings`, `Preferences`, `Options`, o similar.
2. Dentro de la configuración, busca una opción llamada "File Encodings", "Character Set", "Text File Encoding", o similar.
3. Cambia la codificación global y del proyecto (si está disponible) a `UTF-8`.
4. Guarda los cambios.

## Configuración del Proyecto

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu_usuario/EFIP1_Sistema_de_Calidad.git
   ```

2. **Configurar la base de datos**:
   - Cargar el archivo SQL proporcionado (`labo.sql`) en MySQL para crear las tablas necesarias:
     ```bash
     mysql -u tu_usuario -p < Database/labo.sql
     ```

   - Asegurarse de que los detalles de la conexión a la base de datos (usuario, contraseña, URL) estén correctamente configurados en `DatabaseConnection.java`:
     ```java
     private static final String URL = "jdbc:mysql://localhost/labo?serverTimezone=UTC";
     private static final String USER = "root";
     private static final String PASSWORD = "tu_password";
     ```

3. **Compilar el Proyecto**:
   - Para compilar el proyecto, utiliza Maven desde la raíz del directorio:
     ```bash
     mvn clean compile
     ```

4. **Ejecutar el Proyecto**:
   - Una vez compilado, puedes ejecutar la aplicación ejecutando el archivo `Main.java`:
     ```bash
     java -cp target/classes:libs/* com.labo.Main
     ```

## Usuario por Defecto

- Usuario: `admin`
- Contraseña: `admin`

Este usuario está configurado en la base de datos por defecto y permite el acceso inicial al sistema.

## Ejemplo de Uso

1. **Iniciar Sesión**: Al iniciar la aplicación, se presentará una ventana de login donde deberás ingresar las credenciales.
2. **Gestionar Lotes**: Una vez autenticado, podrás registrar nuevos lotes ingresando los detalles correspondientes.
3. **Gestionar Especificaciones**: Desde la pestaña de especificaciones, puedes agregar o modificar especificaciones y sus atributos.

## Maven

Se utiliza **Apache Maven** para gestionar las dependencias del proyecto. Las principales dependencias incluyen:

- `mysql-connector-java`: Para la conexión con la base de datos MySQL.
- `junit`: Para pruebas unitarias (si se implementan).

Puedes gestionar todas las dependencias y compilar el proyecto utilizando:
```bash
mvn clean install
```

Este comando descargará las dependencias necesarias y compilará todo el código fuente.