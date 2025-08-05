-- Default INSERT Queries

-- m_job_types
INSERT INTO m_job_types (alias, name, description, status, created_at, updated_at)
VALUES ('PRODUCT_DETAIL', 'Product Detail', 'Extract detailed product information from given URL', 1, NOW(6), NOW(6)),
       ('LISTING_PAGE', 'Listing Page', 'Extract product list from a category or search listing page', 1, NOW(6),
        NOW(6)),
       ('KEYWORD_SEARCH', 'Keyword Search', 'Search products using keywords and extract results', 1, NOW(6), NOW(6)),
       ('PRICE_TRACKING', 'Price Tracking', 'Track price over time from given product URL', 1, NOW(6), NOW(6));

-- m_site_types
INSERT INTO m_site_types (alias, name, description, status, created_at, updated_at)
VALUES ('ECOMMERCE', 'E-commerce', 'Sites like Amazon, Flipkart, etc.', 1, NOW(6), NOW(6)),
       ('NEWS', 'News', 'News websites for extracting headlines and articles', 1, NOW(6), NOW(6)),
       ('REAL_ESTATE', 'Real Estate', 'Sites related to real estate listings', 1, NOW(6), NOW(6)),
       ('FINANCE', 'Finance', 'Sites offering financial data and reports', 1, NOW(6), NOW(6));

-- m_status_types
INSERT INTO m_status_types (alias, name, description, status, created_at, updated_at)
VALUES ('PENDING', 'Pending', 'Job created but not yet processed', 1, NOW(6), NOW(6)),
       ('IN_PROGRESS', 'In Progress', 'Job is currently being processed', 1, NOW(6), NOW(6)),
       ('SUCCESS', 'Success', 'Job completed successfully', 1, NOW(6), NOW(6)),
       ('FAILED', 'Failed', 'Job failed due to error during processing', 1, NOW(6), NOW(6)),
       ('CANCELLED', 'Cancelled', 'Job was cancelled before completion', 1, NOW(6), NOW(6));

-- m_user_roles
INSERT INTO m_user_roles (alias, name, description, status, created_at, updated_at)
VALUES ('user', 'USER', 'USER role', 1, NOW(6), NOW(6)),
       ('admin', 'ADMIN', 'ADMIN role', 1, NOW(6), NOW(6)),
       ('moderator', 'MODERATOR', 'MODERATOR role', 1, NOW(6), NOW(6));
