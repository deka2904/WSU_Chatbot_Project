#test코드

from gensim.models import Word2Vec

# 모델 로딩
model = Word2Vec.load('C:\\inetpub\\wwwroot\\project\\Re_Qu\\nvmc.model')

print(model.wv.most_similar("수시전형 안내하기", topn=5))
print(model.wv.most_similar("수시 전형일정 및 모집 인원 안내", topn=5))
print(model.wv.most_similar("수시 전형일정 및 모집 인원 안내", topn=5))
print(model.wv.most_similar("학생부 교과전형 안내", topn=5))
print(model.wv.most_similar("학생부 종합전형 안내", topn=5))
print(model.wv.most_similar("전공별 교육 과정", topn=5))
print(model.wv.most_similar("수시 합격자 안내", topn=5))
print(model.wv.most_similar("학교생활 안내", topn=5))
print(model.wv.most_similar("정시전형 안내하기", topn=5))
print(model.wv.most_similar("정시 전형일정 및 모집인원 안내", topn=5))
print(model.wv.most_similar("정시 전형유형별 지원안내", topn=5))
print(model.wv.most_similar("정시 합격자 안내", topn=5))


