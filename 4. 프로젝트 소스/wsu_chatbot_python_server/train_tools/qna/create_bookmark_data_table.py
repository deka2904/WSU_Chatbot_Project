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
        CREATE TABLE IF NOT EXISTS bookmark (
        id varchar(50) UNSIGNED NOT nullable,
        query TEXT NOT NULL)
    ENGINE = InnoDB DEFAULT CHARSET=utf8
    '''

    with db.cursor() as cursor:
        cursor.execute(sql)

except Exception as e:
    print(e)
finally:
    if db is not None:
        db.close()
