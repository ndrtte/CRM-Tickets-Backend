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
cd CRM-Tickets-Backend\gestiontickets
```

4. Para abrir el proyecto en Visual Studio Code, ejecutar:

```bash
code .
```

5. El proyecto se abrirá automáticamente en Visual Studio Code.

## Ejecutar el proyecto

El proyecto tendrá la siguiente estructura:

```text
CRM-Tickets-Backend/
└── gestiontickets/
    ├── .mvn/
    ├── .vscode/
    ├── src/
    │   ├── main/
    │   │   ├── java/com/crm/gestiontickets/
    │   │   └── resources/
    │   └── test/
    ├── target/
    ├── .gitattributes
    ├── .gitignore
    ├── HELP.md
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    └── README.md
```

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

DB_HOST=localhost
DB_PORT=1433
DB_NAME=NOMBRE_BASE_DE_DATOS
DB_USER=Usuario123
DB_PASSWORD=ContraseñaSegura1234

**Importante:**  
Verifica previamente que las credenciales, el puerto, el nombre de la base de datos y la configuración de red sean correctos.


### 3. Crear la carpeta `.vscode`

En la raíz del proyecto (al mismo nivel que `src` y `.env`), crea una carpeta llamada:

`.vscode`

Si ya existe, puedes utilizarla sin necesidad de crear una nueva.

### 4. Configurar el archivo `launch.json`

Dentro de la carpeta `.vscode`, crea un archivo llamado:

`launch.json`

Agrega la siguiente configuración:

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

Esta configuración permite que, cada vez que ejecutes la aplicación Spring Boot desde Visual Studio Code, las variables definidas en el archivo `.env` se carguen automáticamente.

### 5. Configurar `.gitignore`

Por razones de seguridad, es fundamental evitar que el archivo `.env` y la carpeta `.vscode` se incluyan en los commits.

Abre el archivo `.gitignore` y agrega lo siguiente:

.vscode/
.env

Después de esto, estos archivos deberían mostrarse en un tono más gris en el panel de control de cambios, indicando que Git ya no los incluirá en los commits.

### 6. Ejecutar la aplicación

Una vez completados todos los pasos:

1. Verifica que las credenciales sean correctas.
2. Ejecuta la aplicación desde la configuración creada en Visual Studio Code.

Si todo está correctamente configurado, la aplicación debería iniciar sin inconvenientes utilizando las variables de entorno definidas.