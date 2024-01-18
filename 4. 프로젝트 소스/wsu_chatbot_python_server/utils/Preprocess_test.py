from Preprocess import Preprocess
sent = '우송대 위치'

# 전처리 객체 생성
p = Preprocess(userdic='./user_dic.tsv')

pos = p.pos(sent)

ret = p.get_keywords(pos, without_tag=False)
print(ret)

ret = p.get_keywords(pos, without_tag=True)
print(ret)
