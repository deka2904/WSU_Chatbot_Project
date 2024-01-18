import pymysql
# import sys,os
# sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname("e:\ChatBot\config\DatabaseConfig.py"))))
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
        CREATE TABLE IF NOT EXISTS Questions (
        member_num VARCHAR(1024) NOT NULL,
        question TEXT NOT NULL
    ENGINE = InnoDB DEFAULT CHARSET=utf8
    '''

    with db.cursor() as cursor:
        cursor.execute(sql)

except Exception as e:
    print(e)
finally:
    if db is not None:
        db.close()
