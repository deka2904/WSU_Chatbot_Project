import pymysql
from config.DatabaseConfig import *  # DB 접속 정보 불러오기

db = None
try:
    db = pymysql.connect(
        host=DB_HOST,
        user=DB_USER,
        passwd=DB_PASSWORD,
        db=DB_NAME,
        charset='utf8'
    )
    sql = '''
        CREATE TABLE IF NOT EXISTS insertquery (
        intent TEXT NULL,
        query varchar(1000) NOT NULL,
        count INT DEFAULT 1,
        PRIMARY KEY (query))
    ENGINE = InnoDB DEFAULT CHARSET=utf8
    '''

    with db.cursor() as cursor:
        cursor.execute(sql)

except Exception as e:
    print(e)
finally:
    if db is not None:
        db.close()
