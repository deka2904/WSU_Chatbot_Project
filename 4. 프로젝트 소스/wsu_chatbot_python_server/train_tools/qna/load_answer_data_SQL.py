import pymysql
import openpyxl
from config.DatabaseConfig import *  # DB 접속 정보 불러오기


# 기존꺼 클리어
def all_clear_train_data(db):
    sql = '''
        delete from chatbot_train_data
        '''
    with db.cursor() as cursor:
        cursor.execute(sql)

    sql = '''
        ALTER TABLE chatbot_train_data AUTO_INCREMENT=1
    '''
    with db.cursor() as cursor:
        cursor.execute(sql)


# 엑셀 데이터 데이터 베이스에 추가
def insert_data(db, xls_row):
    intent, ner, query, answer, answer_img_url = xls_row

    sql = '''
        INSERT chatbot_train_data(intent,ner,query,answer,answer_image)
        values(
             '%s', '%s', '%s', '%s', '%s'
        )
    ''' % (intent.value, ner.value, query.value, answer.value, answer_img_url.value)
    # 없는것들을 널로
    sql = sql.replace("'None'", "null")

    with db.cursor() as cursor:
        cursor.execute(sql)
        print('{} 저장'.format(query.value))
        db.commit()


train_file = 'answer_dataset.xlsx'
db = None


db = pymysql.connect(
        host=DB_HOST,
        user=DB_USER,
        passwd=DB_PASSWORD,
        db=DB_NAME,
        charset='utf8mb4'
    )

all_clear_train_data(db)
wb = openpyxl.load_workbook(train_file)
sheet = wb['dataset']
for row in sheet.iter_rows(min_row=2):  # 헤더빼고 2행 부터
    insert_data(db, row)

wb.close()

if db is not None:
    db.close()
