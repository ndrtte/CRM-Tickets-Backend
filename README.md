# CRM Tickets Backend

## Configuración de entorno para Java Spring Boot

Utilizaremos la herramienta Visual Studio Code.

### 1. Instalación de JDK 17

Para este proyecto se utilizará **Java 17**. Se recomienda descargar el JDK desde el sitio oficial de Oracle y seguir los pasos del asistente de instalación según el sistema operativo.

Para comprobar que Java está correctamente instalado, ejecutar en la terminal:

```bash
java --version
```

La salida debería mostrar una versión 17, por ejemplo:

```bash
java 17.0.12 2024-07-16 LTS
```

### 2. Instalación de extensiones en Visual Studio Code

Para ejecutar un proyecto de Java Spring Boot en Visual Studio Code, es necesario instalar las extensiones correspondientes.

1. Abrir Visual Studio Code.
2. Ir al apartado de **Extensiones**.
3. Buscar **"Spring Boot Extension Pack"** publicado por VMware.
4. Instalar el paquete completo de extensiones.

Se recomienda también verificar que esté instalado el "**Extension Pack for Java**", ya que es requerido para el correcto funcionamiento del entorno.

## Clonar el proyecto

Para clonar el proyecto utilizaremos Git Bash.

1. Abrir Git Bash en el directorio donde se desea descargar el proyecto.
2. Ejecutar el siguiente comando:

```bash
git clone https://github.com/ndrtte/CRM-Tickets-Backend.git
```

3. Una vez finalizada la clonación, ingresar al directorio del proyecto:

```bash
cd CRM-Tickets-Backend
```

4. Para abrir el proyecto en Visual Studio Code, ejecutar:

```bash
code .
```

5. El proyecto se abrirá automáticamente en Visual Studio Code.

## Ejecutar el proyecto

Dentro del directorio `src/main/java/com/crm/gestiontickets/`, ubicar el archivo:

`GestionTicketsApplication.java`

Abrir el archivo y verificar que en la parte superior derecha aparezcan las opciones **Run** y **Debug**.

**Importante:** Si las opciones no aparecen, significa que el entorno de desarrollo no está correctamente configurado.

## Configuración de Variables de Entorno en Visual Studio Code

A continuación se describen los pasos necesarios para configurar correctamente las variables de entorno del proyecto en Visual Studio Code.

### 1. Ubicarse en el directorio raíz

Asegúrate de estar dentro del directorio raíz del proyecto `gestiontickets` y de tenerlo abierto como Workspace en Visual Studio Code.

En la raíz del proyecto (al mismo nivel que `src` y `pom.xml`) encontrarás el archivo:

`env.template`

Este archivo es únicamente una plantilla del archivo real que utilizaremos para definir las variables de entorno (`.env`).

### 2. Crear el archivo `.env`

1. Copia el archivo `env.template`.
2. Renombra la copia a `.env`.
3. Abre el archivo `.env` y completa las variables con tus valores correspondientes. Por ejemplo:

```
DB_HOST=localhost
DB_PORT=1433
DB_NAME=NOMBRE_BASE_DE_DATOS
DB_USER=Usuario123
DB_PASSWORD=ContraseñaSegura1234.
```
### 3. Crear el archivo `.env.docker`

1. Copia el archivo `env.template`.
2. Renombra la copia a `.env.docker`.
3. Abre el archivo `.env.docker` y completa las variables con tus valores correspondientes. Por ejemplo:

```
DB_HOST=sqlserver
DB_PORT=1433
DB_NAME=NOMBRE_BASE_DE_DATOS
DB_USER=Usuario123
DB_PASSWORD=ContraseñaSegura1234.
```

### Notas importantes

- El puerto `1433` es el puerto interno estándar de SQL Server.
- Cuando se ejecuta dentro de Docker, no se debe usar `localhost` como host de la base de datos.
- Cuando se ejecuta fuera de Docker, no se debe usar el nombre del servicio (`sqlserver`).
- Asegúrate de que las credenciales y el nombre de la base de datos sean correctos antes de ejecutar el proyecto.

### 3. Crear la carpeta `.vscode`

En la raíz del proyecto (al mismo nivel que `src` y `.env`), crea una carpeta llamada:

`.vscode`

Si ya existe, puedes utilizarla sin necesidad de crear una nueva.

### 4. Configurar el archivo `launch.json`

Dentro de la carpeta `.vscode`, crea un archivo llamado:

`launch.json`

Agrega la siguiente configuración:
```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "GestionTickets",
            "request": "launch",
            "mainClass": "com.crm.gestiontickets.GestionTicketsApplication",
            "projectName": "gestiontickets",
            "envFile": "${workspaceFolder}/.env"
        }
    ]
}
```

Esta configuración permite que, cada vez que ejecutes la aplicación Spring Boot desde Visual Studio Code, las variables definidas en el archivo `.env` se carguen automáticamente.

### 5. Configurar `.gitignore`

Por razones de seguridad, es fundamental evitar que el archivo `.env` y la carpeta `.vscode` se incluyan en los commits.

Abre el archivo `.gitignore` y agrega lo siguiente:

```
.vscode/
.env
.env.docker
```

Después de esto, estos archivos deberían mostrarse en un tono más gris en el panel de control de cambios, indicando que Git ya no los incluirá en los commits.

### 6. Ejecutar la aplicación

Una vez completados todos los pasos:

1. Verifica que las credenciales sean correctas.
2. Ejecuta la aplicación desde la configuración creada en Visual Studio Code.

Si todo está correctamente configurado, la aplicación debería iniciar sin inconvenientes utilizando las variables de entorno definidas.

# Configuración con Docker: Backend y SQL Server

Este apartado describe el proceso para construir y ejecutar el backend desarrollado en Spring Boot junto con una instancia de SQL Server utilizando Docker.

## 1. Requisitos previos

Antes de comenzar, asegúrate de cumplir con los siguientes requisitos:

- Tener Docker Desktop instalado
- Tener el proyecto clonado en tu equipo
- Estar ubicado en la raíz del proyecto: `CRM-TICKETS-BACKEND`

## 2. Instalación de Docker Desktop

Si no tienes Docker instalado, puedes descargarlo desde el sitio oficial:

https://www.docker.com/products/docker-desktop/

### Pasos de instalación

1. Descarga la versión correspondiente a tu sistema operativo (Windows, Linux o macOS) y arquitectura (AMD o ARM).
2. Ejecuta el instalador y sigue las instrucciones.
3. Durante la instalación:
   - En Windows, puede ser necesario habilitar WSL 2.
   - Puede requerirse activar la virtualización en la BIOS.
4. Reinicia el equipo si el instalador lo solicita.
5. Verifica que Docker Desktop esté en ejecución.

## 3. Preparación de la aplicación

Antes de levantar los contenedores, es necesario generar el archivo `.jar` del proyecto.

Ejecuta el siguiente comando en la raíz del proyecto:

```bash
mvn clean install -DskipTests
```
Esto generará el archivo:

`target/gestiontickets-0.0.1-SNAPSHOT.jar`

Este archivo es necesario para construir la imagen del backend.

## 4. Construcción y ejecución de contenedores

Ubicado en la raíz del proyecto, ejecuta:

```bash
docker compose up -d --build
```

Este comando realiza lo siguiente:

- Construye la imagen del backend a partir del Dockerfile
- Descarga la imagen de SQL Server si no existe localmente
- Crea y ejecuta los contenedores definidos en docker-compose.yaml
- Ejecuta los contenedores en segundo plano

## 5. Verificación de contenedores

Para verificar que los contenedores están en ejecución, utiliza:

```bash
docker ps
```

Deberías visualizar al menos:

- Un contenedor correspondiente a SQL Server
- Un contenedor correspondiente al backend

También puedes validarlo desde Docker Desktop en la sección de contenedores.

## 6. Notas importantes
Asegúrate de que Docker Desktop esté en ejecución antes de levantar los contenedores.
Si realizas cambios en el Dockerfile o en el docker-compose.yaml, debes reconstruir las imágenes con:

```bash
docker compose up -d --build
```

## 7. Recomendaciones

Se recomienda instalar las siguientes extensiones en Visual Studio Code:

- Docker
- Container Tools

## 8. Orden de ejecución

Para ejecutar correctamente el proyecto, sigue este orden:

- Iniciar Docker Desktop
- Ejecutar el siguiente comando en la raíz del proyecto:

```bash
docker compose up -d
```
La base de datos y la aplicación se iniciarán automáticamente.