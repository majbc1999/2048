# script for analysing algorithms and drawing graphs
from xml.dom.minicompat import defproperty

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import scipy
import seaborn as sns


def import_file_as_db(file, name_of_alg, depth):
    def split_(string):
        return(string[:-1].split(",") + [name_of_alg, depth])

    with open('data/' + file + '.txt') as f:
        lines = f.readlines()

    lines = [split_(line) for line in lines]

    return(pd.DataFrame(lines, columns=("score", "highest_reached", "algorithm", "depth")))

if __name__ == "__main__":
    

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
    df = pd.concat([espa,rand,s001,s002,s005,s010,s015, s020,s050,s100,s500,sdyn])
    df["score"] = pd.to_numeric(df["score"])
    df["highest_reached"] = pd.to_numeric(df["highest_reached"])
    
    
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    # 1. histogram of average values
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    histdf = df.groupby(by=["algorithm", "depth"], dropna=False).mean().sort_values(by="score")
    
    graph1 = histdf.plot.bar(y="score", rot=90, legend=None, color="navy")
    graph1.set(title="Average score of algorithms", xlabel="algorithm", ylabel="score")
    graph1.set_xticklabels(["Random", "Empty Spaces", "Simulator k=1", "Simulator k=2", "Simulator k=5", "Simulator k=10", "Simulator k=15", "Simulator k=20", "Simulator k=50", "Simulator k=100", "Dynamic simulator", "Simulator k=500"])
    plt.savefig('plots/graph1.png', bbox_inches = 'tight')
    
    graph2 = histdf.plot.bar(y="highest_reached", rot=90, color="orange", legend=None)
    graph2.set(title="Average highest number reached of algorithms", xlabel="algorithm", ylabel="highest number")
    graph2.set_xticklabels(["Random", "Empty Spaces", "Simulator k=1", "Simulator k=2", "Simulator k=5", "Simulator k=10", "Simulator k=15", "Simulator k=20", "Simulator k=50", "Simulator k=100", "Dynamic simulator", "Simulator k=500"])
    plt.savefig('plots/graph2.png', bbox_inches = 'tight')
    
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    # 2. distributions for simulator algorithms of different depths
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
    
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    # 3. dynamic simulator graph
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    graph9 = plt.figure()
    plt.hlines("50",0,6000, colors="navy", linewidth=3)
    plt.hlines("100",6000,7500, colors="navy", linewidth=3)
    plt.hlines("50",7500,13500, colors="navy", linewidth=3)
    plt.hlines("500",13500,14800, colors="navy", linewidth=3)
    plt.hlines("50",14800,15000, colors="navy", linewidth=3)
    plt.hlines("500",15000,16500, colors="navy", linewidth=3)
    plt.hlines("100",16500,22000, colors="navy", linewidth=3)
    plt.hlines("500",22000,23600, colors="navy", linewidth=3)
    plt.hlines("100",23600,24700, colors="navy", linewidth=3)
    plt.hlines("500",24700,25400, colors="navy", linewidth=3)
    plt.hlines("100",25400,26000, colors="navy", linewidth=3)
    plt.hlines("500",26000,27600, colors="navy", linewidth=3)
    plt.hlines("100",27600,29500, colors="navy", linewidth=3)
    plt.hlines("500",29500,30700, colors="navy", linewidth=3)
    plt.hlines("100",30700,32000, colors="navy", linewidth=3)
    plt.hlines("500",32000,32700, colors="navy", linewidth=3)
    plt.hlines("100",32700,33500, colors="navy", linewidth=3)
    plt.hlines("1000",33500,34700, colors="navy", linewidth=3)
    plt.hlines("100",34700,35000, colors="navy", linewidth=3)
    plt.hlines("1000",35000,36500, colors="navy", linewidth=3)
    plt.hlines("100",36500,49000, colors="navy", linewidth=3)
    plt.hlines("500",49000,51600, colors="navy", linewidth=3)
    plt.hlines("100",51600,58000, colors="navy", linewidth=3)
    plt.hlines("500",58000,69000, colors="navy", linewidth=3)
    plt.hlines("1000",69000,72000, colors="navy", linewidth=3)
    plt.hlines("500",72000,76000, colors="navy", linewidth=3)
    plt.hlines("1000",76000,100000, colors="navy", linewidth=3)
    
    plt.ylabel("number of simulations per move")
    plt.xlabel("game score")
    plt.title("Dynamic depths of simulator algorithm")
    plt.savefig('plots/graph9.png', bbox_inches = 'tight')
    
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    # 4. percentage of beating games
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    df_filter = df[df["highest_reached"] >= 2048]
    
    dfper = pd.DataFrame(df_filter.groupby(by=["algorithm", "depth"], dropna=False).count().reset_index())
    
    new_dict1 = {
        "algorithm": "simulator",
        "depth": 1,
        "highest_reached": 0,
        "score": 0
    }
    
    new_dict2 = {
        "algorithm": "simulator",
        "depth": 2,
        "highest_reached": 0,
        "score": 0
    }
    
    dfper = dfper.append(new_dict1, ignore_index=True)
    dfper = dfper.append(new_dict2, ignore_index=True)
    
    dfper = dfper[["depth", "score"]][dfper["depth"] != "dynamic"].sort_values(by="score")
    dfper = dfper.rename(columns={"score": "reached 2048 (percentage)", "depth": "number of simulations per move"})
    
    
    x = dfper["number of simulations per move"].astype(str).astype(float)
    y = dfper["reached 2048 (percentage)"].astype(str).astype(float)
    
    fig, graph10 = plt.subplots()
    graph10.scatter(x, y, color="orange")
    
    # scipy not working as I wished -> no problem
    p = scipy.optimize.curve_fit(lambda t,a,b: a + b * np.log(t),  x,  y)
    plt.plot(x, y, color="orange")
    
    graph10.set(title="Success of Simulator algorithm", xlabel="number of simulations per move", ylabel="reached 2048 (percentage)")
    plt.savefig('plots/graph10.png', bbox_inches = 'tight')
    