-- ------------------------------------------------------------------
-- Jeu de données de démonstration chargé au démarrage de l'application.
-- Permet de visualiser immédiatement un tableau de bord alimenté.
-- ------------------------------------------------------------------

INSERT INTO users (first_name, last_name, email, age, weight, height) VALUES
  ('Calixte', 'Kenmeugne', 'calixte@example.com', 28, 74.5, 178.0),
  ('Amina', 'Diallo', 'amina.diallo@example.com', 34, 62.0, 165.0);

-- Activités de l'utilisateur 1
INSERT INTO activities (type, duration_minutes, calories_burned, date, user_id) VALUES
  ('Course à pied', 45, 480, DATEADD('DAY', -1, CURRENT_DATE), 1),
  ('Vélo', 60, 550, DATEADD('DAY', -3, CURRENT_DATE), 1),
  ('Natation', 30, 300, DATEADD('DAY', -5, CURRENT_DATE), 1);

-- Activités de l'utilisateur 2
INSERT INTO activities (type, duration_minutes, calories_burned, date, user_id) VALUES
  ('Marche rapide', 40, 220, DATEADD('DAY', -2, CURRENT_DATE), 2),
  ('Yoga', 50, 180, DATEADD('DAY', -4, CURRENT_DATE), 2);

-- Mesures de santé
INSERT INTO measures (weight, systolic, diastolic, heart_rate, date, user_id) VALUES
  (74.5, 120, 78, 62, DATEADD('DAY', -1, CURRENT_DATE), 1),
  (75.0, 124, 80, 65, DATEADD('DAY', -7, CURRENT_DATE), 1),
  (62.0, 118, 76, 70, DATEADD('DAY', -2, CURRENT_DATE), 2);

-- Traitements
INSERT INTO medications (name, dosage, frequency, start_date, end_date, user_id) VALUES
  ('Vitamine D', '1000 UI', '1 fois par jour', DATEADD('DAY', -30, CURRENT_DATE), NULL, 1),
  ('Ibuprofène', '400 mg', '2 fois par jour', DATEADD('DAY', -5, CURRENT_DATE), DATEADD('DAY', 2, CURRENT_DATE), 2);
