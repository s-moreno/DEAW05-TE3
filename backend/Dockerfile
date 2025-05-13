# Construcción multi-etapa para compilar y ejecutar una aplicación Java con Maven
# Utiliza una imagen de Eclipse Temurin para Java 21

# Etapa 1: Construcción de la aplicación (builder)

# Versión del JDK para compilar la aplicación Java
FROM eclipse-temurin:21-jdk AS builder

# Establecer el directorio de trabajo en el contenedor
# Este es el directorio donde se ejecutarán los comandos posteriores
WORKDIR /app

# Copia todo el contenido del directorio donde está el Dockerfile (el proyecto)
# al directorio /app del contenedor.
# Incluye el código fuente, el pom.xml y el script mvnw.
COPY . .

# Ejecuta el wrapper de Maven (./mvnw) para compilar y empaquetar el proyecto.
# clean package: limpia compilaciones anteriores y empaqueta la app (.jar).
# -DskipTests: omite los tests para acelerar la construcción.
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Etapa 2: Entorno de ejecución (runtime)

# Utiliza una imagen de Eclipse Temurin para Java 17 JRE (Java Runtime Environment)
FROM eclipse-temurin:21-jre

# Establecer el directorio de trabajo en el contenedor
# Este es el directorio donde se ejecutarán los comandos posteriores
WORKDIR /app

# Copia el archivo .jar generado en la etapa de construcción (builder) 
# (/app/target/*.jar) al contenedor de runtime, renombrándolo a app.jar.
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto 8080 solo dentro del contenedor.
EXPOSE 8080

# Comando para ejecutar la aplicación Java al iniciar el contenedor.
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
