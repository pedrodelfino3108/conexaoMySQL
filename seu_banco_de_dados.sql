-- Criação do banco de dados se não existir
CREATE DATABASE IF NOT EXISTS seu_banco_de_dados;

-- Utilizar o banco de dados recém-criado
USE seu_banco_de_dados;

-- Criação da tabela de usuários, se não existir
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

-- Inserção de alguns dados de exemplo na tabela de usuários
INSERT INTO usuarios (username, password) VALUES ('usuario1', 'senha1');
INSERT INTO usuarios (username, password) VALUES ('usuario2', 'senha2');

-- Criação do usuário 'root' com senha adequada
CREATE USER 'root'@'localhost' IDENTIFIED BY '3306';

-- Concessão de permissões específicas para o usuário 'root' no banco de dados 'seu_banco_de_dados'
GRANT ALL PRIVILEGES ON seu_banco_de_dados.* TO 'root'@'localhost';
