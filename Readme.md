# Sistema de Gestión de Calidad para Laboratorios Químicos

Este proyecto implementa un **Sistema de Gestión de Calidad** para laboratorios químicos que automatiza el control de lotes de productos, permitiendo centralizar y optimizar los procesos de calidad. El sistema está desarrollado en **Java** con **Swing** para la interfaz gráfica y utiliza **MySQL** como base de datos.

## Funcionalidades Principales

- **Ingreso de lotes**: Registro automatizado de lotes que ingresan al laboratorio con sus respectivas especificaciones.
- **Análisis de atributos**: Gestión y almacenamiento de los resultados de análisis de calidad para cada lote.
- **Alertas en tiempo real**: Generación de alertas cuando un lote no cumple con las especificaciones de calidad establecidas.
- **Generación de reportes**: Creación automática de informes detallados sobre la conformidad de cada lote con las normas establecidas.
- **Historial de lotes**: Consulta de lotes históricos filtrados por fecha, tipo de producto y responsable del análisis.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal del sistema.
- **Java Swing**: Para la creación de la interfaz gráfica.
- **MySQL**: Base de datos para almacenar los registros de lotes y análisis.
- **JDBC**: Para la conexión entre la base de datos y la aplicación Java.
- **Arquitectura Cliente-Servidor**: Separación de la lógica de negocio y el cliente para una mejor escalabilidad.

## Requisitos

- **JDK 8 o superior**.
- **MySQL 5.7 o superior**.
- **JDBC MySQL Connector**.

## Configuración del Proyecto

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/Mariano3860/EFIP1_Sistema_de_Calidad.git
   ```
   
2. **Configurar la base de datos**:
   - Cargar el archivo `labo.sql` en tu instancia de MySQL para crear las tablas necesarias:
   ```bash
   mysql -u tu_usuario -p < labo.sql
   ```
   - Asegúrate de tener configurado el acceso a la base de datos en el archivo de configuración de la aplicación Java.

3. **Compilar y ejecutar**: (Todavia no esta implementado)
   - Compilar el código Java utilizando el siguiente comando desde el directorio raíz del proyecto:
   ```bash
   javac -cp .:mysql-connector-java.jar Main.java
   ```
   - Ejecutar la aplicación:
   ```bash
   java -cp .:mysql-connector-java.jar Main
   ```

## Estructura del Proyecto

```bash
EFIP1_Sistema_de_Calidad/
├── src/                     # Código fuente del proyecto
├── Database/                # Archivo SQL para la base de datos
├── resources/               # Recursos como imágenes o archivos de configuración
└── README.md                # Este archivo
```

## Casos de Uso Clave

- **CU001: Iniciar Sesión**: Permite al usuario autenticarse en el sistema.
- **CU002: Registrar Nuevo Lote**: Permite registrar nuevos lotes con sus especificaciones.
- **CU006: Registrar Resultados de Análisis**: Los usuarios pueden registrar los resultados de los análisis.
- **CU010: Generar Informe Final**: El sistema genera informes sobre la conformidad de un lote.
