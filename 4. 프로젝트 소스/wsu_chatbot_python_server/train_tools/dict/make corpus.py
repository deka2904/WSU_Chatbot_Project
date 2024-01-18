def read_text_data(filename):
    with open(filename, 'r' , encoding='utf-8') as f:
        data = [line for line in f.read().splitlines()]
        # data = [line for line in f.readlines()]
        # data = data[1:] #헤더 제거 헤더가 없다
    return data

corpus_data = read_text_data('dict_corpus.txt') #본인 데이터셋
file = open("corpus1.txt", "w", encoding='utf-8')

for cc in corpus_data:
    a = "0000\t{}\t0\n".format(cc)
    b = "0000\t{} 알려줘\t0\n".format(cc)
    c = "0000\t{} 알려주세요\t0\n".format(cc)
    d = "0000\t{} 알려줘봐 \t0\n".format(cc)
    e = "0000\t{} 알려줘요\t0\n".format(cc)
    f = "0000\t{}에 대해서 알려줘\t0\n".format(cc)
    g = "0000\t{}에 대해서 알려주세요\t0\n".format(cc)
    h = "0000\t{}에 관해서 알려줘\t0\n".format(cc)
    i = "0000\t{}를 알려줘\t0\n".format(cc)
    aa = "0000\t{}을 알려주라고\t0\n".format(cc)
    bb = "0000\t{}을 도\t0\n".format(cc)
    cc = "0000\t{}을 내놔\t0\n".format(cc)

    file.write(a)
    file.write(b)
    file.write(c)
    file.write(d)
    file.write(e)
    file.write(f)
    file.write(g)
    file.write(h)
    file.write(i)
    file.write(aa)
    file.write(cc)
    file.write(bb)

    print(a, b, c, d, e, f, g, h, i, aa, bb, cc)
    # print(a)

file.close()