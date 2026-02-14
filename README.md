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

