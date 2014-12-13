import networkx as nx
n=10000; p=0.003
g = nx.fast_gnp_random_graph(n, p)
output = open("random_graph_"+str(n)+"_"+str(p)+".txt", "w")
for e in g.edges():
    output.write(str(e[0])+" "+str(e[1])+"\n")
output.close()
