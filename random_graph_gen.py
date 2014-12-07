import networkx as nx
g = nx.fast_gnp_random_graph(n=1300, p=0.03)
output = open("output.txt", "w")
for e in g.edges():
    output.write(str(e[0])+" "+str(e[1])+"\n")
output.close()
