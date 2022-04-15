# script for analysing algorithms and drawing graphs
import matplotlib
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sns


def import_file_as_db(file, name_of_alg, depth):
    def split_(string):
        return(string[:-1].split(",") + [name_of_alg, depth])

    with open('data/' + file + '.txt') as f:
        lines = f.readlines()

    lines = [split_(line) for line in lines]

    return(pd.DataFrame(lines, columns=("score", "highest_reached", "algorithm", "depth")))

espa = import_file_as_db("emptyspaces", "empty spaces", None)
rand = import_file_as_db("random", "random moves", None)
s001 = import_file_as_db("simulator1", "simulator", 1)
s002 = import_file_as_db("simulator2", "simulator", 2)
s005 = import_file_as_db("simulator5", "simulator", 5)
s010 = import_file_as_db("simulator10", "simulator", 10)
s015 = import_file_as_db("simulator15", "simulator", 15)
s020 = import_file_as_db("simulator20", "simulator", 20)
s050 = import_file_as_db("simulator50", "simulator", 50) 
s100 = import_file_as_db("simulator100", "simulator", 100)
s500 = import_file_as_db("simulator500", "simulator", 500)
sdyn = import_file_as_db("dynamicsimulator", "simulator", "dynamic")

# merged dataframe from all data
df = pd.concat([espa,rand,s001,s002,s005,s010,s015,s020,s050,s100,s500,sdyn])
df["score"] = pd.to_numeric(df["score"])
df["highest_reached"] = pd.to_numeric(df["highest_reached"])


# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# 1. histogram povprečnih vrednosti
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

histdf = df.groupby(by=["algorithm", "depth"], dropna=False).mean().sort_values(by="score")
print(histdf)

graph1 = histdf.plot.bar(y="score", rot=90, legend=None, color="navy")
graph1.set(title="Average score of algorithms", xlabel="algorithm", ylabel="score")
graph1.set_xticklabels(["Random", "Empty Spaces", "Simulator k=1", "Simulator k=1", "Simulator k=2", "Simulator k=10", "Simulator k=15", "Simulator k=20", "Simulator k=50", "Simulator k=100", "Dynamic simulator", "Simulator k=500"])
plt.savefig('plots/graph1.png', bbox_inches = 'tight')

graph2 = histdf.plot.bar(y="highest_reached", rot=90, color="orange", legend=None)
graph2.set(title="Average highest number reached of algorithms", xlabel="algorithm", ylabel="highest number")
graph2.set_xticklabels(["Random", "Empty Spaces", "Simulator k=1", "Simulator k=1", "Simulator k=2", "Simulator k=10", "Simulator k=15", "Simulator k=20", "Simulator k=50", "Simulator k=100", "Dynamic simulator", "Simulator k=500"])
plt.savefig('plots/graph2.png', bbox_inches = 'tight')

# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# 2. porazdelitve za posamezne algoritme
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

df01 = df[(df.algorithm == "simulator") & (df.depth == 1)]
graph3 = sns.displot(df01, x="score", bins=30, color="navy")
graph3.set(title="Distribution of Simulator with k=1", xlabel="score", ylabel="distribution")
plt.vlines(np.quantile(df01.score, 0.1), 0, 21, linestyles='dashed', colors='red')
plt.savefig('plots/graph3.png', bbox_inches = 'tight')

df10 = df[(df.algorithm == "simulator") & (df.depth == 10)]
graph4 = sns.displot(df10, x="score", bins=30, color="orange")
graph4.set(title="Distribution of Simulator with k=10", xlabel="score", ylabel="distribution")
plt.vlines(np.quantile(df10.score, 0.1), 0, 21, linestyles='dashed', colors='red')
plt.savefig('plots/graph4.png', bbox_inches = 'tight')

df20 = df[(df.algorithm == "simulator") & (df.depth == 20)]
graph5 = sns.displot(df20, x="score", bins=30, color="navy")
graph5.set(title="Distribution of Simulator with k=20", xlabel="score", ylabel="distribution")
plt.vlines(np.quantile(df20.score, 0.1), 0, 20, linestyles='dashed', colors='red')
plt.savefig('plots/graph5.png', bbox_inches = 'tight')

df100 = df[(df.algorithm == "simulator") & (df.depth == 100)]
graph6 = sns.displot(df100, x="score", bins=30, color="orange")
graph6.set(title="Distribution of Simulator with k=100", xlabel="score", ylabel="distribution")
plt.vlines(np.quantile(df100.score, 0.1), 0, 39, linestyles='dashed', colors='red')
plt.savefig('plots/graph6.png', bbox_inches = 'tight')

df500 = df[(df.algorithm == "simulator") & (df.depth == 500)]
graph7 = sns.displot(df500, x="score", bins=30, color="navy")
graph7.set(title="Distribution of Simulator with k=500", xlabel="score", ylabel="distribution")
plt.vlines(np.quantile(df500.score, 0.1), 0, 35, linestyles='dashed', colors='red')
plt.savefig('plots/graph7.png', bbox_inches = 'tight')

dfdyn = df[(df.algorithm == "simulator") & (df.depth == "dynamic")]
graph8 = sns.displot(dfdyn, x="score", bins=30, color="orange")
graph8.set(title="Distribution of Dynamic simulator", xlabel="score", ylabel="distribution")
plt.vlines(np.quantile(dfdyn.score, 0.1), 0, 35, linestyles='dashed', colors='red')
plt.savefig('plots/graph8.png', bbox_inches = 'tight')
