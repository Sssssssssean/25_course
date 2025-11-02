-- 创建数据库
CREATE DATABASE IF NOT EXISTS coursedesign;
USE coursedesign;

-- 创建 pointobject 表
CREATE TABLE pointobject (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  growScore INT DEFAULT 0,
  exchangeScore INT DEFAULT 0,
  scoreTotal INT DEFAULT 0,
  lastLoginTime VARCHAR(20),
  lastComplicationTime VARCHAR(20),
  lastYdqnTime VARCHAR(20),
  profileInputed BOOLEAN DEFAULT FALSE,
  bloodSugarCount INT DEFAULT 0
);

-- 创建 score_record 表
CREATE TABLE score_record (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  grow_score INT DEFAULT 0,
  exchange_score INT DEFAULT 0,
  add_time VARCHAR(20) NOT NULL,
  p_id INT NOT NULL,
  CONSTRAINT fk_score_record_pointobject 
      FOREIGN KEY (p_id) REFERENCES pointobject(id) ON DELETE CASCADE
);
