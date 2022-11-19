CREATE DATABASE termometro;

CREATE TABLE temperatura (fecha DATE, hora TIME, temperatura FLOAT, PRIMARY KEY(fecha, hora));

CREATE TABLE humedad (fecha DATE, hora TIME, humedad FLOAT, PRIMARY KEY(fecha, hora));