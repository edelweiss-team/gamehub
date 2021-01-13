from sklearn.cluster import KMeans
import pandas as pd
import pickle
from typing import Final

#costants
TAG_AMOUNT: Final = 5
NUM_CLUSTERS: Final = 5
DATASET_FILENAME: Final = 'personalization/datasetOfficial.csv'
DATASET_TEMP_FILENAME: Final = 'personalization/datasetTemp.csv'
BUFFER_FILENAME: Final = "personalization/buffer.csv"
VOTES_FILENAME: Final = "personalization/votes.csv"
SERIALIZED_CLUSTER_FILENAME: Final = "personalization/serializedClusterer"


def saveClusterer(clustererTrained: KMeans, mostCommonTagsList: list):
	couple = (clustererTrained, mostCommonTagsList)
	pickle.dump(couple, open(SERIALIZED_CLUSTER_FILENAME, 'wb'))


def loadClusterer() -> (KMeans, list):
	couple = pickle.load(open(SERIALIZED_CLUSTER_FILENAME, 'rb'))
	clusterer = couple[0]
	mostCommonTagsList = couple[1]
	return clusterer, mostCommonTagsList


def findMostCommonTagsList(df: pd.DataFrame, clustererTrained: KMeans, amountTags: int, n_clusters: int) -> list:
	df['cluster'] = clustererTrained.labels_ #prendiamo le label
	listMaps = [] #costruiamo una mappa che contenga ogni tag con la sua frequenza nel cluster

	for i in range (0, n_clusters):
		tagsHead = df.filter(regex="((^Tag_.*$)|(usesMultiplayer))", axis=1).columns
		listMaps.append({})
		filteredDf = df.filter(regex="((^Tag_.*$)|(usesMultiplayer)|(cluster))", axis=1)
		for tag in tagsHead:
			#tagColumn = filteredDf.filter(like=tag, axis=1)
			buffer = filteredDf[filteredDf[tag]==1]
			listMaps[i][tag] = len(buffer[buffer['cluster']==i])

	#estraiamo i migliori amountTags da ogni cluster
	bestLists = [[] for l in range(0, n_clusters)] #lista di best vuoti
	for i in range(0, n_clusters):
		for j in range(0, amountTags):
			#chiave con il massimo valore, ripetuto per amountTags volte
			maxKey = max(listMaps[i].keys(), key=(lambda k: listMaps[i][k]))
			listMaps[i].pop(maxKey) #rimuoviamo il massimo
			bestLists[i].append(maxKey) #aggiungiamo la sua chiave alla lista dei migliori tag per il cluster specificato
	return bestLists


def trainClusterer(num_clusters: int) -> (KMeans, list):
	df = pd.read_csv(DATASET_FILENAME)
	X = df.drop(['Id', 'Username'], axis=1)
	km = KMeans(n_clusters=num_clusters, max_iter=1000).fit(X)
	mostCommonTagsList = findMostCommonTagsList(df, km, TAG_AMOUNT, num_clusters)
	return km, mostCommonTagsList


def main():
	clusterer, mostCommonTagsList = trainClusterer(NUM_CLUSTERS)
	saveClusterer(clusterer, mostCommonTagsList)


if __name__ == '__main__':
	main()
	'''
	df = pd.read_csv('datasetOfficial.csv')
	X = df.drop(['Id', 'Username'], axis=1)
	predictValue = pd.read_csv("buffer.csv")
	predictValue = predictValue.drop(['Id', 'Username'], axis=1)
	km = KMeans(n_clusters=5, max_iter=1000).fit(X)
	cluster = km.predict(predictValue)
	print(cluster)
	print(km.predict(predictValue))
	pickle.dump(km, open("serializedCluster", 'wb'))
	loaded = pickle.load(open("serializedCluster", 'rb'))
	print(loaded.predict(predictValue))
	cluster_map = pd.DataFrame()
	cluster_map['data_index'] = X.index.values
	cluster_map['cluster'] = km.labels_
	df['cluster'] = km.labels_
	listMaps = []
	for i in range (0, 5):
		tagsHead = df.filter(regex="((^Tag_.*$)|(usesMultiplayer))", axis=1).columns
		tagsMap = {}
		filteredDf = df.filter(regex="((^Tag_.*$)|(usesMultiplayer)|(cluster))", axis=1)
		for tag in tagsHead:
			tagColumn = filteredDf.filter(like=tag, axis=1)
			buffer = filteredDf[filteredDf[tag]==1]
			tagsMap[tag] = len(buffer[buffer['cluster']==i])
		listMaps.append(tagsMap)
	print(listMaps)
	
	#estraiamo i migliori 5 da ogni cluster
	bestLists = [[] in range(0, 5)] #lista di best vuoti
	for i in range(0, 5):
		for j in range(0, 5):
			maxKey = max(listMaps[i], key=lambda key: stats[key]) #chiave con il massimo valore, ripetuto per 5 volte
			listMaps[i].pop(maxKey) #rimuoviamo il massimo
			bestList[i].append(maxKey) #aggiungiamo la sua chiave alla lista dei migliori tag per il cluster specificato
	
	
	
	
	
	couple = (km, listMaps)
	pickle.dump(couple, open("serializedCouple", 'wb'))
	km2 = pickle.load(open("serializedCouple", 'rb'))[0]
	print(km2.predict(predictValue))
	
	
	valueToPredict = pd.read_csv("buffer.csv")
	valueToPredict = valueToPredict.drop(['Id', 'Username', 'Tag_Action', 'Ctg_demo', 'usesMultiplayer'], axis=1)
	print(valueToPredict)
	cluster = loaded.predict(valueToPredict)
	print(cluster)
	
	
	valueToPredict = pd.read_csv("buffer.csv")
	valueToPredict = valueToPredict.drop(['Id', 'Username', 'Tag_Action', 'Ctg_demo', 'usesMultiplayer'], axis=1)
	print(valueToPredict)
	cluster = loaded.predict(valueToPredict)
	print(cluster)
	'''