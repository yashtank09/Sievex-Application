CREATE TABLE `m_site` (
  `site_id` bigint NOT NULL AUTO_INCREMENT,
  `site_category` varchar(255) DEFAULT NULL,
  `site_country` varchar(255) DEFAULT NULL,
  `site_description` varchar(255) DEFAULT NULL,
  `site_domain` varchar(255) DEFAULT NULL,
  `site_name` varchar(255) DEFAULT NULL,
  `site_status` varchar(255) DEFAULT NULL,
  `site_url` varchar(255) DEFAULT NULL,
  `site_type_id` int NOT NULL,
  PRIMARY KEY (`site_id`),
  KEY `FK600qktp2ygx7npap5yja3lfub` (`site_type_id`),
  CONSTRAINT `FK600qktp2ygx7npap5yja3lfub` FOREIGN KEY (`site_type_id`) REFERENCES `m_site_type` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE m_sites (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    domain VARCHAR(100) NOT NULL,            -- e.g., amazon.in
    url VARCHAR(255),
    description TEXT,
    category VARCHAR(100),
    country VARCHAR(50),
    status BOOLEAN DEFAULT TRUE,
    site_type_id TINYINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_site_type FOREIGN KEY (site_type_id) REFERENCES site_type(id)
);