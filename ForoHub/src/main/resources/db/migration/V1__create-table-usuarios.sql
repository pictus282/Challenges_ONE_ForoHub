CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    login VARCHAR(255) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    usuario_activo BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);