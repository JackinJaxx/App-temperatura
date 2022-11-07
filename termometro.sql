CREATE DATABASE termometro;

CREATE TABLE temperatura (fecha DATE, hora TIME, temperatura INTEGER, PRIMARY KEY(fecha, hora));

CREATE TABLE humedad (fecha DATE, hora DATE, humedad INTEGER, PRIMARY KEY(fecha, hora));