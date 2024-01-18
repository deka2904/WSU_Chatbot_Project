from gensim.models import Word2Vec
from konlpy.tag import Komoran
import time
from utils.Preprocess import Preprocess


# 네이버 영화 리뷰 데이터 읽어옴
def read_review_data(filename):
    with open(filename, 'r' , encoding='utf-8') as f:
        data = [line.split('\t') for line in f.read().splitlines()]
        data = data[1:] # header 제거
    return data


# 측정 시작
start = time.time()

# 리뷰 파일 읽어오기
print('1) 말뭉치 데이터 읽기 시작')
review_data = read_review_data('C:\\02 Coding\\project\\train_tools\\dict\\corpus-1.txt')
review_data2 = read_review_data('C:\\02 Coding\\project\\train_tools\\dict\\corpus.txt')

# 전처리 객체 생성
p = Preprocess(userdic='C:\\02 Coding\\project\\utils\\user_dic.tsv')
for b in review_data2:
    pos = p.pos(b[1])
    test = p.get_keywords(pos, without_tag=True)
    print(test)

print(len(review_data)) # 리뷰 데이터 전체 개수
print('1) 말뭉치 데이터 읽기 완료: ', time.time() - start)

# 문장단위로 명사만 추출해 학습 입력 데이터로 만듬
print('2) 형태소에서 명사만 추출 시작')
komoran = Komoran()
docs = [komoran.nouns(sentence[1]) for sentence in review_data]
p = Preprocess(userdic='C:\\02 Coding\\project\\utils\\user_dic.tsv')
for b in review_data2:
    pos = p.pos(b[1])
    test = p.get_keywords(pos, without_tag=True)
    docs.append(test)
    print(docs)

print('2) 형태소에서 명사만 추출 완료: ', time.time() - start)

# word2vec 모델 학습
print('3) word2vec 모델 학습 시작')
model = Word2Vec(sentences=docs, vector_size=200,window=4, min_count=1, sg=1)
print('3) word2vec 모델 학습 완료: ', time.time() - start)

# 모델 저장
print('4) 학습된 모델 저장 시작')
model.save('nvmc.model')
print('4) 학습된 모델 저장 완료: ', time.time() - start)

# 학습된 말뭉치 개수, 코퍼스 내 전체 단어 개수
print("corpus_count : ", model.corpus_count)
print("corpus_total_words : ", model.corpus_total_words)