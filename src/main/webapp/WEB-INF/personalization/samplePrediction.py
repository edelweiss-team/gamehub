import pandas as pd
from sklearn.preprocessing import MultiLabelBinarizer
import regex
import clustering
import os


def formatTagList(tagList: list):
    for i in range(0, len(tagList)):
        tagList[i] = regex.sub("Tag_", "", tagList[i])


def predict() -> list:
    print(os.listdir())
    #leggiamo il campione dal csv e lo formattiamo con la one-hot encode
    predictValue = format()
    predictValue.drop(['Username', "Id"], axis=1, inplace=True)

    #accediamo alla coppia (clusterizzatore, listaDeiTagMiglioreDeiCluster) serializzata
    clusterer, mostCommonTagList = clustering.loadClusterer()

    #prevediamo il valore del cluster del campione
    clusterNum = clusterer.predict(predictValue)[0] #cluster più vicino al campione

    tagList = mostCommonTagList[clusterNum] #accediamo alla lista di tag del cluster
    formatTagList(tagList) #formattiamo correttamente i tag

    return tagList

def checkRetraining():
    print(clustering.hasToRetrain())
    if(clustering.hasToRetrain()):
        #leggiamo il dataset con le nuove features
        df = pd.read_csv(clustering.DATASET_TEMP_FILENAME)

        # Rimozione di eventuali colonne "Unnamed"
        df = df.filter(regex="^(?!Unnamed)", axis=1)
        df.drop(columns=['Vote'], inplace=True, axis=1)

        #sovrascriviamo il dataset vecchio con quello con le nuove features
        df.to_csv(clustering.DATASET_FILENAME)

        #leggiamo il dataset dei voti
        format(votes=True)

        #leggiamo il dataset con le nuove features
        df = pd.read_csv(clustering.DATASET_TEMP_FILENAME)
        df = df.filter(regex="^(?!Unnamed)", axis=1)
        df.drop(columns=['Vote'], inplace=True, axis=1)

        # Rimozione di eventuali colonne "Unnamed"
        df = df.filter(regex="^(?!Unnamed)", axis=1)

        print(df)

        #sovrascriviamo il dataset vecchio con quello con le nuove features
        df.to_csv(clustering.DATASET_FILENAME)

        #alleniamo il clusterizzatore e serializziamolo salvandolo
        clustererTrained, mostCommonTagsList = clustering.trainClusterer(clustering.NUM_CLUSTERS)
        clustering.saveClusterer(clustererTrained, mostCommonTagsList)

def registerVote(vote: bool) -> bool:
    try:
        # Leggiamo i dati del votante dal buffer, ed uniamoli ad i dati presenti nel dataset di voti
        dataFrame = pd.read_csv(clustering.BUFFER_FILENAME)
        dataFrame['Vote'] = vote
        dataFrameVotes = pd.read_csv(clustering.VOTES_FILENAME)
        username = dataFrame.iloc[0].Username
        flag = False

        # Se c'è già il voto di un utente con lo stesso username, aggiorniamo il voto
        for i in range(len(dataFrameVotes['Username'])):
            if(dataFrameVotes['Username'][i] == username):
                dataFrameVotes['Vote'][i] = vote
                flag = True
        print(dataFrameVotes)

        # Salviamo i risultati in un csv
        if(not flag):
            dataFrame.to_csv(clustering.VOTES_FILENAME, index=False, mode='a', header=False)
        else:
            dataFrameVotes.to_csv(clustering.VOTES_FILENAME, index=False, header=True)

        return True
    except:
        return False


def format(votes=False):
    # Caricamento dataset iniziale e campione da clusterizzare

    if(votes):
        buffer = pd.read_csv(clustering.VOTES_FILENAME)
    else:
        buffer = pd.read_csv(clustering.BUFFER_FILENAME)
    df = pd.read_csv(clustering.DATASET_FILENAME)
    official = df.columns

    # Filtraggio delle colonne sulle quali eseguire la one hot encode
    categories = buffer['Categories']
    genres = buffer['Tags']
    developers = buffer['Developers']
    publishers = buffer['Publishers']
    countries = buffer['Country']

    # Esecuzione della one hot encode
    one_hot = MultiLabelBinarizer()

    resultCategories = pd.DataFrame(one_hot.fit_transform(categories.dropna().str.split(',')),
                                    columns=one_hot.classes_, index=buffer.index)
    resultCategories = resultCategories.add_prefix('Ctg_')

    resultGenres = pd.DataFrame(one_hot.fit_transform(genres.dropna().str.split(',')),
                                columns=one_hot.classes_, index=buffer.index)
    resultGenres = resultGenres.add_prefix('Tag_')

    resultDevelopers = pd.DataFrame(one_hot.fit_transform(developers.dropna().str.split(',')),
                                    columns=one_hot.classes_, index=buffer.index)
    resultDevelopers = resultDevelopers.add_prefix('Dvp_')

    resultPublishers = pd.DataFrame(one_hot.fit_transform(publishers.dropna().str.split(',')),
                                    columns=one_hot.classes_, index=buffer.index)
    resultPublishers = resultPublishers.add_prefix('Pbl_')

    resultCountries = pd.DataFrame(one_hot.fit_transform(countries.dropna().str.split(' ')),
                                   columns=one_hot.classes_, index=buffer.index)

    # Merge dei risultati ottenuti in un unico dataframe e rimozione delle colonne inutilizzate
    result = pd.merge(buffer, resultCategories, on=buffer.index)
    result.drop(columns=['key_0', 'Categories'], axis=1, inplace=True)

    result = pd.merge(result, resultGenres, on=buffer.index)
    result.drop(columns=['key_0', 'Tags'], axis=1, inplace=True)

    result = pd.merge(result, resultDevelopers, on=buffer.index)
    result.drop(columns=['key_0', 'Developers'], axis=1, inplace=True)

    result = pd.merge(result, resultPublishers, on=buffer.index)
    result.drop(columns=['key_0', 'Publishers'], axis=1, inplace=True)

    result = pd.merge(result, resultCountries, on=buffer.index)
    result.drop(columns=['key_0', 'Country'], axis=1, inplace=True)

    # Aggiunta delle colonne non presenti nel campione, ma nel dataset
    # Mappa per tenere traccia di eventuali nuove colonne presenti all'interno del
    #  campione, ma non nel dataset
    map = {}
    oldColumns = result.columns
    for header in official:
        if header not in oldColumns:
            result[header] = 0
        else:
            map[header] = True

    # Salviamo il risultato con le nuove colonne, per salvarle nel dataset(in caso di voto)
    if(votes):
        resultWithOldColumns = result.drop(axis=1, columns=['Vote'])

    # Aggiunta di eventuali nuove colonne al dataframe e rimozione
    # di esse dal campione per permettere la clusterizzazione
    for c in oldColumns:
        if c not in map:
            result = result.drop(c, axis=1)
            df[c] = 0

    # Rimozione di eventuali colonne "Unnamed"
    df = df.filter(regex="^(?!Unnamed)", axis=1)

    if(votes):
        # Aggiungiamo i voti al dataset aggiornato
        df = pd.concat([df, resultWithOldColumns])

    # Salvataggio del dataset aggiornato in un file temporaneo
    df.to_csv(clustering.DATASET_TEMP_FILENAME)
    return result