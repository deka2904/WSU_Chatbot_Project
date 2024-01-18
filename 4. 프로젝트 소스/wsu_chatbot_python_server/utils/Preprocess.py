# 전처리과정 문장을 토크나이징 작업으로 의미있는정보만(토큰화)
# KoNLPy 설치 할때 자바 설치후 자바에 버전에맞는 jpype 후 설치
from konlpy.tag import Komoran
import pickle


class Preprocess:
    def __init__(self, word2index_dic='', userdic=None):  # 생성자임
        if word2index_dic != '':
            f = open(word2index_dic, "rb")
            self.word_index = pickle.load(f)
            f.close()
        else:
            self.word_index = None

        self.komoran = Komoran(userdic=userdic)

        self.exclusion_tags = [
            'JKS', 'JKC', 'JKO', 'JKB', 'JKV', 'JKQ',
            'JX', 'JC',
            'SF', 'SP', 'SS', 'SE', 'SO',
            'EP', 'EF', 'EC', 'ETN', 'ETM',
            'XSN', 'XSV', 'XSA'
        ]

    def pos(self, sentence):
        return self.komoran.pos(sentence)

    def get_keywords(self, pos, without_tag=False):
        f = lambda x: x in self.exclusion_tags
        word_list = []
        for p in pos:
            if f(p[1]) is False:
                word_list.append(
                    p if without_tag is False

                    else p[0])  # pos는 튜플형식으로 [('문장','품사기호')]으로 만들어준다(자체 함수 형태분쇄소 이다)
                # f(p[1])로  두번째 인덱스 품사기호를 exclusion에 있는지를 람다로 확인후
                # 람다로 예외처리한 품사가 있으면 그냥 넘어가고(is true) 예외처리한 품사가 아닐시  is flase 임으로 if문 안으로 들어가 word리스트에
                # append 추가한다 만약 without_tag가 is false 일시 그냥 단어와 품사기호를 둘다 넣어주지만 is false 일시 p[0]만 즉 단어만 리스트에 추가한다

        return word_list


    def get_wordidx_sequence(self, keywords):
        if self.word_index is None:
            return []
        w2i = []
        for word in keywords:
            try:
                w2i.append(self.word_index[word.lower()])
            except KeyError:
                w2i.append(self.word_index['OOV'])  # 없는 단어 OOV out of value
        return w2i
# 추후에 단어 시퀀스를 의도분류 모델학습용 함수
