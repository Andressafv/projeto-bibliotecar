INSERT INTO tb_bloqueio (motivo, data_inicio, data_fim) VALUES
('Multa pendente', '2025-01-01', '2025-06-30'),
('Documentacao incompleta', '2025-03-15', '2025-12-31'),
('Atraso recorrente', '2025-05-01', '2025-08-01'),
('Suspensao disciplinar', '2024-11-01', '2024-12-01');

INSERT INTO tb_regra_emprestimo (prazo_dias, multa_por_dia, multa_max, limite_emprestimos, ativa) VALUES
(7, 2.0, 20.0, 3, 1),
(14, 1.5, 30.0, 5, 1),
(21, 3.0, 50.0, 2, 1),
(10, 2.5, 25.0, 4, 0);

INSERT INTO tb_usuario (nome, cpf, matricula, email, senha, tipo_usuario, fk_bloqueio, fk_regra_emprestimo) VALUES
('Ana Silva', '12345678901', '2024001', 'ana@iftm.edu.br', 'senha123', 'ALUNO', NULL, 1),
('Bruno Costa', '23456789012', '2024002', 'bruno@iftm.edu.br', 'senha123', 'ALUNO', 1, 1),
('Carla Mendes', '34567890123', 'PROF001', 'carla@iftm.edu.br', 'senha123', 'PROFESSOR', NULL, 2),
('Diego Alves', '45678901234', 'FUNC001', 'diego@iftm.edu.br', 'senha123', 'FUNCIONARIO', NULL, 2),
('Elena Souza', '56789012345', '2024005', 'elena@iftm.edu.br', 'senha123', 'ALUNO', 3, 3);

INSERT INTO tb_emprestimo (data_emprestimo, data_devolucao_prevista) VALUES
('2025-05-01', '2025-05-08'),
('2025-05-10', '2025-05-17'),
('2025-04-01', '2025-04-08'),
('2025-06-01', '2025-06-15'),
('2025-03-01', '2025-03-10');

INSERT INTO tb_item_emprestimo (data_devolucao_prevista, data_devolucao_real, status, multa_gerada) VALUES
('2025-05-08', '2025-05-10', 'DEVOLVIDO', 4.0),
('2025-05-17', NULL, 'PENDENTE', NULL),
('2025-04-08', '2025-04-15', 'ATRASADO', 14.0),
('2025-06-15', NULL, 'PENDENTE', NULL),
('2025-03-10', '2025-03-12', 'DEVOLVIDO', 4.0);
