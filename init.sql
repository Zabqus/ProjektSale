SELECT 'CREATE DATABASE projektsale_db'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'projektsale_db')\gexec