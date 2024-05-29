# TFG_EZYSHOP_BACKEND

## Descripción

Breve descripción del backend de tu proyecto.

## Requisitos previos

- Java 17
- Docker

## Instalación

1. Clona el repositorio del backend.
```bash
git clone git@github.com:Domiidp04/TFG_Ezyshop_Backend.git
```

2. Navega hasta el directorio del proyecto.
```bash
cd TFG_Ezyshop_Backend
```
3. Compila el proyecto con Maven.
```bash
./mvnw clean install
```

# Base de datos

#### Asegúrate de tener Docker instalado y en ejecución.
1. Navega hasta el directorio donde se encuentra el archivo docker-compose.yml.
```bash
cd /TFG_Ezyshop_Backend/src/main/resources/BBDD
```
2. Ejecuta el contenedor Docker.
```bash
docker-compose up
```

## Despliegue
Ejecuta la aplicación Spring Boot.
```bash
./mvnw spring-boot:run
```

## Configuración
Para configurar el backend, puedes modificar el archivo application.properties que se encuentra en el directorio
```bash 
src/main/resources. 
```
Aquí puedes cambiar la configuración de la base de datos, el puerto en el que se ejecuta la aplicación, entre otras cosas.

## Solución de problemas
Si encuentras algún problema durante la instalación o el despliegue de la aplicación, por favor, crea un issue en el repositorio de GitHub con una descripción detallada del problema y los pasos para reproducirlo. Haremos todo lo posible para ayudarte a resolverlo.

