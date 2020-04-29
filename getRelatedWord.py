import gensim
import requests
from gensim.models import KeyedVectors

EMBEDDING_FILE = '/root/input/GoogleNews-vectors-negative300.bin.gz'
#!wget -P /root/input/ -c "https://s3.amazonaws.com/dl4j-distribution/GoogleNews-vectors-negative300.bin.gz"

word2vec = KeyedVectors.load_word2vec_format(EMBEDDING_FILE, binary=True)


f = open("inputRelated.txt", "r")
f1 = f.readlines()
englishWord = f1[0].strip()
data = word2vec.most_similar(positive=englishWord, topn=1)
print(str(data[0][0])