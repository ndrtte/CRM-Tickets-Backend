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

### 3. Dependiendo de donde quieran ejecutarlo, hacer lo siguiente:

#### 3.1 Si el backend se ejecuta directamente en la máquina local, se deben utilizar valores similares a los siguientes:
```
DB_URL=jdbc:sqlserver://localhost:1433;database=NOMBRE_DB;encrypt=true;trustServerCertificate=true
DB_USER=Usuario123
DB_PASSWORD=ContraseñaSegura1234.
JWT_SECRET = EjemploDeSecret1234+
ALLOWED_ORIGINS=http://localhost:5173,https://tu.app.com
```
#### 3.2 Para trabajar con el docker, utilizar:
```
DB_URL=jdbc:sqlserver://sqlserver:1433;database=NOMBRE_DB;encrypt=true;trustServerCertificate=true
DB_PORT=1434
DB_USER=Usuario123
DB_PASSWORD=ContraseñaSegura1234.
JWT_SECRET = EjemploDeSecret1234+
ALLOWED_ORIGINS=http://localhost:5173
```

### Notas importantes

- El puerto `1433` corresponde al puerto interno estándar de SQL Server.
- Cuando la aplicación se ejecuta dentro de Docker, no se debe usar `localhost` como host de la base de datos; en su lugar se debe utilizar el nombre del servicio definido en docker-compose (`sqlserver`).
- Cuando la aplicación se ejecuta local, no se debe usar el nombre del servicio (`sqlserver`), sino `localhost`.
- Asegúrate de que las credenciales y el nombre de la base de datos sean correctos antes de ejecutar el proyecto.
- Reemplaza `NOMBRE_DB` por el nombre real de la base de datos que se utilizará en tu máquina.

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
```

Después de esto, estos archivos deberían mostrarse en un tono más gris en el panel de control de cambios, indicando que Git ya no los incluirá en los commits.

### 6. Ejecutar la aplicación

Una vez completados todos los pasos:

1. Verifica que las credenciales sean correctas.
2. Ejecuta la aplicación desde la configuración creada en Visual Studio Code.

Si todo está correctamente configurado, la aplicación debería iniciar sin inconvenientes utilizando las variables de entorno definidas.

# Configuración con Docker: Backend y SQL Server

Este apartado describe el proceso necesario para construir y ejecutar el backend desarrollado con Spring Boot junto con una instancia de SQL Server utilizando Docker.

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

## 3. Construcción y ejecución de contenedores

Ubicado en la raíz del proyecto, ejecuta:

```bash
docker compose down
```

```bash
docker compose up --build
```

El primer comando detiene y elimina contenedores existentes. El segundo comando construye la imagen del backend a partir del Dockerfile, 
descarga la imagen de SQL Server si no está disponible localmente y levanta los contenedores definidos en el archivo docker-compose.yaml

## 5. Verificación de contenedores

Para verificar que los contenedores están en ejecución, utiliza:

```bash
docker ps
```

Deberías visualizar al menos:

- Un contenedor correspondiente a SQL Server
- Un contenedor correspondiente al backend

También puedes validarlo desde Docker Desktop en la sección de contenedores.

Cuando el contenedor del backend se inicia correctamente, la aplicación debería ejecutarse 
de la misma forma que cuando se corre en un entorno local.

## 6. Notas importantes
Asegúrate de que Docker Desktop esté en ejecución antes de levantar los contenedores.
Si realizas cambios en el Dockerfile o en el docker-compose.yaml, debes reconstruir las imágenes con:

```bash
docker compose up --build
```

## 7. Recomendaciones

Se recomienda instalar las siguientes extensiones en Visual Studio Code:

- Docker
- Container Tools

## 8. Orden de ejecución para usar Docker

Para ejecutar correctamente el proyecto, sigue este orden:

- Iniciar Docker Desktop
- Ejecutar el siguiente comando en la raíz del proyecto:

```bash
docker compose up -d
```
La base de datos y la aplicación se iniciarán automáticamente.
