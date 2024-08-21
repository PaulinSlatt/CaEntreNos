create table relatos(

                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        aluno_id BIGINT,
                        descricao VARCHAR(255),
                        data DATETIME NOT NULL,
                        status VARCHAR(50),
                        tipo ENUM('SEGURANÇA', 'PROBLEMAS_ACADEMICOS', 'ABUSO', 'SUGESTOES') NOT NULL,
                        resposta VARCHAR(255),
                        FOREIGN KEY (aluno_id) REFERENCES alunos(id) ON DELETE SET NULL

);
