-- ================================
-- 1. status_type
-- ================================
CREATE TABLE status_type (
    id TINYINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,       -- e.g., PENDING, RUNNING, FAILED
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ================================
-- 2. job_type
-- ================================
CREATE TABLE job_type (
    id TINYINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,       -- e.g., CRAWLING, EXTRACTION
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ================================
-- 3. site_type
-- ================================
CREATE TABLE site_type (
    id TINYINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,       -- e.g., ECOMMERCE, SOCIAL_MEDIA
    alias VARCHAR(50),
    description TEXT,
    status BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ================================
-- 4. m_sites
-- ================================
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

-- ================================
-- 5. jobs
-- ================================
CREATE TABLE jobs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    url VARCHAR(500) NOT NULL,
    description TEXT,
    job_type_id TINYINT NOT NULL,
    site_id BIGINT NOT NULL,
    status_type_id TINYINT NOT NULL,
    priority TINYINT DEFAULT 5,
    page_source_path VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_job_type FOREIGN KEY (job_type_id) REFERENCES job_type(id),
    CONSTRAINT fk_site_id FOREIGN KEY (site_id) REFERENCES m_sites(id),
    CONSTRAINT fk_status_type FOREIGN KEY (status_type_id) REFERENCES status_type(id)
);

-- ================================
-- 6. job_logs
-- ================================
CREATE TABLE job_logs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    job_id BIGINT NOT NULL,
    status_type_id TINYINT,
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_job_logs_job FOREIGN KEY (job_id) REFERENCES jobs(id),
    CONSTRAINT fk_job_logs_status FOREIGN KEY (status_type_id) REFERENCES status_type(id)
);
