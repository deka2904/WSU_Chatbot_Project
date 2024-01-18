import re

import pandas as pd
from konlpy.tag import Komoran


def read_text_data(filename):
    with open(filename, 'r', encoding='utf-8') as f:
        data = [line for line in f.read().splitlines()]
        # data = [line for line in f.readlines()]
        # data = data[1:] #헤더 제거 헤더가 없다
    return data

corpus_data = read_text_data('1H_intent.txt') #본인 데이터셋
file = open("C:\\inetpub\\wwwroot\\project\\models\\intent\\1H_corpus.txt", "w")

for cc in corpus_data:
    a = "{}\n".format(cc)
    b = "{} 알려줘\n".format(cc)
    c = "{} 알려주세요\n".format(cc)
    d = "{} 알려줘봐\n".format(cc)
    e = "{} 알려줘요\n".format(cc)
    f = "{}에 대해서 알려줘\n".format(cc)
    g = "{}에 대해서 알려주세요\n".format(cc)
    h = "{}에 관해서 알려줘\n".format(cc)
    i = "{}를 알려줘\n".format(cc)

    file.write(a)
    file.write(b)
    file.write(c)
    file.write(d)
    file.write(e)
    file.write(f)
    file.write(g)
    file.write(h)
    file.write(i)

    print(a, b, c, d, e, f, g, h, i)
    #print(b, c, d, e, f)

file.close()