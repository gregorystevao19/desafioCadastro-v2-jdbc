CREATE TABLE IF NOT EXISTS pet (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('GATO', 'CACHORRO')),
    sexo VARCHAR(20) NOT NULL CHECK (sexo IN ('MACHO', 'FEMEA')),
    endereco VARCHAR(255),
    idade DOUBLE PRECISION CHECK (idade >= 0),
    peso DOUBLE PRECISION CHECK (peso >= 0),
    raca VARCHAR(50)
);

CREATE TABLE pergunta (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    editavel BOOLEAN NOT NULL DEFAULT FALSE
);