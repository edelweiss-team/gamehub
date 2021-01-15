import pandas as pd

def predict() -> list:
    return ["Avventura", "Azione", "Plastic"]

def registerVote(vote: bool) -> bool:
    try:
        dataFrame = pd.read_csv("./buffer.csv")
        dataFrame['Vote'] = vote
        dataFrameVotes = pd.read_csv("./votes.csv")
        username = dataFrame.iloc[0].Username
        flag = False
        for i in range(len(dataFrameVotes['Username'])):
            if(dataFrameVotes['Username'][i] == username):
                dataFrameVotes['Vote'][i] = vote
                flag = True
        print(dataFrameVotes)
        if(not flag):
            dataFrame.to_csv("./votes.csv", index=False, mode='a', header=False)
        else:
            dataFrameVotes.to_csv("./votes.csv", index=False, header=True)

        return True
    except:
        return False