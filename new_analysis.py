import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

from analysis import import_file_as_db

espa = import_file_as_db("emptyspaces", "empty spaces", '')
rand = import_file_as_db("random", "random moves", '')
# s001 = import_file_as_db("simulator1", "simulator", 1)
# s002 = import_file_as_db("simulator2", "simulator", 2)
s005 = import_file_as_db("simulator5", "simulator", 5)
# s010 = import_file_as_db("simulator10", "simulator", 10)
#s015 = import_file_as_db("simulator15", "simulator", 15)
#s020 = import_file_as_db("simulator20", "simulator", 20)
s050 = import_file_as_db("simulator50", "simulator", 50) 
# s100 = import_file_as_db("simulator100", "simulator", 100)
s500 = import_file_as_db("simulator500", "simulator", 500)
sdyn = import_file_as_db("dynamicsimulator", "simulator", "dynamic")
evl1 = import_file_as_db("evaluator1", "evaluator", 1)
# evl2 = import_file_as_db("evaluator2", "evaluator", 2)

df = pd.concat([espa,rand,s005, s050, s500,sdyn, evl1]) # evl1])
df["score"] = pd.to_numeric(df["score"])
df["highest_reached"] = pd.to_numeric(df["highest_reached"])

# for each algorithm and depth, get percentage of games that reached at 
# least 2048

df["won"] = df["highest_reached"] >= 2048

df["depth"] = df["depth"].astype(str)
df["algorithm"] = df["algorithm"] + ' ' + df["depth"]

df2 = df.groupby(by=["algorithm"], dropna=False).mean()

# sort from lowest to highest percentage of games that reached at least 2048
df2 = df2.sort_values(by=["won"])

# plot the percentage of games that reached at least 2048 for each algorithm

df2["won"].plot.bar()
plt.title("Percentage of games that reached at least 2048")
plt.ylabel("Percentage")
plt.xlabel("Algorithm")
plt.savefig('plots/percentage_of_wins.png', bbox_inches='tight')
plt.ylim(0,1)


distribution = df.groupby('algorithm')['highest_reached'].value_counts().unstack()

# order the columns by the highest number reached
distribution = distribution.reindex(sorted(distribution.columns), axis=1)

colors = {
    32: 'black',
    64: 'darkred',
    128: 'firebrick',
    256: 'red',
    512: 'orangered',
    1024: 'coral',
    2048: 'limegreen',
    4096: 'forestgreen',
    8192: 'darkgreen',
}

distribution.plot.bar(stacked=True, color=distribution.columns.map(colors))
plt.xlabel('Algorithm')
plt.ylabel('Distribution of highest number')
plt.title('Distribution of highest number reached by Algorithm')
plt.savefig('plots/distribution_of_highest_reached.png', bbox_inches='tight')
plt.show()


